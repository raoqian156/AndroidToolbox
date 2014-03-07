package me.xiaopan.android.easy.util.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.support.v4.app.FragmentActivity;

public class FragmentInjectInterpolator implements InjectInterpolator{
	private FragmentActivity fragmentActivity;
	
	public FragmentInjectInterpolator(FragmentActivity fragmentActivity) {
		super();
		this.fragmentActivity = fragmentActivity;
	}

	@Override
	public void onInject(Field field) {
		InjectFragment injectFragment = field.getAnnotation(InjectFragment.class);
		field.setAccessible(true);
		try {
			if(injectFragment.value() > 0){
				field.set(fragmentActivity, fragmentActivity.getSupportFragmentManager().findFragmentById(injectFragment.value()));
			}else if(StringUtils.isNotEmpty(injectFragment.tag())){
				field.set(fragmentActivity, fragmentActivity.getSupportFragmentManager().findFragmentByTag(injectFragment.tag()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
