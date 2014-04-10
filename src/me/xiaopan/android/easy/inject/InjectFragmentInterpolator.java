package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

public class InjectFragmentInterpolator implements InjectInterpolator{
	private FragmentActivity fragmentActivity;
	
	public InjectFragmentInterpolator(FragmentActivity fragmentActivity) {
		super();
		this.fragmentActivity = fragmentActivity;
	}

	@Override
	public void onInject(Field field) {
		InjectFragment injectFragment = field.getAnnotation(InjectFragment.class);
		try {
			if(injectFragment.value() > 0){
				field.setAccessible(true);
				field.set(fragmentActivity, fragmentActivity.getSupportFragmentManager().findFragmentById(injectFragment.value()));
			}else if(StringUtils.isNotEmpty(injectFragment.tag())){
				field.setAccessible(true);
				field.set(fragmentActivity, fragmentActivity.getSupportFragmentManager().findFragmentByTag(injectFragment.tag()));
			}
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "注入"+fragmentActivity.getClass().getSimpleName()+"."+field.getName()+"出错");
			e.printStackTrace();
		}
	}
}
