package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.os.Bundle;

public class ExtraInjectInterpolator implements InjectInterpolator {
	private Object object;
	private Bundle bundle;
	
	public ExtraInjectInterpolator(Object object, Bundle bundle) {
		this.object = object;
		this.bundle = bundle;
	}

	@Override
	public void onInject(Field field) {
		if(bundle != null){
			InjectExtra injectExtra = field.getAnnotation(InjectExtra.class);
			if(StringUtils.isNotEmpty(injectExtra.value())){
				field.setAccessible(true);
				try {
					field.set(object, bundle.get(injectExtra.value()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
