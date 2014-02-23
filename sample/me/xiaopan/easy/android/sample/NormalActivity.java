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
import me.xiaopan.android.easy.inject.DisableInject;
import me.xiaopan.android.easy.inject.InjectExtra;
import android.os.Bundle;
import android.widget.TextView;

@DisableInject
public class NormalActivity extends EasyActivity {
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private TextView textView7;
	private TextView textView8;
	private TextView textView9;
	private TextView textView10;

	private byte byteField;
	private short shortField;
	private int intField;
	private long longField;
	private char charField;
	private float floatField;
	private double doubleField;
	private boolean booleanField;
	private String stringField;
	private CharSequence charSequenceField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView1 = (TextView) findViewById(R.id.text_main1);
		textView2 = (TextView) findViewById(R.id.text_main2);
		textView3 = (TextView) findViewById(R.id.text_main3);
		textView4 = (TextView) findViewById(R.id.text_main4);
		textView5 = (TextView) findViewById(R.id.text_main5);
		textView6 = (TextView) findViewById(R.id.text_main6);
		textView7 = (TextView) findViewById(R.id.text_main7);
		textView8 = (TextView) findViewById(R.id.text_main8);
		textView9 = (TextView) findViewById(R.id.text_main9);
		textView10 = (TextView) findViewById(R.id.text_main10);
		
		byteField = getIntent().getExtras().getByte(InjectActivity.PARAM_BYTE);
		shortField = getIntent().getExtras().getShort(InjectActivity.PARAM_SHORT);
		intField = getIntent().getExtras().getInt(InjectActivity.PARAM_INT);
		longField = getIntent().getExtras().getLong(InjectActivity.PARAM_LONG);
		charField = getIntent().getExtras().getChar(InjectActivity.PARAM_CHAR);
		floatField = getIntent().getExtras().getFloat(InjectActivity.PARAM_FLOAT);
		doubleField = getIntent().getExtras().getDouble(InjectActivity.PARAM_DOUBLE);
		booleanField = getIntent().getExtras().getBoolean(InjectActivity.PARAM_BOOLEAN);
		stringField = getIntent().getExtras().getString(InjectActivity.PARAM_STRING);
		charSequenceField = getIntent().getExtras().getCharSequence(InjectActivity.PARAM_CHAR_SEQUENCE);
		
		textView2.setText("byteField="+byteField+"; " +
				"shortField="+shortField+"; " +
				"intField="+intField+"; " +
				"longField="+longField+"; " +
				"charField="+charField+"; " +
				"floatField="+floatField+"; " +
				"doubleField="+doubleField+"; " +
				"booleanField="+booleanField+"; " +
				"stringField="+stringField+"; " +
				"charSequenceField="+charSequenceField);
	}

	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.count().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
