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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.xiaopan.easyandroid.util.DeviceUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * 列表菜单对话框
 */
public class ListMenuDialog extends AlertDialog {
	private String[] items;
	private Context context;
	private OnItemClickListener onItemClickListener;
	private int layoutResId, listViewId;
	private ListView listView;
	
	public ListMenuDialog(Context context, int layoutResId, int listViewId, String... items) {
		super(context);
		this.context = context;
		this.layoutResId = layoutResId;
		this.listViewId = listViewId;
		this.items = items;
	}
	
	public ListMenuDialog(Context context, int theme, int layoutResId, int listViewId, String... items) {
		super(context, theme);
		this.context = context;
		this.layoutResId = layoutResId;
		this.listViewId = listViewId;
		this.items = items;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
    	setContentView(layoutResId);
    	listView = (ListView) findViewById(listViewId);
		
    	List<Map<String, String>> listInfoMap = new ArrayList<Map<String, String>>();
		for(String item : items){
			Map<String, String> info = new HashMap<String, String>();
			info.put("text", item);
			listInfoMap.add(info);
		}
		listView.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, items));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
				if(onItemClickListener != null){
					onItemClickListener.onItemClick(parent, view, position, id);
				}
			}
		});
		
		//设置窗体显示位置
		Window window = getWindow();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.y = DeviceUtils.getScreenSize(getContext()).getHeight() - lp.height;
		window.setAttributes(lp);
	}

	public String[] getItems() {
		return items;
	}

	public void setItems(String... items) {
		this.items = items;
	}

	public OnItemClickListener getOnItemClickListener() {
		return onItemClickListener;
	}

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public int getLayoutResId() {
		return layoutResId;
	}

	public void setLayoutResId(int layoutResId) {
		this.layoutResId = layoutResId;
	}

	public int getListViewId() {
		return listViewId;
	}

	public void setListViewId(int listViewId) {
		this.listViewId = listViewId;
	}
	
	/**
	 * 设置窗体显示动画
	 * @param styleResId 样式资源ID
	 */
	public void setWindowShowAnim(int styleResId){
		getWindow().setWindowAnimations(styleResId);
	}
}
