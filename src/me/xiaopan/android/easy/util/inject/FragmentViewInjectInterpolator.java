package me.xiaopan.android.easy.util.inject;

import java.lang.reflect.Field;

import android.support.v4.app.Fragment;

public class FragmentViewInjectInterpolator implements InjectInterpolator{
	private Fragment fragment;
	
	public FragmentViewInjectInterpolator(Fragment fragment) {
		this.fragment = fragment;
	}

	public void onInject(Field field){
		field.setAccessible(true);
		try {
			field.set(fragment, fragment.getView().findViewById(field.getAnnotation(InjectView.class).value()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
