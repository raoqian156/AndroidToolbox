/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
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

package me.xiaopan.android.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Android系统工具箱
 */
public class AndroidUtils {
	public static final String SMS_BODY = "sms_body";
	
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
	
	/**
	 * 打开拨号界面，需要CALL_PHONE权限
	 * @param phoneNumber 要呼叫的电话号码
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能打开拨号界面
	 */
	public static void openDialingInterface(Activity activity, String phoneNumber){
		activity.startActivity(new Intent(Intent.ACTION_DIAL, UriUtils.getCallUri(phoneNumber)));
	}
	
	/**
	 * 呼叫给定的电话号码，需要CALL_PHONE权限
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能呼叫给定的电话
	 * @param phoneNumber 要呼叫的电话号码
	 */
	public static void call(Activity activity, String phoneNumber){
		activity.startActivity(new Intent(Intent.ACTION_CALL, UriUtils.getCallUri(phoneNumber)));
	}
	
	/**
	 * 打开给定的页面
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能打开给定的页面
	 * @param url 要打开的web页面的地址
	 */
	public static void openWebBrowser(Activity activity, String url){
		activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
	}
	
	/**
	 * 打开发送短信界面
	 * @param activity Activity对象，需要依托于Activity所在的主线程才能打开给定的页面
	 * @param mobileNumber 目标手机号
	 * @param messageConten 短信内容
	 */
	public static void openSmsInterface(Activity activity, String mobileNumber, String messageConten){
		Intent intent = new Intent(Intent.ACTION_SENDTO, UriUtils.getSmsUri(mobileNumber));
		intent.putExtra(SMS_BODY, messageConten);
		activity.startActivity(intent);
	}
	
	/**
	 * 发送短信，需要SEND_SMS权限
	 * @param context 上下文
	 * @param number 电话号码
	 * @param messageContent 短信内容，如果长度过长将会发多条发送
	 */
	public static void sendSms(Context context, String number, String messageContent){
		SmsManager smsManager = SmsManager.getDefault();
		List<String> contentList = smsManager.divideMessage(messageContent);
		for(String content : contentList){
			smsManager.sendTextMessage(number, null, content, null, null);
		}
	}
	
	/**
	 * 获取所有联系人的姓名和电话号码，需要READ_CONTACTS权限
	 * @param context 上下文
	 * @return Cursor。姓名：CommonDataKinds.Phone.DISPLAY_NAME；号码：CommonDataKinds.Phone.NUMBER
	 */
	public static Cursor getContactsNameAndNumber(Context context){
		return context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[] {
				CommonDataKinds.Phone.DISPLAY_NAME, CommonDataKinds.Phone.NUMBER}, null, null, CommonDataKinds.Phone.DISPLAY_NAME + " COLLATE LOCALIZED ASC");
	}
	
	/**
	 * 为给定的编辑器开启软键盘
	 * @param context 
	 * @param editText 给定的编辑器
	 */
	public static void openSoftKeyboard(Context context, EditText editText){
		editText.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
		ViewUtils.setEditTextSelectionToEnd(editText);
	}
	
	/**
	 * 关闭软键盘
	 * @param context
	 */
	public static void closeSoftKeyboard(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		//如果软键盘已经开启
		if(inputMethodManager.isActive()){
			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
	
	/**
	 * 切换软键盘的状态
	 * @param context
	 */
	public static void toggleSoftKeyboardState(Context context){
		((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS);
	}
	
	/**
	 * 获取手机号码
	 * @param context 上下文
	 * @return 手机号码，手机号码不一定能获取到
	 */
	public static String getMobilePhoneNumber(Context context){
		return ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getLine1Number();
	}
}