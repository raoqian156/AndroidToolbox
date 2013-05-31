package test.widget;

import me.xiaopan.easyandroid.widget.ClickLoadListView;
import me.xiaopan.easyandroid.widget.MyClickLoadListFooter;
import me.xiaopan.easyandroid.widget.MyClickLoadListFooter.ClickLoadListener;
import android.content.Context;
import android.util.AttributeSet;

public class MyClickLoadListView extends ClickLoadListView {
	private MyClickLoadListFooter clickLoadListView;
		
	public MyClickLoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setClickLoadListFooter(clickLoadListView = new MyClickLoadListFooter(getContext()));
	}

	public MyClickLoadListView(Context context) {
		super(context);
		setClickLoadListFooter(clickLoadListView = new MyClickLoadListFooter(getContext()));
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