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
 * 上拉加载更多列表尾
 */
public abstract class AbsPullUpLoadMoreListFooter extends AbsPullListHeaderAndFoooter {
	public AbsPullUpLoadMoreListFooter(Context context) {
		super(context);
	}

	public AbsPullUpLoadMoreListFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 上拉加载更多监听器 
	 * @author xiaopan
	 */
	public interface PullUpLoadMoreListener{
		public void onLoadMore(PullUpLoadMoreFinishListener pullUpLoadMoreFinishListener);
	}
	
	/**
	 * 上拉加载更多终止监听器
	 * @author xiaopan
	 *
	 */
	public interface PullUpLoadMoreFinishListener{
		/**
		 * 终止加载
		 */
		public void finishLoadMore();
	}
}
