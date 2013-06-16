/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test;

import java.lang.Thread.UncaughtExceptionHandler;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Looper;
import android.widget.Toast;

/**
 * 应用程序异常处理器
 * @author xiaopan
 *
 */
public class ApplicationExceptionHandler implements UncaughtExceptionHandler {
	private Context context;
	private UncaughtExceptionHandler defaultUncaughtExceptionHandler;
	private static ApplicationExceptionHandler applicationExceptionHandlerInstance;
	
	private ApplicationExceptionHandler(){}
	
	public static final ApplicationExceptionHandler getInstance(){
		if(applicationExceptionHandlerInstance == null){
			applicationExceptionHandlerInstance = new ApplicationExceptionHandler();
		}
		return applicationExceptionHandlerInstance;
	}
	
	/**
	 * 初始化，取得默认的异常处理器并绑定新的异常处理器
	 * @param context 上下文
	 */
	public void init(Context context){
		setContext(context);
		//取出旧的异常处理器
		setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler());
		//注册新的异常处理器
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	/** 
	 * 当应用程序的主线程发生异常时会调用此方法
	 */
	@Override
	public void uncaughtException(final Thread thread, final Throwable ex) {
		ex.printStackTrace();
		//如果上下文是Activity
		if(getContext() instanceof Activity){
			//在新的线程里启动一个Looper用来发送消息
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
					builder.setTitle("应用程序发生异常！");
					builder.setCancelable(false);
					builder.setMessage("是否发送异常信息给我们，帮助我们该善应用的质量，以便为您提供更好的服务？");
					builder.setPositiveButton("发送", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//结束当前进程
							android.os.Process.killProcess(android.os.Process.myPid());
							//退出虚拟机
							System.exit(1);
							
//							defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
						}
					});
					builder.setNegativeButton("不发送", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							//结束当前进程
							android.os.Process.killProcess(android.os.Process.myPid());
							//退出虚拟机
							System.exit(1);
							
//							defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
						}
					});
					builder.create().show();
					Looper.loop();
				}
			}).start();
		}else{
			new Thread(new Runnable() {
				@Override
				public void run() {
					Looper.prepare();
					Toast.makeText(getContext(), "应用程序发生异常，即将退出！", Toast.LENGTH_LONG).show();
					Looper.loop();
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					//结束当前进程
//					Process.killProcess(Process.myPid());
//					//退出虚拟机
//					System.exit(1);

					defaultUncaughtExceptionHandler.uncaughtException(thread, ex);
				}
			}).start();
		}
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public UncaughtExceptionHandler getDefaultUncaughtExceptionHandler() {
		return defaultUncaughtExceptionHandler;
	}

	public void setDefaultUncaughtExceptionHandler(UncaughtExceptionHandler defaultUncaughtExceptionHandler) {
		this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
	}
}
