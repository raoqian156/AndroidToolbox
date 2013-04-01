package test.activity.views;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import test.activity.adapter.ImageFragmentPagerAdapter;
import test.activity.fragment.ImageFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

public class ViewPagerActivity extends FragmentActivity {
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<ImageFragment> testFragmentList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_pager);
		viewPager = (ViewPager) findViewById(R.id.viewPager_viewPager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTab_viewPager);
	
		testFragmentList = new ArrayList<ImageFragment>();
		testFragmentList.add(new ImageFragment("页面1", R.drawable.liuyifei1));
		testFragmentList.add(new ImageFragment("页面2", R.drawable.liuyifei2));
		testFragmentList.add(new ImageFragment("页面3", R.drawable.liuyifei3));
		testFragmentList.add(new ImageFragment("页面4", R.drawable.liuyifei4));
		testFragmentList.add(new ImageFragment("页面5", R.drawable.liuyifei5));
		testFragmentList.add(new ImageFragment("页面6", R.drawable.liuyifei6));
		
		viewPager.setAdapter(new ImageFragmentPagerAdapter(getSupportFragmentManager(), testFragmentList));
		
		pagerTabStrip.setTextColor(0xff00bfff);//设置标题的颜色
		pagerTabStrip.setTabIndicatorColor(0xff00bfff);//设置滑块的颜色
	}
}