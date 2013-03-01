package test.activity;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.DrawabLevelController;
import me.xiaopan.androidlibrary.util.DrawabLevelController.Listener;
import me.xiaopan.androidlibrary.util.DrawabLevelController.RepeatMode;
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
	private Button resetButton;//旋转控制按钮
	private DrawabLevelController drawableController;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.rotate_drawable);
		rotateDrawable = (RotateDrawable) findViewById(R.id.rotateDrawable_image).getBackground();
		rotaryControlButton = (Button) findViewById(R.id.rotateDrawable_button_rotaryControl);
		resetButton = (Button) findViewById(R.id.rotateDrawable_button_reset);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		rotaryControlButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(drawableController.isRunning()){
					drawableController.pause();
				}else{
					if(!drawableController.start()){
						toastS("已经达到最大重复次数，请使用重置功能重置执行次数！");
					}
				}
			}
		});
		
		resetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				drawableController.reset();
			}
		});
		
		drawableController = new DrawabLevelController(this, rotateDrawable);
		drawableController.setListener(new Listener() {
			@Override
			public void onStart() {
				rotaryControlButton.setText(R.string.base_stop);
			} 
			
			@Override
			public void onReset() {
				toastS("重置成功！");
			}
			
			@Override
			public void onPause() {
				rotaryControlButton.setText(R.string.base_start);
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		drawableController.setRepeatMode(RepeatMode.REVERSE);
//		drawableController.setMinLevel(5000);
		drawableController.setRepeatCount(10);
		drawableController.start();
	}
}