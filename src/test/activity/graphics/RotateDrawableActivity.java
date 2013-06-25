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
import me.xiaopan.easyandroid.util.DrawabLevelController;
import me.xiaopan.easyandroid.util.DrawabLevelController.Listener;
import me.xiaopan.easyandroid.util.DrawabLevelController.RepeatMode;
import test.MyBaseActivity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Rotate Drawable测试
 * @author xiaopan
 *
 */
public class RotateDrawableActivity extends MyBaseActivity {
	private Drawable orderDrawable;//顺序旋转图片
	private Drawable reverseDrawable;//倒序旋转图片
	private Drawable mirrorDrawable;//镜像旋转图片
	private TextView orderText;//顺序旋转图片
	private TextView reverseText;//倒序旋转图片
	private TextView mirrorText;//镜像旋转图片
	private Button rotaryControlButton;//旋转控制按钮
	private Button resetButton;//旋转控制按钮
	private DrawabLevelController orderDrawableController;
	private DrawabLevelController reverseDrawableController;
	private DrawabLevelController mirrorDrawableController;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_rotate_drawable);
		orderDrawable = ((ImageView) findViewById(R.id.rotateDrawable_image_order)).getDrawable();
		reverseDrawable = ((ImageView) findViewById(R.id.rotateDrawable_image_reverse)).getDrawable();
		mirrorDrawable = ((ImageView) findViewById(R.id.rotateDrawable_image_mirror)).getDrawable();
		orderText = (TextView) findViewById(R.id.rotateDrawable_text_order);
		reverseText = (TextView) findViewById(R.id.rotateDrawable_text_reverse);
		mirrorText = (TextView) findViewById(R.id.rotateDrawable_text_mirror);
		rotaryControlButton = (Button) findViewById(R.id.rotateDrawable_button_rotaryControl);
		resetButton = (Button) findViewById(R.id.rotateDrawable_button_reset);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		rotaryControlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(orderDrawableController.isRunning()){
					orderDrawableController.pause();
					reverseDrawableController.pause();
					mirrorDrawableController.pause();
				}else{
					orderDrawableController.start();
					reverseDrawableController.start();
					mirrorDrawableController.start();
				}
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				orderDrawableController.reset();
				reverseDrawableController.reset();
				mirrorDrawableController.reset();
			}
		});
		
		orderDrawableController = new DrawabLevelController(this, orderDrawable);
		orderDrawableController.setListener(new Listener() {
			@Override
			public void onStart() {
				rotaryControlButton.setText(R.string.base_pause);
			} 
			
			@Override
			public void onReset() {
				toastS("重置成功！");
			}
			
			@Override
			public void onPause() {
				rotaryControlButton.setText(R.string.base_start);
			}

			@Override
			public void onCompletedACircle(int totalRepeatCount, int hasRepeatCount) {
				orderText.setText(hasRepeatCount+"/"+totalRepeatCount);
			}
		});
		reverseDrawableController = new DrawabLevelController(this, reverseDrawable);
		reverseDrawableController.setListener(new Listener() {
			@Override
			public void onStart() {
			} 
			
			@Override
			public void onReset() {
			}
			
			@Override
			public void onPause() {
			}

			@Override
			public void onCompletedACircle(int totalRepeatCount, int hasRepeatCount) {
				reverseText.setText(hasRepeatCount+"/"+totalRepeatCount);
				Log.i("测试", "完成次数="+hasRepeatCount);
			}
		});
		mirrorDrawableController = new DrawabLevelController(this, mirrorDrawable);
		mirrorDrawableController.setListener(new Listener() {
			@Override
			public void onStart() {
			} 
			
			@Override
			public void onReset() {
			}
			
			@Override
			public void onPause() {
			}

			@Override
			public void onCompletedACircle(int totalRepeatCount, int hasRepeatCount) {
				mirrorText.setText(hasRepeatCount+"/"+totalRepeatCount);
			}
		});
	} 

	@Override
	public void onInitData(Bundle savedInstanceState) {
		orderDrawableController.setRepeatMode(RepeatMode.ORDER);
		orderDrawableController.setDelayed(1);
		orderDrawableController.setIncremental(100);
//		orderDrawableController.setRepeatCount(9);
		orderDrawableController.start();
		
		reverseDrawableController.setRepeatMode(RepeatMode.REVERSE);
		reverseDrawableController.setDelayed(1);
		reverseDrawableController.setIncremental(100);
//		reverseDrawableController.setRepeatCount(9);
		reverseDrawableController.start();

		mirrorDrawableController.setRepeatMode(RepeatMode.MIRROR);
		mirrorDrawableController.setDelayed(1);
		mirrorDrawableController.setIncremental(100);
//		mirrorDrawableController.setRepeatCount(9);
		mirrorDrawableController.start();
	}
}
