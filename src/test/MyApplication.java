package test;

import me.xiaopan.javalibrary.net.HttpClient;
import android.app.Application;

public class MyApplication extends Application {
	private boolean networkSpeedFloatingWindowDisplay;
	
	@Override
	public void onCreate() {
		super.onCreate();
		HttpClient.OUTPUT_LOG = true;
//		ApplicationExceptionHandler.getInstance().init(this);
	}

	public boolean isNetworkSpeedFloatingWindowDisplay() {
		return networkSpeedFloatingWindowDisplay;
	}

	public void setNetworkSpeedFloatingWindowDisplay(boolean networkSpeedFloatingWindowDisplay) {
		this.networkSpeedFloatingWindowDisplay = networkSpeedFloatingWindowDisplay;
	}
}