package test.widget;

import me.xiaopan.easyandroid.widget.PullListView;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListAdapter;

/**
 * 下拉刷新和上拉加载更多列表列表
 * @author xiaopan
 *
 */
public class PullDownAndUpListView extends PullListView {

	public PullDownAndUpListView(Context context) {
		super(context);
	}

	public PullDownAndUpListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		openPullDownRefreshMode(new MyPullDownRefreshListHeader(getContext()), null);//打开下拉刷新模式
		openPullUpLoadMoreMode(new MyPullUpLoadMoreListFooter(getContext()), null);//打开上拉加载更多模式
		super.setAdapter(adapter);
	}
}