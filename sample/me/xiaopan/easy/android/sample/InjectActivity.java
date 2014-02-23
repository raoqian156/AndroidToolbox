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
import me.xiaopan.android.easy.activity.EasyActivity;
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectParentMember;
import me.xiaopan.android.easy.inject.InjectView;
import android.widget.TextView;

@InjectContentView(R.layout.activity_main)
@InjectParentMember
public class InjectActivity extends EasyActivity {
	@InjectView(R.id.text_main)
	private TextView text;

//	@InjectExtra("number")
//	private byte nunber;
//	
//	@InjectExtra("number")
//	private short nunber1;
//	
//	@InjectExtra("number")
//	private int nunber2;
//	
//	@InjectExtra("number")
//	private long nunber3;
//	
//	@InjectExtra("number")
//	private char nunber4;
//	
//	@InjectExtra("number")
//	private float nunber5;
//	
//	@InjectExtra("number")
//	private double nunber6;
//	
//	@InjectExtra("number")
//	private boolean nunber7;
//	
//	@InjectExtra("number")
//	private String nunber8;
//	
//	@InjectExtra("number")
//	private CharSequence nunber9;
//	
//	@InjectExtra("number")
//	private Serializable nunber10;
//	
//	@InjectExtra("number")
//	private Parcelable nunber11;
	
	@Override
	protected void onResume() {
		super.onResume();
		text.setText("启动耗时："+Second.SECOND_CHRONOGRAPH.count().getIntervalMillis()+"毫秒");
	}
}
