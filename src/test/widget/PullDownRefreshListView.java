package test.widget;

import me.xiaopan.easyandroid.widget.PullListView;
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

	@Override
	public void setAdapter(ListAdapter adapter) {
		openPullDownRefreshMode(new MyPullDownRefreshListHeader(getContext()), null);//打开下拉刷新模式
		super.setAdapter(adapter);
	}
}