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
package test;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.app.BaseActivity;
import me.xiaopan.easyandroid.app.OnDoubleClickPromptExitListener;
import me.xiaopan.easyandroid.app.OnExceptionFinishActivityListener;
import me.xiaopan.easyandroid.app.OnNetworkVerifyListener;
import android.os.Bundle;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

public abstract class MyBaseActivity extends BaseActivity {
	private boolean hiddenTitleBar;
	private boolean enableBackHome = true;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ApplicationExceptionHandler.getInstance().setContext(this);
		MobclickAgent.onError(this);
		setEnableCustomActivitySwitchAnimation(true);
		setStartActivityAnimation(new int[]{R.anim.base_slide_to_left_in, R.anim.base_normal});
		setFinishActivityAnimation(new int[]{R.anim.base_normal, R.anim.base_slide_to_right_out});
		setOnNetworkVerifyListener(new OnNetworkVerifyListener() {
			@Override
			public void onVerifyFailure() {
				toastL(R.string.toast_network_connectionException);				
			}
		});
		setOnDoubleClickPromptExitListener(new OnDoubleClickPromptExitListener() {
			@Override
			public void onPrompt() {
				toastL(R.string.toast_exitHint);
			}
		});
		setOnExceptionFinishActivityListener(new OnExceptionFinishActivityListener() {
			@Override
			public void onExceptionFinishActivity() {
				finishActivity(R.anim.base_alpha_show, R.anim.base_alpha_hide);
			}
		});
		
		onInitLayout(savedInstanceState);
		onInitListener(savedInstanceState);
		onInitData(savedInstanceState);
		
		if(!hiddenTitleBar){
			getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_titlebar));
			if(isEnableBackHome()){
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
	}
	
	public abstract void onInitLayout(Bundle savedInstanceState);
	public abstract void onInitListener(Bundle savedInstanceState);
	public abstract void onInitData(Bundle savedInstanceState);
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
	
	@Override
	public void hiddenTitleBar() {
		super.hiddenTitleBar();
		hiddenTitleBar = true;
	}
	
	public boolean isEnableBackHome() {
		return enableBackHome;
	}

	public void setEnableBackHome(boolean enableBackHome) {
		this.enableBackHome = enableBackHome;
	}

	public void showLoadingHintView(){
		showLoadingHintView(R.id.layout_loadingHint);
	}

	public void closeLoadingHintView(){
		closeLoadingHintView(R.id.layout_loadingHint);
	}

	public MyApplication getMyApplication(){
		return (MyApplication) getApplication();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: finishActivity(); break;
			case R.id.menu_exception: throw new NullPointerException();
			case R.id.menu_exit: finishApplication(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}