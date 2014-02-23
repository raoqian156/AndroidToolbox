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

import me.xiaopan.android.easy.activity.EasyFragmentActivity;
import me.xiaopan.android.easy.inject.InjectView;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends EasyFragmentActivity{
	@InjectView(android.R.id.list)
	private ListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView listView = new ListView(getBaseContext());
		listView.setId(android.R.id.list);
		setContentView(listView);
		
		String[] items = new String[]{"注入功能测试", "非注入功能测试", "FragmentDialog测试"};
		listView.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, android.R.id.text1, items));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putBoolean(InjectActivity.PARAM_BOOLEAN, true);
				bundle.putByte(InjectActivity.PARAM_BYTE, (byte) 110);
				bundle.putChar(InjectActivity.PARAM_CHAR, 'R');
				bundle.putCharSequence(InjectActivity.PARAM_CHAR_SEQUENCE, "CharSequence");
				bundle.putDouble(InjectActivity.PARAM_DOUBLE, 12.09d);
				bundle.putFloat(InjectActivity.PARAM_FLOAT, 12.0943f);
				bundle.putInt(InjectActivity.PARAM_INT, 123);
				bundle.putLong(InjectActivity.PARAM_LONG, 123456789);
				bundle.putShort(InjectActivity.PARAM_SHORT, (short) 2);
				bundle.putString(InjectActivity.PARAM_STRING, "String");
				switch(position){
					case 0 : 
						Second.SECOND_CHRONOGRAPH.count(); 
						startActivity(InjectActivity.class, bundle); 
						break;
					case 1 : 
						Second.SECOND_CHRONOGRAPH.count(); 
						startActivity(NormalActivity.class, bundle); 
						break;
					case 2 : 
						Second.SECOND_CHRONOGRAPH.count(); 
						new TestDialogFragment().show(getSupportFragmentManager(), ""); 
						break;
				}
			}
		});
	}
}
