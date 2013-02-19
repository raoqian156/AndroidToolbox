package test.activity;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.ReboundListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 其它示例
 * @author xiaopan
 *
 */
public class OtherActivity extends MyBaseActivity{
	private SimpleAdapter simpleAdapter;
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
				arg2 -= reboundListView.getHeaderViewsCount();
				switch(arg2){
				}
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		simpleAdapter = new SimpleAdapter(getBaseContext(), new String[]{});
		reboundListView.setAdapter(simpleAdapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return super.onCreateOptionsMenu(menu);
	}
}