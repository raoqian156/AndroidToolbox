package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;

public class ViewInjectInterpolator implements InjectInterpolator{
	private Object object;
	private OnFindViewInterpolator onFindViewInterpolator;
	
	public ViewInjectInterpolator(Activity activity) {
		this.object = activity;
		this.onFindViewInterpolator = new ActivityFindInjectInterpolator(activity);
	}
	
	public ViewInjectInterpolator(Fragment fragment) {
		this.object = fragment;
		this.onFindViewInterpolator = new FragmentFindViewInterpolator(fragment);
	}

	public void onInject(Field field){
		field.setAccessible(true);
		try {
			field.set(object, onFindViewInterpolator.onFindViewByeId(field.getAnnotation(InjectView.class).value()));
		} catch (Exception e) {
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
