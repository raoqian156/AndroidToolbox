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
package me.xiaopan.easy.android.app;

import java.util.Set;

import me.xiaopan.easy.android.util.ActivityUtils;
import me.xiaopan.easy.android.util.NetworkUtils;
import me.xiaopan.easy.android.util.ToastUtils;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

/**
 * 自定义抽象的Activity基类，提供了很多实用的方法以及功能
 */
public abstract class BaseListActivity extends ListActivity implements BaseActivityInterface{
	private static long lastClickBackButtonTime;	//记录上次点击返回按钮的时间，用来配合实现双击返回按钮退出应用程序的功能
	private int doubleClickSpacingInterval = 2000;	//双击退出程序的间隔时间
	private int[] startActivityAnimation;	//启动Activity的动画
	private int[] finishActivityAnimation;	//终止Activity的动画
	private long activityId = -5;	//当前Activity在ActivityManager中的ID
	private long createTime;	//创建时间
	private boolean openedBroadcaseReceiver;	//已经打开了广播接收器
	private boolean enableDoubleClickExitApplication;	//是否开启双击退出程序功能
	private boolean enableCustomActivitySwitchAnimation;	//是否启用自定义的Activity切换动画
	private Handler hanlder;	//主线程消息处理器
	private BroadcastReceiver broadcastReceiver;	//广播接收器
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		createTime = System.currentTimeMillis();	//记录创建时间，用于异常终止时判断是否需要等待一段时间再终止，因为时间过短的话体验不好
		activityId = ActivityManager.getInstance().putActivity(this);	//将当前Activity放入ActivityManager中，并获取其ID
		hanlder = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				if(!isFinishing()){
					onReceivedMessage(msg);
				}
			}
		}; 
	}
	
	@Override
	public void onBackPressed() {
		if(isEnableDoubleClickExitApplication()){
			long currentMillisTime = System.currentTimeMillis();
			//两次点击的间隔时间尚未超过规定的间隔时间将执行退出程序
			if(lastClickBackButtonTime != 0 && (currentMillisTime - lastClickBackButtonTime) < getDoubleClickSpacingInterval()){
				finishApplication();
			}else{
				onDoubleClickPromptExit();
				lastClickBackButtonTime = currentMillisTime;
			}
		}else{
			finishActivity();
		}
	}
	
	/**
	 * 第一击之后会执行此方法来提示用户
	 */
	public void onDoubleClickPromptExit(){
		toastS("再按一次退出程序");
	}
	
	@Override
	protected void onDestroy() {
		ActivityManager.getInstance().removeActivity(getActivityId());
		closeBroadcastReceiver();
		super.onDestroy();
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
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
	
	public void onReceivedMessage(Message message){
		
	};
	
	public void onReceivedBroadcast(Intent intent){
		
	}
	
	
	/* ********************************************** 常用 ************************************************ */
	@Override
	public void finishApplication(){
		ActivityManager.getInstance().finishApplication();
	}
	
	@Override
	public void hiddenTitleBar(){
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	}
	
	@Override
	public void hiddenStatusBar(){
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	
	}
	
	
	/* ********************************************** 网络 ************************************************ */
	@Override
	public boolean isNetworkAvailable() {
		return NetworkUtils.isConnectedByState(getBaseContext());
	}

	
	/* ********************************************** 消息/广播 ************************************************ */
	@Override
	public void sendMessage(){
		sendMessage(-5);
	}
	
	@Override
	public void sendMessage(Message message){
		message.setTarget(getHanlder());
		message.sendToTarget();
	}
	
	@Override
	public void sendMessage(int what){
		Message message = new Message();
		message.what = what;
		sendMessage(message);
	}
	
	@Override
	public void sendMessage(int what, Bundle bundle){
		Message message = new Message();
		message.what = what;
		message.setData(bundle);
		sendMessage(message);
	}
	
	@Override
	public void sendMessage(int what, Object object){
		Message message = new Message();
		message.what = what;
		message.obj = object;
		sendMessage(message);
	}
	
	@Override
	public boolean openBroadcastReceiver(String filterAction){
		if(broadcastReceiver == null){
			openedBroadcaseReceiver = true;
			broadcastReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					onReceivedBroadcast(intent);
				}
			};
			registerReceiver(broadcastReceiver, new IntentFilter(filterAction));
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean closeBroadcastReceiver(){
		if(broadcastReceiver != null){
			unregisterReceiver(broadcastReceiver);
			openedBroadcaseReceiver = false;
			broadcastReceiver = null;
			return true;
		}else{
			return false;
		}
	}
	
	
	/* ********************************************** Toast ************************************************ */
	@Override
	public void toastL(final View view, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), view);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastL(final View view){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), view);
			}
		});
	}
	
	@Override
	public void toastS(final View view, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), view);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastS(final View view){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), view);
			}
		});
	}
	
	@Override
	public void toastL(final String content, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), content);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastL(final String content){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), content);
			}
		});
	}
	
	@Override
	public void toastS(final String content, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), content);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastS(final String content){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), content);
			}
		});
	}
	
	@Override
	public void toastL(final int resId, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), resId);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastL(final int resId){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), resId);
			}
		});
	}
	
	@Override
	public void toastS(final int resId, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), resId);
			}
		}, delayMillis);
	}
	
	@Override
	public void toastS(final int resId){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), resId);
			}
		});
	}
	
	@Override
	public void toastL(final String format, int delayMillis, final Object... args){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), String.format(format, args));
			}
		}, delayMillis);
	}
	
	@Override
	public void toastL(final String format, final Object... args){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), String.format(format, args));
			}
		});
	}
	
	@Override
	public void toastS(final String format, int delayMillis, final Object... args){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), String.format(format, args));
			}
		}, delayMillis);
	}
	
	@Override
	public void toastS(final String format, final Object... args){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), String.format(format, args));
			}
		});
	}
	
	@Override
	public void toastL(final int formatResId, int delayMillis, final Object... args){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), getString(formatResId, args));
			}
		}, delayMillis);
	}
	
	@Override
	public void toastL(final int formatResId, final Object... args){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getBaseContext(), getString(formatResId, args));
			}
		});
	}
	
	@Override
	public void toastS(final int formatResId, int delayMillis, final Object... args){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), getString(formatResId, args));
			}
		}, delayMillis);
	}
	
	@Override
	public void toastS(final int formatResId, final Object... args){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastS(getBaseContext(), getString(formatResId, args));
			}
		});
	}
	
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	@Override
	public void startActivity(final Class<?> targetActivity, final int flag, final Bundle bundle, final boolean isClose, final int inAnimation, final int outAnimation){
		final Activity activity = this;
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				if(isEnableCustomActivitySwitchAnimation()){
					if(inAnimation > 0 && outAnimation > 0){
						ActivityUtils.startActivity(activity, targetActivity, flag, bundle, isClose, inAnimation, outAnimation);
					}else{
						if(startActivityAnimation != null && startActivityAnimation.length >= 2){
							ActivityUtils.startActivity(activity, targetActivity, flag, bundle, isClose, startActivityAnimation[0], startActivityAnimation[1]);
						}else{
							ActivityUtils.startActivity(activity, targetActivity, flag, bundle, isClose);
						}
					}
				}else{
					ActivityUtils.startActivity(activity, (Class<?>)targetActivity, flag, bundle, isClose);
				}
			}
		});
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose){
		startActivity(targetActivity, flag, bundle, isClose, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(targetActivity, flag, bundle, false, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, flag, null, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, bundle, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle){
		startActivity(targetActivity, flag, bundle, false, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose){
		startActivity(targetActivity, flag, null, false, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose){
		startActivity(targetActivity, -5, bundle, isClose, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, false, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, bundle, false, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, isClose, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int inAnimation, int outAnimation){
		startActivity(targetActivity, -5, null, false, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, boolean isClose){
		startActivity(targetActivity, -5, null, isClose, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, Bundle bundle){
		startActivity(targetActivity, -5, bundle, false, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity, int flag){
		startActivity(targetActivity, flag, null, false, -5, -5);
	}
	
	@Override
	public void startActivity(Class<?> targetActivity){
		startActivity(targetActivity, -5, null, false, -5, -5);
	}
	
	@Override
	public void startActivityForResult(final Class<?> targetActivity, final int requestCode, final int flag, final Bundle bundle, final int inAnimation, final int outAnimation){
		final Activity activity = this;
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				if(isEnableCustomActivitySwitchAnimation()){
					if(inAnimation > 0 && outAnimation > 0){
						ActivityUtils.startActivityForResult(activity, targetActivity, requestCode, flag, bundle, inAnimation, outAnimation);
					}else{
						if(startActivityAnimation != null && startActivityAnimation.length >= 2){
							ActivityUtils.startActivityForResult(activity, targetActivity, requestCode, flag, bundle, startActivityAnimation[0], startActivityAnimation[1]);
						}else{
							ActivityUtils.startActivityForResult(activity, targetActivity, requestCode, flag, bundle);
						}
					}
				}else{
					ActivityUtils.startActivityForResult(activity, targetActivity, requestCode, flag, bundle);
				}
			}
		});
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle){
		startActivityForResult(targetActivity, requestCode, flag, bundle, -5, -5);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, flag, null, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, -5, bundle, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag){
		startActivityForResult(targetActivity, requestCode, flag, null, -5, -5);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle){
		startActivityForResult(targetActivity, requestCode, -5, bundle, -5, -5);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation){
		startActivityForResult(targetActivity, requestCode, -5, null, inAnimation, outAnimation);
	}
	
	@Override
	public void startActivityForResult(Class<?> targetActivity, int requestCode){
		startActivityForResult(targetActivity, requestCode, -5, null, -5, -5);
	}

	
	
	/* ********************************************** 终止Activity ************************************************ */
	@Override
	public void finishActivity(int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				if(isEnableCustomActivitySwitchAnimation()){
					if(finishActivityAnimation != null && finishActivityAnimation.length >= 2){
						overridePendingTransition(finishActivityAnimation[0], finishActivityAnimation[1]);
					}
				}
			}
		}, delayMillis);
	}
	
	@Override
	public void finishActivity(){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				finish();
				if(isEnableCustomActivitySwitchAnimation() && finishActivityAnimation != null && finishActivityAnimation.length >= 2){
					overridePendingTransition(finishActivityAnimation[0], finishActivityAnimation[1]);
				}
			}
		});
	}
	
	@Override
	public void finishActivity(final int inAnimation, final int outAnimation, int delayMillis){
		getHanlder().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
				overridePendingTransition(inAnimation, outAnimation);
			}
		}, delayMillis);
	}
	
	@Override
	public void finishActivity(final int inAnimation, final int outAnimation){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				finish();
				overridePendingTransition(inAnimation, outAnimation);
			}
		});
	}
	
	@Override
	public void finishActivityByMinimumDuration(int minimumDurationMillis){
		final int useTime = (int) (System.currentTimeMillis() - createTime);
		if(useTime < minimumDurationMillis){//如果当前Activity从创建到销毁的时间小于最小用时就等一会儿在终止，否则立即终止
			finishActivity(minimumDurationMillis - useTime);
		}else{
			finishActivity();
		}
	}
	
	@Override
	public void finishActivityByMinimumDuration(int minimumDurationMillis, final int inAnimation, final int outAnimation){
		final int useTime = (int) (System.currentTimeMillis() - createTime);
		if(useTime < minimumDurationMillis){//如果当前Activity从创建到销毁的时间小于最小用时就等一会儿在终止，否则立即终止
			finishActivity(inAnimation, outAnimation, minimumDurationMillis - useTime);
		}else{
			finishActivity(inAnimation, outAnimation);
		}
	}
	
	@Override
	public void finishActivity(final long id){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ActivityManager.getInstance().finishActivity(id);
			}
		});
	}
	
	@Override
	public void finishActivitys(final long[] ids){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ActivityManager.getInstance().finishActivitys(ids);
			}
		});
	}
	
	@Override
	public void finishActivitys(final Set<Long> ids){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ActivityManager.getInstance().finishActivitys(ids);
			}
		});
	}
	
	@Override
	public void finishOtherActivitys(){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ActivityManager.getInstance().finishOtherActivitys(getActivityId());
			}
		});
	}
	
	@Override
	public void putToWaitFinishActivitys(){
		ActivityManager.getInstance().putToWaitFinishActivitys(getActivityId());
	}
	
	@Override
	public boolean removeFromWaitFinishActivitys(){
		return ActivityManager.getInstance().removeFromWaitFinishActivitys(getActivityId());
	}
	
	@Override
	public void clearWaitFinishActivitys(){
		ActivityManager.getInstance().clearWaitFinishActivitys();
	}
	
	@Override
	public void finishAllWaitingActivity(){
		getHanlder().post(new Runnable() {
			@Override
			public void run() {
				ActivityManager.getInstance().finishAllWaitingActivity();
			}
		});
	}
	
	
	
	/* ********************************************** 对话框 ************************************************ */
	@Override
	public void showMessageDialog(final String message, final String confrimButtonName){
		getHanlder().post(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Bundle bundle = new Bundle();
				bundle.putString(KEY_DIALOG_MESSAGE, message);
				bundle.putString(KEY_DIALOG_CONFRIM_BUTTON_NAME, confrimButtonName);
				showDialog(BaseActivityInterface.DIALOG_MESSAGE, bundle); 
			}
		});
	}
	
	@Override
	public void showMessageDialog(String message){
		showMessageDialog(message, "确定");
	}
	
	@Override
	public void showMessageDialog(int messageId){
		showMessageDialog(getString(messageId));
	}
	
	@Override
	public void showMessageDialog(int messageId, int confrimButtonNameId){
		showMessageDialog(getString(messageId), getString(confrimButtonNameId));
	}
	
	@Override
	public void closeMessageDialog(){
		getHanlder().post(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				dismissDialog(BaseActivityInterface.DIALOG_MESSAGE); 
			}
		});
	}
	
	@Override
	public void showProgressDialog(final String message){
		getHanlder().post(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				Bundle bundle = new Bundle();
				bundle.putString(KEY_DIALOG_MESSAGE, message);
				showDialog(BaseActivityInterface.DIALOG_PROGRESS, bundle);
			}
		});
	}
	
	@Override
	public void showProgressDialog(int messageId){
		showProgressDialog(getString(messageId));
	}
	
	@Override
	public void closeProgressDialog(){
		getHanlder().post(new Runnable() {
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				dismissDialog(BaseActivityInterface.DIALOG_PROGRESS); 
			}
		});
	}
	
	
	
	/* ********************************************** 资源管理 ************************************************ */
	@Override
	public Animation getAnimation(int resId){
		return AnimationUtils.loadAnimation(getBaseContext(), resId);
	}
	
	@Override
	public LayoutAnimationController getLayoutAnimation(int ersId){
		return AnimationUtils.loadLayoutAnimation(getBaseContext(), ersId);
	}
	
	@Override
	public Interpolator getInterpolator(int resId){
		return AnimationUtils.loadInterpolator(getBaseContext(), resId);
	}
	
	@Override
	public boolean getBoolean(int resId){
		return getResources().getBoolean(resId);
	}
	
	@Override
	public int getColor(int resId){
		return getResources().getColor(resId);
	}
	
	@Override
	public ColorStateList getColorStateList(int resId){
		return getResources().getColorStateList(resId);
	}
	
	@Override
	public float getDimension(int resId){
		return getResources().getDimension(resId);
	}
	
	@Override
	public float getDimensionPixelOffset(int resId){
		return getResources().getDimensionPixelOffset(resId);
	}
	
	@Override
	public float getDimensionPixelSize(int resId){
		return getResources().getDimensionPixelSize(resId);
	}
	
	@Override
	public Drawable getDrawable(int resId){
		return getResources().getDrawable(resId);
	}
	
	@Override
	public float getFraction(int resId, int base, int pbase){
		return  getResources().getFraction(resId, base, pbase);
	}
	
	@Override
	public int getIdentifier(String name, String defType, String defPackage){
		return getResources().getIdentifier(name, defType, defPackage);
	}
	
	@Override
	public int[] getIntArray(int resId){
		return getResources().getIntArray(resId);
	}
	
	@Override
	public int getInteger(int resId){
		return getResources().getInteger(resId);
	}
	
	@Override
	public XmlResourceParser getLayout(int resId){
		return getResources().getLayout(resId);
	}
	
	@Override
	public Movie getMovie(int resId){
		return getResources().getMovie(resId);
	}
	
	@Override
	public String getQuantityString(int resId, int quantity){
		return getResources().getQuantityString(resId, quantity);
	}
	
	@Override
	public String getQuantityString(int resId, int quantity, Object... formatArgs){
		return getResources().getQuantityString(resId, quantity, formatArgs);
	}
	
	@Override
	public CharSequence getQuantityText(int resId, int quantity){
		return getResources().getQuantityText(resId, quantity);
	}
	
	@Override
	public String[] getStringArray(int resId){
		return getResources().getStringArray(resId);
	}
	
	@Override
	public String getResourceEntryName(int resId){
		return getResources().getResourceEntryName(resId);
	}
	
	@Override
	public String getResourceName(int resId){
		return getResources().getResourceName(resId);
	}
	
	@Override
	public String getResourcePackageName(int resId){
		return getResources().getResourcePackageName(resId);
	}
	
	@Override
	public String getResourceTypeName(int resId){
		return getResources().getResourceTypeName(resId);
	}
	
	@Override
	public CharSequence getText(int resId, CharSequence defSequence){
		return getResources().getText(resId, defSequence);
	}
	
	@Override
	public CharSequence[] getTextArray(int resId){
		return getResources().getTextArray(resId);
	}
	
	@Override
	public XmlResourceParser getXml(int resId){
		return getResources().getXml(resId);
	}
	
	@Override
	public View getViewByLayout(int resId, ViewGroup parentView){
		return LayoutInflater.from(getBaseContext()).inflate(resId, parentView);
	}
	
	@Override
	public View getViewByLayout(int resId){
		return getViewByLayout(resId, null);
	}
	
	
	
	
	/* ********************************************** GET/SET ************************************************ */
	@Override
	public long getActivityId(){
		return activityId;
	}

	@Override
	public Handler getHanlder() {
		return hanlder;
	}

	@Override
	public boolean isOpenedBroadcaseReceiver() {
		return openedBroadcaseReceiver;
	}

	/**
	 * 获取Activty创建时间
	 * @return Activty创建时间
	 */
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * 判断是否激活了双击退出程序功能
	 * @return 是否激活了双击退出程序功能
	 */
	public boolean isEnableDoubleClickExitApplication() {
		return enableDoubleClickExitApplication;
	}

	/**
	 * 设置是否激活了双击退出程序功能
	 * @param enableDoubleClickExitApplication 是否激活了双击退出程序功能
	 */
	public void setEnableDoubleClickExitApplication(boolean enableDoubleClickExitApplication) {
		this.enableDoubleClickExitApplication = enableDoubleClickExitApplication;
	}

	/**
	 * 获取双击时间间隔
	 * @return 双击时间间隔
	 */
	public int getDoubleClickSpacingInterval() {
		return doubleClickSpacingInterval;
	}

	/**
	 * 设置双击时间间隔
	 * @param doubleClickSpacingInterval 双击时间间隔
	 */
	public void setDoubleClickSpacingInterval(int doubleClickSpacingInterval) {
		this.doubleClickSpacingInterval = doubleClickSpacingInterval;
	}

	/**
	 * 获取启动Activity动画
	 * @return 启动Activity动画
	 */
	public int[] getStartActivityAnimation() {
		return startActivityAnimation;
	}

	/**
	 * 设置启动Activity动画
	 * @param startActivityAnimation 启动Activity动画
	 */
	public void setStartActivityAnimation(int[] startActivityAnimation) {
		this.startActivityAnimation = startActivityAnimation;
	}

	/**
	 * 获取终止Activity动画
	 * @return 终止Activity动画
	 */
	public int[] getFinishActivityAnimation() {
		return finishActivityAnimation;
	}

	/**
	 * 设置终止Activity动画
	 * @param finishActivityAnimation 终止Activity动画
	 */
	public void setFinishActivityAnimation(int[] finishActivityAnimation) {
		this.finishActivityAnimation = finishActivityAnimation;
	}

	/**
	 * 判断是否激活了自定义Activity切换动画功能
	 * @return 是否激活了自定义Activity切换动画功能
	 */
	public boolean isEnableCustomActivitySwitchAnimation() {
		return enableCustomActivitySwitchAnimation;
	}

	/**
	 * 设置是否激活了自定义Activity切换动画功能
	 * @param enableCustomActivitySwitchAnimation 是否激活了自定义Activity切换动画功能
	 */
	public void setEnableCustomActivitySwitchAnimation(boolean enableCustomActivitySwitchAnimation) {
		this.enableCustomActivitySwitchAnimation = enableCustomActivitySwitchAnimation;
	}
}