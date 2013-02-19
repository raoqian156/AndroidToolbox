package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseTabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * TabHost使用示例Activity
 * @author xiaopan
 *
 */
public class TabHostActivity extends MyBaseTabActivity {
	
	private static final String TAB_TAG_ONE = "TAB_TAG_ONE";
	private static final String TAB_TAG_TWO = "TAB_TAG_TWO";
	private static final String TAB_TAG_THREE = "TAB_TAG_THREE";
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.tabhost);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onInitData(Bundle savedInstanceState) {
		//添加3个选项卡
		getTabHost().addTab(createTabSpec(TAB_TAG_ONE, "选项卡1", "1"));
		getTabHost().addTab(createTabSpec(TAB_TAG_TWO, "选项卡2", "2"));
		getTabHost().addTab(createTabSpec(TAB_TAG_THREE, "选项卡3", "3"));
	}
	
	private TabSpec createTabSpec(String tag, String title, String content){
		@SuppressWarnings("deprecation")
		TabSpec tabSpec = getTabHost().newTabSpec(tag);
		
		TextView titleText = (TextView) getViewByLayout(R.layout.tab);
		titleText.setText(title);
		tabSpec.setIndicator(titleText);
		
		Intent intent = new Intent(this, TabContentActivity.class);
		intent.putExtra(TabContentActivity.PARAM_TAB_INDEX, content);
		tabSpec.setContent(intent);
		
		return tabSpec;
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return super.onCreateOptionsMenu(menu);
	}
}