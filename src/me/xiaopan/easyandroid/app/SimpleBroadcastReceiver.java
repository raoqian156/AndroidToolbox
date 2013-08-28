package me.xiaopan.easyandroid.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 广播接收器
 */
public class SimpleBroadcastReceiver extends BroadcastReceiver{
	private BaseActivityInterface baseActivityInterface;
	
	public SimpleBroadcastReceiver(BaseActivityInterface baseActivityInterface){
		this.baseActivityInterface = baseActivityInterface;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		baseActivityInterface.onReceivedBroadcast(intent);
	}
}