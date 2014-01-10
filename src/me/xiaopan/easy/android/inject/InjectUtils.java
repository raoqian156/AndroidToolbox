package me.xiaopan.easy.android.inject;

import java.lang.reflect.Field;

import me.xiaopan.easy.java.util.ReflectUtils;
import android.app.Activity;

public class InjectUtils {
	public static final void injectView(Activity activity){
		for(Field field : ReflectUtils.getFields(activity.getClass(), true, true, false)){
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView != null){
				field.setAccessible(true);
				try {
					field.set(activity, activity.findViewById(injectView.value()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
