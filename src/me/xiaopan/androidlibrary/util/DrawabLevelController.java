package me.xiaopan.androidlibrary.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.Log;

/**
 * 图片级别控制器，用来控制Drawable的级别
 * @author xiaopan
 *
 */
public class DrawabLevelController {
	private Activity activity;
	private Drawable drawable;
	private Handler handler;
	private Handle handle;
	private boolean running;
	private int delayed = 10;	//延迟，默认10
	private int incremental = 150;	//增量，默认150
	private int repeatCount = -1;	//重复次数，默认-1表示永不停止
	private RepeatMode repeatMode = RepeatMode.ORDER;	//重复方式
	private Integer hasRepeatCount = 0;	//已经重复的次数
	private Listener listener;	//监听器
	private int minLevel = 0;	//最小级别，默认0
	private int maxLevel = 10000;	//最大级别，默认10000
	
	/**
	 * 创建一个图片级别控制器
	 * @param activity 当activity销毁的时候会停止
	 * @param drawable 要控制的图片
	 */
	public DrawabLevelController(Activity activity, Drawable drawable){
		this.activity = activity;
		this.drawable = drawable;
		handler = new Handler();
		handle = new Handle();
	}
	
	/**
	 * 是否正在运行
	 * @return 正在运行
	 */
	public boolean isRunning(){
		return running;
	}
	
	/**
	 * 开始执行
	 * @return true：执行成功；false：执行次数已经达到规定的最大重复次数，所以不再执行，您可以通过reset()方法重置执行次数
	 */
	public boolean start(){
		//如果执行次数已经达到规定的最大重复次数的话就不执行
		if((repeatCount > 0 && hasRepeatCount >= repeatCount)){
			return false;
		}else{
			running = true;
			handler.post(handle);
			if(listener != null){
				listener.onStart();
			}
			return true;
		}
	}
	
	/**
	 * 暂停执行
	 */
	public void pause(){
		running = false;
		handler.removeCallbacks(handle);
		if(listener != null){
			listener.onPause();
		}
	}
	
	/**
	 * 重置，重置已执行的次数，重置的时候并不会停止执行
	 */
	public void reset(){
		synchronized (hasRepeatCount) {
			hasRepeatCount = 0;
		}
		if(listener != null){
			listener.onReset();
		}
	}
	
	/**
	 * 处理
	 */
	private class Handle implements Runnable{
		@Override
		public void run() {
			if(!activity.isFinishing()){
				if(repeatMode == RepeatMode.ORDER){
					if(drawable.getLevel() < minLevel){
						drawable.setLevel(minLevel);
					}
					int newLevel = drawable.getLevel() + incremental;		//计算新的级别
					drawable.setLevel(newLevel % maxLevel);//更新级别
					if(newLevel >= maxLevel){//如果新的级别大于等于最大级别，说明执行玩一圈了
						hasRepeatCount++;//既然执行完一圈了那么重复次数就应该加1
						if(repeatCount > 0 && hasRepeatCount >= repeatCount){//如果重复次数有限制并且执行次数大于或等于重复次数，说明该停止了
							pause();
						}else{
							handler.postDelayed(handle, delayed);
						}
					}else{
						handler.postDelayed(handle, delayed);
					}
					Log.i("测试", "drawableLevel="+drawable.getLevel());
				}else if(repeatMode == RepeatMode.REVERSE){
					if(drawable.getLevel() <= minLevel){
						drawable.setLevel(maxLevel);
					}
					int newLevel = drawable.getLevel() - incremental;		//计算新的级别，由于是逆向执行的，所以新的级别的计算方式也不一样
					drawable.setLevel(newLevel % maxLevel);//更新级别
					if(newLevel <= minLevel){//如果新的级别小于等于最小级别，说明执行玩一圈了
						hasRepeatCount++;//既然执行完一圈了那么重复次数就应该加1
						if(repeatCount > 0 && hasRepeatCount >= repeatCount){//如果重复次数有限制并且执行次数大于或等于重复次数，说明该停止了
							pause();
						}else{
							handler.postDelayed(handle, delayed);
						}
					}else{
						handler.postDelayed(handle, delayed);
					}
					Log.i("测试", "drawableLevel="+drawable.getLevel());
				}else if(repeatMode == RepeatMode.MIRROR){
					
				}
			}else{
				running = false;
				handler.removeCallbacks(handle);
			}
		}
	}

	/**
	 * 获取延迟，每次完成之后都会等待一段时间才会进行下一次，这就是所谓的延迟
	 * @return 延迟，单位毫秒，默认10
	 */
	public int getDelayed() {
		return delayed;
	}

	/**
	 * 设置延迟，每次完成之后都会等待一段时间才会进行下一次，这就是所谓的延迟
	 * @param delayed 延迟，单位毫秒，默认10
	 */
	public void setDelayed(int delayed) {
		this.delayed = delayed;
	}

	/**
	 * 获取增量，例如本次级别为100，下次为150，那么增量就是50
	 * @return 增量，默认150
	 */
	public int getIncremental() {
		return incremental;
	}

	/**
	 * 设置增量，例如本次级别为100，下次为150，那么增量就是50
	 * @param incremental 增量，默认150
	 */
	public void setIncremental(int incremental) {
		this.incremental = incremental;
	}
	
	/**
	 * 获取重复次数
	 * @return 重复次数，默认为-1，-1表示永不停止
	 */
	public int getRepeatCount() {
		return repeatCount;
	}

	/**
	 * 设置重复次数
	 * @param repeatCount 重复次数，默认为-1，-1表示永不停止
	 */
	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	/**
	 * 获取重复方式
	 * @return 重复方式，默认为RepeatMode.ORDER
	 */
	public RepeatMode getRepeatMode() {
		return repeatMode;
	}

	/**
	 * 设置重复方式
	 * @param repeatMode 重复方式，默认为RepeatMode.ORDER
	 */
	public void setRepeatMode(RepeatMode repeatMode) {
		this.repeatMode = repeatMode;
	}

	/**
	 * 获取事件监听器 
	 * @return 事件监听器
	 */
	public Listener getListener() {
		return listener;
	}

	/**
	 * 设置事件监听器
	 * @param listener 事件监听器
	 */
	public void setListener(Listener listener) {
		this.listener = listener;
	}

	/**
	 * 获取最小级别
	 * @return 最小级别，默认0
	 */
	public int getMinLevel() {
		return minLevel;
	}

	/**
	 * 设置最小级别
	 * @param minLevel 最小级别，默认0
	 */
	public void setMinLevel(int minLevel) {
		this.minLevel = minLevel;
	}

	/**
	 * 获取最大级别
	 * @return 最大级别，默认10000
	 */
	public int getMaxLevel() {
		return maxLevel;
	}

	/**
	 * 设置最大级别
	 * @param minLevel 最大级别，默认10000
	 */
	public void setMaxLevel(int maxLevel) {
		this.maxLevel = maxLevel;
	}

	/**
	 * 重复方式
	 * @author xiaopan
	 *
	 */
	public enum RepeatMode{
		/**
		 * 顺序执行，例如：从最小级别到最大级别，再从最小级别到最大级别...
		 */
		ORDER,
		/**
		 * 反向执行，例如：从最大级别到最小级别，再从最大级别到最小级别...
		 */
		REVERSE,
		/**
		 * 镜像执行，例如：从最小级别到最大级别，再从最大级别-最小级别，再从最小级别到最大级别...
		 */
		MIRROR;
	}
	
	/**
	 * 事件监听器
	 */
	public interface Listener{
		/**
		 * 当启动的时候
		 */
		public void onStart();
		/**
		 * 当暂停的时候
		 */
		public void onPause();
		/**
		 * 当重置的时候
		 */
		public void onReset();
	}
}