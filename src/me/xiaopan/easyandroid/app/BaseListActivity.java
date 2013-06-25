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
package me.xiaopan.easyandroid.app;

import java.io.File;
import java.util.Set;

import me.xiaopan.easyandroid.util.ActivityUtils;
import me.xiaopan.easyandroid.util.NetworkUtils;
import me.xiaopan.easyandroid.util.SDCardUtils;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

/**
 * 自定义抽象的Activity基类
 */
public abstract class BaseListActivity extends ListActivity implements BaseActivityInterface{
	private static long lastClickBackButtonTime;	//记录上次点击返回按钮的时间，用来配合实现双击返回按钮退出应用程序的功能
	private long activityId = -5;	//当前Activity在ActivityManager中的ID
	private long createTime;	//创建时间
	private boolean openedBroadcaseReceiver;	//已经打开了广播接收器
	private MessageHandler messageHanlder;	//主线程消息处理器
	private MyBroadcastReceiver broadcastReceiver;	//广播接收器
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState); 
		createTime = System.currentTimeMillis();	//记录创建时间，用于异常终止时判断是否需要等待一段时间再终止，因为时间过短的话体验不好
		activityId = ActivityManager.getInstance().putActivity(this);	//将当前Activity放入ActivityManager中，并获取其ID
		if(isRemoveTitleBar()) requestWindowFeature(Window.FEATURE_NO_TITLE);	//如果需要去掉标题栏	
		if(isRemoveStatusBar()) getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);	//如果需要全屏就去掉通知栏
		messageHanlder = new MessageHandler(this);//实例化消息处理器
		onPreInit(savedInstanceState);//在初始化之前											
		onInitLayout(savedInstanceState);//初始化布局								
		onInitListener(savedInstanceState);//初始化监听器						
		onInitData(savedInstanceState);//初始化数据
		onPostInit(savedInstanceState);//在初始化之后
	}
	
	/**
	 * 在初始化之前
	 * @param savedInstanceState
	 */
	public void onPreInit(Bundle savedInstanceState){
		
	}
	
	/**
	 * 在初始化之后
	 * @param savedInstanceState
	 */
	public void onPostInit(Bundle savedInstanceState){
		
	}
	
	@Override
	public void onBackPressed() {
		if(isEnableDoubleClickBackButtonExitApplication()){
			long currentMillisTime = System.currentTimeMillis();
			//两次点击的间隔时间尚未超过规定的间隔时间将执行退出程序
			if(lastClickBackButtonTime != 0 && (currentMillisTime - lastClickBackButtonTime) < onGetDoubleClickSpacingInterval()){
				finishApplication();
			}else{
				onPromptExitApplication();
				lastClickBackButtonTime = currentMillisTime;
			}
		}else{
			finishActivity();
		}
	}
	
	@Override
	protected void onDestroy() {
		ActivityManager.getInstance().removeActivity(getActivityId());
		closeBroadcastReceiver();
		super.onDestroy();
	}
	
	@Override
	public boolean isEnableDoubleClickBackButtonExitApplication(){
		return false;
	}
	
	@Override
	public int onGetDoubleClickSpacingInterval(){
		return 2000;
	}
	
	@Override
	public void onPromptExitApplication(){
		toastS("再按一次退出程序！");
	}

	@Override
	protected Dialog onCreateDialog(int id, Bundle args) {
		Dialog dialog = null;
		switch(id){
			case DIALOG_MESSAGE : 
					AlertDialog messageDialog = new AlertDialog.Builder(this).create();
					if(args != null){
						messageDialog.setMessage(args.getString(KEY_DIALOG_MESSAGE));
						messageDialog.setButton(args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
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
					messageDialog.setButton(args.getString(KEY_DIALOG_CONFRIM_BUTTON_NAME), new android.content.DialogInterface.OnClickListener() { @Override public void onClick(DialogInterface dialog, int which) {} });
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
	public void onReceivedMessage(Message message){
		
	};
	
	@Override
	public void onReceivedBroadcast(Intent intent){
		
	}
	
	
	/* ********************************************** 常用 ************************************************ */
	@Override
	public void finishApplication(){
		ActivityManager.getInstance().finishApplication();
	}
	
	/**
	 * 判断在启动或者终止Activity的时候是否使用自定义动画
	 * @return 在启动或者终止Activity的时候是否使用自定义动画
	 */
	protected boolean isUseCustomAnimation(){
		return true;
	}
	
	/**
	 * 判断是否需要去除标题栏，默认不去除
	 * @return 是否需要去除标题栏
	 */
	@Override
	public boolean isRemoveTitleBar() {
		return false;
	}
	
	/**
	 * 判断是否需要全屏，默认不全屏
	 * @return 是否需要全屏
	 */
	@Override
	public boolean isRemoveStatusBar() {
		return false;
	}
	
	@Override
	public SharedPreferences getDefultPreferences(){
		return PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}
	
	@Override
	public boolean isFirstUsing(){
		return getDefultPreferences().getBoolean(PRFERENCES_FIRST_USING, true);
	}
	
	@Override
	public void setFirstUsing(boolean firstUsing){
		Editor editor = getDefultPreferences().edit();
		editor.putBoolean(PRFERENCES_FIRST_USING, firstUsing);
		editor.commit();
	} 
	
	/**
	 * 当需要获取默认的启动Activity动画
	 * @return 默认的启动Activity动画
	 */
	protected int[] onGetDefaultStartActivityAnimation(){
		return null;
	}
	
	/**
	 * 当需要获取默认的终止Activity动画
	 * @return 默认的终止Activity动画
	 */
	protected int[] onGetDefaultFinishActivityAnimation(){
		return null;
	} 
	

	
	/* ********************************************** 提示视图 ************************************************ */
	@Override
	public void showLoadingHintView(View loadingHintView){
		if(loadingHintView != null){
			Message message = messageHanlder.obtainMessage();
			message.what = MessageHandler.SHOW_LOADING_HINT_VIEW;
			message.obj = loadingHintView;
			message.sendToTarget();
		}
	}
	
	@Override
	public void closeLoadingHintView(View loadingHintView){
		if(loadingHintView != null){
			Message message = new Message();
			message.what = MessageHandler.CLOSE_LOADING_HINT_VIEW;
			message.obj = loadingHintView;
			sendMessage(message);
		}
	}
	
	@Override
	public void showLoadingHintView(int loadingHintViewId){
		showLoadingHintView(findViewById(loadingHintViewId));
	}
	
	@Override
	public void closeLoadingHintView(int loadingHintViewId){
		closeLoadingHintView(findViewById(loadingHintViewId));
	}
	
	 
	
	/* ********************************************** 网络 ************************************************ */
	@Override
	public boolean isNetworkAvailable() {
		boolean result = false;
		result = NetworkUtils.isConnectedByState(getBaseContext());
		if(!result){
			onNetworkNotAvailable();
		}
		return result;
	}

	/**
	 * 在验证网络是否可用时发现网络不可用
	 */
	protected void onNetworkNotAvailable() {
		
	}

	/* ********************************************** 消息/广播 ************************************************ */
	@Override
	public void sendMessage(){
		sendMessage(-5);
	}
	
	@Override
	public void sendMessage(Message message){
		message.setTarget(messageHanlder);
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
	public void openBroadcastReceiver(String filterAction){
		setOpenedBroadcaseReceiver(true);
		setBroadcastReceiver(new MyBroadcastReceiver(this));
	    registerReceiver(getBroadcastReceiver(), new IntentFilter(filterAction));
	}
	
	@Override
	public void closeBroadcastReceiver(){
		if(getBroadcastReceiver() != null){
			unregisterReceiver(getBroadcastReceiver());
		}
		setOpenedBroadcaseReceiver(false);
		setBroadcastReceiver(null);
	}
	
	
	/* ********************************************** Toast ************************************************ */
	@Override
	public void toastL(int resId){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = getString(resId);
		sendMessage(message);
	}
	
	@Override
	public void toastS(int resId){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = getString(resId);
		sendMessage(message);
	}
	
	@Override
	public void toastL(String content){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = content;
		sendMessage(message);
	}
	
	@Override
	public void toastS(String content){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = content;
		sendMessage(message);
	}
	
	@Override
	public void toastL(int formatResId, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = getString(formatResId, args);
		sendMessage(message);
	}
	
	@Override
	public void toastS(int formatResId, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = getString(formatResId, args);
		sendMessage(message);
	}
	
	@Override
	public void toastL(String format, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_LONG;
		message.obj = String.format(format, args);
		sendMessage(message);
	}
	
	@Override
	public void toastS(String format, Object... args){
		Message message = new Message();
		message.what = MessageHandler.TOAST;
		message.arg1 = Toast.LENGTH_SHORT;
		message.obj = String.format(format, args);
		sendMessage(message);
	}
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	@Override
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		Bundle bundle2 = null;
		if(bundle != null){
			bundle2 = bundle;
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, true);
		}else{
			bundle2 = new Bundle();
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, false);
		}
		bundle2.putInt(MessageHandler.FLAG, flag);
		bundle2.putBoolean(MessageHandler.IS_CLOSE, isClose);
		bundle2.putInt(MessageHandler.IN_ANIMATION, inAnimation);
		bundle2.putInt(MessageHandler.OUT_ANIMATION, outAnimation);
		Message message = new Message();
		message.what = MessageHandler.START_ACTIVITY;
		message.obj = targetActivity;
		message.setData(bundle2);
		sendMessage(message);
	}
	
	@Override
	public void onStartActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation){
		if(isUseCustomAnimation()){
			if(inAnimation > 0 && outAnimation > 0){
				ActivityUtils.startActivity(this, targetActivity, flag, bundle, isClose, inAnimation, outAnimation);
			}else{
				int[] animations = onGetDefaultStartActivityAnimation();
				if(animations != null && animations.length >= 2){
					ActivityUtils.startActivity(this, targetActivity, flag, bundle, isClose, animations[0], animations[1]);
				}else{
					ActivityUtils.startActivity(this, targetActivity, flag, bundle, isClose);
				}
			}
		}else{
			ActivityUtils.startActivity(this, targetActivity, flag, bundle, isClose);
		}
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
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		Bundle bundle2 = null;
		if(bundle != null){
			bundle2 = bundle;
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, true);
		}else{
			bundle2 = new Bundle();
			bundle2.putBoolean(MessageHandler.HAVE_BUNDLE, false);
		}
		bundle2.putInt(MessageHandler.REQUEST_CODE, requestCode);
		bundle2.putInt(MessageHandler.FLAG, flag);
		bundle2.putInt(MessageHandler.IN_ANIMATION, inAnimation);
		bundle2.putInt(MessageHandler.OUT_ANIMATION, outAnimation);
		Message message = new Message();
		message.what = MessageHandler.START_ACTIVITY_FOR_RESULT;
		message.obj = targetActivity;
		message.setData(bundle2);
		sendMessage(message);
	}
	
	@Override
	public void onStartActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation){
		if(isUseCustomAnimation()){
			if(inAnimation > 0 && outAnimation > 0){
				ActivityUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle, inAnimation, outAnimation);
			}else{
				int[] animations = onGetDefaultStartActivityAnimation();
				if(animations != null && animations.length >= 2){
					ActivityUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle, animations[0], animations[1]);
				}else{
					ActivityUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle);
				}
			}
		}else{
			ActivityUtils.startActivityForResult(this, targetActivity, requestCode, flag, bundle);
		}
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
	public void finishActivity(){
		sendMessage(MessageHandler.FINISH_ACTIVITY);
	}
	
	@Override
	public void onFinishActivity(){
		finish();
		if(isUseCustomAnimation()){
			int[] animations = onGetDefaultFinishActivityAnimation();
			if(animations != null && animations.length >= 2){
				overridePendingTransition(animations[0], animations[1]);
			}
		}
	}
	
	@Override
	public void finishActivity(int inAnimation, int outAnimation){
		Message message = new Message();
		message.what = MessageHandler.FINISH_ACTIVITY_ANIMATION;
		message.arg1 = inAnimation;
		message.arg2 = outAnimation;
		sendMessage(message);
	}
	
	@Override
	public void onFinishActivity(int inAnimation, int outAnimation){
		finish();
		if(isUseCustomAnimation()){
			overridePendingTransition(inAnimation, outAnimation);
		}
	}
	
	@Override
	public void finishActivity(long id){
		ActivityManager.getInstance().finishActivity(id);
	}
	
	@Override
	public void finishActivitys(long[] ids){
		ActivityManager.getInstance().finishActivitys(ids);
	}
	
	@Override
	public void finishActivitys(Set<Long> ids){
		ActivityManager.getInstance().finishActivitys(ids);
	}
	
	@Override
	public void finishOtherActivitys(){
		ActivityManager.getInstance().finishOtherActivitys(getActivityId());
	}
	
	@Override
	public void becauseExceptionFinishActivity(){
		final int useTime = (int) (System.currentTimeMillis() - createTime);
		//如果当前Activity从创建到销毁的时间小于最小用时
		if(useTime < MIN_USE_TIME){
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(MIN_USE_TIME - useTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					sendMessage(MessageHandler.BECAUSE_EXCEPTION_FINISH_ACTIVITY);
				}
			}).start();
		}else{
			sendMessage(MessageHandler.BECAUSE_EXCEPTION_FINISH_ACTIVITY);
		}
	}
	
	@Override
	public void onBecauseExceptionFinishActivity(){
		finishActivity();
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
		ActivityManager.getInstance().finishAllWaitingActivity();
	}
	
	
	
	/* ********************************************** 对话框 ************************************************ */
	@Override
	public void showMessageDialog(String message, String confrimButtonName){
		Bundle bundle = new Bundle();
		bundle.putString(KEY_DIALOG_MESSAGE, message);
		bundle.putString(KEY_DIALOG_CONFRIM_BUTTON_NAME, confrimButtonName);
		sendMessage(MessageHandler.SHOW_MESSAGE_DIALOG, bundle);
	}
	
	@Override
	public void showMessageDialog(int messageId, int confrimButtonNameId){
		showMessageDialog(getString(messageId), getString(confrimButtonNameId));
	}
	
	@Override
	public void closeMessageDialog(){
		sendMessage(MessageHandler.CLOSE_MESSAGE_DIALOG);
	}
	
	@Override
	public void showProgressDialog(String message){
		Bundle bundle = new Bundle();
		bundle.putString(KEY_DIALOG_MESSAGE, message);
		sendMessage(MessageHandler.SHOW_PROGRESS_DIALOG, bundle);
	}
	
	@Override
	public void showProgressDialog(int messageId){
		showProgressDialog(getString(messageId));
	}
	
	@Override
	public void closeProgressDialog(){
		sendMessage(MessageHandler.CLOSE_PROGRESS_DIALOG);
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
	
	@Override
	public File getDynamicFilesDir(){
		return SDCardUtils.isAvailable() ? getExternalFilesDir(null) : getFilesDir();
	}
	
	@Override
	public File getDynamicCacheDir(){
		return SDCardUtils.isAvailable() ? getExternalCacheDir() : getCacheDir();
	}
	
	@Override
	public File getFileFromFilesDir(String fileName){
		return new File(getFilesDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public File getFileFromExternalFilesDir(String fileName){
		return SDCardUtils.isAvailable() ? new File(getExternalFilesDir(null).getPath() + File.separator + fileName) : null;
	}
	
	@Override
	public File getFileFromCacheDir(String fileName){
		return new File(getCacheDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public File getFileFromExternalCacheDir(String fileName){
		return SDCardUtils.isAvailable() ? new File(getExternalCacheDir().getPath() + File.separator + fileName) : null;
	}
	
	@Override
	public File getFileFromDynamicFilesDir(String fileName){
		return new File(getDynamicFilesDir().getPath() + File.separator + fileName);
	}
	
	@Override
	public File getFileFromDynamicCacheDir(String fileName){
		return new File(getDynamicCacheDir().getPath() + File.separator + fileName);
	}
	
	
	
	/* ********************************************** GET/SET ************************************************ */
	@Override
	public long getActivityId(){
		return activityId;
	}

	@Override
	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	@Override
	public MessageHandler getMessageHanlder() {
		return messageHanlder;
	}

	@Override
	public void setMessageHanlder(MessageHandler messageHanlder) {
		this.messageHanlder = messageHanlder;
	}

	@Override
	public MyBroadcastReceiver getBroadcastReceiver() {
		return broadcastReceiver;
	}

	@Override
	public void setBroadcastReceiver(MyBroadcastReceiver broadcastReceiver) {
		this.broadcastReceiver = broadcastReceiver;
	}

	@Override
	public boolean isOpenedBroadcaseReceiver() {
		return openedBroadcaseReceiver;
	}

	@Override
	public void setOpenedBroadcaseReceiver(boolean openedBroadcaseReceiver) {
		this.openedBroadcaseReceiver = openedBroadcaseReceiver;
	}
}
