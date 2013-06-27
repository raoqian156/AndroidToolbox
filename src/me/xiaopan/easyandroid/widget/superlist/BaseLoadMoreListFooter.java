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
import android.widget.LinearLayout;

/**
 * 点击加载更多列表尾
 */
public abstract class BaseLoadMoreListFooter  extends LinearLayout{
	public BaseLoadMoreListFooter(Context context) {
		super(context);
	}
	
	/**
	 * 当进入加载状态
	 */
	public abstract void onLoadingState();
	
	/**
	 * 当恢复到正常状态
	 */
	public abstract void onNormalState();
}