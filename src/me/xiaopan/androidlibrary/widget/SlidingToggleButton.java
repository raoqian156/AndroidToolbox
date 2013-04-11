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
	private boolean checked;//状态，true：开启；false：关闭
	private int currentLeft;//当前背景图以及滑块图的X坐标
	private int checkedLeft;//当状态为开启时背景图以及滑块图的X坐标
	private int uncheckedLeft;//当状态为关闭时背景图以及滑块图的X坐标
	private int scrollDistanceCount;//滚动距离计数器
	private boolean needHandle;//当在一组时件中发生了滚动操作时，在弹起或者取消的时候就需要根据滚动的距离来切换状态或者回滚
	private boolean down;//是否按下，用来在弹起的时候，恢复背景图以及滑块的状态
	private boolean enabled;//是否可用，表示当前视图的激活状态
	private OnCheckedChanageListener onCheckedChanageListener;//状态改变监听器
	private boolean pendingSetState;//在调用setState()来设置初始状态的时候，如果onLeft字段还没有初始化（在Activity的onCreate()中调用此setState的时候就会出现这种情况），那么就将此字段标记为true，等到在onDraw()方法中初始化onLeft字段是，会检查此字段，如果为true就会再次调用setState()设置初始状态
	private boolean pendingChecked;//记录默认状态值

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
		enabled = isEnabled();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//初始化当开启时的Left值
		if(checkedLeft == 0){
			checkedLeft = -1 * (backgroundNormalBitmap.getWidth() - getWidth());
			if(pendingSetState){
				pendingSetState = false;
				setChecked(pendingChecked, 0);
			}
		}
		
		//创建一个新的全透明图层，大小同当前视图的大小一样
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        //绘制背景层
        canvas.drawBitmap(enabled?backgroundNormalBitmap:backgroundDisableBitmap, currentLeft, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(backgroundMaskBitmap, 0, 0, paint);
        paint.setXfermode(null);
        
        //绘制框架层
        canvas.drawBitmap(frameBitmap, 0, 0, paint);

        //绘制滑块层
        if(enabled){
        	canvas.drawBitmap(down?sliderPressedBitmap:sliderNormalBitmap, currentLeft, 0, paint);
        }else{
        	canvas.drawBitmap(sliderDisableBitmap, currentLeft, 0, paint);
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
	}
	
	@Override
	public void computeScroll() {
		if(scroller.computeScrollOffset()){
			currentLeft = scroller.getCurrX();
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(enabled){
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
						setChecked(scrollDistanceCount > 0, currentLeft, DURATION);
					}else{
						setChecked(isChecked(), currentLeft, DURATION);
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
		toggle();
		return true;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		needHandle = true;
		scrollDistanceCount += distanceX;
		currentLeft -= distanceX;
		if(currentLeft >= uncheckedLeft){
			currentLeft = uncheckedLeft;
		}else if(currentLeft <= checkedLeft){
			currentLeft = checkedLeft;
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
		setChecked(e2.getX() < e1.getX(), currentLeft, DURATION);
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
   
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.enabled = enabled;
	}

	public boolean isChecked() {
		return checked;
	}
	
	/**
	 * 滚动
	 * @param startX 开始X坐标
	 * @param endX 结束Y坐标
	 * @param duration 持续时间
	 */
	private void scroll(int startX, int endX, int duration){
		if(startX != endX){
			scroller.startScroll(startX, 0, endX - startX, 0, duration);
			invalidate();
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 * @param startX 开始滚动的位置
	 * @param duration 持续时间
	 */
	private void setChecked(boolean on, int startX, int duration){
		this.checked = on;
		//如果是要开启
		if(isChecked()){
			scroll(startX, checkedLeft, duration);
		}else{
			scroll(startX, uncheckedLeft, duration);
		}
		if(onCheckedChanageListener != null){
			onCheckedChanageListener.onCheckedChanage(this, isChecked());
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 * @param duration 持续时间
	 */
	public void setChecked(boolean on, int duration){
		if(checkedLeft == 0){
			pendingSetState = true;
			pendingChecked = on;
		}else{
			setChecked(on, on?uncheckedLeft:checkedLeft, duration);
		}
	}
	
	/**
	 * 设置状态
	 * @param on 开启还是关闭
	 */
	public void setChecked(boolean on){
		setChecked(on, DURATION);
	}
	
	/**
	 * 切换状态
	 * @param duration 持续时间
	 */
	public void toggle(int duration){
		setChecked(!isChecked(), duration);
	}
	
	/**
	 * 切换状态
	 */
	public void toggle(){
		setChecked(!isChecked());
	}
	
	public void setOnCheckedChanageListener(OnCheckedChanageListener onCheckedChanageListener) {
		this.onCheckedChanageListener = onCheckedChanageListener;
	}

	public interface OnCheckedChanageListener{
		public void onCheckedChanage(SlidingToggleButton slidingToggleButton, boolean isChecked);
	}
}