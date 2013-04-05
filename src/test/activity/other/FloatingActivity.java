package test.activity.other;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import test.activity.service.NetworkSpeedFloatingWindowService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FloatingActivity extends MyBaseActivity {
	private Button button;
	private Intent networkSpeedFloatingService;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.floating);
		button = (Button) findViewById(R.id.button_floating);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(getMyApplication().isNetworkSpeedFloatingWindowDisplay()){
					stopService(networkSpeedFloatingService);
					button.setText("打开网速悬浮窗");
				}else{
					startService(networkSpeedFloatingService);
					button.setText("关闭网速悬浮窗");
				}
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		networkSpeedFloatingService = new Intent(this, NetworkSpeedFloatingWindowService.class);
		uodateButtonText(getMyApplication().isNetworkSpeedFloatingWindowDisplay());
	}

	public void uodateButtonText(boolean state){
		if(state){
			button.setText("关闭网速悬浮窗");
		}else{
			button.setText("打开网速悬浮窗");
		}
	}
}