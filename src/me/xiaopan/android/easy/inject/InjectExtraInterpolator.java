package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.os.Bundle;
import android.util.Log;

public class InjectExtraInterpolator implements InjectInterpolator {
	private Object object;
	private Bundle bundle;
	
	public InjectExtraInterpolator(Object object, Bundle bundle) {
		this.object = object;
		this.bundle = bundle;
	}

	@Override
	public void onInject(Field field) {
		if(bundle != null){
			InjectExtra injectExtra = field.getAnnotation(InjectExtra.class);
			if(StringUtils.isNotEmpty(injectExtra.value())){
				try {
					Object valueObject = bundle.get(injectExtra.value());
					if(valueObject != null){
						field.setAccessible(true);
						field.set(object, valueObject);
					}
				} catch (Exception e) {
					Log.w(getClass().getSimpleName(), "注入"+object.getClass().getSimpleName()+"."+field.getName()+"出错");
					e.printStackTrace();
				}
			}
		}
	}
}
