package me.xiaopan.android.easy.util;

import java.lang.reflect.Type;
import java.util.Set;

import me.xiaopan.java.easy.util.StringUtils;
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
	 * @param context
	 * @param sharedPreferencesName
	 * @param mode
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String sharedPreferencesName, int mode, String key, boolean value){
		context.getSharedPreferences(sharedPreferencesName, mode).edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 保存一个boolean型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String sharedPreferencesName, String key, boolean value){
		context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
	}
	
	/**
	 * 保存一个boolean型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putBoolean(Context context, String key, boolean value){
		PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).commit();
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
		return context.getSharedPreferences(sharedPreferencesName, mode).getBoolean(key, defaultValue);
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
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getBoolean(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个boolean型的值，默认值为false
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final boolean getBoolean(Context context, String sharedPreferencesName, String key){
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getBoolean(key, false);
	}
	
	/**
	 * 从默认的Preference中取出一个boolean型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final boolean getBoolean(Context context, String key, boolean defaultValue){
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个boolean型的值，默认值为false
	 * @param context
	 * @param key
	 * @return
	 */
	public static final boolean getBoolean(Context context, String key){
		return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, false);
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
		context.getSharedPreferences(sharedPreferencesName, mode).edit().putFloat(key, value).commit();
	}
	
	/**
	 * 保存一个float型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putFloat(Context context, String sharedPreferencesName, String key, float value){
		context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit().putFloat(key, value).commit();
	}
	
	/**
	 * 保存一个float型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putFloat(Context context, String key, float value){
		PreferenceManager.getDefaultSharedPreferences(context).edit().putFloat(key, value).commit();
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
		return context.getSharedPreferences(sharedPreferencesName, mode).getFloat(key, defaultValue);
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
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getFloat(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final float getFloat(Context context, String sharedPreferencesName, String key){
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getFloat(key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final float getFloat(Context context, String key, float defaultValue){
		return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final float getFloat(Context context, String key){
		return PreferenceManager.getDefaultSharedPreferences(context).getFloat(key, 0);
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
		context.getSharedPreferences(sharedPreferencesName, mode).edit().putInt(key, value).commit();
	}
	
	/**
	 * 保存一个int型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putInt(Context context, String sharedPreferencesName, String key, int value){
		context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit().putInt(key, value).commit();
	}
	
	/**
	 * 保存一个int型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putInt(Context context, String key, int value){
		PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).commit();
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
		return context.getSharedPreferences(sharedPreferencesName, mode).getInt(key, defaultValue);
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
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getInt(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final int getInt(Context context, String sharedPreferencesName, String key){
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getInt(key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final int getInt(Context context, String key, int defaultValue){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个int型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final int getInt(Context context, String key){
		return PreferenceManager.getDefaultSharedPreferences(context).getInt(key, 0);
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
		context.getSharedPreferences(sharedPreferencesName, mode).edit().putLong(key, value).commit();
	}
	
	/**
	 * 保存一个long型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putLong(Context context, String sharedPreferencesName, String key, long value){
		context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit().putLong(key, value).commit();
	}
	
	/**
	 * 保存一个long型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putLong(Context context, String key, long value){
		PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).commit();
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
		return context.getSharedPreferences(sharedPreferencesName, mode).getLong(key, defaultValue);
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
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getLong(key, defaultValue);
	}
	
	/**
	 * 从指定的Preference中取出一个long型的值，默认值为0
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @return
	 */
	public static final long getLong(Context context, String sharedPreferencesName, String key){
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getLong(key, 0);
	}
	
	/**
	 * 从默认的Preference中取出一个long型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final long getLong(Context context, String key, long defaultValue){
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个long型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final long getLong(Context context, String key){
		return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, 0);
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
		context.getSharedPreferences(sharedPreferencesName, mode).edit().putString(key, value).commit();
	}
	
	/**
	 * 保存一个String型的值到指定的Preference中
	 * @param context
	 * @param sharedPreferencesName
	 * @param key
	 * @param value
	 */
	public static final void putString(Context context, String sharedPreferencesName, String key, String value){
		context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).edit().putString(key, value).commit();
	}
	
	/**
	 * 保存一个String型的值到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	public static final void putString(Context context, String key, String value){
		PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).commit();
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
		return context.getSharedPreferences(sharedPreferencesName, mode).getString(key, defaultValue);
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
		return context.getSharedPreferences(sharedPreferencesName, Context.MODE_PRIVATE).getString(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个String型的值
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	public static final String getString(Context context, String key, String defaultValue){
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
	}
	
	/**
	 * 从默认的Preference中取出一个String型的值，默认值为0
	 * @param context
	 * @param key
	 * @return
	 */
	public static final String getString(Context context, String key){
		return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
	}
	
	
	/**
	 * 保存一个Set<String>
	 * @param context
	 * @param key
	 * @param value
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static final void putStringSet(SharedPreferences preferences, String key, Set<String> value){
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			preferences.edit().putStringSet(key, value).commit();
		}else{
			putObject(preferences, key, value);
		}
	}
	
	/**
	 * 取出一个Set<String>
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
	 * 保存一个Set<String>到默认的Preference中
	 * @param context
	 * @param key
	 * @param value
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static final void putStringSet(Context context, String key, Set<String> value){
		putStringSet(PreferenceManager.getDefaultSharedPreferences(context), key, value);
	}
	
	/**
	 * 从默认的Preference中取出一个Set<String>
	 * @param context
	 * @param key
	 * @param defaultVaule
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	public static final Set<String> getStringSet(Context context, String key, Set<String> defaultVaule){
		return getStringSet(PreferenceManager.getDefaultSharedPreferences(context), key, defaultVaule);
	}
	
	/**
	 * 保存一个对象，此对象会被格式化为JSON格式再存
	 * @param preferences
	 * @param key
	 * @param object
	 */
	public static final void putObject(SharedPreferences preferences, String key, Object object){
		preferences.edit().putString(key, new Gson().toJson(object)).commit();
	}
	
	/**
	 * 取出一个对象
	 * @param preferences
	 * @param key
	 * @param clas
	 * @return
	 */
	public static final <T> T getObject(SharedPreferences preferences, String key, Class<T> clas){
		String configJson = preferences.getString(key, null);
		if(StringUtils.isNotEmpty(configJson)){
			return (T) new Gson().fromJson(configJson, clas);
		}else{
			return null;
		}
	}
	
	/**
	 * 取出一个对象
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
	 * 保存一个对象到默认的Preference中，此对象会被格式化为JSON格式再存
	 * @param context
	 * @param key
	 * @param object
	 */
	public static final void putObject(Context context, String key, Object object){
		putObject(PreferenceManager.getDefaultSharedPreferences(context), key, object);
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
	public static final void remove(SharedPreferences preferences, String... keys){
		Editor editor = preferences.edit();
		for(String key : keys){
			editor.remove(key);
		}
		editor.commit();
	}
	
	/**
	 * 删除
	 * @param context
	 * @param keys
	 */
	public static final void remove(Context context, String... keys){
		Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
		for(String key : keys){
			editor.remove(key);
		}
		editor.commit();
	}
}