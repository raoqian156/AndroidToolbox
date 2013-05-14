package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.AbsClickLoadMoreListFooter.ClickLoadMoreFinishListener;
import me.xiaopan.easyandroid.widget.AbsClickLoadMoreListFooter.ClickLoadMoreListener;
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
 * 点击加载更多列表
 * @author xiaopan
 *
 */
public class ClickLoadMoreListActivity extends MyBaseActivity {
	private PullListView pullListView;
	private SimpleAdapter simpleAdapter;
	private List<String> contents;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_click_load_more_list);
		pullListView = (PullListView) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.pullList_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
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
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleButton(!((Boolean) button.getTag()));
				pullListView.setAllowRollToBotttomLoadMore(!(Boolean) button.getTag());
			}
		});
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
		switch (item.getItemId()) {
			case R.id.menu_clickLoad_load :
				pullListView.startClickLoadMore();
				break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}