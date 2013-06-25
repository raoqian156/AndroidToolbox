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
package test.activity.views;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.service.FloatingWindowService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FloatingWindowActivity extends MyBaseActivity {
	private Button button;
	private Intent networkSpeedFloatingService;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_floating_window);
		button = (Button) findViewById(R.id.button_floatingWindow);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(getMyApplication().isNetworkSpeedFloatingWindowDisplay()){
					stopService(networkSpeedFloatingService);
					button.setText("打开悬浮窗");
				}else{
					startService(networkSpeedFloatingService);
					button.setText("关闭悬浮窗");
				}
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		networkSpeedFloatingService = new Intent(this, FloatingWindowService.class);
		uodateButtonText(getMyApplication().isNetworkSpeedFloatingWindowDisplay());
	}

	public void uodateButtonText(boolean state){
		if(state){
			button.setText("关闭悬浮窗");
		}else{
			button.setText("打开悬浮窗");
		}
	}
}
