package test.widget;

import java.util.Date;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.AbsPullUpLoadMoreListFooter;
import me.xiaopan.easyjava.util.DateTimeUtils;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 上拉加载更多
 * @author xiaopan
 */
public class MyPullUpLoadMoreListFooter extends AbsPullUpLoadMoreListFooter {
	
	public MyPullUpLoadMoreListFooter(Context context) {
		super(context);
	}

	public MyPullUpLoadMoreListFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected LinearLayout onGetContentView(){
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_footer_pull_up_load_more, null);
	}
	
	@Override
	protected void onNormalToReadyLoadState(LinearLayout contentView){
		//旋转箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.loadmoreFooter_image);
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(0f, 180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		//改变主提示文字为“现在松开即可刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.loadmoreFooter_text_refreshHint);
		refreshHintText.setText("现在松开即可加载");
	}
	
	@Override
	protected void onReadyLoadToNormalState(LinearLayout contentView){
		//恢复箭头方向
		ImageView imageView = (ImageView) contentView.findViewById(R.id.loadmoreFooter_image);
		imageView.clearAnimation();
		RotateAnimation rotateAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		rotateAnimation.setFillAfter(true);
		rotateAnimation.setDuration(400);
		imageView.startAnimation(rotateAnimation);
		
		//改变主提示文字为“下拉刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.loadmoreFooter_text_refreshHint);
		refreshHintText.setText("上拉加载更多");
	}
	
	@Override
	protected void onReadyLoadToLoadingState(LinearLayout contentView){
		//隐藏箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.loadmoreFooter_image);
		imageView.clearAnimation();
		imageView.setImageDrawable(null);

		//显示进度条
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.loadmoreFooter_progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		//改变主提示文字为“正在刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.loadmoreFooter_text_refreshHint);
		refreshHintText.setText("正在加载...");
	}
	
	@Override
	protected void onLoadingToNormalState(LinearLayout contentView){
		//显示箭头
		ImageView imageView = (ImageView) contentView.findViewById(R.id.loadmoreFooter_image);
		imageView.clearAnimation();
		imageView.setImageResource(R.drawable.ic_pull_up);
		imageView.setVisibility(View.VISIBLE);

		//隐藏进度条
		ProgressBar progressBar = (ProgressBar) contentView.findViewById(R.id.loadmoreFooter_progressBar);
		progressBar.setVisibility(View.GONE);
		
		//改变主提示文字为“下拉刷新”
		TextView refreshHintText = (TextView) contentView.findViewById(R.id.loadmoreFooter_text_refreshHint);
		refreshHintText.setText("上拉加载更多");
	}
	
	@Override
	protected void onUpdateLastLoadTime(LinearLayout contentView, long lastLoadTime){
		((TextView) contentView.findViewById(R.id.loadmoreFooter_text_timeHint)).setText("上次加载："+DateTimeUtils.getDateTimeFormatByCustomFormat("MM-dd HH:mm").format(new Date(lastLoadTime)));
	}

	@Override
	protected int onGetOriginalHeight(LinearLayout contentView) {
		return (int) getResources().getDimension(R.dimen.pull_height);
	}
}