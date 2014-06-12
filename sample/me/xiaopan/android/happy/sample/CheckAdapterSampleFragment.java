package me.xiaopan.android.happy.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.happy.R;
import me.xiaopan.android.widget.CheckAdapter;
import me.xiaopan.android.widget.CheckAdapter.CheckItem;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CheckAdapterSampleFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final ListView listView = new ListView(getActivity());
		listView.setBackgroundColor(Color.BLACK);
		
		List<Item> items = new ArrayList<Item>();
		items.add(new Item("诺基亚"));
		items.add(new Item("摩托罗拉"));
		items.add(new Item("黑莓"));
		items.add(new Item("小米"));
		items.add(new Item("三星"));
		items.add(new Item("HTC"));
		items.add(new Item("联想"));
		items.add(new Item("华为"));
		items.add(new Item("苹果"));
		items.add(new Item("金立"));
		items.add(new Item("OPPO"));
		items.add(new Item("步步高"));
		items.add(new Item("朵唯"));
		items.add(new Item("酷派"));
		items.add(new Item("魅族"));
		items.add(new Item("中兴"));
		items.add(new Item("索尼"));
		items.add(new Item("LG"));
		items.add(new Item("TCL"));
		items.add(new Item("海信"));
		items.add(new Item("神舟"));
		items.add(new Item("天语"));
		final StringAdapter stringAdapter = new StringAdapter(getActivity(), items); 
		listView.setAdapter(stringAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				stringAdapter.clickItem(arg2);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				if(stringAdapter.isEnabledCheckMode()){
					stringAdapter.cancelCheckMode();
				}else{
					stringAdapter.enableCheckMode();
					stringAdapter.clickItem(arg2);
				}
				return true;
			}
		});
		return listView;
	}

	public class StringAdapter extends CheckAdapter<Item>{
		private Context context;
		
		public StringAdapter(Context context, List<Item> items) {
			super(items);
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder;
			if(convertView == null){
				convertView = LayoutInflater.from(context).inflate(R.layout.list_item_check, null);
				viewHolder = new ViewHolder();
				viewHolder.titleTextView = (TextView) convertView.findViewById(R.id.text_checkItem_title);
				viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.check_checkItem_check);
				convertView.setTag(viewHolder);
			}else{
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			Item item = getItems().get(position);
			viewHolder.titleTextView.setText(item.title);
			viewHolder.checkBox.setChecked(item.checked);
			
			handleCompoundButton(viewHolder.checkBox, item);
			
			return convertView;
		}
		
		private class ViewHolder{
			private TextView titleTextView;
			private CheckBox checkBox;
		}
	}
	
	public class Item implements CheckItem{
		private String title;
		private boolean checked;
		
		public Item(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		@Override
		public boolean isChecked() {
			return checked;
		}

		@Override
		public void setChecked(boolean checked) {
			this.checked = checked;
		}
	}
}
