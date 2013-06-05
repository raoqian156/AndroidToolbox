package me.xiaopan.easyandroid.widget;

import java.util.List;

import me.xiaopan.easyandroid.widget.ViewPlayer.BaseViewPlayAdapter;
import me.xiaopan.imageloader.android.ImageLoader;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * 图片播放适配器，给我一组图片再给我，再把我交给一个ViewPlayer，我就可以在ViewPlayer上播放你给我的图片
 */
public class PicturePlayAdapter extends BaseViewPlayAdapter{
	private Context context;//上下文
	private List<String> pictures;//图片列表
	private int defaultImageResId;//默认图片ID
	private ScaleType imageScaleType = ScaleType.FIT_XY;//图片缩放模式
	
	public PicturePlayAdapter(Context context, List<String> pictures){
		super(pictures);
		this.context = context;
		this.pictures = pictures;
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
		
		ImageLoader.getInstance().load(pictures.get(position), viewHolder.imageView);
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView imageView;
	}
	
	public List<String> getPictures() {
		return pictures;
	}

	public void setPictures(List<String> pictures) {
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