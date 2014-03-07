package me.xiaopan.android.easy.util.inject;

import java.lang.reflect.Field;

import android.app.Activity;

public class ActivityViewInjectInterpolator implements InjectInterpolator{
	private Activity activity;
	
	public ActivityViewInjectInterpolator(Activity activity) {
		this.activity = activity;
	}

	public void onInject(Field field){
		field.setAccessible(true);
		try {
			field.set(activity, activity.findViewById(field.getAnnotation(InjectView.class).value()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
