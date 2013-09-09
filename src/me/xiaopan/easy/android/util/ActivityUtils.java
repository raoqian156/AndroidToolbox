package me.xiaopan.easy.android.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity工具箱
 */
public class ActivityUtils {

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		Intent intent = new Intent(fromActivity, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		fromActivity.startActivity(intent);
		if(inAnimation >0 && outAnimation >0){
			fromActivity.overridePendingTransition(inAnimation, outAnimation);
		}
		if(isClose){
			fromActivity.finish();
		}
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, boolean isClose){
		startActivity(fromActivity, targetActivity, flag, bundle, isClose, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, bundle, false, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, null, isClose, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, bundle, isClose, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, Bundle bundle){
		startActivity(fromActivity, targetActivity, flag, bundle, false, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, boolean isClose){
		startActivity(fromActivity, targetActivity, flag, null, isClose, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, boolean isClose){
		startActivity(fromActivity, targetActivity, -5, bundle, isClose, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, flag, null, false, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, bundle, false, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, null, isClose, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int inAnimation, int outAnimation){
		startActivity(fromActivity, targetActivity, -5, null, false, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, boolean isClose){
		startActivity(fromActivity, targetActivity, -5, null, isClose, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, Bundle bundle){
		startActivity(fromActivity, targetActivity, -5, bundle, false, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity, int flag){
		startActivity(fromActivity, targetActivity, flag, null, false, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 */
	public static void startActivity(Activity fromActivity, Class<?> targetActivity){
		startActivity(fromActivity, targetActivity, -5, null, false, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Context context, Class<?> targetActivity, int flag, Bundle bundle){
		Intent intent = new Intent(context, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		context.startActivity(intent);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivity(Context context, Class<?> targetActivity, Bundle bundle){
		startActivity(context, targetActivity, -5, bundle);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivity(Context context, Class<?> targetActivity, int flag){
		startActivity(context, targetActivity, flag, null);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivity 目标Activity
	 */
	public static void startActivity(Context context, Class<?> targetActivity){
		startActivity(context, targetActivity, -5, null);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		Intent intent = new Intent(fromActivity, targetActivity);
		if(flag != -5){
			intent.setFlags(flag);
		}
		if(bundle != null){
			intent.putExtras(bundle);
		}
		fromActivity.startActivityForResult(intent, requestCode);
		if(inAnimation >0 && outAnimation >0){
			fromActivity.overridePendingTransition(inAnimation, outAnimation);
		}
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, Bundle bundle){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, bundle, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, null, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, bundle, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int flag){
		startActivityForResult(fromActivity, targetActivity, requestCode, flag, null, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, Bundle bundle){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, bundle, -5, -5);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, null, inAnimation, outAnimation);
	}

	/**
	 * 启动Activity
	 * @param fromActivity 来源Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 */
	public static void startActivityForResult(Activity fromActivity, Class<?> targetActivity, int requestCode){
		startActivityForResult(fromActivity, targetActivity, requestCode, -5, null, -5, -5);
	}

}