package test.activity.other;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.net.DownloadImageAsyncTask;
import me.xiaopan.androidlibrary.net.DownloadImageAsyncTask.DownloadImageListener;
import test.MyBaseActivity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * 下载图片界面
 * @author xiaopan
 *
 */
public class DownloadImageActivity extends MyBaseActivity {

	private ImageView imageView;
	private ProgressBar progressBar;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.download_image);
		imageView = (ImageView) findViewById(R.id.image_downloadImage);
		progressBar = (ProgressBar) findViewById(R.id.progressBar_downloadImage);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		new DownloadImageAsyncTask(this, "http://imgsrc.baidu.com/forum/wh%3D1024%2C768/sign=de1cd357d0a20cf446c5f6de463b7f1a/157d9e2f0708283898ae88c4b899a9014d08f145.jpg", new DownloadImageListener() {
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
			public void onException(Exception exception, Context context) {
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