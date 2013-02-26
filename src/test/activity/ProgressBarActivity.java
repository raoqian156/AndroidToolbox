package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import android.os.Bundle;
import android.view.Menu;

/**
 * TabHost使用示例Activity
 * @author xiaopan
 *
 */
public class ProgressBarActivity extends MyBaseActivity {
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.progressbar);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return super.onCreateOptionsMenu(menu);
	}
}