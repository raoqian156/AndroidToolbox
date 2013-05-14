package me.xiaopan.easyandroid.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URL;

import me.xiaopan.easyjava.net.HttpClient;
import me.xiaopan.easyjava.net.HttpListener;
import me.xiaopan.easyjava.net.HttpRequest;
import me.xiaopan.easyjava.net.HttpResponse;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

/**
 * 下载图片异步任务
 */
public class DownloadImageAsyncTask extends AsyncTask<Integer, Integer, Integer> {
	private static final int RESULT_SUCCESS = 101;//结果标记 - 成功了
	private static final int RESULT_EXCEPTION = 102;//结果标记 - 异常了
	private Context context;//上下文
	private Activity activity;
	private int resultFlag = RESULT_EXCEPTION;//结果标记，默认异常
	private Exception exception;//访问网络过程中发生的异常
	private String imageUrl;
	private DownloadImageListener downloadImageListener;
	private long totalLength;
	private byte[] resultObject;
	
	public DownloadImageAsyncTask(Context context, String imageUrl, DownloadImageListener downloadImageListener){
		this.context = context;
		if(this.context instanceof Activity){
			activity = (Activity) this.context;
		}
		this.imageUrl = imageUrl;
		this.downloadImageListener = downloadImageListener;
	}
	
	@Override
	protected void onPreExecute() {
		if((activity == null || !activity.isFinishing()) && downloadImageListener != null){
			downloadImageListener.onStart();
		}
	}

	@Override
	protected Integer doInBackground(Integer... params) {
		if(downloadImageListener != null){
			try {
				HttpRequest httpRequest = new HttpRequest(new URL(imageUrl));
				HttpClient.sendRequest(httpRequest, new HttpListener() {
					@Override
					public void onStart() {}
					
					@Override
					public void onHandleResponse(HttpResponse httpResponse) throws Exception {
						totalLength = httpResponse.getContentLength().getLength();
						BufferedInputStream bufferedInputStream = null;
						ByteArrayOutputStream byteArrayOutputStream = null;
						try{
							bufferedInputStream = new BufferedInputStream(httpResponse.getInputStream());
							byteArrayOutputStream = new ByteArrayOutputStream();
							byte[] data = new byte[1024];
							int readLength;
							while((readLength = bufferedInputStream.read(data)) != -1){
								byteArrayOutputStream.write(data, 0, readLength);
								onProgressUpdate(byteArrayOutputStream.size());
							}
							resultObject = byteArrayOutputStream.toByteArray();
							resultFlag = RESULT_SUCCESS;
						}catch (Exception e) {
							throw e;
						}finally{
							if(bufferedInputStream != null){
								bufferedInputStream.close();
							}
							if(byteArrayOutputStream != null){
								byteArrayOutputStream.flush();
								byteArrayOutputStream.close();
							}
						}
					}
					
					@Override
					public void onException(Exception e) {
						resultFlag = RESULT_EXCEPTION;
						exception = e;
					}
					
					@Override
					public void onEnd() {}
				});
			} catch (Exception e) {
				e.printStackTrace();
				resultFlag = RESULT_EXCEPTION;
				exception = e;
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		if(downloadImageListener != null){
			downloadImageListener.onProgressUpdate(totalLength, values[0]);
		}
	}

	@Override
	protected void onPostExecute(Integer result) {
		if((activity == null || !activity.isFinishing()) && downloadImageListener != null){
			switch(resultFlag){
				case RESULT_SUCCESS : downloadImageListener.onSuccess(resultObject); break;
				case RESULT_EXCEPTION : downloadImageListener.onException(exception, context); break;
			}
			downloadImageListener.onEnd();
		}
		context = null;
		activity = null;
		exception = null;
		imageUrl = null;
		downloadImageListener = null;
		resultObject = null;
	}
	
	public interface DownloadImageListener{
		public void onStart();
		public void onProgressUpdate(long totalLength, int finishLength);
		public void onSuccess(byte[] bytes);
		public void onException(Exception exception, Context context);
		public void onEnd();
	}
}