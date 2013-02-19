package test.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.AbsClickLoadMoreListFooter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyClickLoadMoreListFooter extends AbsClickLoadMoreListFooter {

	public MyClickLoadMoreListFooter(Context context) {
		super(context);
	}

	@Override
	protected LinearLayout onGetContentView() {
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.comm_list_footer_click_loadmore, null);
	}

	@Override
	protected void onIntoLoadingState(LinearLayout contentView) {
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.clickLoadMore_progress);
		progressBar.setVisibility(View.VISIBLE);
		
		TextView textView = (TextView) contentView.findViewById(R.id.clickLoadMore_text);
		textView.setText(getResources().getString(R.string.base_loadingLater));
	}

	@Override
	protected void onIntoNormalState(LinearLayout contentView) {
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.clickLoadMore_progress);
		progressBar.setVisibility(View.GONE);
		
		TextView textView = (TextView) contentView.findViewById(R.id.clickLoadMore_text);
		textView.setText("点击加载更多");
	}
}