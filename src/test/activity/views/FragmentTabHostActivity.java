package test.activity.views;

import me.xiaopan.easyandroid.R;
import test.MyBaseFragmentActivity;
import test.activity.fragment.Image2Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

/**
 * FragmentTabHost使用示例Activity
 * @author xiaopan
 *
 */
public class FragmentTabHostActivity extends MyBaseFragmentActivity {
	private static final String TAB_TAG_ONE = "TAB_TAG_ONE";
	private static final String TAB_TAG_TWO = "TAB_TAG_TWO";
	private static final String TAB_TAG_THREE = "TAB_TAG_THREE";
	private static final String TAB_TAG_FOUR = "TAB_TAG_FOUR";
	private FragmentTabHost fragmentTabHost;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_fragment_tabhost);
		fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		fragmentTabHost.setup(getBaseContext(), getSupportFragmentManager(), R.id.realtabcontent);
		fragmentTabHost.getTabWidget().setDividerDrawable(R.drawable.tabs_divider);
		
		fragmentTabHost.addTab(createTabSpec(TAB_TAG_ONE, "选项卡1"), Image2Fragment.class, getBundle(R.drawable.liuyifei1));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_TWO, "选项卡2"), Image2Fragment.class, getBundle(R.drawable.liuyifei2));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_THREE, "选项卡3"), Image2Fragment.class, getBundle(R.drawable.liuyifei3));
        fragmentTabHost.addTab(createTabSpec(TAB_TAG_FOUR, "选项卡4"), Image2Fragment.class, getBundle(R.drawable.liuyifei4));
	}
	
	private TabSpec createTabSpec(String tag, String title){
		TabSpec tabSpec = fragmentTabHost.newTabSpec(tag);
		TextView titleText = (TextView) getViewByLayout(R.layout.tab);
		titleText.setText(title);
		tabSpec.setIndicator(titleText);
		return tabSpec;
	}
	
	public Bundle getBundle(int imageResId){
		Bundle bundle = new Bundle();
		bundle.putInt(Image2Fragment.PARAM_REQUIRED_IMAHE_RES_ID, imageResId);
		return bundle;
	}
}