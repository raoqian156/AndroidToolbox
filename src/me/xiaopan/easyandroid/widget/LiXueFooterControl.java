package me.xiaopan.easyandroid.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LiXueFooterControl  extends LinearLayout {
	private TextView textView;
	private ProgressBar progressBar;
	private onloadListener onListener;
	public LiXueFooterControl(Context context) {
		super(context);
		//初始化控件
		LoadControl();
	}
	public LiXueFooterControl(Context context, AttributeSet attrs) {
		super(context, attrs);
		//初始化控件
		LoadControl();
	}
	//加载控件
	public void LoadControl(){
		textView =new TextView(getContext());
		textView.setText("加载更多");
		textView.setVisibility(GONE);
		progressBar=new ProgressBar(getContext());
		progressBar.setMax(100);
		progressBar.setVisibility(GONE);
		addView(progressBar);
		addView(textView);
		setGravity(Gravity.CENTER);
	}
	//开始加载
	public void Startload(){
		textView.setVisibility(VISIBLE);
		textView.setText("开始加载");
		progressBar.setVisibility(VISIBLE);
		if(onListener!=null){
			onListener.onStartload(this);
		}
	}

	public onloadListener getOnListener() {
		return onListener;
	}
	public void setOnListener(onloadListener onListener) {
		this.onListener = onListener;
	}
	//加載完成
	public void Endload(){
		textView.setVisibility(GONE);
		progressBar.setVisibility(GONE);
	}
	
	public interface onloadListener{
		public void onStartload(LiXueFooterControl liXueFooterControl);
	}
}