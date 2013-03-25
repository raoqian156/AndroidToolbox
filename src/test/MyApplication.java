package test;

import android.app.Application;

public class MyApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
//		ApplicationExceptionHandler.getInstance().init(this);
	}
}