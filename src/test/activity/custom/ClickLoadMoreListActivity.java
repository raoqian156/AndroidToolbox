package test.activity.custom;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.AbsClickLoadMoreListFooter.ClickLoadMoreFinishListener;
import me.xiaopan.androidlibrary.widget.AbsClickLoadMoreListFooter.ClickLoadMoreListener;
import me.xiaopan.androidlibrary.widget.AbsPullDownRefreshListHeader.PullDownRefreshFinishListener;
import me.xiaopan.androidlibrary.widget.AbsPullDownRefreshListHeader.PullDownRefreshListener;
import me.xiaopan.androidlibrary.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import test.widget.MyClickLoadMoreListFooter;
import test.widget.MyPullDownRefreshListHeader;
import android.os.AsyncTask;
import android.os.Bundle;
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
	private SimpleAdapter textAdapter;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.pull_list);
		pullListView = (PullListView) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.pullList_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
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
		//打开下拉刷新模式
		pullListView.openPullDownRefreshMode(new MyPullDownRefreshListHeader(getBaseContext()), new PullDownRefreshListener() {
			@Override
			public void onRefresh(final PullDownRefreshFinishListener pullDownRefreshFinishListener) {
				new AsyncTask<String, String, String>(){
					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						pullDownRefreshFinishListener.finishRefresh();
					}
				}.execute("");
			}
		});
		
//		pullListView.setAllowRollToBotttomLoadMore(true);
		pullListView.openClickLoadMoreMode(new MyClickLoadMoreListFooter(getBaseContext()), new ClickLoadMoreListener() {
			@Override
			public void onLoadMore(final ClickLoadMoreFinishListener clickLoadMoreFinishListener) {
				new AsyncTask<String, String, String>(){
					@Override
					protected String doInBackground(String... params) {
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						return null;
					}

					@Override
					protected void onPostExecute(String result) {
						clickLoadMoreFinishListener.finishClickLoadMore();
					}
				}.execute("");
			}
		});
		
		String[] strings = new String[30];
		for(int i = 0; i < strings.length; i++){
			strings[i] = "This is Title "+i; 
		}
		pullListView.setAdapter(textAdapter = new SimpleAdapter(getBaseContext(), strings));
		
		handleButton(true);
		
		new AsyncTask<String, String, String>(){
			@Override
			protected String doInBackground(String... params) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				pullListView.startClickLoadMore();
			}
		}.execute("");
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
}