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

package me.xiaopan.android.easy.fragment;

import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.util.ActivityUtils;
import me.xiaopan.android.easy.util.EasyHandler;
import me.xiaopan.android.easy.util.NetworkUtils;
import me.xiaopan.android.easy.util.ToastUtils;
import roboguice.fragment.RoboListFragment;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EasyListFragment extends RoboListFragment {
	private EasyHandler handler;
	
	public EasyListFragment() {
		handler = new EasyHandler(){
			@Override
			public void handleMessage(Message msg) {
				onHandleMessage(msg);
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		InjectContentView injectContentView = getClass().getAnnotation(InjectContentView.class); 
		if(injectContentView != null){
			return inflater.inflate(injectContentView.value(), null);
		}else{
			return null;
		}
	}
	
	/**
	 * 当需要处理消息
	 * @param message
	 */
	protected void onHandleMessage(Message message){
		
	}

	/**
	 * 网络是否可用
	 * @return
	 */
	public boolean isNetworkAvailable() {
		return !isDetached()?NetworkUtils.isConnectedByState(getActivity()):true;
	}
	
	/**
	 * 吐出一个显示时间较长的提示
	 * @param view
	 */
	public void toastL(final View view){
		handler.post(new Runnable() {
			@Override
			public void run() {
				ToastUtils.toastL(getActivity(), view);
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
				ToastUtils.toastS(getActivity(), view);
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
				ToastUtils.toastL(getActivity(), content);
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
				ToastUtils.toastS(getActivity(), content);
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
				ToastUtils.toastL(getActivity(), resId);
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
				ToastUtils.toastS(getActivity(), resId);
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
				ToastUtils.toastL(getActivity(), String.format(format, args));
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
				ToastUtils.toastS(getActivity(), String.format(format, args));
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
				ToastUtils.toastL(getActivity(), getString(formatResId, args));
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
				ToastUtils.toastS(getActivity(), getString(formatResId, args));
			}
		});
	}
	
	/**
	 * 获取Handler
	 * @return
	 */
	public Handler getHandler() {
		return handler;
	}

	@Override
	public void onDetach() {
		handler.destroy();
		super.onDetach();
	}
	
	/**
	 * 启动Activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param flag Intent标记，不需要的话就设为-1
	 * @param bundle 参数集
	 */
	public void startActivity(final Class<? extends Activity> targetActivityClass, final int flag, final Bundle bundle){
		ActivityUtils.start(getActivity(), targetActivityClass, flag, bundle);
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
		ActivityUtils.startForResult(getActivity(), targetActivityClass, requestCode, flag, bundle);
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
}
