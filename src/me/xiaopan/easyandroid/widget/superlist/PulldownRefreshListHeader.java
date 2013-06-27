package me.xiaopan.easyandroid.widget.superlist;

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

public class PulldownRefreshListHeader extends BasePulldownRefershListHeader {

	public PulldownRefreshListHeader(Context context) {
		super(context);
	}

	@Override
	public LinearLayout onGetContentView() {
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_header_pull_down_refresh, null);
	}

	@Override
	public void onNormalToReadyState() {
		//旋转箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.refreshHeader_image);
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		//改变主提示文字为“现在松开即可刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.refreshHeader_text_refreshHint);
		refreshHintText.setText("现在松开即可刷新");
	}

	@Override
	public void onReadyToNormalState() {
		//恢复箭头方向
		ImageView imageView = (ImageView) contentView.findViewById(R.id.refreshHeader_image);
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		//改变主提示文字为“下拉刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.refreshHeader_text_refreshHint);
		refreshHintText.setText("下拉刷新");
	}
	
	@Override
	public void onReadyToRefreshState() {
		//隐藏箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.refreshHeader_image);
		imageView.clearAnimation();
		imageView.setImageDrawable(null);

		//显示进度条
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.refreshHeader_progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		//改变主提示文字为“正在刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.refreshHeader_text_refreshHint);
		refreshHintText.setText("正在刷新...");
	}

	@Override
	public void onRefreshToNormalState() {
		//显示箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.refreshHeader_image);
		imageView.clearAnimation();
		imageView.setImageResource(R.drawable.icon_pull_down);
		imageView.setVisibility(View.VISIBLE);

		//隐藏进度条
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.refreshHeader_progressBar);
		progressBar.setVisibility(View.GONE);
		
		//改变主提示文字为“下拉刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.refreshHeader_text_refreshHint);
		refreshHintText.setText("下拉刷新");
	}
}