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
package test.activity.other;

import me.xiaopan.easy.network.android.http.EasyHttpClient;
import me.xiaopan.easy.network.android.http.StringHttpResponseHandler;
import me.xiaopan.easy.android.R;
import test.MyBaseActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 访问网络界面
 * @author xiaopan
 *
 */
public class AccessNetworkActivity extends MyBaseActivity {
	private TextView text;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_network);
		text = (TextView) findViewById(R.id.tv_network);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		loadData();
	}
	
	private void loadData(){
		EasyHttpClient.getInstance().get("http://www.miui.com/forum.php", new StringHttpResponseHandler(){
			@Override
			public void onStart() {
				showLoadingHintView();
			}

			@Override
			public void onSuccess(String responseContent) {
				text.setText(Html.fromHtml(responseContent));
			}

			@Override
			public void onFailure(Throwable throwable) {
				text.setText("失败了："+throwable.getMessage());
			}

			@Override
			public void onEnd() {
				closeLoadingHintView();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.network, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_network_refresh: loadData(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}
