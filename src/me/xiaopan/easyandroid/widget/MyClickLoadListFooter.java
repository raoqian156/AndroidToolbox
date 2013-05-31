package me.xiaopan.easyandroid.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyClickLoadListFooter extends AbstractClickLoadListFooter{
	private ProgressBar progressBar;
	private TextView textView;
	private ClickLoadListener onLoadListener;

	public MyClickLoadListFooter(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		setPadding(20, 20, 20, 20);
		setGravity(Gravity.CENTER);
		
		progressBar = new ProgressBar(getContext());
		progressBar.setVisibility(View.GONE);
		textView = new TextView(getContext());
		textView.setText("正在加载");
		
		intoNormalState();
		
		addView(progressBar);
		addView(textView);
	}
	
	public void intoNormalState(){
		textView.setVisibility(View.GONE);
		progressBar.setVisibility(View.GONE);
	}
	
	@Override
	public void startLoad(){
		textView.setVisibility(View.VISIBLE);
		progressBar.setVisibility(View.VISIBLE);
		if(onLoadListener != null){
			onLoadListener.onStartLoad(this);
		}
	}

	@Override
	public void finishLoad() {
		intoNormalState();
	}
	
	public interface ClickLoadListener{
		/**
		 * 当开始加载
		 */
		public void onStartLoad(AbstractClickLoadListFooter clickLoadListFooter);
	}

	public ClickLoadListener getOnLoadListener() {
		return onLoadListener;
	}

	public void setOnLoadListener(ClickLoadListener onLoadListener) {
		this.onLoadListener = onLoadListener;
	}
}