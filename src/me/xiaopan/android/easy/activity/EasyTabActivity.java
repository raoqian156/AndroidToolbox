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
package me.xiaopan.android.easy.activity;

import java.util.Locale;

import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.util.ActivityPool;
import me.xiaopan.android.easy.util.ActivityUtils;
import me.xiaopan.android.easy.util.DoubleClickDetector;
import me.xiaopan.android.easy.util.EasyHandler;
import me.xiaopan.android.easy.util.NetworkUtils;
import me.xiaopan.android.easy.util.ToastUtils;
import roboguice.activity.RoboTabActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public abstract class EasyTabActivity extends RoboTabActivity{
	private static final int DIALOG_MESSAGE = -1212343;	//对话框 - 消息对话框
	private static final int DIALOG_PROGRESS = -1212346;	//对话框 - 进度对话框
	private static final String KEY_DIALOG_MESSAGE = "KEY_DIALOG_MESSAGE";	// 键 - 对话框消息
	private static final String KEY_DIALOG_CONFRIM_BUTTON_NAME = "KEY_DIALOG_CONFRIM_BUTTON_NAME";	//键 - 对话框确认按钮的名字
	private boolean haveDestroy;
	private ActivityPool activityPool;
	private EasyHandler handler;
	private DoubleClickDetector doubleClickExitAcpplicationDetector;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		activityPool = new ActivityPool(this);
		handler = new EasyHandler(){
			@Override
			public void handleMessage(Message msg) {
				onHandleMessage(msg);
			}
		};
		InjectContentView injectContentView = getClass().getAnnotation(InjectContentView.class);
		if(injectContentView != null && injectContentView.value() > 0){
			setContentView(injectContentView.value());
		}
	}
	
	@Override
	public void onBackPressed() {
		if(doubleClickExitAcpplicationDetector != null){
			doubleClickExitAcpplicationDetector.click();
		}else{
			super.onBackPressed();
		}
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		if(!haveDestroy){
			Dialog dialog = null;
			switch(id){
				case DIALOG_MESSAGE : 
					AlertDialog messageDialog = new AlertDialog.Builder(this).create();
					if(args != null){
						messageDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
						messageDialog.setButton(AlertDialog.BUTTON_POSITIVE, args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
					}
					dialog = messageDialog;
					break;
				case DIALOG_PROGRESS : 
					ProgressDialog progressDialog = new ProgressDialog(this);
					progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
					progressDialog.setCancelable(false);
					if(args != null){
						progressDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
					}
					dialog = progressDialog;
					break;
			}
			return dialog;
		}else{
			return null;
		}
	}

	@Override
	protected void onPrepareDialog(int id, Dialog dialog, Bundle args) {
		switch(id){
			case DIALOG_MESSAGE : 
				if(args != null){
					AlertDialog messageDialog = (AlertDialog) dialog;
					messageDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
					messageDialog.setButton(AlertDialog.BUTTON_POSITIVE, args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
				}
				break;
			case DIALOG_PROGRESS : 
				if(args != null){
					((ProgressDialog) dialog).setMessage(args.getString(KEY_DIALOG_MESSAGE));
				}
				break;
		}
	}
	
	@Override
	protected void onDestroy() {
		haveDestroy = true;
		handler.destroy();
		activityPool.removeSelf();
		super.onDestroy();
	}
	
	/**
	 * 当需要处理消息
	 * @param message
	 */
	protected void onHandleMessage(Message message){
		
	}
	
	/**
	 * 终止Activity，所采用的方式就是终止所有的Activity
	 */
	public void finishApplication(){
		activityPool.finishAll();
	}
	
	/**
	 * 去掉标题栏，此方法必须在setContentView()之前调用
	 */
	public void removeTitleBar(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	/**
	 * 隐藏状态栏
	 */
	public void hiddenStatusBar(){
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	
	}
	
	
	/* ********************************************** 网络 ************************************************ */
	/**
	 * 网络是否可用
	 * @return
	 */
	public boolean isNetworkAvailable() {
		return NetworkUtils.isConnectedByState(getBaseContext());
	}

	
	/* ********************************************** Toast ************************************************ */
	/**
	 * 吐出一个显示时间较长的提示
	 * @param view
	 */
	public void toastL(final View view){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), view);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param view
	 */
	public void toastS(final View view){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), view);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param content
	 */
	public void toastL(final String content){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), content);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param content
	 */
	public void toastS(final String content){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), content);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param resId
	 */
	public void toastL(final int resId){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), resId);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param resId
	 */
	public void toastS(final int resId){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), resId);
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param format
	 * @param args
	 */
	public void toastL(final String format, final Object... args){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), String.format(format, args));
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param format
	 * @param args
	 */
	public void toastS(final String format, final Object... args){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), String.format(format, args));
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param formatResId
	 * @param args
	 */
	public void toastL(final int formatResId, final Object... args){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), getString(formatResId, args));
			}
		});
	}
	
	/**
	 * 吐出一个显示时间较短的提示
	 * @param formatResId
	 * @param args
	 */
	public void toastS(final int formatResId, final Object... args){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), getString(formatResId, args));
			}
		});
	}
	
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param flag Intent标记，不需要的话就设为-1
	 * @param bundle 参数集
	 */
	public void startActivity(final Class<? extends Activity> targetActivityClass, final int flag, final Bundle bundle){
		ActivityUtils.start(this, targetActivityClass, flag, bundle);
	}
	
	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param flag Intent标记，不需要的话就设为-1
	 */
	public void startActivity(final Class<? extends Activity> targetActivityClass, final int flag){
		startActivity(targetActivityClass, flag, null);
	}
	
	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param bundle 参数集
	 */
	public void startActivity(final Class<? extends Activity> targetActivityClass, Bundle bundle){
		startActivity(targetActivityClass, -1, bundle);
	}
	
	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 */
	public void startActivity(final Class<? extends Activity> targetActivityClass){
		startActivity(targetActivityClass, -1, null);
	}

	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param flag Intent标记，不需要的话就设为-1
	 * @param bundle 参数集
	 */
	public void startActivityForResult(final Class<? extends Activity> targetActivityClass, final int requestCode, final int flag, final Bundle bundle){
		ActivityUtils.startForResult(this, targetActivityClass, requestCode, flag, bundle);
	}

	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param flag Intent标记，不需要的话就设为-1
	 */
	public void startActivityForResult(final Class<? extends Activity> targetActivityClass, final int requestCode, final int flag){
		startActivityForResult(targetActivityClass, requestCode, flag, null);
	}

	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param bundle 参数集
	 */
	public void startActivityForResult(final Class<? extends Activity> targetActivityClass, final int requestCode, final Bundle bundle){
		startActivityForResult(targetActivityClass, requestCode, -1, bundle);
	}

	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 */
	public void startActivityForResult(final Class<? extends Activity> targetActivityClass, final int requestCode){
		startActivityForResult(targetActivityClass, requestCode, -1, null);
	}
	
	
	/* ********************************************** 对话框 ************************************************ */
	/**
	 * 弹出一个消息对话框
	 * @param message 消息
	 * @param confrimButtonName 确定按钮的名字
	 */
	public void showMessageDialog(final String message, final String confrimButtonName){
		if(!haveDestroy){
			handler.post(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					Bundle bundle = new Bundle();
					bundle.putString(KEY_DIALOG_MESSAGE, message);
					bundle.putString(KEY_DIALOG_CONFRIM_BUTTON_NAME, confrimButtonName);
					showDialog(DIALOG_MESSAGE, bundle); 
				}
			});
		}
	}
	
	/**
	 * 弹出一个消息对话框
	 * @param message 消息
	 */
	public void showMessageDialog(String message){
		showMessageDialog(message, getResources().getConfiguration().locale == Locale.CHINESE?"确定":"Confirm");
	}
	
	/**
	 * 弹出一个消息对话框
	 * @param messageId 消息ID
	 */
	public void showMessageDialog(int messageId){
		showMessageDialog(getString(messageId));
	}
	
	/**
	 * 弹出一个消息对话框
	 * @param messageId 消息ID
	 * @param confrimButtonNameId 确定按钮ID
	 */
	public void showMessageDialog(int messageId, int confrimButtonNameId){
		showMessageDialog(getString(messageId), getString(confrimButtonNameId));
	}
	
	/**
	 * 关闭消息对话框
	 */
	public void closeMessageDialog(){
		if(!haveDestroy){
			handler.post(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					try{
						dismissDialog(DIALOG_MESSAGE);
					}catch(Throwable throwable){
						throwable.printStackTrace();
					} 
				}
			});
		}
	}
	
	/**
	 * 弹出一个进度对话框
	 * @param message 消息
	 */
	public void showProgressDialog(final String message){
		if(!haveDestroy){
			handler.post(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					Bundle bundle = new Bundle();
					bundle.putString(KEY_DIALOG_MESSAGE, message);
					showDialog(DIALOG_PROGRESS, bundle);
				}
			});
		}
	}
	
	/**
	 * 弹出一个进度对话框
	 * @param messageId 消息ID
	 */
	public void showProgressDialog(int messageId){
		showProgressDialog(getString(messageId));
	}
	
	/**
	 * 关闭进度对话框
	 */
	public void closeProgressDialog(){
		if(!haveDestroy){
			handler.post(new Runnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					try{
						dismissDialog(DIALOG_PROGRESS); 
					}catch(Throwable throwable){
						throwable.printStackTrace();
					}
				}
			});
		}
	}
	
	/**
	 * 获取Handler
	 * @return
	 */
	public Handler getHandler() {
		return handler;
	}

	/**
	 * 获取双击退出程序识别器
	 * @return
	 */
	public DoubleClickDetector getDoubleClickExitAcpplicationDetector() {
		return doubleClickExitAcpplicationDetector;
	}
	
	/**
	 * 设置是否激活双击退出程序功能
	 * @param isEnableDoubleClickExitAcpplication
	 */
	public void setEnableDoubleClickExitAcpplication(boolean isEnableDoubleClickExitAcpplication){
		if(isEnableDoubleClickExitAcpplication){
			if(doubleClickExitAcpplicationDetector == null){
				doubleClickExitAcpplicationDetector = new DoubleClickDetector(new DoubleClickDetector.OnSingleClickListener() {
					@Override
					public void onSingleClick() {
						ToastUtils.toastS(getBaseContext(), "再按一次退出程序");
					}
				}, new DoubleClickDetector.OnDoubleClickListener() {
					@Override
					public void onDoubleClick() {
						finishApplication();
					}
				});
			}
		}else{
			if(doubleClickExitAcpplicationDetector != null){
				doubleClickExitAcpplicationDetector = null;
			}
		}
	}
	
	/**
	 * 是否激活双击退出程序功能
	 * @return
	 */
	public boolean isEnableDoubleClickExitAcpplication(){
		return doubleClickExitAcpplicationDetector != null;
	}
	
	/**
	 * 当前Activity是否已经销毁
	 * @return
	 */
	public boolean isHaveDestroy() {
		return haveDestroy;
	}

	/**
	 * 获取Activity池
	 * @return
	 */
	public ActivityPool getActivityPool() {
		return activityPool;
	}
}