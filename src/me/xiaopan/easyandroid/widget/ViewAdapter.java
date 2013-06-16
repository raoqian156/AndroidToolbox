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
package me.xiaopan.easyandroid.widget;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;

/**
 * View适配器
 */
public class ViewAdapter extends MyBaseAdapter {
	private List<View> viewList;
	
	public ViewAdapter(List<View> viewList){
		this.viewList = viewList;
	}
	
	@Override
	public int getRealCount() {
		return viewList.size();
	}

	@Override
	public Object getItem(int position) {
		return viewList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getRealView(int realPosition, View convertView, ViewGroup parent) {
		return viewList.get(realPosition);
	}
}
