package test.activity;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.RotateDrawableController;
import test.MyBaseActivity;
import android.graphics.drawable.RotateDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * Rotate Drawable测试
 * @author xiaopan
 *
 */
public class RotateDrawableActivity extends MyBaseActivity {
	private RotateDrawable rotateDrawable;//旋转图片
	private Button rotaryControlButton;//旋转控制按钮
	private RotateDrawableController rotateDrawableController;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.rotate_drawable);
		rotateDrawable = (RotateDrawable) findViewById(R.id.rotateDrawable_image).getBackground();
		rotaryControlButton = (Button) findViewById(R.id.rotateDrawable_button_rotaryControl);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		rotaryControlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(rotateDrawableController.isRotating()){
					rotateDrawableController.stopRotate();
					rotaryControlButton.setText(R.string.rotateDrawable_button_startRotary);
				}else{
					rotateDrawableController.startRotate();
					rotaryControlButton.setText(R.string.rotateDrawable_button_stopRotary);
				}
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		rotateDrawableController = new RotateDrawableController(this, rotateDrawable);
		rotateDrawableController.startRotate();
		rotaryControlButton.setText(R.string.rotateDrawable_button_stopRotary);
	}
}