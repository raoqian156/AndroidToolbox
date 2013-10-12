package me.xiaopan.easy.android.util;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * 设备工具箱，提供与设备硬件相关的工具方法
 */
public class DeviceUtils {

	/**
	 * 获取当前屏幕的尺寸
	 * @param context
	 * @return
	 */
	public static int[] getScreenSize(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		return new int[]{display.getWidth(), display.getHeight()};
	}

	/**
	 * 获取设备ID
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
}