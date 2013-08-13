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
package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.adapter.TextAdapter;
import test.adapter.TextAdapter.Text;
import test.beans.ActivityEntry;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 自定义组件或特效界面
 */
public class CustomListActivity extends MyBaseActivity{
	private List<Text> texts;
	private ListView listView;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_simple_list_rebound);
		listView = (ListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				startActivity(((ActivityEntry)texts.get(arg2 - listView.getHeaderViewsCount())).getAction());
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		texts = new ArrayList<Text>();
		texts.add(new ActivityEntry(getString(R.string.activityTitle_reboundListView), ReboundListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_superListView), SuperListViewActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_picturePlayer), PicturePlayerActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_customEditText), CustomEditTextActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_slideTabHost), SlideTabHostActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_slidingToggleButton), SlidingToggleButtonActivity.class));
		
		listView.setAdapter(new TextAdapter(getBaseContext(), texts));
	}
}