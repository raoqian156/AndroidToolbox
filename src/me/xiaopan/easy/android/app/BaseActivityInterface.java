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

import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LayoutAnimationController;

/**
 * Activity基类接口
 */
public interface BaseActivityInterface {
	String STATE_KEY = "SAVE_KEY";
	/**
	 * 对话框 - 消息对话框
	 */
	int DIALOG_MESSAGE = -1212343;
	/**
	 * 对话框 - 进度对话框
	 */
	int DIALOG_PROGRESS = -1212346;
	/**
	 * 键 - 对话框消息
	 */
	String KEY_DIALOG_MESSAGE = "KEY_DIALOG_MESSAGE";
	/**
	 * 键 - 对话框确认按钮的名字
	 */
	String KEY_DIALOG_CONFRIM_BUTTON_NAME = "KEY_DIALOG_CONFRIM_BUTTON_NAME";
	/**
	 * 版本号
	 */
	String PRFERENCES_VERSION_CODE = "PRFERENCES_VERSION_CODE";
	

	
	
	/* ********************************************** 常用 ************************************************ */
	/**
	 * 终止应用程序
	 */
	public void finishApplication();
	
	/**
	 * 隐藏标题栏
	 */
	public void hiddenTitleBar();
	
	/**
	 * 隐藏状态栏
	 */
	public void hiddenStatusBar();
	
	
	
	
	/* ********************************************** 网络 ************************************************ */
	/**
	 * 判断网络是否可用
	 * @return true：可用；false：不可用
	 */
	public boolean isNetworkAvailable();
	
	
	
	/* ********************************************** 消息/广播 ************************************************ */
	/**
	 * 发送一个没有内容的消息给主线程，其what等于-5
	 */
	public void sendMessage();
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param message 要发送的消息
	 */
	public void sendMessage(Message message);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 */
	public void sendMessage(int what);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 * @param bundle 数据
	 */
	public void sendMessage(int what, Bundle bundle);
	
	/**
	 * 发送消息到主线程，然后在onReceivedMessage()方法中处理
	 * @param what 消息标记，当收到消息时用这个字段来区分该如何执行
	 * @param object 将要携带的对象，将放在Message.obj对象上
	 */
	public void sendMessage(int what, Object object);
	
	/**
	 * 打开广播接收器
	 * @param filterAction Itent过滤器的动作
	 * @return true：打开成功；false：打开失败，因为已经打开了一个广播接收器了
	 */
	public boolean openBroadcastReceiver(String filterAction);
	
	/**
	 * 关闭广播接收器
	 * @return true：关闭成功；false：关闭失败，因为当前没有已经打开的广播接收器
	 */
	public boolean closeBroadcastReceiver();
	
	
	
	
	/* ********************************************** Toast ************************************************ */
	/**
	 * 在延迟delayMillis毫秒后吐出视图view，使其显示较长的时间
	 * @param view
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastL(View view, int delayMillis);
	
	/**
	 * 在延迟delayMillis毫秒后吐出视图view，使其显示较短的时间
	 * @param view
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastS(View view, int delayMillis);
	
	/**
	 * 立即吐出视图view，使其显示较长的时间
	 * @param view
	 */
	public void toastL(View view);
	
	/**
	 * 立即吐出视图view，使其显示较短的时间
	 * @param view
	 */
	public void toastS(View view);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较长的提示
	 * @param content 显示内容
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastL(final String content, int delayMillis);
	
	/**
	 * 立即吐出一个显示时间较长的提示
	 * @param content 显示内容
	 */
	public void toastL(String content);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较短的提示
	 * @param content 显示内容
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastS(final String content, int delayMillis);
	
	/**
	 * 立即吐出一个显示时间较短的提示
	 * @param content 显示内容
	 */
	public void toastS(String content);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较长的提示
	 * @param resId 显示内容资源ID
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastL(int resId, int delayMillis);
	
	/**
	 * 立即吐出一个显示时间较长的提示
	 * @param resId 显示内容资源ID
	 */
	public void toastL(int resId);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较短的提示
	 * @param resId 显示内容资源ID
	 * @param delayMillis 延迟时间，单位毫秒
	 */
	public void toastS(int resId, int delayMillis);
	
	/**
	 * 立即吐出一个显示时间较短的提示
	 * @param resId 显示内容资源ID
	 */
	public void toastS(int resId);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较长的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param delayMillis 延迟时间，单位毫秒
	 * @param args 参数数组
	 */
	public void toastL(int formatResId, int delayMillis, Object... args);
	
	/**
	 * 立即吐出一个显示时间较长的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public void toastL(int formatResId, Object... args);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较短的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param delayMillis 延迟时间，单位毫秒
	 * @param args 参数数组
	 */
	public void toastS(int formatResId, int delayMillis, Object... args);
	
	/**
	 * 立即吐出一个显示时间较短的提示
	 * @param formatResId 被格式化的字符串资源的ID
	 * @param args 参数数组
	 */
	public void toastS(int formatResId, Object... args);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较长的提示
	 * @param format 被格式化的字符串
	 * @param delayMillis 延迟时间，单位毫秒
	 * @param args 参数数组
	 */
	public void toastL(String format, int delayMillis, Object... args);
	
	/**
	 * 立即吐出一个显示时间较长的提示
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public void toastL(String format, Object... args);
	
	/**
	 * 在延迟delayMillis毫秒后吐出一个显示时间较短的提示
	 * @param format 被格式化的字符串
	 * @param delayMillis 延迟时间，单位毫秒
	 * @param args 参数数组
	 */
	public void toastS(String format, int delayMillis, Object... args);
	
	/**
	 * 立即吐出一个显示时间较短的提示
	 * @param format 被格式化的字符串
	 * @param args 参数数组
	 */
	public void toastS(String format, Object... args);
	
	
	
	/* ********************************************** 启动Activity ************************************************ */
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose, int inAnimation, int outAnimation);

	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivity(Class<?> targetActivity, int flag, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int flag, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, boolean isClose, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivity(Class<?> targetActivity, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param isClose fromActivity在跳转完成后是否关闭
	 */
	public void startActivity(Class<?> targetActivity, boolean isClose);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivity(Class<?> targetActivity, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param flag Intent标记。-5：不添加标记
	 */
	public void startActivity(Class<?> targetActivity, int flag);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 */
	public void startActivity(Class<?> targetActivity);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param flag Intent标记。-5：不添加标记
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int flag);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param bundle 在跳的过程中要传的数据，为null的话不传
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, Bundle bundle);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 * @param inAnimation targetActivity的进入动画。inAnimation和fromActivity都大于0才会使用动画
	 * @param outAnimation fromActivity的出去动画。inAnimation和fromActivity都大于0才会使用动画
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode, int inAnimation, int outAnimation);
	
	/**
	 * 启动Activity
	 * @param targetActivity 目标Activity
	 * @param requestCode 请求码
	 */
	public void startActivityForResult(Class<?> targetActivity, int requestCode);
	
	
	
	/* ********************************************** 终止Activity ************************************************ */
	/**
	 * 终止当前Activity
	 * @param delayMillis 延迟多少毫秒后执行
	 */
	public void finishActivity(int delayMillis);
	
	/**
	 * 终止当前Activity
	 */
	public void finishActivity();
	
	/**
	 * 终止当前Activity
	 * @param inAnimation 下一个Activity的进入动画
	 * @param outAnimation 当前Activity的退出动画
	 * @param delayMillis 延迟多少毫秒后执行
	 */
	public void finishActivity(int inAnimation, int outAnimation, int delayMillis);
	
	/**
	 * 终止当前Activity
	 * @param inAnimation 下一个Activity的进入动画
	 * @param outAnimation 当前Activity的退出动画
	 */
	public void finishActivity(int inAnimation, int outAnimation);
	
	/**
	 * 终止当前Activity，如果如果当前Activity从创建到现在的时间小于minimumDurationMillis，就再等一会儿再终止当前Activity
	 * @param minimumDurationMillis
	 */
	public void finishActivityByMinimumDuration(int minimumDurationMillis);
	
	/**
	 * 终止当前Activity，如果如果当前Activity从创建到现在的时间小于minimumDurationMillis，就再等一会儿再终止当前Activity
	 * @param minimumDurationMillis
	 * @param inAnimation
	 * @param outAnimation
	 */
	public void finishActivityByMinimumDuration(int minimumDurationMillis, final int inAnimation, final int outAnimation);
	
	/**
	 * 终止给定ID的Activity
	 * @param id 要终止的Activity的ID
	 */
	public void finishActivity(long id);
	
	/**
	 * 终止给定ID数组中包含的Activity
	 * @param ids 要终止的Activity的ID数组
	 */
	public void finishActivitys(long[] ids);
	
	/**
	 * 终止给定ID集合中包含的Activity
	 * @param ids 要终止的Activity的ID集合
	 */
	public void finishActivitys(Set<Long> ids);
	
	/**
	 * 销毁除当前Activity之外的所有Activity
	 */
	public void finishOtherActivitys();
	
	/**
	 * 将当前Activity放到等待终止的Activity列表中
	 */
	public void putToWaitFinishActivitys();
	
	/**
	 * 将当前Activity从等待终止的Activity列表中移除
	 * @return 移除之前列表中是否存在当前Activity
	 */
	public boolean removeFromWaitFinishActivitys();
	
	/**
	 * 把等待终止的Activity列表清空
	 */
	public void clearWaitFinishActivitys();
	
	/**
	 * 终止所有等待中的Activity，终止之后其ID自动从等待列表中清除
	 */
	public void finishAllWaitingActivity();
	
	
	
	/* ********************************************** 对话框 ************************************************ */
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息
	 * @param confrimButtonName 确认按钮的名字
	 */
	public void showMessageDialog(String message, String confrimButtonName);
	
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息
	 */
	public void showMessageDialog(String message);
	
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息的ID
	 */
	public void showMessageDialog(int messageId);
	
	/**
	 * 显示消息对话框
	 * @param message 要显示的消息的ID
	 * @param confrimButtonName 确认按钮的名字的ID
	 */
	public void showMessageDialog(int messageId, int confrimButtonNameId);
	
	/**
	 * 关闭消息对话框
	 */
	public void closeMessageDialog();
	
	/**
	 * 显示进度对话框
	 * @param message 要显示的消息
	 */
	public void showProgressDialog(String message);
	
	/**
	 * 显示进度对话框
	 * @param message 要显示的消息的ID
	 */
	public void showProgressDialog(int messageId);
	
	/**
	 * 关闭进度对话框
	 */
	public void closeProgressDialog();
	
	
	
	/* ********************************************** 资源管理 ************************************************ */
	/**
	 * 获取动画
	 * @param resId 资源ID
	 * @return 动画
	 */
	public Animation getAnimation(int resId);
	
	/**
	 * 获取布局动画
	 * @param ersId 资源ID
	 * @return 布局动画
	 */
	public LayoutAnimationController getLayoutAnimation(int ersId);
	
	/**
	 * 获取插值器
	 * @param resId 资源ID
	 * @return 插值器
	 */
	public Interpolator getInterpolator(int resId);
	
	/**
	 * 获取布尔值
	 * @param resId 资源ID
	 * @return 布尔值
	 */
	public boolean getBoolean(int resId);
	
	/**
	 * 获取颜色
	 * @param resId 资源ID
	 * @return 颜色
	 */
	public int getColor(int resId);
	
	/**
	 * 获取颜色状态列表
	 * @param resId 资源ID
	 * @return 颜色状态列表
	 */
	public ColorStateList getColorStateList(int resId);
	
	/**
	 * 获取尺寸
	 * @param resId 资源ID
	 * @return 尺寸
	 */
	public float getDimension(int resId);
	
	/**
	 * 获取根据维度便宜后的尺寸
	 * @param resId 资源ID
	 * @return 根据维度便宜后的尺寸
	 */
	public float getDimensionPixelOffset(int resId);
	
	/**
	 * 获取根据维度便宜后的大小值
	 * @param resId 资源ID
	 * @return 根据维度便宜后的大小值
	 */
	public float getDimensionPixelSize(int resId);
	
	/**
	 * 获取图片
	 * @param resId 资源ID
	 * @return 图片
	 */
	public Drawable getDrawable(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param base
	 * @param pbase
	 * @return
	 */
	public float getFraction(int resId, int base, int pbase);
	
	/**
	 * 获取标示符
	 * @param name
	 * @param defType
	 * @param defPackage
	 * @return 标示符
	 */
	public int getIdentifier(String name, String defType, String defPackage);
	
	/**
	 * 获取整数数组
	 * @param resId 资源ID
	 * @return 整数数组
	 */
	public int[] getIntArray(int resId);
	
	/**
	 * 获取整数
	 * @param resId 资源ID
	 * @return 整数
	 */
	public int getInteger(int resId);
	
	/**
	 * 获取布局资源
	 * @param resId 资源ID
	 * @return 布局资源
	 */
	public XmlResourceParser getLayout(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @return
	 */
	public Movie getMovie(int resId);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @return
	 */
	public String getQuantityString(int resId, int quantity);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @param formatArgs
	 * @return
	 */
	public String getQuantityString(int resId, int quantity, Object... formatArgs);
	
	/**
	 * 获取
	 * @param resId 资源ID
	 * @param quantity
	 * @return
	 */
	public CharSequence getQuantityText(int resId, int quantity);
	
	/**
	 * 获取字符串数组
	 * @param resId 资源ID
	 * @return 字符串数组
	 */
	public String[] getStringArray(int resId);
	
	/**
	 * 获取资源实体的名字
	 * @param resId 资源ID
	 * @return 资源实体的名字
	 */
	public String getResourceEntryName(int resId);
	
	/**
	 * 获取资源名称
	 * @param resId 资源ID
	 * @return 资源名称
	 */
	public String getResourceName(int resId);
	
	/**
	 * 获取资源包名字
	 * @param resId 资源ID
	 * @return 资源包名字
	 */
	public String getResourcePackageName(int resId);
	
	/**
	 * 获取资源类型名字
	 * @param resId 资源ID
	 * @return 资源类型名字
	 */
	public String getResourceTypeName(int resId);
	
	/**
	 * 获取文本
	 * @param resId 资源ID
	 * @param defSequence 默认值
	 * @return 文本
	 */
	public CharSequence getText(int resId, CharSequence defSequence);
	
	/**
	 * 获取文本数组
	 * @param resId 资源ID
	 * @return 文本数组
	 */
	public CharSequence[] getTextArray(int resId);
	
	/**
	 * 获取XML资源
	 * @param resId 资源ID
	 * @return XML资源
	 */
	public XmlResourceParser getXml(int resId);
	
	/**
	 * 根据布局文件ID获取视图
	 * @param resId 布局文件ID
	 * @param parentView 其父容器
	 * @return 视图
	 */
	public View getViewByLayout(int resId, ViewGroup parentView);
	
	/**
	 * 根据布局文件ID获取视图
	 * @param resId 布局文件ID
	 * @return 视图
	 */
	public View getViewByLayout(int resId);
	
	
	
	
	/* ********************************************** GET/SET ************************************************ */
	/**
	 * 获取当前Activity的ID
	 * @return
	 */
	public long getActivityId();

	/**
	 * 获取消息处理器
	 * @return 消息处理器
	 */
	public Handler getHanlder();

	/**
	 * 是否已经开启了广播接收器
	 * @return 已经开启了广播接收器
	 */
	public boolean isOpenedBroadcaseReceiver();
}