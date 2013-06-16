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

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.MyBaseAdapter;
import me.xiaopan.easynetwork.android.image.ImageLoader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;

public class ImageAdapter extends MyBaseAdapter {
	private boolean full;
	private String[] imageUrls;
	
	public ImageAdapter(Context context){
		super(context);
		imageUrls = context.getResources().getStringArray(R.array.imageurls);
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_image_loader, null);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.listItem_image);
			viewHolder.choiceButton = (CompoundButton) convertView.findViewById(R.id.button_listItemChoice);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ImageLoader.getInstance().load(imageUrls[realPosition], viewHolder.image);
		choiceButtonHandle(viewHolder.choiceButton, realPosition);
		return convertView;
	}
	
	class ViewHolder{
		ImageView image;
		CompoundButton choiceButton;
	}

	@Override
	public Object getItem(int position) {
		return imageUrls[position];
	}
	
	@Override
	public int getRealCount() {
		return isFull()?imageUrls.length:3;
	}

	public boolean isFull() {
		return full;
	}

	public void setFull(boolean full) {
		this.full = full;
	}
}
