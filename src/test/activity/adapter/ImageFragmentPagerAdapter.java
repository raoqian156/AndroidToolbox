package test.activity.adapter;

import java.util.List;

import test.activity.fragment.ImageFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<ImageFragment> testFragmentList;
	
	public ImageFragmentPagerAdapter(FragmentManager fragmentManager,  List<ImageFragment> testFragmentList){
		super(fragmentManager);
		this.testFragmentList = testFragmentList;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return testFragmentList.get(arg0);
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return testFragmentList.get(position).getTitle();
    }

	@Override
	public int getCount() {
		return testFragmentList.size();
	}
}