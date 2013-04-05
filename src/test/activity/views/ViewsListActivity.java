package test.activity.views;

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
 * 系统组件使用示例
 * @author xiaopan
 *
 */
public class ViewsListActivity extends MyBaseActivity{

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
		activityItemList.add(new ActivityItem(getString(R.string.tabHost_title), TabHostActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.fragmentTabHost_title), FragmentTabHostActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.progressBar_title), ProgressBarActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.viewPager_title), ViewPagerActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.floatingWindow_title), FloatingWindowActivity.class));
		
		listView.setAdapter(new ActivityAdapter(getBaseContext(), activityItemList));
	}
}