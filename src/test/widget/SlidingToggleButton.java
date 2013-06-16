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
package test.widget;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.BaseSlidingToggleButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

/**
 * 滑动开关按钮
 */
public class SlidingToggleButton extends BaseSlidingToggleButton {
	public SlidingToggleButton(Context context) {
		super(context);
	}
	
	public SlidingToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public Bitmap onGetStateNormalBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_state_normal);
	}

	@Override
	public Bitmap onGetStateDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_state_disable);
	}

	@Override
	public Bitmap onGetStateMaskBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_state_mask);
	}

	@Override
	public Bitmap onGetFrameBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_frame);
	}

	@Override
	public Bitmap onGetSliderNormalBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_slider_normal);
	}

	@Override
	public Bitmap onGetSliderPressedBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_slider_pressed);
	}

	@Override
	public Bitmap onGetSliderDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_slider_disable);
	}

	@Override
	public Bitmap onGetSliderMaskBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.button_sliding_slider_mask);
	}
}
