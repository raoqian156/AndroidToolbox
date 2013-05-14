package test.activity.other;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.adapter.ActivityAdapter;
import test.adapter.ActivityAdapter.ActivityItem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 其它示例
 * @author xiaopan
 *
 */
public class OtherListActivity extends MyBaseActivity{
	private List<ActivityItem> activityItemList;
	private ListView listView;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_simple_list_rebound);
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
		activityItemList.add(new ActivityItem(getString(R.string.accessNetwork_title), AccessNetworkActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.downloadImage_title), DownloadImageActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.barcodeScanner_title), BarcodeScannerActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.imageLoader_title), ImageLoaderActivity.class));
		
		listView.setAdapter(new ActivityAdapter(getBaseContext(), activityItemList));
	}
}