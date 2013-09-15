/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.service;

import me.xiaopan.easy.android.R;
import test.MyApplication;
import test.activity.views.FloatingWindowActivity;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 悬浮窗Service 该服务会在后台一直运行一个悬浮的透明的窗体。
 * @author
 */
public class FloatingWindowService extends Service implements OnClickListener{
	public static float CLICK_DIS = 5f;
	private int statusBarHeight;// 状态栏高度
	private float[] tempPosition = new float[] {0f,0f,0f,0f};//用来记录按下时的点的相对与其父元素的位置以及相对于屏幕的位置
	private View floatingWindowView;//悬浮窗视图
	private boolean floatingWindowViewAdded = false;//悬浮窗视图是否已经添加到窗口管理器中了
	private WindowManager.LayoutParams floatingWindowParams;//悬浮窗的参数
	private WindowManager windowManager;//窗口管理器
	private MyApplication myApplication;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		myApplication = (MyApplication) getApplication();
		windowManager = (WindowManager) this.getSystemService(WINDOW_SERVICE);
		
		floatingWindowParams = new LayoutParams();
		floatingWindowParams.width = LayoutParams.WRAP_CONTENT;//设置悬浮窗的宽
		floatingWindowParams.height = LayoutParams.WRAP_CONTENT;//设置悬浮窗的宽
		floatingWindowParams.type = LayoutParams.TYPE_PHONE;//设置悬浮窗的级别为PHONE级，可以保证悬浮窗会浮在除状态栏搜索栏之外所有类型视图的最上方
		floatingWindowParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE;//设置悬浮窗不接受焦点事件，如果不加这个标记的话，悬浮窗就会把在他之下的所有的视图的事件都给拦截了
		floatingWindowParams.format = PixelFormat.TRANSPARENT;//设置悬浮窗的背景为透明
		floatingWindowParams.gravity = Gravity.LEFT | Gravity.TOP;//设置悬浮窗开始显示的位置在左上角

		floatingWindowView = LayoutInflater.from(this).inflate(R.layout.floating_view, null);
		floatingWindowView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN: onDownHandle(event); break;
					case MotionEvent.ACTION_MOVE: onMoveHandle(event); break;
					case MotionEvent.ACTION_UP: onUpOrCancelHandle(event); break;
					case MotionEvent.ACTION_CANCEL: onUpOrCancelHandle(event); break;
					default: break;
				}
				return false;
			}
		});
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		myApplication.setNetworkSpeedFloatingWindowDisplay(true);
		updateFloatingWindow();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		myApplication.setNetworkSpeedFloatingWindowDisplay(false);
		closeFloatingWindow();
	}
	
	private void onDownHandle(MotionEvent event){
		floatingWindowView.setBackgroundResource(R.drawable.icon_floating_pressed);
		tempPosition[0] = event.getX();
		tempPosition[1] = event.getY();
		tempPosition[2] = event.getRawX();
		tempPosition[3] = event.getRawY();
	}
	
	private void onMoveHandle(MotionEvent event){
		//更新新的X轴的坐标
		floatingWindowParams.x = (int) (event.getRawX() - tempPosition[0]);
		
		//更新新的Y轴的坐标
		if(statusBarHeight == 0){//状态栏高度不能立即取，不然得到的值是0
			View rootView  = floatingWindowView.getRootView();
			Rect rect = new Rect();
			rootView.getWindowVisibleDisplayFrame(rect);
			statusBarHeight = rect.top;
		}
		floatingWindowParams.y = (int) (event.getRawY() - tempPosition[1]) - statusBarHeight;//y轴的位置要减去状态栏的高度，因为我们不可以将悬浮窗拖到状态栏去
		
		updateFloatingWindow();
	}
	
	private void onUpOrCancelHandle(MotionEvent event){
		floatingWindowView.setBackgroundResource(R.drawable.icon_floating_normal);
		if(Math.abs(event.getRawX() - tempPosition[2]) <= CLICK_DIS || Math.abs(event.getRawY() - tempPosition[3]) <= CLICK_DIS){
			onClick(floatingWindowView);
		}
	}

	/**
	 * 刷新悬浮窗，如果已经添加到WindowManager中了，就执行更新视图操作，否则执行添加视图操作
	 */
	private void updateFloatingWindow() {
		if (floatingWindowViewAdded) {
			windowManager.updateViewLayout(floatingWindowView, floatingWindowParams);
		} else {
			windowManager.addView(floatingWindowView, floatingWindowParams);
			floatingWindowViewAdded = true;
		}
	}

	/**
	 * 关闭悬浮窗
	 */
	public void closeFloatingWindow() {
		if (floatingWindowViewAdded) {
			windowManager.removeView(floatingWindowView);
			floatingWindowViewAdded = false;
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(getBaseContext(), FloatingWindowActivity.class); 
		intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}
}
