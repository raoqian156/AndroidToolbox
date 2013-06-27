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
import me.xiaopan.easyandroid.widget.superlist.SuperListView;
import me.xiaopan.easyandroid.widget.superlist.SuperListView.OnLoadMoreListener;
import me.xiaopan.easyandroid.widget.superlist.SuperListView.OnRefreshListener;
import me.xiaopan.easyjava.util.DateTimeUtils;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import test.widget.LoadMoreListFooter;
import test.widget.PulldownRefreshListHeader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 下拉刷新列表
 */
public class SuperListViewActivity extends MyBaseActivity {
	public static final String[] CONTENS = new String[20];
	private SuperListView superListView;
	private List<String> contents;
	private SimpleAdapter simpleAdapter;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.list_super);
		superListView = (SuperListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		superListView.setPulldownRefershListHeader(new PulldownRefreshListHeader(getBaseContext()));
		superListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new AsyncTask<String, String, String>() {
					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						contents.clear();
						for(String string : getContents()){
							contents.add(string);
						}
						simpleAdapter.notifyDataSetChanged();
						superListView.finishRefresh();
					}
				}.execute("");
			}
		});
		
		superListView.setLoadMoreListFooter(new LoadMoreListFooter(getBaseContext()));
		superListView.setOnLoadMoreListener(new OnLoadMoreListener() {
			@Override
			public void onLoadMore() {
				new AsyncTask<String, String, String>() {
					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						for(String string : getContents()){
							contents.add(string);
						}
						simpleAdapter.notifyDataSetChanged();
						superListView.finishLoadMore();
					}
				}.execute("");
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		contents = new ArrayList<String>();
		for(String string : getContents()){
			contents.add(string);
		}
		superListView.setAdapter(simpleAdapter = new SimpleAdapter(getBaseContext(), contents));
	}
	
	public static String[] getContents(){
		String currentTime = DateTimeUtils.getCurrentDateTimeByCustomFormat("yyyy年MM月dd日 HH点mm分ss秒");
		for(int i = 0; i < CONTENS.length; i++){
			CONTENS[i] = currentTime+"第"+(i+1)+"条"; 
		}
		return CONTENS;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pull_down, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_pullDown_refresh :
				superListView.startRefresh();
				break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}
