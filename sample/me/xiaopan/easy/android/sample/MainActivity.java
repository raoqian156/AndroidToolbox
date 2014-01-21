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

package me.xiaopan.easy.android.sample;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.activity.EasyFragmentActivity;
import me.xiaopan.android.easy.inject.InjectContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

@InjectContentView(R.layout.activity_main)
public class MainActivity extends EasyFragmentActivity {
	@InjectView(R.id.text_main)
	private TextView text;

	@InjectView(R.id.button_main)
	private Button button;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		text.setText("这是一个Android开发框架和工具包");
		
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				TestDialogFragment dialogFragment = new TestDialogFragment();
				dialogFragment.show(getSupportFragmentManager(), "dada");
			}
		});
	}
}
