/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.adapter;

import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.MyBaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ActivityAdapter extends MyBaseAdapter{

	private Context conetxt;
	private List<ActivityItem> itemList;
	
	public ActivityAdapter(Context conetxt, List<ActivityItem> itemList){
		this.conetxt = conetxt;
		this.itemList = itemList;
	}
	
	@Override
	public Object getItem(int position) {
		return itemList.get(position).getName();
	}

	@Override
	public int getRealCount() {
		return itemList.size();
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(conetxt).inflate(R.layout.list_item_simple, null);
			viewHolder.name = (TextView) convertView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.name.setText(itemList.get(realPosition).getName());
		return convertView;
	}
	
	class ViewHolder{
		private TextView name;
	}

	public static class ActivityItem {
		private String name;
		private Class<?> action;
		
		public ActivityItem(String name, Class<?> action){
			setName(name);
			setAction(action);
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Class<?> getAction() {
			return action;
		}
		public void setAction(Class<?> action) {
			this.action = action;
		}
	}
}
