package me.xiaopan.easy.android.util;

import java.io.DataOutputStream;
import java.io.IOException;

import android.os.Build;

/**
 * Android系统工具箱
 */
public class AndroidUtils {
	
	/**
	 * 判断当前应用是否已经获取ROOT权限。
	 * <br>如果当前设备没有ROOT就直接返回false；
	 * <br>如果当前设备已经ROOT，但是当前应用没有获得ROOT权限，系统就会弹出 申请获取ROOT权限提示；
	 * <br>如果当前设备已经ROOT，当前应用已经获得ROOT权限就直接返回true。
	 * @return true：当前设备已经ROOT，当前应用已经获得ROOT权限；<br> false：当前设备没有ROOT或者当前设备已经ROOT，但是当前应用没有获得ROOT权限。
	 */
	public static boolean isRooted(){
    	boolean result = false;
    	try {
			Process process = Runtime.getRuntime().exec("su -");
			DataOutputStream dos = new DataOutputStream(process.getOutputStream());
			dos.writeBytes("ls /data/\n");
			dos.flush();
			dos.writeBytes("exit\n");
			dos.flush();
			try {
				if(process.waitFor() == 0){
					result = true;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			process.destroy();
        } catch (IOException e) {
			e.printStackTrace();
		}
        return result;
    }
	
	/**
	 * 判断当前系统是否是Android4.0
	 * @return 0：是；小于0：小于4.0；大于0：大于4.0
	 */
	public static int isAPI14(){
		return Build.VERSION.SDK_INT - 14;
	}
}