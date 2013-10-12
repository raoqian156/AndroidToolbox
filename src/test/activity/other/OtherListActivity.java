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
package test.activity.other;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easy.android.R;
import test.MyBaseActivity;
import test.adapter.TextAdapter;
import test.adapter.TextAdapter.Text;
import test.beans.ActivityEntry;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * 其它示例
 */
public class OtherListActivity extends MyBaseActivity{
	private static final int REQUEST_CODE_BARCODE_SCANN = 11231;
	private List<Text> texts;
	private ListView listView;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_simple_list_rebound);
		listView = (ListView) findViewById(android.R.id.list);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				ActivityEntry activityEntry = (ActivityEntry)texts.get(arg2 - listView.getHeaderViewsCount());
				if(activityEntry.getRequestCode() != -1){
					startActivityForResult(activityEntry.getAction(), activityEntry.getRequestCode(), activityEntry.getBundle());
				}else{
					startActivity(activityEntry.getAction(), activityEntry.getBundle());
				}
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		texts = new ArrayList<Text>();
		texts.add(new ActivityEntry(getString(R.string.activityTitle_accessNetwork), AccessNetworkActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_downloadImage), DownloadImageActivity.class));
		
//		ActivityEntry scann = new ActivityEntry(getString(R.string.activityTitle_barcodeScanner), BarcodeScannerActivity.class); 
//		scann.setRequestCode(REQUEST_CODE_BARCODE_SCANN);
//		texts.add(scann);
		
		texts.add(new ActivityEntry(getString(R.string.activityTitle_takeBusinessCard), TakeBusinessCardActivity.class));
		texts.add(new ActivityEntry(getString(R.string.activityTitle_imageLoader), ImageLoaderActivity.class));
		
		listView.setAdapter(new TextAdapter(getBaseContext(), texts));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK && requestCode == REQUEST_CODE_BARCODE_SCANN){
//			toastL(data.getStringExtra(BarcodeScannerActivity.RETURN_BARCODE_CONTENT));
		}
	}
}