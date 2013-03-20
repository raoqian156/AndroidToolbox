package me.xiaopan.androidlibrary.util;

import android.util.Log;

public class Logger {
	private static final String LOG_TAG = "LOG";
	public static final boolean ENABLE = true;
	
	public static final void v(String tag, String msg){
		if(ENABLE){
			Log.v(tag, msg);
		}
	}
	
	public static final void v(String msg){
		v(LOG_TAG, msg);
	}
	
	public static final void d(String tag, String msg){
		if(ENABLE){
			Log.d(tag, msg);
		}
	}
	
	public static final void d(String msg){
		d(LOG_TAG, msg);
	}
	
	public static final void i(String tag, String msg){
		if(ENABLE){
			Log.i(tag, msg);
		}
	}
	
	public static final void i(String msg){
		i(LOG_TAG, msg);
	}
	
	public static final void w(String tag, String msg){
		if(ENABLE){
			Log.w(tag, msg);
		}
	}
	
	public static final void w(String msg){
		w(LOG_TAG, msg);
	}
	
	public static final void e(String tag, String msg){
		if(ENABLE){
			Log.e(tag, msg);
		}
	}
	
	public static final void e(String msg){
		e(LOG_TAG, msg);
	}
	
	public static final void wtf(String tag, String msg){
		if(ENABLE){
			Log.wtf(tag, msg);
		}
	}
	
	public static final void wtf(String msg){
		wtf(LOG_TAG, msg);
	}
	
	public static final void wtf(String tag, Throwable th){
		if(ENABLE){
			Log.wtf(tag, th);
		}
	}
	
	public static final void wtf(Throwable th){
		wtf(LOG_TAG, th);
	}
	
	public static final void wtf(String tag, String msg, Throwable th){
		if(ENABLE){
			Log.wtf(tag, msg, th);
		}
	}
}