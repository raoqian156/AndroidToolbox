package me.xiaopan.easyandroid.widget;

import android.content.Context;
import android.widget.LinearLayout;

public abstract class AbstractClickLoadListFooter  extends LinearLayout{
	public AbstractClickLoadListFooter(Context context) {
		super(context);
	}
	
	/**
	 * 当开始加载
	 */
	public abstract void startLoad();
	/**
	 * 当完成加载
	 */
	public abstract void finishLoad();
}