package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.AbsPullDownRefreshListHeader.PullDownRefreshFinishListener;
import me.xiaopan.easyandroid.widget.AbsPullDownRefreshListHeader.PullDownRefreshListener;
import me.xiaopan.easyandroid.widget.PullListView;
import me.xiaopan.easyjava.util.DateTimeUtils;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 下拉刷新列表
 */
public class PullDownRefreshListActivity extends MyBaseActivity {
	public static final String[] CONTENS = new String[20];
	private PullListView pullListView;
	private SimpleAdapter simpleAdapter;
	private List<String> contents;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_simple_list_pull_down_refresh);
		pullListView = (PullListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		//设置下拉刷新监听器
		pullListView.setPullDownRefreshListener(new PullDownRefreshListener() {
			@Override
			public void onRefresh(final PullDownRefreshFinishListener pullDownRefreshFinishListener) {
				new AsyncTask<String, String, String[]>(){
					@Override
					protected String[] doInBackground(String... params) {
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return getContents();
					}
					
					@Override
					protected void onPostExecute(String[] result) {
						pullDownRefreshFinishListener.finishRefresh();
						for(int w = result.length - 1; w >= 0; w--){
							contents.add(0, result[w]);
						}
						simpleAdapter.notifyDataSetChanged();
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
		pullListView.setAdapter(simpleAdapter = new SimpleAdapter(getBaseContext(), contents));
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
				pullListView.startRefresh();
				break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}