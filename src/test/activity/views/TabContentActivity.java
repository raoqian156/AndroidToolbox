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
import test.MyBaseListActivity;
import test.activity.custom.SuperListViewActivity;
import test.adapter.StringAdapter;
import android.os.Bundle;

public class TabContentActivity extends MyBaseListActivity {

	public static final String PARAM_TAB_INDEX = "PARAM_SHOW_CONTENT";
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		getListView().setAdapter(new StringAdapter(getBaseContext(), SuperListViewActivity.getStrings("这是选项卡 "+getIntent().getStringExtra(PARAM_TAB_INDEX)+" 的第 %s 条数据", 20)));
	}

	@Override
	public boolean isRemoveTitleBar() {
		return true;
	}
}