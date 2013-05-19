package test.activity.adapter;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easynetwork.android.ImageLoader;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class GalleryAdapter extends BaseAdapter{
	private Context context;
	private String[] urls;
	private ImageLoader imageLoader;//图片加载器
	
	public GalleryAdapter (Context context, String[] urls){
		this.context = context;
		this.urls = urls;
		imageLoader = new ImageLoader(R.drawable.image_default);
	}
	
	@Override
	public int getCount() {
		return urls.length;
	}
	
	@Override
	public Object getItem(int position) {
		return urls[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			ImageView imageView = new ImageView(context);
			imageView.setLayoutParams(new Gallery.LayoutParams(Gallery.LayoutParams.FILL_PARENT, Gallery.LayoutParams.FILL_PARENT));
			imageView.setScaleType(ScaleType.FIT_CENTER);
			viewHolder.imageView = imageView;
			convertView = imageView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		imageLoader.fromNetwork(urls[position], viewHolder.imageView);
		return convertView;
	}
	
	private class ViewHolder{
		public ImageView imageView;
	}
}