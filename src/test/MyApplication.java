package test;

import android.app.Application;

public class MyApplication extends Application {
	private boolean networkSpeedFloatingWindowDisplay;
	
	@Override
	public void onCreate() {
		super.onCreate();
		com.umeng.common.Log.LOG = true;
	}

	public boolean isNetworkSpeedFloatingWindowDisplay() {
		return networkSpeedFloatingWindowDisplay;
	}

	public void setNetworkSpeedFloatingWindowDisplay(boolean networkSpeedFloatingWindowDisplay) {
		this.networkSpeedFloatingWindowDisplay = networkSpeedFloatingWindowDisplay;
	}
}