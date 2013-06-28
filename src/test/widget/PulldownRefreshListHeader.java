package test.widget;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.superlist.BasePulldownRefershListHeader;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PulldownRefreshListHeader extends BasePulldownRefershListHeader {
	private ImageView imageView;
	private TextView refreshHintText;
	private ProgressBar progressBar;
	
	public PulldownRefreshListHeader(Context context) {
		super(context);
	}

	@Override
	public View onGetContentView() {
		LinearLayout contentView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_header_pull_down_refresh, null);
		imageView = (ImageView) contentView.findViewById(R.id.refreshHeader_image);
		refreshHintText = (TextView) contentView.findViewById(R.id.refreshHeader_text_refreshHint);
		progressBar = (ProgressBar) contentView.findViewById(R.id.refreshHeader_progressBar);
		return contentView;
	}

	@Override
	public void onNormalToReadyRefreshState() {
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		refreshHintText.setText("现在松开即可刷新");
	}

	@Override
	public void onReadyRefreshToNormalState() {
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		refreshHintText.setText("下拉刷新");
	}
	
	@Override
	public void onToggleToRefreshingState() {
		imageView.clearAnimation();
		imageView.setImageDrawable(null);

		progressBar.setVisibility(View.VISIBLE);
		
		refreshHintText.setText("正在刷新，请稍后...");
	}

	@Override
	public void onRefreshingToNormalState() {
		imageView.clearAnimation();
		imageView.setImageResource(R.drawable.icon_pull_down);
		imageView.setVisibility(View.VISIBLE);

		progressBar.setVisibility(View.GONE);
		
		refreshHintText.setText("下拉刷新");
	}
}