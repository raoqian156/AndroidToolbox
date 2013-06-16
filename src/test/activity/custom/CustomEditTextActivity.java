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
package test.activity.custom;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.ClearEditText;
import test.MyBaseActivity;
import android.os.Bundle;

/**
 * 自定义文本编辑器
 * @author xiaopan
 *
 */
public class CustomEditTextActivity extends MyBaseActivity {
	private ClearEditText editText;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_custom_edit_text);
		editText = (ClearEditText) findViewById(R.id.edit_customEditText_account);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		editText.setLeftDrawableResId(R.drawable.icon_user_name);
	}
}
