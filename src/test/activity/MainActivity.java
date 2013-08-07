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
package test.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.activity.animation.AnimationListActivity;
import test.activity.custom.CustomListActivity;
import test.activity.graphics.GraphicsListActivity;
import test.activity.media.MediaListActivity;
import test.activity.other.OtherListActivity;
import test.activity.views.ViewListActivity;
import test.adapter.TextAdapter;
import test.adapter.TextAdapter.Text;
import test.beans.ActivityEntry;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 主页
 */
public class MainActivity extends MyBaseActivity{
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
		setEnableDoubleClickExitApplication(true);
		
		texts = new ArrayList<Text>();
		texts.add(new ActivityEntry(getString(R.string.activityTitle_animationList), AnimationListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_graphicsList), GraphicsListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_mediaList), MediaListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_viewsList), ViewListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_customList), CustomListActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_otherList), OtherListActivity.class));
		
		listView.setAdapter(new TextAdapter(getBaseContext(), texts));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected boolean isEnableBackHome() {
		return false;
	}
}
