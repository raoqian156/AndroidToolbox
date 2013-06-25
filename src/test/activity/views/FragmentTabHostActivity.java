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

import me.xiaopan.easyandroid.R;
import test.MyBaseFragmentActivity;
import test.activity.fragment.Image2Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * FragmentTabHost使用示例Activity
 * @author xiaopan
 *
 */
public class FragmentTabHostActivity extends MyBaseFragmentActivity {
	private static final String TAB_TAG_ONE = "TAB_TAG_ONE";
	private static final String TAB_TAG_TWO = "TAB_TAG_TWO";
	private static final String TAB_TAG_THREE = "TAB_TAG_THREE";
	private static final String TAB_TAG_FOUR = "TAB_TAG_FOUR";
	private FragmentTabHost fragmentTabHost;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_fragment_tabhost);
		fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		fragmentTabHost.setup(getBaseContext(), getSupportFragmentManager(), R.id.realtabcontent);
		fragmentTabHost.getTabWidget().setDividerDrawable(R.drawable.tabs_divider);
		
		fragmentTabHost.addTab(createTabSpec(TAB_TAG_ONE, "选项卡1"), Image2Fragment.class, getBundle(R.drawable.image_liuyifei1));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_TWO, "选项卡2"), Image2Fragment.class, getBundle(R.drawable.image_liuyifei2));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_THREE, "选项卡3"), Image2Fragment.class, getBundle(R.drawable.image_liuyifei3));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_FOUR, "选项卡4"), Image2Fragment.class, getBundle(R.drawable.image_liuyifei4));
	}
	
	private TabSpec createTabSpec(String tag, String title){
		TabSpec tabSpec = fragmentTabHost.newTabSpec(tag);
		TextView titleText = (TextView) getViewByLayout(R.layout.tab);
		titleText.setText(title);
		tabSpec.setIndicator(titleText);
		return tabSpec;
	}
	
	public Bundle getBundle(int imageResId){
		Bundle bundle = new Bundle();
		bundle.putInt(Image2Fragment.PARAM_REQUIRED_IMAHE_RES_ID, imageResId);
		return bundle;
	}
}
