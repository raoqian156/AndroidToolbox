package me.xiaopan.android.easy.util;

import android.os.Handler;
import android.view.View;

/**
 * 视图刷新处理器
 */
public class ViewRefreshHandler implements Runnable{
	private View view;
	private Handler handler;
	private int intervalMillis;
	
	public ViewRefreshHandler(View view, Handler handler, int intervalMillis) {
		this.view = view;
		this.handler = handler;
		this.intervalMillis = intervalMillis;
	}
	
	public ViewRefreshHandler(View view, Handler handler) {
		this(view, handler, 30);
	}
	
	public ViewRefreshHandler(View view, int intervalMillis) {
		this(view, new Handler(), intervalMillis);
	}
	
	public ViewRefreshHandler(View view) {
		this(view, new Handler(), 30);
	}
	
	@Override
	public void run(){
		view.invalidate();
		handler.postDelayed(this, intervalMillis);
	}
	
	public void start(){
		handler.removeCallbacks(this);
		handler.post(this);
	}
	
	public void stop(){
		handler.removeCallbacks(this);
		view.invalidate();
	}
}