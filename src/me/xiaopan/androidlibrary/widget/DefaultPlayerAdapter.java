package me.xiaopan.androidlibrary.widget;

import java.io.File;
import java.util.List;

import me.xiaopan.androidlibrary.util.ImageLoader;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class DefaultPlayerAdapter extends PlayerAdapter{
	private Context context;//上下文
	private List<Picture> pictures;//图片列表
	private int defaultImageResId;//默认图片ID
	private ScaleType imageScaleType = ScaleType.FIT_CENTER;//图片缩放模式
	private ImageLoader imageLoader;//图片加载器
	
	public DefaultPlayerAdapter(Context context, List<Picture> pictures, int defaultImageResId){
		super(pictures);
		this.context = context;
		this.pictures = pictures;
		imageLoader = new ImageLoader(defaultImageResId);
	}
	
	@Override
	public View getRealView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			imageView.setScaleType(imageScaleType);
			viewHolder.imageView = imageView;
			convertView = imageView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		Picture picture = pictures.get(position);
		
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

	public List<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public int getDefaultImageResId() {
		return defaultImageResId;
	}

	public void setDefaultImageResId(int defaultImageResId) {
		this.defaultImageResId = defaultImageResId;
	}

	public ScaleType getImageScaleType() {
		return imageScaleType;
	}

	public void setImageScaleType(ScaleType imageScaleType) {
		this.imageScaleType = imageScaleType;
	}
}