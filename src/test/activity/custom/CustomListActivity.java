package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import test.adapter.ActivityAdapter;
import test.adapter.ActivityAdapter.ActivityItem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 自定义组件或特效界面
 * @author xiaopan
 *
 */
public class CustomListActivity extends MyBaseActivity{
	private List<ActivityItem> activityItemList;
	private ListView listView;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.comm_simple_list_rebound);
		listView = (ListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				startActivity(activityItemList.get(arg2 - listView.getHeaderViewsCount()).getAction());
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		activityItemList = new ArrayList<ActivityItem>();
		activityItemList.add(new ActivityItem(getString(R.string.reboundList_title), ReboundListActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.pullDownRefreshList_title), PullDownRefreshListActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.pullUpLoadMoreList_title), PullUpLoadMoreListActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.pullDownAndUpList_title), PullDownAndUpActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.clickLoadMoreList_title), ClickLoadMoreListActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.pullDownRefreshAndClickLoadMoreList_title), PullDownRefreshAndClickLoadMoreActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.picturePlayer_title), PicturePlayerActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.customEditText_title), CustomEditTextActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.slideTabHost_title), SlideTabHostActivity.class));
		
		listView.setAdapter(new ActivityAdapter(getBaseContext(), activityItemList));
	}
}