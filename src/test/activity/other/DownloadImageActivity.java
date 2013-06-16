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

import me.xiaopan.easyandroid.R;
import me.xiaopan.easynetwork.android.http.DownloadImageAsyncTask;
import me.xiaopan.easynetwork.android.http.DownloadImageAsyncTask.DownloadImageListener;
import test.MyBaseActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 下载图片界面
 */
public class DownloadImageActivity extends MyBaseActivity {

	private ImageView imageView;
	private ProgressBar progressBar;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_download_image);
		imageView = (ImageView) findViewById(R.id.image_downloadImage);
		progressBar = (ProgressBar) findViewById(R.id.progressBar_downloadImage);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		new DownloadImageAsyncTask(this, "http://e.hiphotos.baidu.com/album/w%3D2048/sign=4a605579d1160924dc25a51be03f34fa/1f178a82b9014a900a9e7492a8773912b31bee79.jpg", new DownloadImageListener() {
			@Override
			public void onStart() {
				progressBar.setVisibility(View.VISIBLE);
			}
			
			@Override
			public void onSuccess(byte[] bytes) {
				imageView.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
			}
			
			@Override
			public void onProgressUpdate(long totalLength, int finishLength) {
				progressBar.setMax((int)totalLength);
				progressBar.setProgress(finishLength);
			}
			
			@Override
			public void onException(Throwable exception, Context context) {
				toastL("下载失败");
				becauseExceptionFinishActivity();
			}
			
			@Override
			public void onEnd() {
				progressBar.setVisibility(View.GONE);
			}
		}).execute(0);
	}
}
