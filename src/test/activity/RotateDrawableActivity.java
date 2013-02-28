package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import android.os.Bundle;
import android.os.Message;

/**
 * Rotate Drawable测试
 * @author xiaopan
 *
 */
public class RotateDrawableActivity extends MyBaseActivity {
	
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.rotate_drawable);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				int count = 0;
//				while(count++ < 10){
//					sendMessage();//发送一个消息给主线程
//					try {
//						Thread.sleep(1000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
	}
	
	@Override
	protected void onReceivedMessage(Message message) {
		//当收到消息的时候就更改进度
	}
}