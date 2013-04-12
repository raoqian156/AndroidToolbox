package test.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton;
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
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_state_normal);
	}

	@Override
	public Bitmap onGetStateDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_state_disable);
	}

	@Override
	public Bitmap onGetStateMaskBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_state_mask);
	}

	@Override
	public Bitmap onGetFrameBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_frame);
	}

	@Override
	public Bitmap onGetSliderNormalBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slider_normal);
	}

	@Override
	public Bitmap onGetSliderPressedBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slider_pressed);
	}

	@Override
	public Bitmap onGetSliderDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slider_disable);
	}

	@Override
	public Bitmap onGetSliderMaskBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slider_mask);
	}
}