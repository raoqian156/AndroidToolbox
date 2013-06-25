package me.xiaopan.easyandroid.app;

import me.xiaopan.easyandroid.util.ViewAnimationUtils;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

/**
 * 处理器
 */
class MessageHandler extends Handler{
	public static final int SHOW_LOADING_HINT_VIEW = 40;
	public static final int CLOSE_LOADING_HINT_VIEW = 41;
	public static final int SHOW_MESSAGE_DIALOG = 45;
	public static final int CLOSE_MESSAGE_DIALOG = 46;
	public static final int SHOW_PROGRESS_DIALOG = 47;
	public static final int CLOSE_PROGRESS_DIALOG = 48;
	public static final int TOAST = 300;
	public static final int BECAUSE_EXCEPTION_FINISH_ACTIVITY = 2001;
	public static final int FINISH_ACTIVITY = 2002;
	public static final int FINISH_ACTIVITY_ANIMATION = 2003;
	public static final int START_ACTIVITY = 2004;
	public static final int START_ACTIVITY_FOR_RESULT = 2005;
	
	public static final String FLAG = "FLAG";
	public static final String IS_CLOSE = "IS_CLOSE";
	public static final String IN_ANIMATION = "IN_ANIMATION";
	public static final String OUT_ANIMATION = "OUT_ANIMATION";
	public static final String HAVE_BUNDLE = "HAVE_BUNDLE";
	public static final String REQUEST_CODE = "REQUEST_CODE";
	
	private BaseActivityInterface baseActivityInterface;
	private Activity activity;
	
	public MessageHandler(BaseActivityInterface baseActivityInterface){
		this.baseActivityInterface = baseActivityInterface;
		activity = (Activity) baseActivityInterface;
	}
	
	@Override
	public void handleMessage(Message msg) {
		if(!activity.isFinishing()){
			switch(msg.what){
				case SHOW_MESSAGE_DIALOG : activity.showDialog(BaseActivityInterface.DIALOG_MESSAGE, msg.getData()); break;
				case CLOSE_MESSAGE_DIALOG : activity.dismissDialog(BaseActivityInterface.DIALOG_MESSAGE); break;
				case SHOW_PROGRESS_DIALOG : activity.showDialog(BaseActivityInterface.DIALOG_PROGRESS, msg.getData()); break;
				case CLOSE_PROGRESS_DIALOG : activity.dismissDialog(BaseActivityInterface.DIALOG_PROGRESS); break;
				case SHOW_LOADING_HINT_VIEW : showLoadingHintView(msg); break;
				case CLOSE_LOADING_HINT_VIEW : closeLoadingHintView(msg); break;
				case TOAST : Toast.makeText(activity, (String)msg.obj, msg.arg1).show(); break;
				case BECAUSE_EXCEPTION_FINISH_ACTIVITY : baseActivityInterface.onBecauseExceptionFinishActivity(); break;
				case FINISH_ACTIVITY : baseActivityInterface.onFinishActivity(); break;
				case FINISH_ACTIVITY_ANIMATION : baseActivityInterface.onFinishActivity(msg.arg1, msg.arg2); break;
				case START_ACTIVITY : 
					baseActivityInterface.onStartActivity(
							(Class<?>)msg.obj, 
							msg.getData().getInt(MessageHandler.FLAG), 
							msg.getData().getBoolean(MessageHandler.HAVE_BUNDLE)?msg.getData():null, 
									msg.getData().getBoolean(MessageHandler.IS_CLOSE), 
									msg.getData().getInt(MessageHandler.IN_ANIMATION), 
									msg.getData().getInt(MessageHandler.OUT_ANIMATION)
							); 
					break;
				case START_ACTIVITY_FOR_RESULT : 
					baseActivityInterface.onStartActivityForResult(
							(Class<?>)msg.obj, 
							msg.getData().getInt(MessageHandler.REQUEST_CODE), 
							msg.getData().getInt(MessageHandler.FLAG), 
							msg.getData().getBoolean(MessageHandler.HAVE_BUNDLE)?msg.getData():null, 
									msg.getData().getInt(MessageHandler.IN_ANIMATION), 
									msg.getData().getInt(MessageHandler.OUT_ANIMATION)
							); 
					break;
				default :  baseActivityInterface.onReceivedMessage(msg); break;
			}
		}
		super.handleMessage(msg);
	}
	
	private void showLoadingHintView(Message msg){
		if(msg.obj != null && msg.obj instanceof View){
			((View) msg.obj).setVisibility(View.VISIBLE);
		}
	}
	
	private void closeLoadingHintView(Message msg){
		if(msg.obj != null && msg.obj instanceof View){
			ViewAnimationUtils.goneViewByAlpha((View) msg.obj);
		}
	}
}