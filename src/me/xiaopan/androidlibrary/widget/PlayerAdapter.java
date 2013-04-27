package me.xiaopan.androidlibrary.widget;

import java.util.List;

import me.xiaopan.androidlibrary.widget.PicturePlayer.PlayWay;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class PlayerAdapter extends BaseAdapter{
	private List<?> list;
	private PlayWay playWay = PlayWay.CIRCLE_LEFT_TO_RIGHT;
	
	public PlayerAdapter (List<?> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return (list != null)?((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT  || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT)?Integer.MAX_VALUE:list.size()):0;
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
	
	public abstract View getRealView(int position, View convertView, ViewGroup parent);
	
	/**
	 * 获取当前选中项的真实位置
	 * @param position
	 * @return 当前选中项的真实位置
	 */
	public int getRealSelectedItemPosition(int position){
		return (list != null)?((playWay == PlayWay.CIRCLE_LEFT_TO_RIGHT || playWay == PlayWay.CIRCLE_RIGHT_TO_LEFT)?position % list.size():position):0;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public PlayWay getPlayWay() {
		return playWay;
	}

	public void setPlayWay(PlayWay playWay) {
		this.playWay = playWay;
	}
}