package me.xiaopan.easy.android.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Point;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

/**
 * 设备工具箱，提供与设备硬件相关的工具方法
 */
public class DeviceUtils {

	/**
	 * 获取屏幕尺寸
	 * @param context
	 * @return 返回一个长度为2的int数组，int[0]是宽；int[1]是高
	 */
	@SuppressWarnings("deprecation")
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public static int[] getScreenSize(Context context){
		WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB_MR2){
			return new int[]{display.getWidth(), display.getHeight()};
		}else{
			Point point = new Point();
			display.getSize(point);
			return new int[]{point.x, point.y};
		}
	}

	/**
	 * 获取设备ID
	 * @param context
	 * @return 设备的唯一ID，通常是GSM手机的IMEI号或CDMA手机的MEID号 
	 */
	public static String getId(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
	}
	
	/**
	 * 获取MAC地址
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context){
		return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
	}

	/**
	 * 获取本机IP地址
	 * @return null：没有网络连接
	 */
	public static String getLocalIpAddress() {
		try {
			NetworkInterface nerworkInterface;
			InetAddress inetAddress;
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				nerworkInterface = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = nerworkInterface.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
			return null;
		} catch (SocketException ex) {
			ex.printStackTrace();
			return null;
		}
	}
}