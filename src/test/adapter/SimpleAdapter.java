package test.adapter;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.MyBaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleAdapter extends MyBaseAdapter {
	private boolean full = true;
	private String[] strings;
	
	public SimpleAdapter(Context context, String[] strings){
		super(context);
		this.strings = strings;
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.comm_simple_list_item, null);
			viewHolder.text = (TextView) convertView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.text.setText(strings[realPosition]);
		return convertView;
	}
	
	class ViewHolder{
		TextView text;
	}

	@Override
	public Object getItem(int position) {
		return strings[position];
	}
	
	@Override
	public int getRealCount() {
		return isFull()?strings.length:3;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
}