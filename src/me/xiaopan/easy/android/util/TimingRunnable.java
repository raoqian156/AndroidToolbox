package me.xiaopan.easy.android.util;

import android.os.Handler;

/**
 * 定时器
 */
public class TimingRunnable implements Runnable {
	private int delayed;
	private boolean running;
	private Handler handler;
	private Runnable runnable;
	
	public TimingRunnable(Handler handler, Runnable runnable, int delayed) {
		this.handler = handler;
		this.runnable = runnable;
		this.delayed = delayed;
	}
	
	@Override
	public void run() {
		if(running && runnable != null){
			runnable.run();
		}
	}
	
	/**
	 * 重新启动
	 */
	public void restart(){
		running = true;
		handler.removeCallbacks(this);
		handler.postDelayed(this, delayed);
	}
	
	/**
	 * 停止
	 */
	public void stop(){
		running = false;
		handler.removeCallbacks(this);
	}

	public int getDelayed() {
		return delayed;
	}

	public void setDelayed(int delayed) {
		this.delayed = delayed;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Runnable getRunnable() {
		return runnable;
	}

	public void setRunnable(Runnable runnable) {
		this.runnable = runnable;
	}
}
