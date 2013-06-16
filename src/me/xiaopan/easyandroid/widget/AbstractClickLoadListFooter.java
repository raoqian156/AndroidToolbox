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
import android.widget.LinearLayout;

public abstract class AbstractClickLoadListFooter  extends LinearLayout{
	public AbstractClickLoadListFooter(Context context) {
		super(context);
	}
	
	/**
	 * 当开始加载
	 */
	public abstract void startLoad();
	/**
	 * 当完成加载
	 */
	public abstract void finishLoad();
}
