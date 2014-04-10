package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Set;

import me.xiaopan.android.easy.util.PreferenceUtils;
import me.xiaopan.java.easy.util.StringUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 注入SharedPreferences中的参数
 */
public class InjectPreferencesInterpolator implements InjectInterpolator {
	private Object object;
	private Context context;
	
	public InjectPreferencesInterpolator(Object object, Context context) {
		this.object = object;
		this.context = context;
	}

	@Override
	public void onInject(Field field) {
		InjectPreference injectPreference = field.getAnnotation(InjectPreference.class);
		SharedPreferences sharedPreferences = null;
		if(StringUtils.isNotEmpty(injectPreference.sharedPreferencesName())){
			sharedPreferences = context.getSharedPreferences(injectPreference.sharedPreferencesName(), injectPreference.mode());
		}else{
			sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		}
		Class<?> fieldType = field.getType();
		try {
			if(boolean.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, sharedPreferences.getBoolean(injectPreference.value(), injectPreference.booleanDefaultValue()));
			}else if(float.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, sharedPreferences.getFloat(injectPreference.value(), injectPreference.floatDefaultValue()));
			}else if(int.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, sharedPreferences.getInt(injectPreference.value(), injectPreference.intDefaultValue()));
			}else if(long.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, sharedPreferences.getLong(injectPreference.value(), injectPreference.longDefaultValue()));
			}else if(String.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, sharedPreferences.getString(injectPreference.value(), "".equals(injectPreference.stringDefaultValue())?null:injectPreference.stringDefaultValue()));
			}else if(Set.class.isAssignableFrom(fieldType)){
				Class<?> first = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				if(String.class.isAssignableFrom(first)){
					field.setAccessible(true);
					field.set(object, PreferenceUtils.getStringSet(sharedPreferences, injectPreference.value(), null));
				}
			}
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "注入"+object.getClass().getSimpleName()+"."+field.getName()+"出错");
			e.printStackTrace();
		}
	}
}
