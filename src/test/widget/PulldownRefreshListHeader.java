package test.widget;

import me.xiaopan.easy.android.widget.BasePulldownRefershListHeader;
import me.xiaopan.easyandroid.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 下拉刷新列表头
 */
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
		imageView = (ImageView) contentView.findViewById(R.id.image_refreshHeader_arrow);
		refreshHintText = (TextView) contentView.findViewById(R.id.text_refreshHeader_refreshHint);
		progressBar = (ProgressBar) contentView.findViewById(R.id.progressBar_refreshHeader);
		onToggleToNormalState();
		return contentView;
	}
	
	@Override
	public void onToggleToNormalState() {
		imageView.setVisibility(View.VISIBLE);
		imageView.setImageResource(R.drawable.icon_pull_down);
		progressBar.setVisibility(View.INVISIBLE);
		refreshHintText.setText("下拉刷新");
		invalidate();
	}
	
	@Override
	public void onToggleToRefreshingState() {
		progressBar.setVisibility(View.VISIBLE);
		imageView.setVisibility(View.INVISIBLE);
		imageView.setImageDrawable(null);
		refreshHintText.setText("正在刷新，请稍后...");
		invalidate();
	}

	@Override
	public void onNormalToReadyRefreshState() {
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		refreshHintText.setText("松开刷新");
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
	public void onReadyRefreshToRefresingState() {
		onToggleToRefreshingState();
	}
	
	@Override
	public void onNormalToRefreshingState() {
		onToggleToRefreshingState();
	}

	@Override
	public void onRefreshingToNormalState() {
		onToggleToNormalState();
	}
}