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
import android.widget.ListAdapter;

/**
 * 反弹列表
 */
public class ReboundListView extends PullListView {
	public ReboundListView(Context context) {
		super(context);
	}

	public ReboundListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openListHeaderReboundMode();//打开列表头反弹模式
		openListFooterReboundMode();//打开列表尾反弹模式
		super.setAdapter(adapter);
	}
}
