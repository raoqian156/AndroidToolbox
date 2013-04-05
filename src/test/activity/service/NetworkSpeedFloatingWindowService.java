package test.activity.service;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.AndroidUtils;
import test.MyApplication;
import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

/**
 * 悬浮窗Service 该服务会在后台一直运行一个悬浮的透明的窗体。
 * @author
 */
public class NetworkSpeedFloatingWindowService extends Service{
	private int statusBarHeight;// 状态栏高度
	private float[] temp = new float[] { 0f, 0f };
	private View floatingWindowView;//悬浮窗视图
	private boolean floatingWindowViewAdded = false;//悬浮窗视图是否已经添加到窗口管理器中了
	private WindowManager.LayoutParams floatingWindowParams;//悬浮窗的参数
	private WindowManager windowManager;//窗口管理器
	private MyApplication myApplication;
	private boolean click;

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

		floatingWindowView = LayoutInflater.from(this).inflate(R.layout.network_speed_floating_window, null);
		floatingWindowView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int eventaction = event.getAction();
				switch (eventaction) {
					case MotionEvent.ACTION_DOWN: // 按下事件，记录按下时手指在悬浮窗的XY坐标值
						click = true;
						temp[0] = event.getX();
						temp[1] = event.getY();
						break;
					case MotionEvent.ACTION_MOVE://当移动的时候，就更新悬浮窗的位置
						click = false;
						//更新新的X轴的坐标
						floatingWindowParams.x = (int) (event.getRawX() - temp[0]);
						
						//更新新的Y轴的坐标
						if(statusBarHeight == 0){//状态栏高度不能立即取，不然得到的值是0
							View rootView  = floatingWindowView.getRootView();
							Rect rect = new Rect();
							rootView.getWindowVisibleDisplayFrame(rect);
							statusBarHeight = rect.top;
						}
						floatingWindowParams.y = (int) (event.getRawY() - temp[1]) - statusBarHeight;//y轴的位置要减去状态栏的高度，因为我们不可以将悬浮窗拖到状态栏去
						
						updateFloatingWindow();
						break;
					case MotionEvent.ACTION_UP:
						if(click || event.getRawX() - temp[0] < 10 || event.getRawY() - temp[1] < 10){
							AndroidUtils.toastL(getBaseContext(), "你有病啊，你点我干嘛！");
						}
						break;
					case MotionEvent.ACTION_CANCEL:
						if(click || event.getRawX() - temp[0] < 10 || event.getRawY() - temp[1] < 10){
							AndroidUtils.toastL(getBaseContext(), "你有病啊，你点我干嘛！");
						}
						break;
				}
				return false;
			}
		});
		
//		floatingWindowView.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if(click){
//					AndroidUtils.toastL(getBaseContext(), "你有病啊，你点我干嘛！");
//				}
//			}
//		});
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
}