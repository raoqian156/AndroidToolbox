package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreFinishListener;
import me.xiaopan.androidlibrary.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreListener;
import me.xiaopan.androidlibrary.widget.PullListView;
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
		setContentView(R.layout.pull_up_load_more_list);
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