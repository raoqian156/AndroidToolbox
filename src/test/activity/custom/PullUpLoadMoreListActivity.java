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
import me.xiaopan.easyandroid.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreFinishListener;
import me.xiaopan.easyandroid.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreListener;
import me.xiaopan.easyandroid.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 上拉加载更多列表
 */
public class PullUpLoadMoreListActivity extends MyBaseActivity {
	private PullListView pullListView;
	private SimpleAdapter textAdapter;
	private List<String> contents;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_simple_list_pull_up_load_more);
		pullListView = (PullListView) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.pullList_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		//设置上拉加载更多监听器
		pullListView.setPullUpLoadMoreListener(new PullUpLoadMoreListener() {
			@Override
			public void onLoadMore(final PullUpLoadMoreFinishListener pullUpLoadMoreFinishListener) {
				new AsyncTask<String, String, String[]>(){
					@Override
					protected String[] doInBackground(String... params) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return PullDownRefreshListActivity.getContents();
					}
					
					@Override
					protected void onPostExecute(String[] result) {
						pullUpLoadMoreFinishListener.finishLoadMore();
						for(int w = 0; w < result.length; w++){
							contents.add(result[w]);
						}
						textAdapter.notifyDataSetChanged();
					}
				}.execute("");
			}
		});
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleButton(!((Boolean) button.getTag()));
				textAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		contents = new ArrayList<String>();
		for(String string : PullDownRefreshListActivity.getContents()){
			contents.add(string);
		}
		pullListView.setAdapter(textAdapter = new SimpleAdapter(getBaseContext(), contents));
		
		handleButton(true);
	}
	
	private void handleButton(boolean isFull){
		button.setTag(isFull);
		textAdapter.setFull((Boolean) button.getTag());
		if((Boolean) button.getTag()){
			button.setText("变少");
		}else{
			button.setText("变多");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.pull_up, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_pullUp_load :
				pullListView.startLoadMore();
				break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}
