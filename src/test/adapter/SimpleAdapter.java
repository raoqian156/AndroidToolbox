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

public class SimpleAdapter extends MyBaseAdapter {
	private boolean full = true;
	private List<String> contents;
	
	public SimpleAdapter(Context context, List<String> contents){
		super(context);
		this.contents = contents;
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_simple, null);
			viewHolder.text = (TextView) convertView;
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.text.setText(contents.get(realPosition));
		return convertView;
	}
	
	class ViewHolder{
		TextView text;
	}

	@Override
	public Object getItem(int position) {
		return contents.get(position);
	}
	
	@Override
	public int getRealCount() {
		return isFull()?contents.size():3;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
}
