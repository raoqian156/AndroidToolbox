package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.AbsClickLoadMoreListFooter.ClickLoadMoreFinishListener;
import me.xiaopan.androidlibrary.widget.AbsClickLoadMoreListFooter.ClickLoadMoreListener;
import me.xiaopan.androidlibrary.widget.AbsPullDownRefreshListHeader.PullDownRefreshFinishListener;
import me.xiaopan.androidlibrary.widget.AbsPullDownRefreshListHeader.PullDownRefreshListener;
import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.javalibrary.util.DateTimeUtils;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

/**
 * 下拉刷新列表
 * @author xiaopan
 *
 */
public class PullDownRefreshAndClickLoadMoreActivity extends MyBaseActivity {
	public static final String[] CONTENS = new String[20];
	private PullListView pullListView;
	private SimpleAdapter simpleAdapter;
	private List<String> contents;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.pull_down_refresh_and_click_load_more_list);
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
		
		//设置点击加载更多监听器
		pullListView.setClickLoadMoreListener(new ClickLoadMoreListener() {
			@Override
			public void onLoadMore(final ClickLoadMoreFinishListener clickLoadMoreFinishListener) {
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
						clickLoadMoreFinishListener.finishClickLoadMore();
						for(int w = 0; w < result.length; w++){
							contents.add(result[w]);
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
		getMenuInflater().inflate(R.menu.pull_down_and_click_load_more, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_pullDownRefreshAndCliclLoadMore_refresh :
				pullListView.startRefresh();
				break;
			case R.id.menu_pullDownRefreshAndCliclLoadMore_load :
				pullListView.startClickLoadMore();
				break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}