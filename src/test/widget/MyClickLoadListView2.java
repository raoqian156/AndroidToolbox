package test.widget;

import me.xiaopan.easyandroid.widget.ClickLoadListView;
import me.xiaopan.easyandroid.widget.MyClickLoadListFooter2;
import me.xiaopan.easyandroid.widget.MyClickLoadListFooter2.ClickLoadListener;
import android.content.Context;
import android.util.AttributeSet;

public class MyClickLoadListView2 extends ClickLoadListView {
	private MyClickLoadListFooter2 clickLoadListView;
		
	public MyClickLoadListView2(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickLoadListFooter(clickLoadListView = new MyClickLoadListFooter2(getContext()));
	}

	public MyClickLoadListView2(Context context) {
		super(context);
		setClickLoadListFooter(clickLoadListView = new MyClickLoadListFooter2(getContext()));
	}

	public ClickLoadListener getOnLoadListener() {
		return clickLoadListView !=null?clickLoadListView.getOnLoadListener():null;
	}

	public void setOnLoadListener(ClickLoadListener onLoadListener) {
		if(clickLoadListView != null){
			clickLoadListView.setOnLoadListener(onLoadListener);
		}
	}
}