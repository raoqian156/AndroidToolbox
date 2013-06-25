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

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;

public class TabContentActivity extends MyBaseActivity {

	public static final String PARAM_TAB_INDEX = "PARAM_SHOW_CONTENT";
	private PullListView pullListView;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(pullListView = (PullListView) getViewByLayout(R.layout.list));
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		String tabIndex = getIntent().getStringExtra(PARAM_TAB_INDEX);
		List<String> contents = new ArrayList<String>(20);
		for(int w = 0; w < 20; w++){
			contents.add("这是选项卡 "+tabIndex+" 的第 "+w+" 条数据");
		}
		pullListView.openListHeaderReboundMode();
		pullListView.openListFooterReboundMode();
		pullListView.setAdapter(new SimpleAdapter(getBaseContext(), contents));
	}

	@Override
	public boolean isRemoveTitleBar() {
		return true;
	}
}
