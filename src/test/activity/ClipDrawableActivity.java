package test.activity;

import me.xiaopan.androidlibrary.R;
import test.MyBaseActivity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;

/**
 * Clip Drawable测试
 * @author xiaopan
 *
 */
public class ClipDrawableActivity extends MyBaseActivity {
	
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
				while(count++ < 10){
					sendMessage();//发送一个消息给主线程
					try {
						Thread.sleep(1000);
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
		clipDrawableLeft.setLevel(clipDrawableLeft.getLevel() + 1000);
		clipDrawableRight.setLevel(clipDrawableLeft.getLevel() + 1000);
		clipDrawableTop.setLevel(clipDrawableLeft.getLevel() + 1000);
		clipDrawableBottom.setLevel(clipDrawableLeft.getLevel() + 1000);
		clipDrawableCenterVertical.setLevel(clipDrawableLeft.getLevel() + 1000);
		clipDrawableCenterHorizontal.setLevel(clipDrawableLeft.getLevel() + 1000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.comm, menu);
		return super.onCreateOptionsMenu(menu);
	}
}