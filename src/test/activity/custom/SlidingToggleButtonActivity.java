package test.activity.custom;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.Preference;
import me.xiaopan.androidlibrary.widget.SlidingToggleButton;
import me.xiaopan.androidlibrary.widget.SlidingToggleButton.OnStateChanageListener;
import test.MyBaseActivity;
import android.os.Bundle;

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
		preference.setOnToggleStateChanageListener(new OnStateChanageListener() {
			@Override
			public void onStateChanage(SlidingToggleButton slidingToggleButton, boolean isOn) {
				preference1.setEnabled(isOn);
				preference2.setEnabled(isOn);
				preference3.setEnabled(isOn);
				preference4.setEnabled(isOn);
				preference.setSubtitle(isOn?"开启":"关闭");
			}
		});
		
		preference1.setOnToggleStateChanageListener(new OnStateChanageListener() {
			@Override
			public void onStateChanage(SlidingToggleButton slidingToggleButton, boolean isOn) {
				preference1.setSubtitle(isOn?"开启":"关闭");
			}
		});
		
		preference2.setOnToggleStateChanageListener(new OnStateChanageListener() {
			@Override
			public void onStateChanage(SlidingToggleButton slidingToggleButton, boolean isOn) {
				preference2.setSubtitle(isOn?"开启":"关闭");
			}
		});
		
		preference3.setOnToggleStateChanageListener(new OnStateChanageListener() {
			@Override
			public void onStateChanage(SlidingToggleButton slidingToggleButton, boolean isOn) {
				preference3.setSubtitle(isOn?"开启":"关闭");
			}
		});
		
		preference4.setOnToggleStateChanageListener(new OnStateChanageListener() {
			@Override
			public void onStateChanage(SlidingToggleButton slidingToggleButton, boolean isOn) {
				preference4.setSubtitle(isOn?"开启":"关闭");
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		preference.setOn(true);
		preference1.setOn(true);
		preference2.setOn(true);
		preference3.setOn(true);
		preference4.setOn(true);
	}
}