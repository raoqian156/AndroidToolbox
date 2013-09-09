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
package me.xiaopan.easy.android.widget;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 下拉刷新列表头
 */
public abstract class AbsPullDownRefreshListHeader extends AbsPullListHeaderAndFoooter {
	public AbsPullDownRefreshListHeader(Context context) {
		super(context);
	}

	public AbsPullDownRefreshListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 下拉刷新监听器 
	 * @author xiaopan
	 */
	public interface PullDownRefreshListener{
		/**
		 * 当开始刷新
		 * @param pullDownRefreshFinishListener
		 */
		public void onRefresh(PullDownRefreshFinishListener pullDownRefreshFinishListener);
	}
	
	/**
	 * 下拉刷新终止监听器
	 * @author xiaopan
	 *
	 */
	public interface PullDownRefreshFinishListener{
		/**
		 * 终止刷新
		 */
		public void finishRefresh();
	}
}
