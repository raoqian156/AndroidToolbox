package test;

import me.xiaopan.easyjava.net.HttpClient;
import android.app.Application;

public class MyApplication extends Application {
	private boolean networkSpeedFloatingWindowDisplay;
	
	@Override
	public void onCreate() {
		super.onCreate();
		HttpClient.OUTPUT_LOG = true;
		com.umeng.common.Log.LOG = true;
	}	

	public boolean isNetworkSpeedFloatingWindowDisplay() {
		return networkSpeedFloatingWindowDisplay;
	}

	public void setNetworkSpeedFloatingWindowDisplay(boolean networkSpeedFloatingWindowDisplay) {
		this.networkSpeedFloatingWindowDisplay = networkSpeedFloatingWindowDisplay;
	}
}