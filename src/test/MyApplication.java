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
import me.xiaopan.easynetwork.android.EasyHttpClient;
import me.xiaopan.imageloader.android.ImageLoader;
import me.xiaopan.imageloader.android.Options;
import me.xiaopan.imageloader.android.ShowAnimationListener;
import android.app.Application;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;

public class MyApplication extends Application {
	private boolean networkSpeedFloatingWindowDisplay;
	
	@Override
	public void onCreate() {
		super.onCreate();
		com.umeng.common.Log.LOG = true;
		
		/* 初始化图片加载器 */
		Options options = new Options();
		options.setLoadingDrawableResId(R.drawable.image_loading);	//设置加载中显示的图片
		options.setLoadFailedDrawableResId(R.drawable.image_load_failed);	//设置加载失败时显示的图片
		options.setShowAnimationListener(new ShowAnimationListener() {	//设置显示动画监听器，用来获取显示图片的动画
			@Override
			public Animation onGetShowAnimation() {
				/* 创建一个从50%放大到100%并且持续0.5秒的缩放动画 */
				ScaleAnimation scaleAnimation = new ScaleAnimation(0.5f, 1.0f, 0.5f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				scaleAnimation.setDuration(500);
				return scaleAnimation;
			}
		});
		ImageLoader.init(getBaseContext(), options);
		
		//让EasyHttpClient和ImageLoader共用一个线程池
		EasyHttpClient.getInstance();
		ImageLoader.setThreadPool(EasyHttpClient.getThreadPool());
	}

	public boolean isNetworkSpeedFloatingWindowDisplay() {
		return networkSpeedFloatingWindowDisplay;
	}

	public void setNetworkSpeedFloatingWindowDisplay(boolean networkSpeedFloatingWindowDisplay) {
		this.networkSpeedFloatingWindowDisplay = networkSpeedFloatingWindowDisplay;
	}
}
