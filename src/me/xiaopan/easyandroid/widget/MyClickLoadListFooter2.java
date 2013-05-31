package me.xiaopan.easyandroid.widget;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.ProgressBar;

public class MyClickLoadListFooter2 extends AbstractClickLoadListFooter{
	private ProgressBar progressBar;
	private ClickLoadListener onLoadListener;

	public MyClickLoadListFooter2(Context context) {
		super(context);
		init();
	}
	
	private void init(){
		setPadding(20, 20, 20, 20);
		setGravity(Gravity.CENTER);
		
		progressBar = new ProgressBar(getContext());
		progressBar.setVisibility(View.GONE);
		
		intoNormalState();
		
		addView(progressBar);
	}
	
	public void intoNormalState(){
		progressBar.setVisibility(View.GONE);
	}
	
	@Override
	public void startLoad(){
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