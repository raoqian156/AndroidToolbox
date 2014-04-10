package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

public class InjectViewInterpolator implements InjectInterpolator{
	private Object object;
	private OnFindViewInterpolator onFindViewInterpolator;
	
	public InjectViewInterpolator(Activity activity) {
		this.object = activity;
		this.onFindViewInterpolator = new ActivityFindInjectInterpolator(activity);
	}
	
	public InjectViewInterpolator(Fragment fragment) {
		this.object = fragment;
		this.onFindViewInterpolator = new FragmentFindViewInterpolator(fragment);
	}

	public void onInject(Field field){
		try {
			View view = onFindViewInterpolator.onFindViewByeId(field.getAnnotation(InjectView.class).value());
			if(view != null){
				field.setAccessible(true);
				field.set(object, view);
			}
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "注入"+object.getClass().getSimpleName()+"."+field.getName()+"出错");
			e.printStackTrace();
		}
	}
	
	private interface OnFindViewInterpolator{
		public View onFindViewByeId(int viewId);
	}
	
	private class ActivityFindInjectInterpolator implements OnFindViewInterpolator{
		private Activity activity;
		
		public ActivityFindInjectInterpolator(Activity activity) {
			this.activity = activity;
		}

		@Override
		public View onFindViewByeId(int viewId) {
			return activity.findViewById(viewId);
		}
	}
	
	private class FragmentFindViewInterpolator implements OnFindViewInterpolator{
		private Fragment fragment;
		
		public FragmentFindViewInterpolator(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public View onFindViewByeId(int viewId) {
			return fragment.getView().findViewById(viewId);
		}
	}
}
