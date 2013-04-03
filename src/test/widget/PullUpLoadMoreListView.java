package test.widget;

import me.xiaopan.androidlibrary.widget.PullListView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 上拉加载更多列表
 * @author xiaopan
 *
 */
public class PullUpLoadMoreListView extends PullListView {

	public PullUpLoadMoreListView(Context context) {
		super(context);
	}

	public PullUpLoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openPullUpLoadMoreMode(new MyPullUpLoadMoreListFooter(getContext()), null);//打开上拉加载更多模式
		super.setAdapter(adapter);
	}
}