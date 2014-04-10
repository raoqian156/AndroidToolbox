package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 注入SharedPreferences中的Json对象
 */
public class InjectPreferencesJsonInterpolator implements InjectInterpolator {
	private Object object;
	private Context context;
	
	public InjectPreferencesJsonInterpolator(Object object, Context context) {
		this.object = object;
		this.context = context;
	}

	@Override
	public void onInject(Field field) {
		InjectPreferenceJson injectPreference = field.getAnnotation(InjectPreferenceJson.class);
		SharedPreferences sharedPreferences = null;
		if(StringUtils.isNotEmpty(injectPreference.sharedPreferencesName())){
			sharedPreferences = context.getSharedPreferences(injectPreference.sharedPreferencesName(), injectPreference.mode());
		}else{
			sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
		}
		if(StringUtils.isNotEmpty(injectPreference.value())){
			try {
				String valueObject = sharedPreferences.getString(injectPreference.value(), null);
				if(StringUtils.isNotEmpty(valueObject)){
					Gson gson = null;
					if(injectPreference.withoutExposeAnnotation()){
						gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
					}else{
						gson = new Gson();
					}
					field.setAccessible(true);
					field.set(object, gson.fromJson(valueObject, field.getType()));
				}
			} catch (Exception e) {
				Log.w(getClass().getSimpleName(), "注入"+object.getClass().getSimpleName()+"."+field.getName()+"出错");
				e.printStackTrace();
			}
		}
	}
}
