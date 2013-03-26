package test.activity.views;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;

public class TabContentActivity extends MyBaseActivity {

	public static final String PARAM_TAB_INDEX = "PARAM_SHOW_CONTENT";
	private PullListView pullListView;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		pullListView = (PullListView) getViewByLayout(R.layout.comm_only_list);
		setContentView(pullListView);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		String tabIndex = getIntent().getStringExtra(PARAM_TAB_INDEX);
		String[] contents = new String[20];
		for(int w = 0; w < contents.length; w++){
			contents[w] = "这是选项卡 "+tabIndex+" 的第 "+w+" 条数据";
		}
		pullListView.openListHeaderReboundMode();
		pullListView.openListFooterReboundMode();
		pullListView.setAdapter(new SimpleAdapter(getBaseContext(), contents));
	}

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}
}