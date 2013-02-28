package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import android.os.Bundle;

/**
 * 进度条使用示例
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
}