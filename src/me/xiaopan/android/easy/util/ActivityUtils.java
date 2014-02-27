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

package me.xiaopan.android.easy.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * Activity工具箱
 */
public class ActivityUtils {
	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivityClass 目标Activity的Class
	 * @param flag Intent标记，不需要的话就设为-1
	 * @param bundle 参数集
	 */
	public static void start(Context context, Class<? extends Activity> targetActivityClass, int flag, Bundle bundle){
		if(context != null && targetActivityClass != null){
			Intent intent = new Intent(context, targetActivityClass);
			if(flag > 0){
				intent.setFlags(flag);
			}
			if(bundle != null){
				intent.putExtras(bundle);
			}
			context.startActivity(intent);
		}
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivityClass 目标Activity的Class
	 * @param bundle 参数集
	 */
	public static void start(Context context, Class<? extends Activity> targetActivityClass, Bundle bundle){
		start(context, targetActivityClass, -1, bundle);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivityClass 目标Activity的Class
	 * @param flag Intent标记，不需要的话就设为-1
	 */
	public static void start(Context context, Class<? extends Activity> targetActivityClass, int flag){
		start(context, targetActivityClass, flag, null);
	}

	/**
	 * 启动Activity
	 * @param context 上下文
	 * @param targetActivityClass 目标Activity的Class
	 */
	public static void start(Context context, Class<? extends Activity> targetActivityClass){
		start(context, targetActivityClass, -1, null);
	}

	/**
	 * 启动Activity
	 * @param activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param flag Intent标记，不需要的话就设为-1
	 * @param bundle 参数集
	 */
	public static void startForResult(Activity activity, Class<? extends Activity> targetActivityClass, int requestCode, int flag, Bundle bundle){
		if(activity != null && targetActivityClass != null){
			Intent intent = new Intent(activity, targetActivityClass);
			if(flag > 0){
				intent.setFlags(flag);
			}
			if(bundle != null){
				intent.putExtras(bundle);
			}
			activity.startActivityForResult(intent, requestCode);
		}
	}

	/**
	 * 启动Activity
	 * @param activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param flag Intent标记，不需要的话就设为-1
	 */
	public static void startForResult(Activity activity, Class<? extends Activity> targetActivityClass, int requestCode, int flag){
		startForResult(activity, targetActivityClass, requestCode, flag, null);
	}

	/**
	 * 启动Activity
	 * @param activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 * @param bundle 参数集
	 */
	public static void startForResult(Activity activity, Class<? extends Activity> targetActivityClass, int requestCode, Bundle bundle){
		startForResult(activity, targetActivityClass, requestCode, -1, bundle);
	}

	/**
	 * 启动Activity
	 * @param activity
	 * @param targetActivityClass 目标Activity的Class
	 * @param requestCode 请求码
	 */
	public static void startForResult(Activity activity, Class<? extends Activity> targetActivityClass, int requestCode){
		startForResult(activity, targetActivityClass, requestCode, -1, null);
	}
}