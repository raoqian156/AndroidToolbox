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
import test.MyBaseTabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * TabHost使用示例Activity
 * @author xiaopan
 *
 */
public class TabHostActivity extends MyBaseTabActivity {
	
	private static final String TAB_TAG_ONE = "TAB_TAG_ONE";
	private static final String TAB_TAG_TWO = "TAB_TAG_TWO";
	private static final String TAB_TAG_THREE = "TAB_TAG_THREE";
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_tabhost);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onInitData(Bundle savedInstanceState) {
		//添加3个选项卡
		getTabHost().addTab(createTabSpec(TAB_TAG_ONE, "选项卡1", "1"));
		getTabHost().addTab(createTabSpec(TAB_TAG_TWO, "选项卡2", "2"));
		getTabHost().addTab(createTabSpec(TAB_TAG_THREE, "选项卡3", "3"));
	}
	
	private TabSpec createTabSpec(String tag, String title, String content){
		@SuppressWarnings("deprecation")
		TabSpec tabSpec = getTabHost().newTabSpec(tag);
		
		TextView titleText = (TextView) getViewByLayout(R.layout.tab);
		titleText.setText(title);
		tabSpec.setIndicator(titleText);
		
		Intent intent = new Intent(this, TabContentActivity.class);
		intent.putExtra(TabContentActivity.PARAM_TAB_INDEX, content);
		tabSpec.setContent(intent);
		
		return tabSpec;
	}
}
