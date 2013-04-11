package test.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;

public class SlidingToggleButton extends BaseSlidingToggleButton {
	public SlidingToggleButton(Context context) {
		super(context);
	}
	
	public SlidingToggleButton(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public Bitmap onGetBackgroundNormalBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background_normal);
	}

	@Override
	public Bitmap onGetBackgroundDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_background_disable);
	}

	@Override
	public Bitmap onGetBackgroundMasklBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask_background);
	}

	@Override
	public Bitmap onGetFrameBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_frame);
	}

	@Override
	public Bitmap onGetSliderNormalBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_normal);
	}

	@Override
	public Bitmap onGetSliderPressedBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_pressed);
	}

	@Override
	public Bitmap onGetSliderDisableBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_slilder_disable);
	}

	@Override
	public Bitmap onGetSliderMaskBitmap() {
		return BitmapFactory.decodeResource(getResources(), R.drawable.btn_sliding_mask_slider);
	}
}