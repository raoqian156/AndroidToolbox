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
package test.widget;

import me.xiaopan.easyandroid.widget.superlist.BaseLoadMoreListFooter;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadMoreListFooter extends BaseLoadMoreListFooter{
	private ProgressBar progressBar;
	private TextView textView;
	private ClickLoadListener onLoadListener;

	public LoadMoreListFooter(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		setPadding(20, 20, 20, 20);
		setGravity(Gravity.CENTER);
		
		progressBar = new ProgressBar(getContext());
		progressBar.setVisibility(View.GONE);
		textView = new TextView(getContext());
		textView.setText("正在加载");
		
		intoNormalState();
		
		addView(progressBar);
		addView(textView);
	}
	
	public void intoNormalState(){
		textView.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
	}
	
	@Override
	public void onLoadingState(){
		textView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		if(onLoadListener != null){
			onLoadListener.onStartLoad(this);
		}
	}

	@Override
	public void onNormalState() {
		intoNormalState();
	}
	
	public interface ClickLoadListener{
		/**
		 * 当开始加载
		 */
		public void onStartLoad(BaseLoadMoreListFooter clickLoadListFooter);
	}

	public ClickLoadListener getOnLoadListener() {
		return onLoadListener;
	}

	public void setOnLoadListener(ClickLoadListener onLoadListener) {
		this.onLoadListener = onLoadListener;
	}
}
