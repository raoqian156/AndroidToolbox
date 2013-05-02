package me.xiaopan.androidlibrary.widget;

import java.util.List;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.LinearLayout;

/**
 * 视图播放器，用于循环播放视图，至于播放什么，你可以提供一个PlayAdapter来提供播放的内容
 */
public class ViewPlayer extends FrameLayout{
	private int switchSpace = 3000;//切换间隔
	private int animationDurationMillis = 600;//动画持续时间
	private BaseViewPlayAdapter playerAdapter;//为画廊提供视图的适配器
	private PlayIndicator playerIndicator;//播放指示器
	private OnItemClickListener onItemClickListener;//项点击监听器
	private OnItemSelectedListener onItemSelectedListener;//项选中监听器
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;//播放方式，默认是从左往右转圈
	
	private ViewGallery viewGallery;//画廊
	private boolean loadFinish;//加载成功
	private boolean currentTowardsTheRight;//当前向右播放
	private Handler switchHandler;//切换处理器
	private SwitchHandle switchHandle;//切换处理

	public ViewPlayer(Context context) {
		super(context);
	}
	
	public ViewPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 开始播放
	 */
	public void startPaly(){
		//如果之前加载失败了
		if(!loadFinish){
			if(playerAdapter != null && playerAdapter.getList() != null && playerAdapter.getList().size() > 0){
				loadFinish = true;
				removeAllViews();
				
				//初始化自动切换处理器
				switchHandler = new Handler();
				switchHandle = new SwitchHandle();
				
				//初始化画廊
				viewGallery = new ViewGallery(getContext());
				viewGallery.setAnimationDuration(animationDurationMillis);//设置动画持续时间，默认是600毫秒
				viewGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						if(getOnItemSelectedListener() != null){		//回调
							getOnItemSelectedListener().onNothingSelected(parent);
						}
					}
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						int realSelectedItemPosition = playerAdapter.getRealSelectedItemPosition(position);		//获取真实的位置，
						if(playerIndicator != null){
							playerIndicator.onItemSelected(realSelectedItemPosition);		//修改指示器的选中项
						}
						if(getOnItemSelectedListener() != null){		//回调
							getOnItemSelectedListener().onItemSelected(parent, view, realSelectedItemPosition, id);
						}
					}
				});
				viewGallery.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if(getOnItemClickListener() != null){		//回调
							getOnItemClickListener().onItemClick(parent, view, playerAdapter.getRealSelectedItemPosition(position), id);
						}
					}
				});
				viewGallery.setAdapter(playerAdapter);
				
				//初始化默认选中项
				int defaultPosition = 0;
				if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){//如果播放方式是从左向右转圈
					defaultPosition = ((Integer.MAX_VALUE/playerAdapter.getList().size())/2)*playerAdapter.getList().size();//那么默认选中项是最中间那一组的第一张
				}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){//如果播放方式是从右向左转圈
					defaultPosition = ((Integer.MAX_VALUE/playerAdapter.getList().size())/2)*playerAdapter.getList().size() + playerAdapter.getList().size() -1;//那么默认选中项是最中间那一组的最后一张
				}else if(playWay == PlayWay.SWING_LEFT_TO_RIGHT){//如果播放方式是从左向右摇摆
					defaultPosition = 0;//那么默认选中项是第一组的第一张
					currentTowardsTheRight = true;//播放方向将是向右
				}else if(playWay == PlayWay.SWING_RIGHT_TO_LEFT){//如果播放方式是从右向左摇摆
					defaultPosition = playerAdapter.getList().size() -1;//那么默认选中项是第一组的最后一张
					currentTowardsTheRight = false;//播放方向将是向左
				}
				viewGallery.setSelection(defaultPosition);
				
				//将画廊和指示器放进布局中
				addView(viewGallery);
				if(playerIndicator != null){
					playerIndicator.onInit(playerAdapter.getList().size());
					addView(playerIndicator);
				}
			}else{
				loadFinish = false;
			}
		}
		
		//如果加载成功了
		if(loadFinish){
			switchHandler.removeCallbacks(switchHandle);
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	/**
	 * 停止播放
	 */
	public void stopPaly(){
		//如果加载成功了
		if(loadFinish){
			switchHandler.removeCallbacks(switchHandle);
		}
	}
	
	/**
	 * 获取切换间隔
	 * @return 切换间隔
	 */
	public int getSwitchSpace() {
		return switchSpace;
	}

	/**
	 * 设置切换间隔
	 * @param switchSpace 切换间隔
	 */
	public void setSwitchSpace(int switchSpace) {
		this.switchSpace = switchSpace;
	}

	/**
	 * 获取项选择监听器
	 * @return 项选择监听器
	 */
	public OnItemSelectedListener getOnItemSelectedListener() {
		return onItemSelectedListener;
	}

	/**
	 * 设置项选择监听器
	 * @param onItemSelectedListener 项选择监听器
	 */
	public void setOnItemSelectedListener(OnItemSelectedListener onItemSelectedListener) {
		this.onItemSelectedListener = onItemSelectedListener;
	}

	/**
	 * 获取项点击监听器
	 * @return 项点击监听器
	 */
	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	/**
	 * 设置项点击监听器
	 * @param onItemClickListener 项点击监听器
	 */
	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	/**
	 * 获取播放方式
	 * @return 播放方式，默认：PlayWay.CIRCLE_RIGHT_TO_LEFT
	 */
	public PlayWay getPlayWay() {
		return playWay;
	}

	/**
	 * 设置播放方式
	 * @param playWay 播放方式，默认：PlayWay.CIRCLE_RIGHT_TO_LEFT
	 */
	public void setPlayWay(PlayWay playWay) {
		this.playWay = playWay;
		if(playerAdapter != null){
			playerAdapter.setPlayWay(playWay);
		}
	}

	/**
	 * 获取动画持续时间
	 * @return 动画持续时间
	 */
	public int getAnimationDurationMillis() {
		return animationDurationMillis;
	}

	/**
	 * 设置动画持续时间
	 * @param animationDurationMillis 动画持续时间
	 */
	public void setAnimationDurationMillis(int animationDurationMillis) {
		this.animationDurationMillis = animationDurationMillis;
	}

	public BaseViewPlayAdapter getPlayerAdapter() {
		return playerAdapter;
	}

	public void setPlayerAdapter(BaseViewPlayAdapter playerAdapter) {
		this.playerAdapter = playerAdapter;
		if(this.playerAdapter != null){
			this.playerAdapter.setPlayWay(playWay);
		}
	}

	public PlayIndicator getPlayerIndicator() {
		return playerIndicator;
	}

	public void setPlayerIndicator(PlayIndicator playerIndicator) {
		this.playerIndicator = playerIndicator;
	}

	/**
	 * 默认的画廊
	 * @author xiaopan
	 */
	private class ViewGallery extends Gallery {
		public ViewGallery(Context context) {
			super(context);
			setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			setSoundEffectsEnabled(false);//切换的时候不播放音效
			setSpacing(-1);//设置间距为-1，因为使用按键自动切换的时候间距大于等于0会导致切换失败，所以间距只能是-1
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e2.getX() > e1.getX()) {
				onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			} else {
				onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}
			return false;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: stopPaly(); break;
				case MotionEvent.ACTION_UP: startPaly(); break;
				case MotionEvent.ACTION_CANCEL: startPaly(); break;
				default: break;
			}
			if(getCount() > 1){
				return super.onTouchEvent(event);
			}else{
				return true;
			}
		}
	}
	
	/**
	 * 默认的画廊适配器
	 */
	/**
	 * 切换处理
	 */
	private class SwitchHandle implements Runnable{
		@Override
		public void run() {
			if(viewGallery.getCount() > 1){
				//从左向右转圈
				if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){
					viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
				}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){
					viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
				}else if(playWay == PlayWay.SWING_LEFT_TO_RIGHT || playWay == PlayWay.SWING_RIGHT_TO_LEFT){
					//如果当前是向右播放
					if(currentTowardsTheRight){
						//如果到最后一个了
						if(viewGallery.getSelectedItemPosition() == playerAdapter.getList().size() -1){
							currentTowardsTheRight = false;//标记为向左
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
						}else{
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
						}
					}else{
						//如果到第一个了
						if(viewGallery.getSelectedItemPosition() == 0){
							currentTowardsTheRight = true;
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
						}else{
							viewGallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
						}
					}
				}
			}
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}
	
	/**
	 * 播放方式
	 */
	public enum PlayWay{
		/**
		 * 从右往左转圈
		 */
		CIRCLE_RIGHT_TO_LEFT, 
		
		/**
		 * 从左往右转圈
		 */
		CIRCLE_LEFT_TO_RIGHT, 
		
		/**
		 * 从右往左摇摆
		 */
		SWING_RIGHT_TO_LEFT, 
		
		/**
		 * 从左往右摇摆
		 */
		SWING_LEFT_TO_RIGHT;
	}
	
	/**
	 * 播放指示器
	 */
	public static abstract class PlayIndicator extends LinearLayout{
		public PlayIndicator(Context context) {
			super(context);
		}
		
		public PlayIndicator(Context context, AttributeSet attrs) {
			super(context, attrs);
		}
		
		/**
		 * 初始化
		 * @param size 视图播放器要播放的视图个数
		 */
		public abstract void onInit(int size);

		/**
		 * 当视图播放器的选项被选中时，指示器要同步更改其选中项
		 * @param selectedItemPosition 选中项的位置
		 */
		public abstract void onItemSelected(int selectedItemPosition);
	}
	
	/**
	 * 视图播放适配器，主要为ViewPalyer提供播放视图
	 */
	public static abstract class BaseViewPlayAdapter extends BaseAdapter{
		private List<?> list;
		private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;
		
		public BaseViewPlayAdapter (List<?> list){
			this.list = list;
		}
		
		@Override
		public int getCount() {
			//当时循环播放的时候就返回一个int类型的最大值保证可以一直循环下去，否则就返回真实的长度
			return (list != null)?(((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT  || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT) && list.size() > 1)?Integer.MAX_VALUE:list.size()):0;
		}
		
		@Override
		public Object getItem(int position) {
			return list != null?list.get(getRealSelectedItemPosition(position)):null;
		}

		@Override
		public long getItemId(int position) {
			return getRealSelectedItemPosition(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return getRealView(getRealSelectedItemPosition(position), convertView, parent);
		}
		
		/**
		 * 获取视图
		 * @param position
		 * @param convertView
		 * @param parent
		 * @return
		 */
		public abstract View getRealView(int position, View convertView, ViewGroup parent);
		
		/**
		 * 获取当前选中项的真实位置
		 * @param position
		 * @return 当前选中项的真实位置
		 */
		public int getRealSelectedItemPosition(int position){
			return (list != null)?((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT)?position % list.size():position):0;
		}

		/**
		 * 获取列表
		 * @return 列表
		 */
		public List<?> getList() {
			return list;
		}

		/**
		 * 设置列表
		 * @param list 列表
		 */
		public void setList(List<?> list) {
			this.list = list;
		}

		/**
		 * 获取播放方式
		 * @return 播放方式
		 */
		public PlayWay getPlayWay() {
			return playWay;
		}

		/**
		 * 设置播放方式
		 * @param playWay 播放方式
		 */
		public void setPlayWay(PlayWay playWay) {
			this.playWay = playWay;
		}
	}
}