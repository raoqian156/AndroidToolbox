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
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectExtra;
import me.xiaopan.android.easy.inject.InjectView;
import android.os.Bundle;
import android.widget.TextView;

@InjectContentView(R.layout.activity_main)
public class InjectActivity extends EasyActivity {
	public static final String PARAM_BYTE = "PARAM_BYTE";
	public static final String PARAM_BYTE_ARRAY = "PARAM_BYTE_ARRAY";
	public static final String PARAM_SHORT = "PARAM_SHORT";
	public static final String PARAM_SHORT_ARRAY = "PARAM_SHORT_ARRAY";
	public static final String PARAM_INT = "PARAM_INT";
	public static final String PARAM_INT_ARRAY = "PARAM_INT_ARRAY";
	public static final String PARAM_LONG = "PARAM_LONG";
	public static final String PARAM_LONG_ARRAY = "PARAM_LONG_ARRAY";
	public static final String PARAM_CHAR = "PARAM_CHAR";
	public static final String PARAM_CHAR_ARRAY = "PARAM_CHAR_ARRAY";
	public static final String PARAM_FLOAT = "PARAM_FLOAT";
	public static final String PARAM_FLOAT_ARRAY = "PARAM_FLOAT_ARRAY";
	public static final String PARAM_DOUBLE = "PARAM_DOUBLE";
	public static final String PARAM_DOUBLE_ARRAY = "PARAM_DOUBLE_ARRAY";
	public static final String PARAM_BOOLEAN = "PARAM_BOOLEAN";
	public static final String PARAM_BOOLEAN_ARRAY = "PARAM_BOOLEAN_ARRAY";
	public static final String PARAM_STRING = "PARAM_STRING";
	public static final String PARAM_STRING_ARRAY = "PARAM_STRING_ARRAY";
	public static final String PARAM_STRING_ARRAY_LIST = "PARAM_STRING_ARRAY_LIST";
	public static final String PARAM_CHAR_SEQUENCE = "PARAM_CHAR_SEQUENCE";
	public static final String PARAM_CHAR_SEQUENCE_ARRAY = "PARAM_CHAR_SEQUENCE_ARRAY";
	
	@InjectView(R.id.text_main1) private TextView textView1;
	@InjectView(R.id.text_main2) private TextView textView2;
	@InjectView(R.id.text_main3) private TextView textView3;
	@InjectView(R.id.text_main4) private TextView textView4;
	@InjectView(R.id.text_main5) private TextView textView5;
	@InjectView(R.id.text_main6) private TextView textView6;
	@InjectView(R.id.text_main7) private TextView textView7;
	@InjectView(R.id.text_main8) private TextView textView8;
	@InjectView(R.id.text_main9) private TextView textView9;
	@InjectView(R.id.text_main10) private TextView textView10;

	@InjectExtra(PARAM_BYTE) private byte byteField;
	@InjectExtra(PARAM_BYTE_ARRAY) private byte[] byteFields;
	@InjectExtra(PARAM_SHORT) private short shortField;
	@InjectExtra(PARAM_SHORT_ARRAY) private short[] shortFields;
	@InjectExtra(PARAM_INT) private int intField;
	@InjectExtra(PARAM_INT_ARRAY) private int[] intFields;
	@InjectExtra(PARAM_LONG) private long longField;
	@InjectExtra(PARAM_LONG_ARRAY) private long[] longFields;
	@InjectExtra(PARAM_CHAR) private char charField;
	@InjectExtra(PARAM_CHAR_ARRAY) private char[] charFields;
	@InjectExtra(PARAM_FLOAT) private float floatField;
	@InjectExtra(PARAM_FLOAT_ARRAY) private float[] floatFields;
	@InjectExtra(PARAM_DOUBLE) private double doubleField;
	@InjectExtra(PARAM_DOUBLE_ARRAY) private double[] doubleFields;
	@InjectExtra(PARAM_BOOLEAN) private boolean booleanField;
	@InjectExtra(PARAM_BOOLEAN_ARRAY) private boolean[] booleanFields;
	@InjectExtra(PARAM_STRING) private String stringField;
	@InjectExtra(PARAM_STRING_ARRAY) private String[] stringFields;
	@InjectExtra(PARAM_STRING_ARRAY_LIST) private ArrayList<String> stringFieldList;
	@InjectExtra(PARAM_CHAR_SEQUENCE) private CharSequence charSequenceField;
	@InjectExtra(PARAM_CHAR_SEQUENCE_ARRAY) private CharSequence[] charSequenceFields;

//	@InjectExtra("number")
//	private Serializable nunber10;
//	
//	@InjectExtra("number")
//	private Parcelable nunber11;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
