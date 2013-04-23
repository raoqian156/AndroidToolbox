package me.xiaopan.androidlibrary.widget;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.util.ImageLoader;
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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 图片播放器
 */
public abstract class PicturePlayer extends FrameLayout{
	private int switchSpace = 4000;//切换间隔
	private int animationDurationMillis = 600;//动画持续时间
	private ScaleType imageScaleType = ScaleType.FIT_CENTER;//图片缩放模式
	private List<Picture> pictures;//图片列表
	private DefaultGallery gallery;//画廊
	private OnItemClickListener onItemClickListener;//项点击监听器
	private OnItemSelectedListener onItemSelectedListener;//项选中监听器
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;//播放方式，默认是从左往右转圈
	
	private boolean loadFinish;//加载成功
	private boolean currentTowardsTheRight;//当前向右播放
	private Handler switchHandler;//切换处理器
	private SwitchHandle switchHandle;//切换处理

	public PicturePlayer(Context context) {
		super(context);
	}
	
	public PicturePlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	/**
	 * 当需要初始化的时候
	 * @param size 元素个数
	 */
	public abstract View onInitIndicator(int size);
	
	/**
	 * 当有选项被选中的时候
	 * @param position 被选中选项的位置
	 */
	public abstract void onIndicatorItemSelected(int position);
	
	/**
	 * 当获取默认图片资源
	 * @return
	 */
	protected abstract int onGetDefaultImageResId();
	
	/**
	 * 开始播放
	 */
	public void startPaly(){
		//如果之前加载失败了
		if(!loadFinish){
			if(pictures != null && pictures.size() > 0){
				loadFinish = true;
				removeAllViews();
				
				//初始化自动切换处理器
				switchHandler = new Handler();
				switchHandle = new SwitchHandle();
				
				//初始化画廊
				gallery = new DefaultGallery(getContext());
				gallery.setAnimationDuration(animationDurationMillis);//设置动画持续时间，默认是600毫秒
				gallery.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						if(getOnItemSelectedListener() != null){		//回调
							getOnItemSelectedListener().onNothingSelected(parent);
						}
					}
					
					@Override
					public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
						int realSelectedItemPosition = getRealSelectedItemPosition(position);		//获取真实的位置，
						onIndicatorItemSelected(realSelectedItemPosition);		//修改指示器的选中项
						if(getOnItemSelectedListener() != null){		//回调
							getOnItemSelectedListener().onItemSelected(parent, view, realSelectedItemPosition, id);
						}
					}
				});
				gallery.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
						if(getOnItemClickListener() != null){		//回调
							getOnItemClickListener().onItemClick(parent, view, position, id);
						}
					}
				});
				gallery.setAdapter(new DefaultGalleryAdapter());
				
				//初始化默认选中项
				int defaultPosition = 0;
				if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){//如果播放方式是从左向右转圈
					defaultPosition = ((Integer.MAX_VALUE/pictures.size())/2)*pictures.size();//那么默认选中项是最中间那一组的第一张
				}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){//如果播放方式是从右向左转圈
					defaultPosition = ((Integer.MAX_VALUE/pictures.size())/2)*pictures.size() + pictures.size() -1;//那么默认选中项是最中间那一组的最后一张
				}else if(playWay == PlayWay.SWING_LEFT_TO_RIGHT){//如果播放方式是从左向右摇摆
					defaultPosition = 0;//那么默认选中项是第一组的第一张
					currentTowardsTheRight = true;//播放方向将是向右
				}else if(playWay == PlayWay.SWING_RIGHT_TO_LEFT){//如果播放方式是从右向左摇摆
					defaultPosition = pictures.size() -1;//那么默认选中项是第一组的最后一张
					currentTowardsTheRight = false;//播放方向将是向左
				}
				gallery.setSelection(defaultPosition);
				
				//将画廊和指示器放进布局中
				addView(gallery);
				addView(onInitIndicator(pictures.size()));
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
	 * 获取当前选中项的真实位置
	 * @param gallerySelectedItemPosition
	 * @return 当前选中项的真实位置
	 */
	private int getRealSelectedItemPosition(int gallerySelectedItemPosition){
		if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){
			return gallerySelectedItemPosition % pictures.size();
		}else{
			return gallerySelectedItemPosition;
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
	 * 获取图片缩放类型
	 * @return 图片缩放类型
	 */
	public ScaleType getImageScaleType() {
		return imageScaleType;
	}

	/**
	 * 设置图片缩放类型
	 * @param imageScaleType 图片缩放类型
	 */
	public void setImageScaleType(ScaleType imageScaleType) {
		this.imageScaleType = imageScaleType;
	}

	/**
	 * 获取要播放的图片的列表
	 * @return 要播放的图片的列表
	 */
	public List<Picture> getPictures() {
		return pictures;
	}

	/**
	 * 设置要播放的图片的列表
	 * @param pictures 要播放的图片的列表
	 */
	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	/**
	 * 设置URLS
	 * @param imageUrls
	 */
	public void setUrls(String... imageUrls){
		pictures = new ArrayList<Picture>(imageUrls.length);
		for(int w = 0; w < imageUrls.length; w++){
			pictures.add(new Picture(imageUrls[w]));
		}
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

	/**
	 * 图片对象
	 * @author xiaopan
	 */
	public static class Picture{
		private String url;
		private File file;
		
		public Picture(String url, File file){
			setUrl(url);
			setFile(file);
		}
		
		public Picture(String url){
			this(url, null);
		}
		
		public String getUrl() {
			return url;
		}
		
		public void setUrl(String url) {
			this.url = url;
		}
		
		public File getFile() {
			return file;
		}
		
		public void setFile(File file) {
			this.file = file;
		}
	}
	
	/**
	 * 默认的画廊
	 * @author xiaopan
	 */
	private class DefaultGallery extends Gallery {
		public DefaultGallery(Context context) {
			super(context);
			setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			setSoundEffectsEnabled(false);//切换的时候不播放音效
			setSpacing(-1);//设置间距为-1，因为使用按键自动切换的时候间距大于等于0会导致切换失败，所以间距只能是-1//设置当画廊选中项改变时，改变指示器
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
			return super.onTouchEvent(event);
		}
	}
	
	/**
	 * 默认的画廊适配器
	 */
	private class DefaultGalleryAdapter extends BaseAdapter{
		private ImageLoader imageLoader;
		private DefaultGalleryAdapter(){
			imageLoader = new ImageLoader(onGetDefaultImageResId());
		}
		
		@Override
		public int getCount() {
			if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){
				return Integer.MAX_VALUE;
			}else{
				return pictures.size();
			}
		}

		@Override
		public Object getItem(int position) {
			return pictures.get(getRealSelectedItemPosition(position));
		}

		@Override
		public long getItemId(int position) {
			return getRealSelectedItemPosition(position);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				viewHolder = new ViewHolder();
				ImageView imageView = new ImageView(getContext());
				imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
				imageView.setScaleType(getImageScaleType());
				viewHolder.imageView = imageView;
				convertView = imageView;
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Picture picture = pictures.get(getRealSelectedItemPosition(position));
			
			if(picture.getFile() != null){
				imageLoader.fromLocalByPriority(picture.getFile(), viewHolder.imageView, picture.getUrl());
			}else{
				imageLoader.fromNetwork(picture.getUrl(), viewHolder.imageView);
			}
			return convertView;
		}
		
		private class ViewHolder{
			public ImageView imageView;
		}
	}

	/**
	 * 切换处理
	 * @author xiaopan
	 */
	private class SwitchHandle implements Runnable{
		@Override
		public void run() {
			//从左向右转圈
			if(playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT){
				gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
			}else if(playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT){
				gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
			}else if(playWay == PlayWay.SWING_LEFT_TO_RIGHT || playWay == PlayWay.SWING_RIGHT_TO_LEFT){
				//如果当前是向右播放
				if(currentTowardsTheRight){
					//如果到最后一个了
					if(gallery.getSelectedItemPosition() == pictures.size() -1){
						currentTowardsTheRight = false;//标记为向左
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
					}else{
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					}
				}else{
					//如果到第一个了
					if(gallery.getSelectedItemPosition() == 0){
						currentTowardsTheRight = true;
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
					}else{
						gallery.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, null);
					}
				}
			}
			switchHandler.postDelayed(switchHandle, switchSpace);
		}
	}

	/**
	 * 播放方式
	 * @author xiaopan
	 *
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
}