package me.xiaopan.easyandroid.app;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {
	public BaseFragmentActivity getBaseFragmentActivity(){
		if(getActivity() instanceof BaseFragmentActivity){
			return (BaseFragmentActivity) getActivity();
		}else{
			return null;
		}
	}
}