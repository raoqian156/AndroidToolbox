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

import me.xiaopan.easyandroid.util.AndroidLogger;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 自动加载更多列表
 */
public class ClickLoadListView extends ListView {
	private boolean isShow;
	private int lineNumber = -1;
	private AbstractClickLoadListFooter clickLoadListFooter;

	public ClickLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ClickLoadListView(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		//隐藏列表头和列表尾的分割线
		setFooterDividersEnabled(false);
		
		setOnScrollListener(new OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if(clickLoadListFooter !=null){
					//如果列表没有充满，就隐藏点击加载更多列表尾，否则显示
					clickLoadListFooter.setVisibility((isShow = visibleItemCount != totalItemCount)?View.VISIBLE:View.GONE);
					
					if(isShow){
						AndroidLogger.e("lineNumber="+lineNumber);
						if(lineNumber < 0){
							if(getLastVisiblePosition() == totalItemCount - 2 - getFooterViewsCount()){
								lineNumber = getLastVisiblePosition();
								clickLoadListFooter.startLoad();
							}
						}else{
							//滚回去了
							if(getLastVisiblePosition() < lineNumber){
								lineNumber = -1;
							}
						}
					}
				}
			}
		});
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		if(clickLoadListFooter != null){
			addFooterView(clickLoadListFooter);
		}
		super.setAdapter(adapter);
	}

	public AbstractClickLoadListFooter getClickLoadListFooter() {
		return clickLoadListFooter;
	}

	public void setClickLoadListFooter(AbstractClickLoadListFooter clickLoadListFooter) {
		this.clickLoadListFooter = clickLoadListFooter;
	}
}
