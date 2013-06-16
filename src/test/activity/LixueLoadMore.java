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
package test.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.widget.LiXueAutoLoadListView;
import me.xiaopan.easyandroid.widget.LiXueFooterControl;
import me.xiaopan.easyandroid.widget.LiXueFooterControl.onloadListener;
import test.MyBaseListActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;

public class LixueLoadMore extends MyBaseListActivity{

	private LiXueAutoLoadListView liXueAutoLoadListView;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(me.xiaopan.easyandroid.R.layout.acttivity_lixueloadmore);
		liXueAutoLoadListView=(LiXueAutoLoadListView)getListView();
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		LiXueFooterControl liXueFooterControl=new LiXueFooterControl(getBaseContext());
		liXueAutoLoadListView.SetFooterControl(liXueFooterControl);
		liXueFooterControl.setOnListener(new onloadListener() {
			
			@Override
			public void onStartload(LiXueFooterControl liXueFooterControl) {
				// TODO Auto-generated method stub
				liXueFooterControl.Endload();
			}
		});
		
		
		
		List<String> contents=new ArrayList<String>();
		for(int w = 0; w < 20; w++){
			contents.add("这是第"+(w+1)+"条数据");
		}
		getListView().setAdapter(new SimpleAdapter(getBaseContext(), contents));
	}

}
