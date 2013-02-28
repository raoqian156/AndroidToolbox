package me.xiaopan.androidlibrary.util;

import android.app.Activity;
import android.graphics.drawable.RotateDrawable;
import android.os.Handler;

/**
 * 旋转图片控制器，用来控制RotateDrawable进行旋转，可进行启动、停止控制以及自定义旋转延迟和旋转增量
 * @author xiaopan
 *
 */
public class RotateDrawableController {
	private Activity activity;
	private RotateDrawable rotateDrawable;//旋转图片
	private Handler handler;
	private RotateHandle rotateHandle;
	private boolean rotating;
	private int rotateDelayed = 10;//旋转延迟
	private int rotatingIncremental = 150;//旋转增量
	
	/**
	 * 控制一个旋转图片控制器
	 * @param activity 当activity销毁的时候会停止旋转
	 * @param rotateDrawable 要控制的旋转图片
	 */
	public RotateDrawableController(Activity activity, RotateDrawable rotateDrawable){
		this.activity = activity;
		this.rotateDrawable = rotateDrawable;
		handler = new Handler();
		rotateHandle = new RotateHandle();
	}
	
	/**
	 * 是否正在旋转
	 * @return
	 */
	public boolean isRotating(){
		return rotating;
	}
	
	/**
	 * 开始旋转
	 */
	public void startRotate(){
		rotating = true;
		handler.post(rotateHandle);
	}
	
	/**
	 * 停止旋转
	 */
	public void stopRotate(){
		rotating = false;
		handler.removeCallbacks(rotateHandle);
	}
	
	private class RotateHandle implements Runnable{
		@Override
		public void run() {
			if(!activity.isFinishing()){
				rotateDrawable.setLevel((rotateDrawable.getLevel() + rotatingIncremental) % 10000);
				handler.postDelayed(rotateHandle, rotateDelayed);
			}else{
				handler.removeCallbacks(rotateHandle);
			}
		}
	}

	/**
	 * 获取旋转延迟，每次旋转完成之后都会等待一段时间才会进行下一次旋转，这就是所谓的延迟
	 * @return 旋转延迟，单位毫秒
	 */
	public int getRotateDelayed() {
		return rotateDelayed;
	}

	/**
	 * 设置旋转延迟，每次旋转完成之后都会等待一段时间才会进行下一次旋转，这就是所谓的延迟
	 * @param rotateDelayed 旋转延迟，单位毫秒
	 */
	public void setRotateDelayed(int rotateDelayed) {
		this.rotateDelayed = rotateDelayed;
	}

	/**
	 * 获取旋转增量，例如本次旋转角度为100，下次为150，那么旋转增量就是50
	 * @return 旋转增量，单位度
	 */
	public int getRotatingIncremental() {
		return rotatingIncremental;
	}

	/**
	 * 设置选转增量，例如本次旋转角度为100，下次为150，那么旋转增量就是50
	 * @param rotatingIncremental 旋转增量，单位度
	 */
	public void setRotatingIncremental(int rotatingIncremental) {
		this.rotatingIncremental = rotatingIncremental;
	}
}