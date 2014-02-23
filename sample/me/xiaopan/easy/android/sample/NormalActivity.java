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

import java.util.ArrayList;
import java.util.Arrays;

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
	private byte[] byteFields;
	private short shortField;
	private short[] shortFields;
	private int intField;
	private int[] intFields;
	private long longField;
	private long[] longFields;
	private char charField;
	private char[] charFields;
	private float floatField;
	private float[] floatFields;
	private double doubleField;
	private double[] doubleFields;
	private boolean booleanField;
	private boolean[] booleanFields;
	private String stringField;
	private String[] stringFields;
	private ArrayList<String> stringFieldList;
	private CharSequence charSequenceField;
	private CharSequence[] charSequenceFields;
	
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
		byteFields = getIntent().getExtras().getByteArray(InjectActivity.PARAM_BYTE_ARRAY);
		shortField = getIntent().getExtras().getShort(InjectActivity.PARAM_SHORT);
		shortFields = getIntent().getExtras().getShortArray(InjectActivity.PARAM_SHORT_ARRAY);
		intField = getIntent().getExtras().getInt(InjectActivity.PARAM_INT);
		intFields= getIntent().getExtras().getIntArray(InjectActivity.PARAM_INT_ARRAY);
		longField = getIntent().getExtras().getLong(InjectActivity.PARAM_LONG);
		longFields = getIntent().getExtras().getLongArray(InjectActivity.PARAM_LONG_ARRAY);
		charField = getIntent().getExtras().getChar(InjectActivity.PARAM_CHAR);
		charFields = getIntent().getExtras().getCharArray(InjectActivity.PARAM_CHAR_ARRAY);
		floatField = getIntent().getExtras().getFloat(InjectActivity.PARAM_FLOAT);
		floatFields = getIntent().getExtras().getFloatArray(InjectActivity.PARAM_FLOAT_ARRAY);
		doubleField = getIntent().getExtras().getDouble(InjectActivity.PARAM_DOUBLE);
		doubleFields = getIntent().getExtras().getDoubleArray(InjectActivity.PARAM_DOUBLE_ARRAY);
		booleanField = getIntent().getExtras().getBoolean(InjectActivity.PARAM_BOOLEAN);
		booleanFields = getIntent().getExtras().getBooleanArray(InjectActivity.PARAM_BOOLEAN_ARRAY);
		stringField = getIntent().getExtras().getString(InjectActivity.PARAM_STRING);
		stringFields = getIntent().getExtras().getStringArray(InjectActivity.PARAM_STRING_ARRAY);
		stringFieldList = getIntent().getExtras().getStringArrayList(InjectActivity.PARAM_STRING_ARRAY_LIST);
		charSequenceField = getIntent().getExtras().getCharSequence(InjectActivity.PARAM_CHAR_SEQUENCE);
		charSequenceFields = getIntent().getExtras().getCharSequenceArray(InjectActivity.PARAM_CHAR_SEQUENCE_ARRAY);
		
		textView2.setText(
			"byteField="+byteField+"; " +
			"shortField="+shortField+"; " +
			"intField="+intField+"; " +
			"longField="+longField+"; " +
			"charField="+charField+"; " +
			"floatField="+floatField+"; " +
			"doubleField="+doubleField+"; " +
			"booleanField="+booleanField+"; " +
			"stringField="+stringField+"; " +
			"charSequenceField="+charSequenceField+
			"byteFields="+Arrays.toString(byteFields)+"; " +
			"shortFields="+Arrays.toString(shortFields)+"; " +
			"intFields="+Arrays.toString(intFields)+"; " +
			"longFields="+Arrays.toString(longFields)+"; " +
			"charFields="+Arrays.toString(charFields)+"; " +
			"floatFields="+Arrays.toString(floatFields)+"; " +
			"doubleFields="+Arrays.toString(doubleFields)+"; " +
			"booleanFields="+Arrays.toString(booleanFields)+"; " +
			"stringFields="+Arrays.toString(stringFields)+"; " +
			"stringFieldList="+stringFieldList.toString()+"; " +
			"charSequenceFields="+Arrays.toString(charSequenceFields)
		);
	}

	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.count().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
