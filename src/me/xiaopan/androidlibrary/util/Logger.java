package me.xiaopan.androidlibrary.util;

import java.io.File;
import java.io.IOException;

import me.xiaopan.javalibrary.util.FileUtils;

import android.util.Log;

public class Logger {
	private static final String LOG_TAG = "LOG";
	public static final boolean ENABLE = true;
	public static File outFile;
	
	public static final void v(String tag, String logContent){
		if(ENABLE){
			Log.v(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void v(String logContent){
		v(LOG_TAG, logContent);
	}
	
	public static final void d(String tag, String logContent){
		if(ENABLE){
			Log.d(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void d(String logContent){
		d(LOG_TAG, logContent);
	}
	
	public static final void i(String tag, String logContent){
		if(ENABLE){
			Log.i(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void i(String logContent){
		i(LOG_TAG, logContent);
	}
	
	public static final void w(String tag, String logContent){
		if(ENABLE){
			Log.w(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void w(String logContent){
		w(LOG_TAG, logContent);
	}
	
	public static final void e(String tag, String logContent){
		if(ENABLE){
			Log.e(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void e(String logContent){
		e(LOG_TAG, logContent);
	}
	
	public static final void wtf(String tag, String logContent){
		if(ENABLE){
			Log.wtf(tag, logContent);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final void wtf(String logContent){
		wtf(LOG_TAG, logContent);
	}
	
	public static final void wtf(String tag, Throwable th){
		if(ENABLE){
			Log.wtf(tag, th);
		}
	}
	
	public static final void wtf(Throwable th){
		wtf(LOG_TAG, th);
	}
	
	public static final void wtf(String tag, String logContent, Throwable th){
		if(ENABLE){
			Log.wtf(tag, logContent, th);
			outToFile(tag+" "+logContent);
		}
	}
	
	public static final boolean outToFile(String logContent){
		if(ENABLE && outFile != null){
			if(outFile.exists()){
				try {
					FileUtils.writeStringByLine(outFile, logContent, true);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}else{
				File parentFile = outFile.getParentFile();
				//如果父目录不存在就创建，如果创建失败了就直接结束
				if(!parentFile.exists() && !parentFile.mkdirs()){
					return false;
				}
				try {
					if(outFile.createNewFile()){
						FileUtils.writeStringByLine(outFile, logContent, true);
						return true;
					}else{
						return false;
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}else{
			return false;
		}
	}
}