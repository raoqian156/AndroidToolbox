package me.xiaopan.androidlibrary.widget;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
	private List<Fragment> fragmentList;
	
	public MyFragmentPagerAdapter(FragmentManager fragmentManager,  List<Fragment> fragmentList){
		super(fragmentManager);
		this.fragmentList = fragmentList;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return fragmentList.get(arg0);
	}
	
	@Override
	public int getCount() {
		return fragmentList.size();
	}
}