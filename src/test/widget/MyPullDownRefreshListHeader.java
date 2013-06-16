/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.widget;

import java.util.Date;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.AbsPullDownRefreshListHeader;
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
 * 下拉刷新
 * @author xiaopan
 */
public class MyPullDownRefreshListHeader extends AbsPullDownRefreshListHeader {
	
	public MyPullDownRefreshListHeader(Context context) {
		super(context);
	}

	public MyPullDownRefreshListHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected LinearLayout onGetContentView(){
		return (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.list_header_pull_down_refresh, null);
	}
	
	@Override
	protected void onNormalToReadyLoadState(LinearLayout contentView){
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
	protected void onReadyLoadToNormalState(LinearLayout contentView){
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
	protected void onReadyLoadToLoadingState(LinearLayout contentView){
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
	protected void onLoadingToNormalState(LinearLayout contentView){
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
	
	@Override
	protected void onUpdateLastLoadTime(LinearLayout contentView, long lastLoadTime){
		((TextView) contentView.findViewById(R.id.refreshHeader_text_timeHint)).setText("上次刷新："+DateTimeUtils.getDateTimeFormatByCustomFormat("MM-dd HH:mm").format(new Date(lastLoadTime)));
	}

	@Override
	protected int onGetOriginalHeight(LinearLayout contentView) {
		return (int) getResources().getDimension(R.dimen.pull_height);
	}
}
