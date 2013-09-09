package me.xiaopan.easy.android.util;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

/**
 * 视图动画工具箱，提供简单的控制视图的动画的工具方法
 */
public class ViewAnimationUtils {

	/* ************************************************************* 视图透明度渐变动画 ******************************************************************** */
	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void invisibleViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
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
	public static void invisibleViewByAlpha(final View view, long durationMillis){
		invisibleViewByAlpha(view, durationMillis, null);
	}

	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void invisibleViewByAlpha(final View view, final AnimationListener animationListener){
		invisibleViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}

	/**
	 * 将给定视图渐渐隐去（view.setVisibility(View.INVISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void invisibleViewByAlpha(final View view){
		invisibleViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}

	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void goneViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
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
	public static void goneViewByAlpha(final View view, long durationMillis){
		goneViewByAlpha(view, durationMillis, null);
	}

	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void goneViewByAlpha(final View view, final AnimationListener animationListener){
		goneViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}

	/**
	 * 将给定视图渐渐隐去最后从界面中移除（view.setVisibility(View.GONE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void goneViewByAlpha(final View view){
		goneViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}

	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)）
	 * @param view 被处理的视图
	 * @param durationMillis 持续时间，毫秒
	 * @param animationListener 动画监听器
	 */
	public static void visibleViewByAlpha(final View view, long durationMillis, final AnimationListener animationListener){
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
	public static void visibleViewByAlpha(final View view, long durationMillis){
		visibleViewByAlpha(view, durationMillis, null);
	}

	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 * @param animationListener 动画监听器
	 */
	public static void visibleViewByAlpha(final View view, final AnimationListener animationListener){
		visibleViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, animationListener);
	}

	/**
	 * 将给定视图渐渐显示出来（view.setVisibility(View.VISIBLE)），默认的持续时间为DEFAULT_ALPHA_ANIMATION_DURATION
	 * @param view 被处理的视图
	 */
	public static void visibleViewByAlpha(final View view){
		visibleViewByAlpha(view, ViewAnimationUtils.DEFAULT_ALPHA_ANIMATION_DURATION, null);
	}
	
	

	
	
	/* ************************************************************* 视图移动动画 ******************************************************************** */
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
	 * 视图摇晃
	 * @param view 要摇动的视图
	 * @param fromXDelta X轴开始坐标
	 * @param toXDelta X轴结束坐标
	 * @param durationMillis 持续时间
	 * @param cycles 重复次数
	 */
	public static void shake(View view, float fromXDelta, float toXDelta, long durationMillis, float cycles){
		translate(view, fromXDelta, toXDelta, 0.0f, 0.0f, durationMillis, cycles);
	}

	/**
	 * 视图摇晃，默认摇晃幅度为10，持续时间1秒，重复7次
	 * @param view
	 */
	public static void shake(View view){
		shake(view, 0.0f, 10.0f, AnimationUtils.DEFAULT_ANIMATION_DURATION, 7);
	}

	/**
	 * 默认透明度动画持续时间
	 */
	public static final long DEFAULT_ALPHA_ANIMATION_DURATION = 500;
}