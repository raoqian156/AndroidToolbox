package me.xiaopan.androidlibrary.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * 播放指示器
 */
public abstract class PlayIndicator extends LinearLayout{
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