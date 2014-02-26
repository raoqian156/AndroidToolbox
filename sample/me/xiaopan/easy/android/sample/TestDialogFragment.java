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

package me.xiaopan.easy.android.sample;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.fragment.EasyDialogFragment;
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

@InjectContentView(R.layout.fragment_dialog_test)
public class TestDialogFragment extends EasyDialogFragment {
	@InjectView(R.id.text_dialog)
	private TextView textView;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		textView.setText("启动耗时："+Second.SECOND_CHRONOGRAPH.lap().getIntervalMillis()+"毫秒");
	}
}
