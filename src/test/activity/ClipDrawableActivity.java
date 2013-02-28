package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Message;

/**
 * Clip Drawable测试
 * @author xiaopan
 *
 */
public class ClipDrawableActivity extends MyBaseActivity {
	private static final int INCREMENT = 200;
	private ClipDrawable clipDrawableLeft;
	private ClipDrawable clipDrawableRight;
	private ClipDrawable clipDrawableTop;
	private ClipDrawable clipDrawableBottom;
	private ClipDrawable clipDrawableCenterVertical;
	private ClipDrawable clipDrawableCenterHorizontal;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.clip_drawable);
		clipDrawableLeft = (ClipDrawable) findViewById(R.id.drawableClip_image_left).getBackground();
		clipDrawableRight = (ClipDrawable) findViewById(R.id.drawableClip_image_right).getBackground();
		clipDrawableTop = (ClipDrawable) findViewById(R.id.drawableClip_image_top).getBackground();
		clipDrawableBottom = (ClipDrawable) findViewById(R.id.drawableClip_image_bottom).getBackground();
		clipDrawableCenterVertical = (ClipDrawable) findViewById(R.id.drawableClip_image_center_vertical).getBackground();
		clipDrawableCenterHorizontal = (ClipDrawable) findViewById(R.id.drawableClip_image_center_horizontal).getBackground();
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				int count = 0;
				while(count++ < 50){
					sendMessage();//发送一个消息给主线程
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	@Override
	protected void onReceivedMessage(Message message) {
		//当收到消息的时候就更改进度
		clipDrawableLeft.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableRight.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableTop.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableBottom.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableCenterVertical.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
		clipDrawableCenterHorizontal.setLevel(clipDrawableLeft.getLevel() + INCREMENT);
	}
}