/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.activity.views;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easy.android.R;
import test.MyBaseFragmentActivity;
import test.activity.fragment.ImageFragment;
import test.adapter.ImageFragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;

public class ViewPagerActivity extends MyBaseFragmentActivity {
	private ViewPager viewPager;
	private PagerTabStrip pagerTabStrip;
	private List<ImageFragment> testFragmentList;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_view_pager);
		viewPager = (ViewPager) findViewById(R.id.viewPager_viewPager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagerTab_viewPager);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		testFragmentList = new ArrayList<ImageFragment>();
		testFragmentList.add(new ImageFragment("页面1", R.drawable.image_liuyifei1));
		testFragmentList.add(new ImageFragment("页面2", R.drawable.image_liuyifei2));
		testFragmentList.add(new ImageFragment("页面3", R.drawable.image_liuyifei3));
		testFragmentList.add(new ImageFragment("页面4", R.drawable.image_liuyifei4));
		testFragmentList.add(new ImageFragment("页面5", R.drawable.image_liuyifei5));
		testFragmentList.add(new ImageFragment("页面6", R.drawable.image_liuyifei6));
		
		viewPager.setAdapter(new ImageFragmentPagerAdapter(getSupportFragmentManager(), testFragmentList));
		
		pagerTabStrip.setTextColor(0xff00bfff);//设置标题的颜色
		pagerTabStrip.setTabIndicatorColor(0xff00bfff);//设置滑块的颜色
	}
}
