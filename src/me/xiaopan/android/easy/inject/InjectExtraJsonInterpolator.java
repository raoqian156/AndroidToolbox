package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class InjectExtraJsonInterpolator implements InjectInterpolator {
	private Object object;
	private Bundle bundle;
	
	public InjectExtraJsonInterpolator(Object object, Bundle bundle) {
		this.object = object;
		this.bundle = bundle;
	}

	@Override
	public void onInject(Field field) {
		if(bundle != null){
			InjectExtraJson injectExtraJson = field.getAnnotation(InjectExtraJson.class);
			if(StringUtils.isNotEmpty(injectExtraJson.value())){
				try {
					String valueObject = bundle.getString(injectExtraJson.value());
					if(StringUtils.isNotEmpty(valueObject)){
						Gson gson = null;
						if(injectExtraJson.withoutExposeAnnotation()){
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
}
