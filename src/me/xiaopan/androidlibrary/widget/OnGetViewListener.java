package me.xiaopan.androidlibrary.widget;

import me.xiaopan.androidlibrary.util.ImageLoader;
import me.xiaopan.androidlibrary.widget.PicturePlayer.Picture;
import android.view.View;
import android.view.ViewGroup;

/**
 * 当获取视图监听器
 * @author xiaopan
 *
 */
public interface OnGetViewListener{
	/**
	 * 当获取画廊适配器需要的视图的时候
	 * @param convertView
	 * @param parent
	 * @param picture
	 * @param imageLoader
	 * @return
	 */
	public View onGetView(View convertView, ViewGroup parent, Picture picture, ImageLoader imageLoader);
}