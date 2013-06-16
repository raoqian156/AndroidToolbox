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

import java.io.File;
import java.io.IOException;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.app.BaseActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.umeng.analytics.MobclickAgent;

public abstract class MyBaseActivity extends BaseActivity {
	@Override
	protected void onPreInit(Bundle savedInstanceState) {
		ApplicationExceptionHandler.getInstance().setContext(this);
		if(!isRemoveTitleBar()){
			getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_titlebar));
			if(isEnableBackHome()){
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		MobclickAgent.onError(this);
	}
	
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
	protected boolean isUseCustomAnimation() {
		return true;
	}

	/**
	 * 判断是否启用返回主页
	 * @return 是否启用返回主页
	 */
	protected boolean isEnableBackHome() {
		return true;
	}

	@Override
	protected int[] onGetDefaultStartActivityAnimation() {
		return new int[]{R.anim.base_slide_to_left_in, R.anim.base_normal};
	}

	@Override
	protected int[] onGetDefaultFinishActivityAnimation() {
		return new int[]{R.anim.base_normal, R.anim.base_slide_to_right_out};
	}

	@Override
	protected int onGetLoadingHintViewId() {
		return R.id.layout_loadingHint;
	}

	@Override
	protected int onGetListEmptyHintViewId() {
		return R.id.layout_listEmptyHint;
	}

	@Override
	protected void onClickListEmptyHintView() {
		toastL(R.string.toast_reloadIn);
	}

	@Override
	protected void onNetworkNotAvailable() {
		toastL(R.string.toast_network_connectionException);
	}

	@Override
	public void onBecauseExceptionFinishActivity() {
		finishActivity(R.anim.base_alpha_show, R.anim.base_alpha_hide);
	}

	@Override
	public void onPromptExitApplication() {
		toastL(R.string.toast_exitHint);
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

	/**
	 * 获取拍摄的照片文件
	 * @return 拍摄的照片文件
	 * @throws IOException 
	 */
	public File getPhotoFile() throws IOException{
		File file = getFileFromDynamicCacheDir("card.jpeg");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
}
