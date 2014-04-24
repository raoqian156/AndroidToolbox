package me.xiaopan.android.os;

import android.os.Handler;
import android.os.Message;

public class EasyHandler extends Handler {
	private HandleMessageListener handleMessageListener;
	private boolean destroyed;
	
	public EasyHandler(HandleMessageListener onHandleMessageListener){
		this.handleMessageListener = onHandleMessageListener;
	}
	
	public void destroy(){
		destroyed = true;
	}
	
	@Override
	public void handleMessage(Message msg) {
		if(!destroyed){
			handleMessageListener.onHandleMessage(msg);
		}
	}
	
	public interface HandleMessageListener{
		/**
		 * 当需要处理消息
		 * @param message
		 */
		public void onHandleMessage(Message message);
	}
}