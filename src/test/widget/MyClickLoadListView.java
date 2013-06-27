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

import me.xiaopan.easyandroid.widget.ClickLoadListView;
import test.widget.LoadMoreListFooter.ClickLoadListener;
import android.content.Context;
import android.util.AttributeSet;

public class MyClickLoadListView extends ClickLoadListView {
	private LoadMoreListFooter clickLoadListView;
		
	public MyClickLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickLoadListFooter(clickLoadListView = new LoadMoreListFooter(getContext()));
	}

	public MyClickLoadListView(Context context) {
		super(context);
		setClickLoadListFooter(clickLoadListView = new LoadMoreListFooter(getContext()));
	}

	public ClickLoadListener getOnLoadListener() {
		return clickLoadListView !=null?clickLoadListView.getOnLoadListener():null;
	}

	public void setOnLoadListener(ClickLoadListener onLoadListener) {
		if(clickLoadListView != null){
			clickLoadListView.setOnLoadListener(onLoadListener);
		}
	}
}
