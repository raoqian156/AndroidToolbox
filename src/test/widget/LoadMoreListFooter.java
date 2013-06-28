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

import me.xiaopan.easyandroid.widget.superlist.BaseLoadMoreListFooter;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadMoreListFooter extends BaseLoadMoreListFooter{
	private ProgressBar progressBar;
	private TextView textView;

	public LoadMoreListFooter(Context context) {
		super(context);
	}

	@Override
	public View onGetContentView() {
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setPadding(20, 20, 20, 20);
		linearLayout.setGravity(Gravity.CENTER);
		
		progressBar = new ProgressBar(getContext());
		linearLayout.addView(progressBar);
		
		textView = new TextView(getContext());
		textView.setText("正在加载下一页，请稍后");
		linearLayout.addView(textView);
		
		return linearLayout;
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