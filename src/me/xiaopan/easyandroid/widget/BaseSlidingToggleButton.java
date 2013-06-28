/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xiaopan.easyandroid.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
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
public abstract class BaseSlidingToggleButton extends View implements OnGestureListener, OnDoubleTapListener{
	private static final int DURATION = 300;
	private static final int MIN_ROLLING_DISTANCE = 30;//滚动最小生效距离
	private GestureDetector gestureDetector;//手势识别器
	private Scroller scroller;//滚动器
	private Bitmap stateNormalBitmap;//正常状态时的状态图片
	private Bitmap stateDisableBitmap;//禁用状态时的状态图片
	private Bitmap stateMaskBitmap;//状态遮罩图片
	private Bitmap frameBitmap;//框架图片
	private Bitmap sliderNormalBitmap;//正常状态时的滑块图片
	private Bitmap sliderPressedBitmap;//按下状态时的滑块图片
	private Bitmap sliderDisableBitmap;//禁用状态时的滑块图片
	private Bitmap sliderMaskBitmap;//滑块遮罩图片
	private Paint paint;//颜料
	private PorterDuffXfermode porterDuffXfermode;//遮罩类型
	private boolean checked;//状态，true：开启；false：关闭
	private int currentLeft;//当前状态图以及滑块图的X坐标
	private int checkedLeft;//当状态为开启时状态图以及滑块图的X坐标
	private int uncheckedLeft;//当状态为关闭时状态图以及滑块图的X坐标
	private int scrollDistanceCount;//滚动距离计数器
	private boolean needHandle;//当在一组时件中发生了滚动操作时，在弹起或者取消的时候就需要根据滚动的距离来切换状态或者回滚
	private boolean down;//是否按下，用来在弹起的时候，恢复状态图以及滑块的状态
	private boolean enabled;//是否可用，表示当前视图的激活状态
	private OnCheckedChanageListener onCheckedChanageListener;//状态改变监听器
	private boolean pendingSetState;//在调用setState()来设置初始状态的时候，如果onLeft字段还没有初始化（在Activity的onCreate()中调用此setState的时候就会出现这种情况），那么就将此字段标记为true，等到在onDraw()方法中初始化onLeft字段时，会检查此字段，如果为true就会再次调用setState()设置初始状态
	private boolean pendingChecked;//记录默认状态值

	public BaseSlidingToggleButton(Context context) {
		super(context);
		init();
	}
	
	public BaseSlidingToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private final void init(){
		gestureDetector = new GestureDetector(getContext(), this);
		gestureDetector.setOnDoubleTapListener(this);
		
		stateNormalBitmap = onGetStateNormalBitmap();
		if(stateNormalBitmap == null){
			throw new RuntimeException("onGetStateNormalBitmap() The return value cannot be null");
		}
		
		stateDisableBitmap = onGetStateDisableBitmap();
		if(stateDisableBitmap == null){
			stateDisableBitmap = stateNormalBitmap;
		}
		
		stateMaskBitmap = onGetStateMaskBitmap();
		if(stateMaskBitmap == null){
			throw new RuntimeException("onGetStateMasklBitmap() The return value cannot be null");
		}
		
		frameBitmap = onGetFrameBitmap();
		if(frameBitmap == null){
			throw new RuntimeException("onGetFrameBitmap() The return value cannot be null");
		}
		
		sliderNormalBitmap = onGetSliderNormalBitmap();
		if(sliderNormalBitmap == null){
			throw new RuntimeException("onGetSliderNormalBitmap() The return value cannot be null");
		}
		
		sliderPressedBitmap = onGetSliderPressedBitmap();
		if(sliderPressedBitmap == null){
			sliderPressedBitmap = sliderNormalBitmap;
		}
		
		
		sliderDisableBitmap = onGetSliderDisableBitmap();
		if(sliderDisableBitmap == null){
			sliderDisableBitmap = sliderNormalBitmap;
		}
		
		sliderMaskBitmap = onGetSliderMaskBitmap();
		if(sliderMaskBitmap == null){
			throw new RuntimeException("onGetSliderMaskBitmap() The return value cannot be null");
		}
		
		paint = new Paint();
		paint.setFilterBitmap(false);
		porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
		scroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
		enabled = isEnabled();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//初始化状态为开启时状态图以及滑块图的X坐标
		if(checkedLeft == 0){
			checkedLeft = -1 * (stateNormalBitmap.getWidth() - frameBitmap.getWidth());//选中时的X坐标就是状态层的宽度减去框架层的宽度的负值
			//如果有需要设置的状态
			if(pendingSetState){
				pendingSetState = false;
				setDefaultChecked(pendingChecked);
			}
		}
		
		//创建一个新的全透明图层，大小同当前视图的大小一样，这一步绝对不可缺少，要不然最周绘制出来的图片背景会是黑色的
        canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.MATRIX_SAVE_FLAG | Canvas.CLIP_SAVE_FLAG | Canvas.HAS_ALPHA_LAYER_SAVE_FLAG | Canvas.FULL_COLOR_LAYER_SAVE_FLAG | Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        //绘制状态层
        canvas.drawBitmap(enabled?stateNormalBitmap:stateDisableBitmap, currentLeft, 0, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(stateMaskBitmap, 0, 0, paint);//使用遮罩模式只显示状态层中和状态遮罩重合的部分
        paint.setXfermode(null);//因为是共用一个Paint，所以要立马清除掉遮罩效果
        
        //绘制框架层
        canvas.drawBitmap(frameBitmap, 0, 0, paint);

        //绘制滑块层
        if(enabled){
        	canvas.drawBitmap(down?sliderPressedBitmap:sliderNormalBitmap, currentLeft, 0, paint);
        }else{
        	canvas.drawBitmap(sliderDisableBitmap, currentLeft, 0, paint);
        }
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(sliderMaskBitmap, 0, 0, paint);//使用遮罩模式只显示滑块层中和滑块遮罩重合的部分
        paint.setXfermode(null);//因为是共用一个Paint，所以要立马清除掉遮罩效果
        
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
		//如果正处于滚动中那么就更改X坐标并刷新
		if(scroller.computeScrollOffset()){
			currentLeft = scroller.getCurrX();
			invalidate();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(enabled){
			//先经过手势识别器的处理
			gestureDetector.onTouchEvent(event);
			
			//如果当前事件是弹起或者取消
			if(event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP){
				//如果之前发生了按下事件，那么此时一定要恢复显示的滑块图片为正常状态时的图片
				if(down){
					down = false;
					invalidate();
				}
				
				//如果本次事件中发生了滑动，那么此时需要判断是否需要切换状态还是需要回滚到原来的位置
				if(needHandle){
					//如果本次滚动的距离超过的最小生效距离，就切换状态，否则就回滚
					if(Math.abs(scrollDistanceCount) >= MIN_ROLLING_DISTANCE){
						setChecked(scrollDistanceCount > 0, currentLeft, DURATION, false, false);
					}else{
						setChecked(isChecked(), currentLeft, DURATION, false, true);
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
		
		//切换滑块图片的状态
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
		needHandle = true;//标记在弹起或取消的时候需要处理
		scrollDistanceCount += distanceX;//记录本次总的滑动的距离
		currentLeft -= distanceX;//计算接下来状态层以及滑块曾的X坐标
		//防止滑动的过程中超过范围
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
		needHandle = false;//标记在弹起或取消时不再处理
		setChecked(e2.getX() < e1.getX(), currentLeft, DURATION, false, false);//根据前后两次X坐标的大小，判断接下来谁要切换为开启状态还是关闭状态
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
		//当开始位置和结束位置一样时不处理
		if(startX != endX){
			scroller.startScroll(startX, 0, endX - startX, 0, duration);
			invalidate();
		}
	}
	
	/**
	 * 设置状态
	 * @param isChecked 开启还是关闭
	 * @param startX 开始滚动的位置
	 * @param duration 持续时间
	 * @param isDefaultValue 是否是默认值
	 * @param isRollback 是否是回滚的
	 */
	private void setChecked(boolean isChecked, int startX, int duration, boolean isDefaultValue, boolean isRollback){
		if(isRollback || this.checked != isChecked){
			this.checked = isChecked;
			//如果是要开启
			if(isChecked()){
				scroll(startX, checkedLeft, isDefaultValue?0:duration);
			}else{
				scroll(startX, uncheckedLeft, isDefaultValue?0:duration);
			}
			if(!isRollback){
				//调用选中状态改变回调
				if(onCheckedChanageListener != null && !isDefaultValue){
					onCheckedChanageListener.onCheckedChanage(this, isChecked());
				}
			}
		}
	}
	
	/**
	 * 设置状态
	 * @param isChecked 开启还是关闭
	 * @param duration 持续时间
	 */
	public void setChecked(boolean isChecked, int duration){
		//如果尚未完成初始化工作，就先延迟，等待初始化完毕之后再处理
		if(checkedLeft == 0){
			pendingSetState = true;
			pendingChecked = isChecked;
		}else{
			setChecked(isChecked, isChecked?uncheckedLeft:checkedLeft, duration, false, false);
		}
	}
	
	/**
	 * 设置状态
	 * @param isChecked 开启还是关闭
	 */
	public void setChecked(boolean isChecked){
		setChecked(isChecked, DURATION);
	}
	
	/**
	 * 设置默认状态，设置默认状态时不会有动画效果，并且不会触发状态改变监听器，适合用于Adapter中
	 * @param isChecked 开启还是关闭
	 */
	public void setDefaultChecked(boolean isChecked){
		//如果尚未完成初始化工作，就先延迟，等待初始化完毕之后再处理
		if(checkedLeft == 0){
			pendingSetState = true;
			pendingChecked = isChecked;
		}else{
			setChecked(isChecked, isChecked?uncheckedLeft:checkedLeft, 0, true, false);
		}
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
	
	public OnCheckedChanageListener getOnCheckedChanageListener() {
		return onCheckedChanageListener;
	}

	/**
	 * 设置选中状态改变监听器
	 * @param onCheckedChanageListener 选中状态改变监听器
	 */
	public void setOnCheckedChanageListener(OnCheckedChanageListener onCheckedChanageListener) {
		this.onCheckedChanageListener = onCheckedChanageListener;
	}

	/**
	 * 选中状态改变监听器
	 */
	public interface OnCheckedChanageListener{
		/**
		 * 当选中状态发生改变
		 * @param slidingToggleButton
		 * @param isChecked 是否选中
		 */
		public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked);
	}
	
	/**
	 * 获取正常状态时的状态图片
	 * @return
	 */
	public abstract Bitmap onGetStateNormalBitmap();
	/**
	 * 获取禁用状态时的状态图片
	 * @return
	 */
	public abstract Bitmap onGetStateDisableBitmap();
	/**
	 * 获取状态遮罩图片
	 * @return
	 */
	public abstract Bitmap onGetStateMaskBitmap();
	/**
	 * 获取框架图片
	 * @return
	 */
	public abstract Bitmap onGetFrameBitmap();
	/**
	 * 获取正常状态时的滑块图片
	 * @return
	 */
	public abstract Bitmap onGetSliderNormalBitmap();
	/**
	 * 获取按下状态时的滑块图片
	 * @return
	 */
	public abstract Bitmap onGetSliderPressedBitmap();
	/**
	 * 获取禁用状态时的滑块图片
	 * @return
	 */
	public abstract Bitmap onGetSliderDisableBitmap();
	/**
	 * 获取滑块遮罩图片
	 * @return
	 */
	public abstract Bitmap onGetSliderMaskBitmap();
}
