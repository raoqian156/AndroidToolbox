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

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;

/**
 * 应用程序相关的工具方法
 */
public class ApplicationUtils {
	private static final String VERSION_CODE = "VERSION_CODE";
	
	/**
	 * 判断程序版本号是否发生了改变，当返回true时会自动将新的版本号更新到本地
	 * @param context
	 * @return
	 */
	public static boolean isVersionCodeChange(Context context){
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context); 
		int currentVersionCode = 0;
		try {
			currentVersionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		boolean result = currentVersionCode != sharedPreferences.getInt(VERSION_CODE, -Integer.MIN_VALUE);
		if(result){
			Editor editor = sharedPreferences.edit();
			editor.putInt(VERSION_CODE, currentVersionCode);
			editor.commit();
		}
		return result;
	}
}