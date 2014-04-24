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

import java.lang.reflect.Type;
import java.util.Set;

import me.xiaopan.java.util.StringUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Preference工具箱
 */
public class PreferenceUtils {
	/**
	 * 保存一个boolean型的值到指定的Preference中
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(SharedPreferences sharedPreferences, String key, boolean value){
		sharedPreferences.edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 保存一个boolean型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String sharedPreferencesName, int mode, String key, boolean value){
		putBoolean(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个boolean型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String sharedPreferencesName, String key, boolean value){
		putBoolean(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个boolean型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String key, boolean value){
		putBoolean(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个boolean型的值
	 * @param sharedPreferences
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final boolean getBoolean(SharedPreferences sharedPreferences, String key, boolean defaultValue){
		return sharedPreferences.getBoolean(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个boolean型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final boolean getBoolean(Context context, String sharedPreferencesName, int mode, String key, boolean defaultValue){
		return getBoolean(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个boolean型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final boolean getBoolean(Context context, String sharedPreferencesName, String key, boolean defaultValue){
		return getBoolean(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个boolean型的值，默认值为false
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final boolean getBoolean(Context context, String sharedPreferencesName, String key){
		return getBoolean(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, false);
	}
	
	/**
	 * 从默认的Preference中取出一个boolean型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final boolean getBoolean(Context context, String key, boolean defaultValue){
		return getBoolean(PreferenceManager.getDefaultSharedPreferences(context), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个boolean型的值，默认值为false
	 * @param context
	 * @param key
	 * @return
	 */
	public static final boolean getBoolean(Context context, String key){
		return getBoolean(PreferenceManager.getDefaultSharedPreferences(context), key, false);
	}
	
	/**
	 * 保存一个float型的值到指定的Preference中
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	public static final void putFloat(SharedPreferences sharedPreferences, String key, float value){
		sharedPreferences.edit().putFloat(key, value).commit();
	}
	
	/**
	 * 保存一个float型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putFloat(Context context, String sharedPreferencesName, int mode, String key, float value){
		putFloat(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个float型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putFloat(Context context, String sharedPreferencesName, String key, float value){
		putFloat(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个float型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putFloat(Context context, String key, float value){
		putFloat(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个float型的值
	 * @param sharedPreferences
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final float getFloat(SharedPreferences sharedPreferences, String key, float defaultValue){
		return sharedPreferences.getFloat(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个float型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final float getFloat(Context context, String sharedPreferencesName, int mode, String key, float defaultValue){
		return getFloat(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个float型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final float getFloat(Context context, String sharedPreferencesName, String key, float defaultValue){
		return getFloat(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个float型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final float getFloat(Context context, String sharedPreferencesName, String key){
		return getFloat(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个float型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final float getFloat(Context context, String key, float defaultValue){
		return getFloat(PreferenceManager.getDefaultSharedPreferences(context), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个float型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final float getFloat(Context context, String key){
		return getFloat(PreferenceManager.getDefaultSharedPreferences(context), key, 0);
	}
	
	/**
	 * 保存一个int型的值到指定的Preference中
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	public static final void putInt(SharedPreferences sharedPreferences, String key, int value){
		sharedPreferences.edit().putInt(key, value).commit();
	}
	
	/**
	 * 保存一个int型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putInt(Context context, String sharedPreferencesName, int mode, String key, int value){
		putInt(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个int型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putInt(Context context, String sharedPreferencesName, String key, int value){
		putInt(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个int型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putInt(Context context, String key, int value){
		putInt(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值
	 * @param sharedPreferences
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final int getInt(SharedPreferences sharedPreferences, String key, int defaultValue){
		return sharedPreferences.getInt(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final int getInt(Context context, String sharedPreferencesName, int mode, String key, int defaultValue){
		return getInt(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final int getInt(Context context, String sharedPreferencesName, String key, int defaultValue){
		return getInt(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final int getInt(Context context, String sharedPreferencesName, String key){
		return getInt(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final int getInt(Context context, String key, int defaultValue){
		return getInt(PreferenceManager.getDefaultSharedPreferences(context), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final int getInt(Context context, String key){
		return getInt(PreferenceManager.getDefaultSharedPreferences(context), key, 0);
	}
	
	/**
	 * 保存一个long型的值到指定的Preference中
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	public static final void putLong(SharedPreferences sharedPreferences, String key, long value){
		sharedPreferences.edit().putLong(key, value).commit();
	}
	
	/**
	 * 保存一个long型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putLong(Context context, String sharedPreferencesName, int mode, String key, long value){
		putLong(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个long型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putLong(Context context, String sharedPreferencesName, String key, long value){
		putLong(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个long型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putLong(Context context, String key, long value){
		putLong(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个long型的值
	 * @param sharedPreferences
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final long getLong(SharedPreferences sharedPreferences, String key, long defaultValue){
		return sharedPreferences.getLong(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个long型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final long getLong(Context context, String sharedPreferencesName, int mode, String key, long defaultValue){
		return getLong(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个long型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final long getLong(Context context, String sharedPreferencesName, String key, long defaultValue){
		return getLong(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个long型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final long getLong(Context context, String sharedPreferencesName, String key){
		return getLong(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个long型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final long getLong(Context context, String key, long defaultValue){
		return getLong(PreferenceManager.getDefaultSharedPreferences(context), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个long型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final long getLong(Context context, String key){
		return getLong(PreferenceManager.getDefaultSharedPreferences(context), key, 0);
	}
	
	/**
	 * 保存一个String型的值到指定的Preference中
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	public static final void putString(SharedPreferences sharedPreferences, String key, String value){
		sharedPreferences.edit().putString(key, value).commit();
	}
	
	/**
	 * 保存一个String型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putString(Context context, String sharedPreferencesName, int mode, String key, String value){
		putString(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个String型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putString(Context context, String sharedPreferencesName, String key, String value){
		putString(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个String型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putString(Context context, String key, String value){
		putString(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个String型的值
	 * @param sharedPreferences
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final String getString(SharedPreferences sharedPreferences, String key, String defaultValue){
		return sharedPreferences.getString(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个String型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final String getString(Context context, String sharedPreferencesName, int mode, String key, String defaultValue){
		return getString(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个String型的值
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static final String getString(Context context, String sharedPreferencesName, String key, String defaultValue){
		return getString(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个String型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final String getString(Context context, String key, String defaultValue){
		return getString(PreferenceManager.getDefaultSharedPreferences(context), key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个String型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final String getString(Context context, String key){
		return getString(PreferenceManager.getDefaultSharedPreferences(context), key, null);
	}
	
	/**
	 * 保存一个Set<String>到指定的Preference中，如果当前系统的SDK版本小于11，则会将Set<String>转换成JSON字符串保存
	 * @param sharedPreferences
	 * @param key
	 * @param value
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static final void putStringSet(SharedPreferences sharedPreferences, String key, Set<String> value){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			sharedPreferences.edit().putStringSet(key, value).commit();
		}else{
			putObject(sharedPreferences, key, value);
		}
	}
	
	/**
	 * 保存一个Set<String>到指定的Preference中，如果当前系统的SDK版本小于11，则会将Set<String>转换成JSON字符串保存
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putStringSet(Context context, String sharedPreferencesName, int mode, String key, Set<String> value){
		putStringSet(context.getSharedPreferences(sharedPreferencesName, mode), key, value);
	}
	
	/**
	 * 保存一个Set<String>到指定的Preference中，如果当前系统的SDK版本小于11，则会将Set<String>转换成JSON字符串保存
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putStringSet(Context context, String sharedPreferencesName, String key, Set<String> value){
		putStringSet(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, value);
	}
	
	/**
	 * 保存一个Set<String>到默认的Preference中，如果当前系统的SDK版本小于11，则会将Set<String>转换成JSON字符串保存
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putStringSet(Context context, String key, Set<String> value){
		putStringSet(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从指定的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static final Set<String> getStringSet(SharedPreferences preferences, String key, Set<String> defaultVaule){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			return preferences.getStringSet(key, defaultVaule);
		}else{
			Set<String> strings = getObject(preferences, key, new TypeToken<Set<String>>(){}.getType());
			if(strings == null){
				strings = defaultVaule;
			}
			return strings;
		}
	}
	
	/**
	 * 从指定的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final Set<String> getStringSet(Context context, String sharedPreferencesName, int mode, String key, Set<String> defaultVaule){
		return getStringSet(context.getSharedPreferences(sharedPreferencesName, mode), key, defaultVaule);
	}
	
	/**
	 * 从指定的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final Set<String> getStringSet(Context context, String sharedPreferencesName, String key, Set<String> defaultVaule){
		return getStringSet(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, defaultVaule);
	}
	
	/**
	 * 从指定的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final Set<String> getStringSet(Context context, String sharedPreferencesName, String key){
		return getStringSet(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, null);
	}
	
	/**
	 * 从默认的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final Set<String> getStringSet(Context context, String key, Set<String> defaultVaule){
		return getStringSet(PreferenceManager.getDefaultSharedPreferences(context), key, defaultVaule);
	}
	
	/**
	 * 从默认的Preference中取出一个Set<String>，如果当前系统的SDK版本小于11，则会先取出JSON字符串然后再转换成Set<String>
	 * @param context
	 * @param key
	 * @return
	 */
	public static final Set<String> getStringSet(Context context, String key){
		return getStringSet(PreferenceManager.getDefaultSharedPreferences(context), key, null);
	}
	
	/**
	 * 保存一个对象到指定的Preference中，此对象会被格式化为JSON格式再存
	 * @param sharedPreferences
	 * @param key
	 * @param object
	 */
	public static final void putObject(SharedPreferences sharedPreferences, String key, Object object){
		sharedPreferences.edit().putString(key, new Gson().toJson(object)).commit();
	}
	
	/**
	 * 保存一个对象到指定的Preference中，此对象会被格式化为JSON格式再存
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param object
	 */
	public static final void putObject(Context context, String sharedPreferencesName, int mode, String key, Object object){
		putObject(context.getSharedPreferences(sharedPreferencesName, mode), key, object);
	}
	
	/**
	 * 保存一个对象到指定的Preference中，此对象会被格式化为JSON格式再存
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param object
	 */
	public static final void putObject(Context context, String sharedPreferencesName, String key, Object object){
		putObject(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, object);
	}
	
	/**
	 * 保存一个对象到默认的Preference中，此对象会被格式化为JSON格式再存
	 * @param context
	 * @param key
	 * @param object
	 */
	public static final void putObject(Context context, String key, Object object){
		putObject(PreferenceManager.getDefaultSharedPreferences(context), key, object);
	}
	
	/**
	 * 从指定的Preference中取出一个对象
	 * @param sharedPreferences
	 * @param key
	 * @param clas
	 * @return
	 */
	public static final <T> T getObject(SharedPreferences sharedPreferences, String key, Class<T> clas){
		String configJson = sharedPreferences.getString(key, null);
		if(StringUtils.isNotEmpty(configJson)){
			return (T) new Gson().fromJson(configJson, clas);
		}else{
			return null;
		}
	}
	
	/**
	 * 从指定的Preference中取出一个对象
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param clas
	 * @return
	 */
	public static final <T> T getObject(Context context, String sharedPreferencesName, int mode, String key, Class<T> clas){
		return getObject(context.getSharedPreferences(sharedPreferencesName, mode), key, clas);
	}
	
	/**
	 * 从指定的Preference中取出一个对象
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param clas
	 * @return
	 */
	public static final <T> T getObject(Context context, String sharedPreferencesName, String key, Class<T> clas){
		return getObject(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, clas);
	}
	
	/**
	 * 从默认的Preference中取出一个对象
	 * @param context
	 * @param key
	 * @param clas
	 * @return
	 */
	public static final <T> T getObject(Context context, String key, Class<T> clas){
		return getObject(PreferenceManager.getDefaultSharedPreferences(context), key, clas);
	}
	
	/**
	 * 从指定的Preference中取出一个对象
	 * @param preferences
	 * @param key
	 * @param typeofT
	 * @return
	 */
	public static final <T> T getObject(SharedPreferences preferences, String key, Type typeofT){
		String configJson = preferences.getString(key, null);
		if(StringUtils.isNotEmpty(configJson)){
			return new Gson().fromJson(configJson, typeofT);
		}else{
			return null;
		}
	}

	/**
	 * 从指定的Preference中取出一个对象
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param typeofT
	 * @return
	 */
	public static final <T> T getObject(Context context, String sharedPreferencesName, int mode, String key, Type typeofT){
		return getObject(context.getSharedPreferences(sharedPreferencesName, mode), key, typeofT);
	}

	/**
	 * 从指定的Preference中取出一个对象
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param typeofT
	 * @return
	 */
	public static final <T> T getObject(Context context, String sharedPreferencesName, String key, Type typeofT){
		return getObject(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key, typeofT);
	}

	/**
	 * 从默认的Preference中取出一个对象
	 * @param context
	 * @param key
	 * @param typeofT
	 * @return
	 */
	public static final <T> T getObject(Context context, String key, Type typeofT){
		return getObject(PreferenceManager.getDefaultSharedPreferences(context), key, typeofT);
	}
	
	/**
	 * 删除
	 * @param preferences
	 * @param keys
	 */
	public static final void remove(SharedPreferences preferences, String[] keys){
		Editor editor = preferences.edit();
		for(String key : keys){
			editor.remove(key);
		}
		editor.commit();
	}
	
	/**
	 * 删除
	 * @param preferences
	 * @param key
	 */
	public static final void remove(SharedPreferences preferences, String key){
		preferences.edit().remove(key).commit();
	}
	
	/**
	 * 删除
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param keys
	 */
	public static final void remove(Context context, String sharedPreferencesName, int mode, String[] keys){
		remove(context.getSharedPreferences(sharedPreferencesName, mode), keys);
	}
	
	/**
	 * 删除
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 */
	public static final void remove(Context context, String sharedPreferencesName, int mode, String key){
		remove(context.getSharedPreferences(sharedPreferencesName, mode), key);
	}
	
	/**
	 * 删除
	 * @param context
	 * @param sharedPreferencesName
	 * @param keys
	 */
	public static final void remove(Context context, String sharedPreferencesName, String[] keys){
		remove(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), keys);
	}
	
	/**
	 * 删除
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 */
	public static final void remove(Context context, String sharedPreferencesName, String key){
		remove(context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE), key);
	}
	
	/**
	 * 删除
	 * @param context
	 * @param keys
	 */
	public static final void remove(Context context, String[] keys){
		remove(PreferenceManager.getDefaultSharedPreferences(context), keys);
	}
	
	/**
	 * 删除
	 * @param context
	 * @param key
	 */
	public static final void remove(Context context, String key){
		remove(PreferenceManager.getDefaultSharedPreferences(context), key);
	}
}