package test.activity.views;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import test.service.FloatingWindowService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FloatingWindowActivity extends MyBaseActivity {
	private Button button;
	private Intent networkSpeedFloatingService;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_floating_window);
		button = (Button) findViewById(R.id.button_floatingWindow);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(getMyApplication().isNetworkSpeedFloatingWindowDisplay()){
					stopService(networkSpeedFloatingService);
					button.setText("打开悬浮窗");
				}else{
					startService(networkSpeedFloatingService);
					button.setText("关闭悬浮窗");
				}
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		networkSpeedFloatingService = new Intent(this, FloatingWindowService.class);
		uodateButtonText(getMyApplication().isNetworkSpeedFloatingWindowDisplay());
	}

	public void uodateButtonText(boolean state){
		if(state){
			button.setText("关闭悬浮窗");
		}else{
			button.setText("打开悬浮窗");
		}
	}
}