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

package me.xiaopan.android.easy.util;

/**
 * 双击检查器，用于识别双击手势，并在第一次和第二次点击的时候分别做出回调
 */
public class DoubleClickDetector {
	private long lastClickTime;	//上次点击的时间
	private int doubleClickSpacingInterval;	//有效的双击间隔时间
	private OnSingleClickListener onSingleClickListener;	//单击监听器
	private OnDoubleClickListener onDoubleClickListener; 	//双击监听器
	
	public DoubleClickDetector(int doubleClickSpacingInterval, OnSingleClickListener onSingleClickListener, OnDoubleClickListener onDoubleClickListener) {
		this.doubleClickSpacingInterval = doubleClickSpacingInterval;
		this.onSingleClickListener = onSingleClickListener;
		this.onDoubleClickListener = onDoubleClickListener;
	}

	public DoubleClickDetector(OnSingleClickListener onSingleClickListener, OnDoubleClickListener onDoubleClickListener) {
		this(2000, onSingleClickListener, onDoubleClickListener);
	}

	/**
	 * 点击一次
	 */
	public void click(){
		long currentMillisTime = System.currentTimeMillis();
		if(lastClickTime != 0 && (currentMillisTime - lastClickTime) < doubleClickSpacingInterval){
			if(onDoubleClickListener != null){
				onDoubleClickListener.onDoubleClick();
			}
		}else{
			if(onSingleClickListener != null){
				onSingleClickListener.onSingleClick();
			}
		}
		lastClickTime = currentMillisTime;
	}
	
	/**
	 * 获取双击有效间隔时间，单位毫秒，默认2000
	 * @return
	 */
	public int getDoubleClickSpacingInterval() {
		return doubleClickSpacingInterval;
	}

	/**
	 * 设置双击有效间隔时间，单位毫秒，默认2000
	 * @param doubleClickSpacingInterval
	 */
	public void setDoubleClickSpacingInterval(int doubleClickSpacingInterval) {
		this.doubleClickSpacingInterval = doubleClickSpacingInterval;
	}

	/**
	 * 设置单击监听器
	 * @param onSingleClickListener
	 */
	public void setOnSingleClickListener(OnSingleClickListener onSingleClickListener) {
		this.onSingleClickListener = onSingleClickListener;
	}

	/**
	 * 设置双击听器
	 * @param onDoubleClickListener
	 */
	public void setOnDoubleClickListener(OnDoubleClickListener onDoubleClickListener) {
		this.onDoubleClickListener = onDoubleClickListener;
	}

	/**
	 * 单击监听器
	 */
	public interface OnSingleClickListener{
		/**
		 * 当单击
		 */
		public void onSingleClick();
	}
	
	/**
	 * 双击监听器
	 */
	public interface OnDoubleClickListener{
		/**
		 * 当双击
		 */
		public void onDoubleClick();
	}
}
