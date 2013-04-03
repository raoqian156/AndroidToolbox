package test.widget;

import me.xiaopan.androidlibrary.widget.PullListView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 点击加载更多列表
 * @author xiaopan
 *
 */
public class ClickLoadMoreListView extends PullListView {

	public ClickLoadMoreListView(Context context) {
		super(context);
	}

	public ClickLoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openClickLoadMoreMode(new MyClickLoadMoreListFooter(getContext()), null);
		super.setAdapter(adapter);
	}
}