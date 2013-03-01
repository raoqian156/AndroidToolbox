package me.xiaopan.androidlibrary.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

/**
 * 动画工具箱
 */
public class AnimationUtils {
	/**
	 * 默认动画持续时间
	 */
	public static final long DEFAULT_ANIMATION_DURATION = 1000;
	/**
	 * 默认透明度动画持续时间
	 */
	public static final long DEFAULT_ALPHA_ANIMATION_DURATION = 500;
	
	/**
	 * 视图移动
	 * @param view 要移动的视图
	 * @param fromXDelta X轴开始坐标
	 * @param toXDelta X轴结束坐标
	 * @param fromYDelta Y轴开始坐标
	 * @param toYDelta Y轴结束坐标
	 * @param durationMillis 持续时间
	 * @param cycles 重复
	 */
	public static void translate(View view, float fromXDelta, float toXDelta, float fromYDelta, float toYDelta, long durationMillis, float cycles){
		TranslateAnimation translateAnimation = new TranslateAnimation(fromXDelta, toXDelta, fromYDelta, toYDelta);
		translateAnimation.setDuration(durationMillis);
		if(cycles > 0.0){
			translateAnimation.setInterpolator(new CycleInterpolator(cycles));
		}
		view.startAnimation(translateAnimation);
	}

	/**
	 * 视图摇动
	 * @param view 要摇动的视图
	 * @param fromXDelta X轴开始坐标
	 * @param toXDelta X轴结束坐标
	 * @param durationMillis 持续时间
	 * @param cycles 重复
	 */
	public static void shake(View view, float fromXDelta, float toXDelta, long durationMillis, float cycles){
		translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, durationMillis, cycles);
	}
	
	/**
	 * 视图摇动，默认摇动幅度为10，持续时间DEFAULT_ANIMATION_DURATION，重复7次
	 * @param view
	 */
	public static void shake(View view){
		shake(view, 0.0f, 10.0f, DEFAULT_ANIMATION_DURATION, 7);
	}
	
	/**
	 * 获取一个旋转动画
	 * @param fromDegrees 开始角度
	 * @param toDegrees 结束角度
	 * @param pivotXType 旋转中心点X轴坐标相对类型
	 * @param pivotXValue 旋转中心点X轴坐标
	 * @param pivotYType 旋转中心点Y轴坐标相对类型
	 * @param pivotYValue 旋转中心点Y轴坐标
	 * @param durationMillis 持续时间
	 * @param animationListener 动画监听器
	 * @return 一个旋转动画
	 */
	public static RotateAnimation getRotateAnimation(float fromDegrees, float toDegrees, int pivotXType, float pivotXValue, int pivotYType, float pivotYValue, long durationMillis, AnimationListener animationListener){
		RotateAnimation rotateAnimation = new RotateAnimation(fromDegrees, toDegrees, pivotXType, pivotXValue, pivotYType, pivotYValue);
		rotateAnimation.setDuration(durationMillis);
		if(animationListener != null){
			rotateAnimation.setAnimationListener(animationListener);
		}
		return rotateAnimation;
	}
	
	/**
	 * 获取一个根据视图自身中心点旋转的动画
	 * @param durationMillis 动画持续时间
	 * @param animationListener 动画监听器
	 * @return 一个根据中心点旋转的动画
	 */
	public static RotateAnimation getRotateAnimationByCenter(long durationMillis, AnimationListener animationListener){
		return getRotateAnimation(0f, 359f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f, durationMillis, animationListener);
	}
	
	/**
	 * 获取一个根据中心点旋转的动画
	 * @param duration 动画持续时间
	 * @return 一个根据中心点旋转的动画
	 */
	public static RotateAnimation getRotateAnimationByCenter(long duration){
		return getRotateAnimationByCenter(duration, null);
	}
	
	/**
	 * 获取一个根据视图自身中心点旋转的动画
	 * @param durationMillis 动画持续时间
	 * @param animationListener 动画监听器
	 * @return 一个根据中心点旋转的动画
	 */
	public static RotateAnimation getRotateAnimationByCenter( AnimationListener animationListener){
		return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 获取一个根据中心点旋转的动画
	 * @return 一个根据中心点旋转的动画，默认持续时间为DEFAULT_ANIMATION_DURATION
	 */
	public static RotateAnimation getRotateAnimationByCenter(){
		return getRotateAnimationByCenter(DEFAULT_ANIMATION_DURATION, null);
	}
	
	/**
	 * 获取一个透明度渐变动画
	 * @param fromAlpha 开始时的透明度
	 * @param toAlpha 结束时的透明度都
	 * @param durationMillis 持续时间
	 * @param animationListener 动画监听器
	 * @return 一个透明度渐变动画
	 */
	public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis, AnimationListener animationListener){
		AlphaAnimation alphaAnimation = new AlphaAnimation(fromAlpha, toAlpha);
		alphaAnimation.setDuration(durationMillis);
		if(animationListener != null){
			alphaAnimation.setAnimationListener(animationListener);
		}
		return alphaAnimation;
	}
	
	/**
	 * 获取一个透明度渐变动画
	 * @param fromAlpha 开始时的透明度
	 * @param toAlpha 结束时的透明度都
	 * @param durationMillis 持续时间
	 * @return 一个透明度渐变动画
	 */
	public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, long durationMillis){
		return getAlphaAnimation(fromAlpha, toAlpha, durationMillis, null);
	}
	
	/**
	 * 获取一个透明度渐变动画
	 * @param fromAlpha 开始时的透明度
	 * @param toAlpha 结束时的透明度都
	 * @param animationListener 动画监听器
	 * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha, AnimationListener animationListener){
		return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 获取一个透明度渐变动画
	 * @param fromAlpha 开始时的透明度
	 * @param toAlpha 结束时的透明度都
	 * @return 一个透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getAlphaAnimation(float fromAlpha, float toAlpha){
		return getAlphaAnimation(fromAlpha, toAlpha, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 获取一个由完全显示变为不可见的透明度渐变动画
	 * @param durationMillis 持续时间
	 * @param animationListener 动画监听器
	 * @return 一个由完全显示变为不可见的透明度渐变动画
	 */
	public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis, AnimationListener animationListener){
		return getAlphaAnimation(1.0f, 0.0f, durationMillis, animationListener);
	}
	
	/**
	 * 获取一个由完全显示变为不可见的透明度渐变动画
	 * @param durationMillis 持续时间
	 * @return 一个由完全显示变为不可见的透明度渐变动画
	 */
	public static AlphaAnimation getHiddenAlphaAnimation(long durationMillis){
		return getHiddenAlphaAnimation(durationMillis, null);
	}
	
	/**
	 * 获取一个由完全显示变为不可见的透明度渐变动画
	 * @param animationListener 动画监听器
	 * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getHiddenAlphaAnimation(AnimationListener animationListener){
		return getHiddenAlphaAnimation(DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 获取一个由完全显示变为不可见的透明度渐变动画
	 * @return 一个由完全显示变为不可见的透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getHiddenAlphaAnimation(){
		return getHiddenAlphaAnimation(DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 获取一个由不可见变为完全显示的透明度渐变动画
	 * @param durationMillis 持续时间
	 * @param animationListener 动画监听器
	 * @return 一个由不可见变为完全显示的透明度渐变动画
	 */
	public static AlphaAnimation getShowAlphaAnimation(long durationMillis, AnimationListener animationListener){
		return getAlphaAnimation(0.0f, 1.0f, durationMillis, animationListener);
	}
	
	/**
	 * 获取一个由不可见变为完全显示的透明度渐变动画
	 * @param durationMillis 持续时间
	 * @return 一个由不可见变为完全显示的透明度渐变动画
	 */
	public static AlphaAnimation getShowAlphaAnimation(long durationMillis){
		return getAlphaAnimation(0.0f, 1.0f, durationMillis, null);
	}
	
	/**
	 * 获取一个由不可见变为完全显示的透明度渐变动画
	 * @param animationListener 动画监听器
	 * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getShowAlphaAnimation(AnimationListener animationListener){
		return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 获取一个由不可见变为完全显示的透明度渐变动画
	 * @return 一个由不可见变为完全显示的透明度渐变动画，默认持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 */
	public static AlphaAnimation getShowAlphaAnimation(){
		return getAlphaAnimation(0.0f, 1.0f, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void inVisibilityView(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
		hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.INVISIBLE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(hiddenAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void inVisibilityView(final View view, long durationMillis){
		inVisibilityView(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void inVisibilityView(final View view, final AnimationListener animationListener){
		inVisibilityView(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void inVisibilityView(final View view){
		inVisibilityView(view, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void goneView(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation hiddenAlphaAnimation = AnimationUtils.getHiddenAlphaAnimation(durationMillis);
		hiddenAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.GONE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(hiddenAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void goneView(final View view, long durationMillis){
		goneView(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void goneView(final View view, final AnimationListener animationListener){
		goneView(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void goneView(final View view){
		goneView(view, DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void visibleView(final View view, long durationMillis, final AnimationListener animationListener){
		AlphaAnimation showAlphaAnimation = AnimationUtils.getShowAlphaAnimation(durationMillis);
		showAlphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationStart(animation);
				}
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				if(animationListener != null){
					animationListener.onAnimationRepeat(animation);
				}
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				view.setVisibility(View.VISIBLE);
				if(animationListener != null){
					animationListener.onAnimationEnd(animation);
				}
			}
		});
		view.startAnimation(showAlphaAnimation);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 */
	public static void visibleView(final View view, long durationMillis){
		visibleView(view, durationMillis, null);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void visibleView(final View view, final AnimationListener animationListener){
		visibleView(view, DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}
	
	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void visibleView(final View view){
		visibleView(view, DEFAULT_ALPHA_ANIMATION_DURATION);
	}
}