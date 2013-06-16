/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.activity.custom;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.BaseSlidingToggleButton;
import me.xiaopan.easyandroid.widget.BaseSlidingToggleButton.OnCheckedChanageListener;
import test.MyBaseActivity;
import test.widget.Preference;
import android.os.Bundle;

public class SlidingToggleButtonActivity extends MyBaseActivity {
	private Preference preference;
	private Preference preference1;
	private Preference preference2;
	private Preference preference3;
	private Preference preference4;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_sliding_toggle_button);
		preference = (Preference) findViewById(R.id.preference_slidingToggle);
		preference1 = (Preference) findViewById(R.id.preference_slidingToggle1);
		preference2 = (Preference) findViewById(R.id.preference_slidingToggle2);
		preference3 = (Preference) findViewById(R.id.preference_slidingToggle3);
		preference4 = (Preference) findViewById(R.id.preference_slidingToggle4);
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
