package test.activity.custom;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.Preference;
import android.os.Bundle;
import test.MyBaseActivity;

public class SlidingToggleButtonActivity extends MyBaseActivity {
	private Preference preference;
	private Preference preference1;
	private Preference preference2;
	private Preference preference3;
	private Preference preference4;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.sliding_toggle_button);
		preference = (Preference) findViewById(R.id.preference_slidingToggle);
		preference1 = (Preference) findViewById(R.id.preference_slidingToggle1);
		preference2 = (Preference) findViewById(R.id.preference_slidingToggle2);
		preference3 = (Preference) findViewById(R.id.preference_slidingToggle3);
		preference4 = (Preference) findViewById(R.id.preference_slidingToggle4);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {

	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
	
	}
}