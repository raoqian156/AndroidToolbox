package me.xiaopan.android.easy.app;

import android.os.Message;

public interface OnHandleMessageListener{
	/**
	 * 当需要处理消息
	 * @param message
	 */
	public void onHandleMessage(Message message);
}
