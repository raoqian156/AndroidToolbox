package me.xiaopan.easyandroid.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

/**
 * 应用程序相关的工具方法
 */
public class ApplicationUtils {
	private static final String VERSION_CODE = "VERSION_CODE";
	
	/**
	 * 判断程序版本号是否发生了改变，当返回true时会自动将新的版本号更新到本地
	 * @param context
	 * @return
	 */
	public static boolean isVersionCodeChange(Context context){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); 
		int currentVersionCode = 0;
		try {
			currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		boolean result = currentVersionCode != sharedPreferences.getInt(VERSION_CODE, -Integer.MIN_VALUE);
		if(result){
			Editor editor = sharedPreferences.edit();
			editor.putInt(VERSION_CODE, currentVersionCode);
			editor.commit();
		}
		return result;
	}
}