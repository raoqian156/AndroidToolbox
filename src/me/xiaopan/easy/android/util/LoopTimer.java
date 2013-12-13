package me.xiaopan.easy.android.util;

import android.os.Handler;

/**
 * 循环定时器
 */
public class LoopTimer implements Runnable {
	private int intervalMillis;
	private boolean running;
	private Handler handler;
	private Runnable runnable;
	
	/**
	 * 创建一个循环定时器
	 * @param handler
	 * @param runnable
	 * @param intervalMillis
	 */
	public LoopTimer(Handler handler, Runnable runnable, int intervalMillis) {
		this.handler = handler;
		this.runnable = runnable;
		this.intervalMillis = intervalMillis;
	}
	
	/**
	 * 创建一个循环定时器
	 * @param runnable
	 * @param intervalMillis
	 */
	public LoopTimer(Runnable runnable, int intervalMillis) {
		this(new Handler(), runnable, intervalMillis);
	}
	
	@Override
	public void run() {
		if(running && runnable != null){
			runnable.run();
		}
	}
	
	/**
	 * 立即启动
	 */
	public void start(){
		running = true;
		handler.removeCallbacks(this);
		handler.post(this);
	}
	
	/**
	 * 延迟指定间隔毫秒启动
	 */
	public void delayStart(){
		running = true;
		handler.removeCallbacks(this);
		handler.postDelayed(this, intervalMillis);
	}
	
	/**
	 * 立即停止
	 */
	public void stop(){
		running = false;
		handler.removeCallbacks(this);
	}

	/**
	 * 获取间隔毫秒
	 * @return
	 */
	public int getIntervalMillis() {
		return intervalMillis;
	}

	/**
	 * 设置间隔毫秒
	 * @param intervalMillis
	 */
	public void setIntervalMillis(int intervalMillis) {
		this.intervalMillis = intervalMillis;
	}

	/**
	 * 是否正在运行
	 * @return
	 */
	public boolean isRunning() {
		return running;
	}

	/**
	 * 设置消息处理器
	 * @param handler
	 */
	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	/**
	 * 设置执行内容
	 * @param runnable
	 */
	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
