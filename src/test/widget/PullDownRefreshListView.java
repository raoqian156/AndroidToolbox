package test.widget;

import me.xiaopan.androidlibrary.widget.PullListView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 下拉刷新列表
 * @author xiaopan
 *
 */
public class PullDownRefreshListView extends PullListView {

	public PullDownRefreshListView(Context context) {
		super(context);
	}

	public PullDownRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public PullDownRefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openPullDownRefreshMode(new MyPullDownRefreshListHeader(getContext()), null);//打开下拉刷新模式
		openPullUpLoadMoreMode(new MyPullUpLoadMoreListFooter(getContext()), null);//打开上拉加载更多模式
		super.setAdapter(adapter);
	}
}