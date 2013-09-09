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

import me.xiaopan.easy.android.widget.BaseLoadMoreListFooter;
import me.xiaopan.easyandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

/**
 * 加载更多列表尾
 */
public class LoadMoreListFooter extends BaseLoadMoreListFooter{
	public LoadMoreListFooter(Context context) {
		super(context);
	}

	@Override
	public View onGetContentView() {
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_footer_load_more, null);
	}
	
	@Override
	public void onToggleToLoadingState(){
		getContentView().setVisibility(View.VISIBLE);
	}

	@Override
	public void onToggleToNormalState() {
		getContentView().setVisibility(View.GONE);
	}
}