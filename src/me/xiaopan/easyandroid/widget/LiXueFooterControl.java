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

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LiXueFooterControl  extends LinearLayout {
	private TextView textView;
	private ProgressBar progressBar;
	private onloadListener onListener;
	public LiXueFooterControl(Context context) {
		super(context);
		//初始化控件
		LoadControl();
	}
	public LiXueFooterControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		//初始化控件
		LoadControl();
	}
	//加载控件
	public void LoadControl(){
		textView =new TextView(getContext());
		textView.setText("加载更多");
		textView.setVisibility(GONE);
		progressBar=new ProgressBar(getContext());
		progressBar.setMax(100);
		progressBar.setVisibility(GONE);
		addView(progressBar);
		addView(textView);
		setGravity(Gravity.CENTER);
	}
	//开始加载
	public void Startload(){
		textView.setVisibility(VISIBLE);
		textView.setText("开始加载");
		progressBar.setVisibility(VISIBLE);
		if(onListener!=null){
			onListener.onStartload(this);
		}
	}

	public onloadListener getOnListener() {
		return onListener;
	}
	public void setOnListener(onloadListener onListener) {
		this.onListener = onListener;
	}
	//加載完成
	public void Endload(){
		textView.setVisibility(GONE);
		progressBar.setVisibility(GONE);
	}
	
	public interface onloadListener{
		public void onStartload(LiXueFooterControl liXueFooterControl);
	}
}
