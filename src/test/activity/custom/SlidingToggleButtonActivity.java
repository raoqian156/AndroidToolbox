package test.activity.custom;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.BasePreference;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton.OnCheckedChanageListener;
import test.MyBaseActivity;
import android.os.Bundle;

public class SlidingToggleButtonActivity extends MyBaseActivity {
	private BasePreference preference;
	private BasePreference preference1;
	private BasePreference preference2;
	private BasePreference preference3;
	private BasePreference preference4;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.sliding_toggle_button);
		preference = (BasePreference) findViewById(R.id.preference_slidingToggle);
		preference1 = (BasePreference) findViewById(R.id.preference_slidingToggle1);
		preference2 = (BasePreference) findViewById(R.id.preference_slidingToggle2);
		preference3 = (BasePreference) findViewById(R.id.preference_slidingToggle3);
		preference4 = (BasePreference) findViewById(R.id.preference_slidingToggle4);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		preference.setOnCheckedChanageListener(new OnCheckedChanageListener() {
			@Override
			public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
				preference1.setEnabled(isChecked);
				preference2.setEnabled(isChecked);
				preference3.setEnabled(isChecked);
				preference4.setEnabled(isChecked);
				preference.setSubtitle(isChecked?"开启":"关闭");
			}
		});
		
		preference1.setOnCheckedChanageListener(new OnCheckedChanageListener() {
			@Override
			public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
				preference1.setSubtitle(isChecked?"开启":"关闭");
			}
		});
		
		preference2.setOnCheckedChanageListener(new OnCheckedChanageListener() {
			@Override
			public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
				preference2.setSubtitle(isChecked?"开启":"关闭");
				if(isChecked){
					preference4.setType(BasePreference.TYPE_NEXT);
				}else{
					preference4.setType(BasePreference.TYPE_NONE);
				}
			}
		});
		
		preference3.setOnCheckedChanageListener(new OnCheckedChanageListener() {
			@Override
			public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
				preference3.setSubtitle(isChecked?"开启":"关闭");
			}
		});
		
		preference4.setOnCheckedChanageListener(new OnCheckedChanageListener() {
			@Override
			public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isChecked) {
				preference4.setSubtitle(isChecked?"开启":"关闭");
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
	}
}