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
package test.activity.graphics;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Message;

/**
 * Clip Drawable测试
 * @author xiaopan
 *
 */
public class ClipDrawableActivity extends MyBaseActivity {
	private static final int INCREMENT = 200;
	private ClipDrawable clipDrawableLeft;
	private ClipDrawable clipDrawableRight;
	private ClipDrawable clipDrawableTop;
	private ClipDrawable clipDrawableBottom;
	private ClipDrawable clipDrawableCenterVertical;
	private ClipDrawable clipDrawableCenterHorizontal;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_clip_drawable);
		clipDrawableLeft = (ClipDrawable) findViewById(R.id.drawableClip_image_left).getBackground();
		clipDrawableRight = (ClipDrawable) findViewById(R.id.drawableClip_image_right).getBackground();
		clipDrawableTop = (ClipDrawable) findViewById(R.id.drawableClip_image_top).getBackground();
		clipDrawableBottom = (ClipDrawable) findViewById(R.id.drawableClip_image_bottom).getBackground();
		clipDrawableCenterVertical = (ClipDrawable) findViewById(R.id.drawableClip_image_center_vertical).getBackground();
		clipDrawableCenterHorizontal = (ClipDrawable) findViewById(R.id.drawableClip_image_center_horizontal).getBackground();
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while(count++ < 50){
					sendMessage();//发送一个消息给主线程
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	@Override
	public void onReceivedMessage(Message message) {
		//当收到消息的时候就更改进度
		clipDrawableLeft.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableRight.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableTop.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableBottom.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableCenterVertical.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableCenterHorizontal.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
	}
}
