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
package me.xiaopan.easyandroid.widget.superlist;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * 点击加载更多列表尾
 */
public abstract class BaseLoadMoreListFooter  extends LinearLayout{
	private State state;	//状态
	private View contentView;	//内容视图
	
	public BaseLoadMoreListFooter(Context context) {
		super(context);
		setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
		addView(contentView = onGetContentView(), new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		toggleToNormalState();
	}
	
	/**
	 * 切换到正常状态
	 */
	public void toggleToNormalState(){
		setState(State.NOMRAL);
		onToggleToNormalState();
	}
	
	/**
	 * 切换到加载中状态
	 */
	public void toggleToLoadingState(){
		setState(State.LOADING);
		onToggleToLoadingState();
	}
	
	/**
	 * 获取内容视图
	 * @return
	 */
	public abstract View onGetContentView();
	
	/**
	 * 当切换到正常状态
	 */
	public abstract void onToggleToNormalState();
	
	/**
	 * 当切换到加载中状态
	 */
	public abstract void onToggleToLoadingState();
	
	/**
	 * 状态
	 */
	public enum State{
		/**
		 * 正常状态
		 */
		NOMRAL, 
		/**
		 * 加载中状态
		 */
		LOADING;
	}

	/**
	 * 获取状态
	 * @return 状态
	 */
	public State getState() {
		return state;
	}

	/**
	 * 设置状态
	 * @param state 状态
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 获取内容视图
	 * @return 内容视图
	 */
	public View getContentView() {
		return contentView;
	}

	/**
	 * 设置内容视图
	 * @param contentView 内容视图
	 */
	public void setContentView(View contentView) {
		this.contentView = contentView;
	}
}