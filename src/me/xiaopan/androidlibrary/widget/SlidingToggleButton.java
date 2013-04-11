package me.xiaopan.androidlibrary.widget;

import me.xiaopan.androidlibrary.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.view.GestureDetector;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Scroller;

/**
 * 滑动开关按钮
 */
@SuppressLint("DrawAllocation")
public class SlidingToggleButton extends View implements OnGestureListener, OnDoubleTapListener{
	private static final int DURATION = 300;
	private static final int MIN_ROLLING_DISTANCE = 30;//滚动最小生效距离
	private GestureDetector gestureDetector;//手势识别器
	private Scroller scroller;//滚动器
	private Bitmap backgroundNormalBitmap;//正常状态时的背景图片
	private Bitmap backgroundDisableBitmap;//禁用状态时的背景图片
	private Bitmap backgroundMaskBitmap;//北京遮罩图片
	private Bitmap frameBitmap;//框架图片
	private Bitmap sliderNormalBitmap;//正常状态时的滑块图片
	private Bitmap sliderPressedBitmap;//按下状态时的滑块图片
	private Bitmap sliderDisableBitmap;//禁用状态时的滑块图片
	private Bitmap sliderMaskBitmap;//滑块遮罩图片
	private Paint paint;//颜料
	private PorterDuffXfermode porterDuffXfermode;//遮罩类型
	private boolean on;//状态，true：开启；false：关闭
	private int left;//背景图以及滑块图的X坐标
	private int onLeft;//当状态为开启时背景图以及滑块图的X坐标
	private int offLeft;//当状态为关闭时背景图以及滑块图的X坐标
	private int scrollDistanceCount;//滚动距离计数器
	private boolean needHandle;//当在一组时件中发生了滚动操作时，在弹起或者取消的时候就需要根据滚动的距离来切换状态或者回滚
	private boolean down;
	private boolean isEnabled;

	public SlidingToggleButton(Context context) {
		super(context);
		gestureDetector = new GestureDetector(getContext(), this);
		gestureDetector.setOnDoubleTapListener(this);
		backgroundNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background_normal);
		backgroundDisableBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background_disable);
		backgroundMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask_background);
		frameBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_frame);
		sliderNormalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_normal);
		sliderPressedBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_pressed);
		sliderDisableBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_disable);
		sliderMaskBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask_slider);
		paint = new Paint();
		paint.setFilterBitmap(false);
		porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		isEnabled = isEnabled();
		
		//创建一个新的全透明图层，大小同当前视图的大小一样
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        //绘制背景层
        canvas.drawBitmap(isEnabled?backgroundNormalBitmap:backgroundDisableBitmap, left, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(backgroundMaskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //绘制框架层
        canvas.drawBitmap(frameBitmap, 0, 0, paint);

        //绘制滑块层
        if(isEnabled){
        	canvas.drawBitmap(down?sliderPressedBitmap:sliderNormalBitmap, left, 0, paint);
        }else{
        	canvas.drawBitmap(sliderDisableBitmap, left, 0, paint);
        }
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(sliderMaskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //合并图层
        canvas.restore();

		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//计算宽度
		int realWidthSize = 0;
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);//解析宽度参考类型
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);//解析宽度尺寸
		switch (widthMode) {
			case MeasureSpec.AT_MOST://如果widthSize是当前视图可使用的最大宽度
				realWidthSize = frameBitmap.getWidth();
				break;
			case MeasureSpec.EXACTLY://如果widthSize是当前视图可使用的绝对宽度
				realWidthSize = widthSize;
				break;
			case MeasureSpec.UNSPECIFIED://如果widthSize对当前视图宽度的计算没有任何参考意义
				realWidthSize = frameBitmap.getWidth();
				break;
		}
		
		//计算高度
		int realHeightSize = 0;
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);//解析参考类型
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);//解析高度尺寸
		switch (heightMode) {
			case MeasureSpec.AT_MOST://如果heightSize是当前视图可使用的最大高度
				realHeightSize = frameBitmap.getHeight();
				break;
			case MeasureSpec.EXACTLY://如果heightSize是当前视图可使用的绝对高度
				realHeightSize = heightSize;
				break;
			case MeasureSpec.UNSPECIFIED://如果heightSize对当前视图高度的计算没有任何参考意义
				realHeightSize = frameBitmap.getHeight();
				break;
		}
		
		setMeasuredDimension(realWidthSize, realHeightSize);
		
		onLeft = -1 * (backgroundNormalBitmap.getWidth() - getWidth());
	}
	
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			left = scroller.getCurrX();
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isEnabled()){
			gestureDetector.onTouchEvent(event);
			//如果允许处理,并且当前事件使弹起或者取消
			if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP){
				if(down){
					down = false;
					invalidate();
				}
				
				if(needHandle){
					//如果本次滚动的距离超过的最小生效距离，就切换状态，否则就回滚
					if(Math.abs(scrollDistanceCount) >= MIN_ROLLING_DISTANCE){
						setState(scrollDistanceCount > 0, left);
					}else{
						setState(isOn(), left);
					}
					needHandle = false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean onDown(MotionEvent e) {
		scrollDistanceCount = 0;
		needHandle = false;
		down = true;
		invalidate();
		return true;
	}

	@Override
	public void onShowPress(MotionEvent e) {
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		switchState();
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		needHandle = true;
		scrollDistanceCount += distanceX;
		left -= distanceX;
		if(left >= offLeft){
			left = offLeft;
		}else if(left <= onLeft){
			left = onLeft;
		}
		invalidate();
		return true;
	}

	@Override
	public void onLongPress(MotionEvent e) {
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		needHandle = false;
		setState(e2.getX() < e1.getX(), left);
		return true;
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return true;
   }

   @Override
   public boolean onDoubleTap(MotionEvent e) {
        return true;
   }

   @Override
   public boolean onDoubleTapEvent(MotionEvent e) {
        return true;
   }

	public boolean isOn() {
		return on;
	}
	
	public void setOn(boolean on) {
		this.on = on;
	}
	
	/**
	 * 滚动
	 * @param startX 开始X坐标
	 * @param endX 结束Y坐标
	 */
	private void scroll(int startX, int endX){
		if(startX != endX){
			scroller.startScroll(startX, 0, endX - startX, 0, DURATION);
			invalidate();
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 * @param startX 开始滚动的位置
	 */
	private void setState(boolean on, int startX){
		setOn(on);
		//如果是要开启
		if(isOn()){
			scroll(startX, onLeft);
		}else{
			scroll(startX, offLeft);
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 */
	public void setState(boolean on){
		setState(on, on?offLeft:onLeft);
	}
	
	/**
	 * 切换状态
	 */
	public void switchState(){
		setState(!isOn());
	}
}