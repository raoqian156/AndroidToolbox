package test.net;

import java.io.FileNotFoundException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.app.BaseActivityInterface;
import me.xiaopan.androidlibrary.net.AccessNetworkListener;
import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.NetworkUtils;
import android.content.Context;

public abstract class MyAccessNetworkListener<T> extends AccessNetworkListener<T> {
	private boolean isExceptionFinishActivty;
	
	public MyAccessNetworkListener(){}

	/**
	 * @param isExceptionFinishActivty 当发生异常时是否终止Activity
	 */
	public MyAccessNetworkListener(boolean isExceptionFinishActivty){
		setExceptionFinishActivty(isExceptionFinishActivty);
	}
	
	/**
	 * 当发生异常
	 * @param e 异常。可能的情况有：
	 * <br>SocketTimeoutException（连接超时）；
	 * <br>UnknownHostException（本地网络不可用或者目标主机不存在）；
	 * <br>FileNotFoundException（URL地址拼写错误或者目标主机上不存在URL中指定的路径）；
	 * <br>或者其他的未知异常
	 */
	@Override
	public void onException(Exception e, Context context){
		if(e instanceof SocketTimeoutException){
			AndroidUtils.toastL(context, R.string.comm_hint_connectionTimeout);
		}else if(e instanceof UnknownHostException){
			if(NetworkUtils.isConnectedByState(context)){
				AndroidUtils.toastL(context, R.string.comm_hint_connectionError);
			}else{
				AndroidUtils.toastL(context, R.string.comm_hint_networkError);
			}
		}else if(e instanceof ConnectException){
			if(NetworkUtils.isConnectedByState(context)){
				AndroidUtils.toastL(context, R.string.comm_hint_connectionError);
			}else{
				AndroidUtils.toastL(context, R.string.comm_hint_networkError);
			}
		}else if(e instanceof FileNotFoundException){
			AndroidUtils.toastL(context, R.string.comm_hint_urlError);
		}else{
			AndroidUtils.toastL(context, R.string.comm_hint_unknownException);
		}
		if(isExceptionFinishActivty() && context instanceof BaseActivityInterface){
			((BaseActivityInterface) context).becauseExceptionFinishActivity();
		}
	}
	
	/**
	 * 当网络不可用
	 * @param context 上下文
	 * @param activity Activity
	 */
	@Override
	public void onNetworkNotAvailable(Context context){
		AndroidUtils.toastL(context, R.string.comm_hint_networkError);
		if(isExceptionFinishActivty() && context instanceof BaseActivityInterface){
			((BaseActivityInterface) context).becauseExceptionFinishActivity();
		}
	}

	public boolean isExceptionFinishActivty() {
		return isExceptionFinishActivty;
	}

	public void setExceptionFinishActivty(boolean isExceptionFinishActivty) {
		this.isExceptionFinishActivty = isExceptionFinishActivty;
	}
}