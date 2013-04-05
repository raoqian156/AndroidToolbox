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
public class PullDownRefreshAndClickLoadMoreListView extends PullListView {

	public PullDownRefreshAndClickLoadMoreListView(Context context) {
		super(context);
	}

	public PullDownRefreshAndClickLoadMoreListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openPullDownRefreshMode(new MyPullDownRefreshListHeader(getContext()), null);//打开下拉刷新模式
		openClickLoadMoreMode(new MyClickLoadMoreListFooter(getContext()), null);
		super.setAdapter(adapter);
	}
}