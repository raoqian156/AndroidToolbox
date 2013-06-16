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

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.AbsClickLoadMoreListFooter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyClickLoadMoreListFooter extends AbsClickLoadMoreListFooter {

	public MyClickLoadMoreListFooter(Context context) {
		super(context);
	}

	@Override
	protected LinearLayout onGetContentView() {
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_footer_click_load_more, null);
	}

	@Override
	protected void onIntoLoadingState(LinearLayout contentView) {
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.clickLoadMore_progress);
		progressBar.setVisibility(View.VISIBLE);
		
		TextView textView = (TextView) contentView.findViewById(R.id.clickLoadMore_text);
		textView.setText(getResources().getString(R.string.base_loadingLater));
	}

	@Override
	protected void onIntoNormalState(LinearLayout contentView) {
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.clickLoadMore_progress);
		progressBar.setVisibility(View.GONE);
		
		TextView textView = (TextView) contentView.findViewById(R.id.clickLoadMore_text);
		textView.setText("点击加载更多");
	}
}
