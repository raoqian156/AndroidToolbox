package me.xiaopan.easyandroid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 广播接收器
 */
public class MyBroadcastReceiver extends BroadcastReceiver{
	private BaseActivityInterface baseActivityInterface;
	
	public MyBroadcastReceiver(BaseActivityInterface baseActivityInterface){
		setBaseActivityInterface(baseActivityInterface);
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		baseActivityInterface.onReceivedBroadcast(intent);
	}

	public BaseActivityInterface getBaseActivityInterface() {
		return baseActivityInterface;
	}

	public void setBaseActivityInterface(BaseActivityInterface baseActivityInterface) {
		this.baseActivityInterface = baseActivityInterface;
	}
}