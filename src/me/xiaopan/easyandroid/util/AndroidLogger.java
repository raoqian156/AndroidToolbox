package me.xiaopan.easyandroid.util;

import java.io.File;
import java.io.IOException;

import me.xiaopan.easyjava.util.DateTimeUtils;
import me.xiaopan.easyjava.util.FileUtils;
import android.util.Log;

/**
 * <h2>AndroidLog记录器</h2>
 * <br>*特征1：可以方便的控制是否输出Log。由于我们在开发阶段是需要通过输出Log来进行调试的，而我们的应用在发布到市场以后就不需要输出了。
 * 所以你可以通过AndroidLogger.setEnableOutputToConsole()方法或者AndroidLogger.setEnableOutputToLocalFile()方法来控制是否需要将Log输出到控制台或者输出到本地文件。
 * <br>
 * <br>*特征2：可以将选择将Log输出到本地文件。你可以通过AndroidLogger.setOutputFile()方法来设置存储Log的文件
 * <br>
 * <br>*特征3：你还可以通过设置AndroidLogger.setDefaultLogTag()方法来自定义默认的Log tag
 * 
 * @author XIAOPAN
 */
public class AndroidLogger {
	/**
	 * 默认的Log tag
	 */
	private static String defaultLogTag = "LOG";
	/**
	 * 是否将Log输出到控制台
	 */
	private static boolean enableOutputToConsole = true;
	/**
	 * 是否将Log输出到本地文件
	 */
	private static boolean enableOutputToLocalFile = true;
	/**
	 * 存储Log的本地文件
	 */
	private static File outputFile;
	
	public static final boolean v(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.v(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean v(String logContent){
		return v(defaultLogTag, logContent);
	}
	
	public static final boolean d(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.d(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean d(String logContent){
		return d(defaultLogTag, logContent);
	}
	
	public static final boolean i(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.i(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean i(String logContent){
		return i(defaultLogTag, logContent);
	}
	
	public static final boolean w(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.w(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean w(String logContent){
		return w(defaultLogTag, logContent);
	}
	
	public static final boolean e(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.e(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean e(String logContent){
		return e(defaultLogTag, logContent);
	}
	
	public static final boolean wtf(String logTag, String logContent){
		if(enableOutputToConsole){
			Log.wtf(logTag, logContent);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean wtf(String logContent){
		return wtf(defaultLogTag, logContent);
	}
	
	public static final boolean wtf(String logTag, Throwable th){
		if(enableOutputToConsole){
			Log.wtf(logTag, th);
		}
		return outputToFile(logTag+" "+th.getMessage());
	}
	
	public static final boolean wtf(Throwable th){
		return wtf(defaultLogTag, th);
	}
	
	public static final boolean wtf(String logTag, String logContent, Throwable th){
		if(enableOutputToConsole){
			Log.wtf(logTag, logContent, th);
		}
		return outputToFile(logTag+" "+logContent);
	}
	
	public static final boolean outputToFile(String logContent){
		if(enableOutputToLocalFile && outputFile != null){
			if(outputFile.exists()){
				try {
					FileUtils.writeStringByLine(outputFile, DateTimeUtils.getCurrentDateTimeByDefultCustomFormat()+"" +logContent, true);
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}else{
				File parentFile = outputFile.getParentFile();
				//如果父目录不存在就创建，如果创建失败了就直接结束
				if(!parentFile.exists() && !parentFile.mkdirs()){
					return false;
				}
				try {
					if(outputFile.createNewFile()){
						FileUtils.writeStringByLine(outputFile, DateTimeUtils.getCurrentDateTimeByDefultCustomFormat()+"" +logContent, true);
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

	public static String getDefaultLogTag() {
		return defaultLogTag;
	}

	public static boolean isEnableOutputToConsole() {
		return enableOutputToConsole;
	}

	public static boolean isEnableOutputToLocalFile() {
		return enableOutputToLocalFile;
	}

	public static File getOutputFile() {
		return outputFile;
	}

	public static void setDefaultLogTag(String defaultLogTag) {
		AndroidLogger.defaultLogTag = defaultLogTag;
	}

	public static void setEnableOutputToConsole(boolean enableOutputToConsole) {
		AndroidLogger.enableOutputToConsole = enableOutputToConsole;
	}

	public static void setEnableOutputToLocalFile(boolean enableOutputToLocalFile) {
		AndroidLogger.enableOutputToLocalFile = enableOutputToLocalFile;
	}

	public static void setOutputFile(File outputFile) {
		AndroidLogger.outputFile = outputFile;
	}
}