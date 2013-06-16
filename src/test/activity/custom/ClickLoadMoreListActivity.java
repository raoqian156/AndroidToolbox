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
import me.xiaopan.easyandroid.widget.AbstractClickLoadListFooter;
import me.xiaopan.easyandroid.widget.MyClickLoadListFooter2.ClickLoadListener;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import test.widget.MyClickLoadListView2;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * 点击加载更多列表
 * @author xiaopan
 *
 */
public class ClickLoadMoreListActivity extends MyBaseActivity {
	private MyClickLoadListView2 pullListView;
	private SimpleAdapter simpleAdapter;
	private List<String> contents;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_click_load_more_list);
		pullListView = (MyClickLoadListView2) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.pullList_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		pullListView.setOnLoadListener(new ClickLoadListener() {
			@Override
			public void onStartLoad(final AbstractClickLoadListFooter clickLoadListFooter) {
				new AsyncTask<Integer, Integer, Integer>(){
					@Override
					protected void onPreExecute() {
						toastS("开始加载");
					}

					@Override
					protected Integer doInBackground(Integer... params) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}
		
					@Override
					protected void onPostExecute(Integer result) {
						toastS("加载完成");
						clickLoadListFooter.finishLoad();
					}
				}.execute(0);
			}
		});
		
//		pullListView.setClickLoadMoreListener(new ClickLoadMoreListener() {
//			@Override
//			public void onLoadMore(final ClickLoadMoreFinishListener clickLoadMoreFinishListener) {
//				new AsyncTask<String, String, String[]>(){
//					@Override
//					protected String[] doInBackground(String... params) {
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						return PullDownRefreshListActivity.getContents();
//					}
//					
//					@Override
//					protected void onPostExecute(String[] result) {
//						clickLoadMoreFinishListener.finishClickLoadMore();
//						for(int w = 0; w < result.length; w++){
//							contents.add(result[w]);
//						}
//						simpleAdapter.notifyDataSetChanged();
//					}
//				}.execute("");
//			}
//		});
//		
//		button.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				handleButton(!((Boolean) button.getTag()));
//				pullListView.setAllowRollToBotttomLoadMore(!(Boolean) button.getTag());
//			}
//		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		contents = new ArrayList<String>();
		for(String string : PullDownRefreshListActivity.getContents()){
			contents.add(string);
		}
		pullListView.setAdapter(simpleAdapter = new SimpleAdapter(getBaseContext(), contents));
		
		handleButton(true);
	}
	
	private void handleButton(boolean isFull){
		button.setTag(isFull);
		if((Boolean) button.getTag()){
			button.setText("开启滚动到底部自动加载");
		}else{
			button.setText("关闭滚动到底部自动加载");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.click_load, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
//		switch (item.getItemId()) {
//			case R.id.menu_clickLoad_load :
//				pullListView.startClickLoadMore();
//				break;
//			default: break;
//		}
		return super.onOptionsItemSelected(item);
	}
}
