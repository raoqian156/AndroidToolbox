package test.activity.graphics;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.ReboundListView;
import test.MyBaseActivity;
import test.adapter.ActivityAdapter;
import test.adapter.ActivityAdapter.ActivityItem;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 图形列表
 * @author xiaopan
 *
 */
public class GraphicsListActivity extends MyBaseActivity{
	private List<ActivityItem> activityItemList;
	private ReboundListView reboundListView;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.comm_simple_rebound_list);
		reboundListView = (ReboundListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		reboundListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				startActivity(activityItemList.get(arg2 - reboundListView.getHeaderViewsCount()).getAction());
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		activityItemList = new ArrayList<ActivityItem>();
		activityItemList.add(new ActivityItem(getString(R.string.cameraPreview_title), CameraPreviewActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.clipDrawable_title), ClipDrawableActivity.class));
		activityItemList.add(new ActivityItem(getString(R.string.rotateDrawable_title), RotateDrawableActivity.class));
		
		reboundListView.setAdapter(new ActivityAdapter(getBaseContext(), activityItemList));
	}
}