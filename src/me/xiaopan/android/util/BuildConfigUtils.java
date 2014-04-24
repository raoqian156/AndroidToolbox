package me.xiaopan.android.util;

import android.content.Context;

public class BuildConfigUtils {
	
	/**
	 * 是否是开发包
	 * @param context
	 * @return true：开发包；false：正式包
	 */
	public static boolean isDebug(Context context){
		try {
            return (Boolean) Class.forName(context.getPackageName()+RUtils.POINT+"BuildConfig").getDeclaredField("DEBUG").get(null);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
	}
}
