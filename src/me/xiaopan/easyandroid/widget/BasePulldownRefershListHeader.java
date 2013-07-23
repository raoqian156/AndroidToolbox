package me.xiaopan.easyandroid.widget;

import me.xiaopan.easyandroid.util.ViewUtils;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

public abstract class BasePulldownRefershListHeader extends LinearLayout{
	public static final float OFFSET_RADIO = 0.4f;	//拉伸偏移，模拟出拉橡皮筋的感觉来
	private int contentViewHeight;	//内容高度
	private int contentViewMinHeight;	//内容最小高度
	private State state;	//状态
	private View contentView;	//内容视图
	private OnAllowReadyRefreshListener onAllowReadyRefreshListener;
	
	public BasePulldownRefershListHeader(Context context) {
		super(context);
		setClickable(true);
		setOrientation(LinearLayout.HORIZONTAL);	//设置流向
		setGravity(Gravity.BOTTOM);	//让内容居于底部
		state = State.NORMAL;	//初始化状态
		setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT));
		
		/* 初始化内容视图 */
		contentView = onGetContentView();
		contentViewHeight = ViewUtils.getMeasureHeight(contentView);	//测量内容的高度
		contentView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, contentViewMinHeight));	//设置内容视图的高度为最小高度
		addView(contentView);	//将内容视图添加到下拉刷新布局上
	}
	
	/**
	 * 更新高度
	 * @param newHeight
	 */
	public void updateHeight(int newHeight){
		/* 如果新的高度小于回滚的最终高度就将其设置为回滚的最终高度，然后更新高度 */
		if(newHeight < getRollbackFinalHeight()){
			newHeight = getRollbackFinalHeight();
		}
		ViewUtils.setViewHeight(contentView, newHeight);
		
		/* 根据当前不同的状态以及新的高度，来做不同的处理 */
		if(state == State.NORMAL){																			//如果当前状态是正常
			if(newHeight >= contentViewHeight && (onAllowReadyRefreshListener == null || onAllowReadyRefreshListener.isAllowReadyRefresh())){													//如果新的高度大于原始高度，说明是要从正常状态进入准备刷新状态了
				state = State.READY_REFRESH;																//设置状态为准备刷新
				onNormalToReadyRefreshState();															//调用相应回调方法修改界面显示
			}
		}else if(state == State.READY_REFRESH){														//如果当前状态是准备刷新
			if(newHeight < contentViewHeight){														//如果新的高度小于原始高度，说明是要从准备刷新状态变为正常状态了
				state = State.NORMAL;																			//设置状态为正常
				onReadyRefreshToNormalState();															//调用相应回调方法修改界面显示
			}
		}else if(state == State.REFRESHING_TO_NORMAL){									//正在由刷新中变为正常
			if(newHeight <= contentViewMinHeight){												//如果已经回滚到最小高度了
				state = State.NORMAL;																			//设置状态为正常
				onRefreshingToNormalState();																//调用相应回调方法修改界面显示
			}
		}else if(state == State.NORMAL_TO_REFRESHING){									//如果正处于由正常状态直接变为刷新中状态
			if(newHeight >= contentViewHeight){													//如果新的高度大于原始高度，说明是要从正常状态进入准备刷新状态了
				setState(State.REFRESHING);
				onNormalToRefreshingState();
			}
		}
	}
	
	/**
	 * 采用增量的方式更新高度
	 * @param newOffSetHeight
	 */
	public void updateHeightOffset(int newOffSetHeight){
		updateHeight((int) (contentView.getHeight() - (newOffSetHeight * OFFSET_RADIO)));
	}
	
	/**
	 * 由准备刷新变为刷新中状态
	 */
	public void readyRefreshToRefreshingState(){
		setState(State.REFRESHING);
		onReadyRefreshToRefresingState();
	}
	
	/**
	 * 切换到刷新中转变为正常状态
	 */
	public void toggleToRefreshingToNormalState(){
		setState(State.REFRESHING_TO_NORMAL);
	}
	
	/**
	 * 获取回滚高度
	 * @return 回滚高度，当是刷新中状态时回滚高度是原始高度，否则一律是最小高度
	 */
	public int getRollbackFinalHeight(){
		return state == State.REFRESHING?contentViewHeight:contentViewMinHeight;	//如果正在刷新就回滚到原始高度，否则回滚到最小高度
	}
	
	/**
	 * 获取内容视图
	 * @return 内容视图
	 */
	public abstract View onGetContentView();
	
	/**
	 * 由正常状态变为准备刷新状态
	 */
	public abstract void onNormalToReadyRefreshState();
	
	/**
	 * 由准备刷新状态变为正常状态
	 */
	public abstract void onReadyRefreshToNormalState();
	
	/**
	 * 由准备刷新状态变为刷新中状态
	 */
	public abstract void onReadyRefreshToRefresingState();
	
	/**
	 * 由刷新中状态变为正常状态
	 */
	public abstract void onRefreshingToNormalState();
	
	/**
	 * 由正常状态变为刷新中状态
	 */
	public abstract void onNormalToRefreshingState();
	
	/**
	 * 切换到正常状态
	 */
	public abstract void onToggleToNormalState();

	/**
	 * 切换到刷新中状态
	 */
	public abstract void onToggleToRefreshingState();

	/**
	 * 获取状态
	 * @return 状态
	 */
	public State getState() {
		return state;
	}

	/**
	 * 设置状态状态
	 * @param state 状态
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * 获取内容视图的高度
	 * @return 内容视图的高度
	 */
	public int getContentViewHeight() {
		return contentViewHeight;
	}

	/**
	 * 设置内容视图的高度
	 * @param contentHeight 内容视图的高度
	 */
	public void setContentViewHeight(int contentHeight) {
		this.contentViewHeight = contentHeight;
	}
	
	/**
	 * 获取内容视图的最小高度
	 * @return the contentMinHeight 内容视图的最小高度
	 */
	public int getContentViewMinHeight() {
		return contentViewMinHeight;
	}

	/**
	 * 设置内容视图的最小高度
	 * @param contentViewMinHeight 内容视图的最小高度
	 */
	public void setContentViewMinHeight(int contentViewMinHeight) {
		this.contentViewMinHeight = contentViewMinHeight;
	}

	/**
	 * 获取内容视图
	 * @return the contentView 内容视图
	 */
	public View getContentView() {
		return contentView;
	}

	/**
	 * 设置内容视图
	 * @param contentView 内容视图
	 */
	public void setContentView(View contentView) {
		this.contentView = contentView;
	}

	/**
	 * @return the onAllowReadyRefreshListener
	 */
	public OnAllowReadyRefreshListener getOnAllowReadyRefreshListener() {
		return onAllowReadyRefreshListener;
	}

	/**
	 * @param onAllowReadyRefreshListener the onAllowReadyRefreshListener to set
	 */
	public void setOnAllowReadyRefreshListener(
			OnAllowReadyRefreshListener onAllowReadyRefreshListener) {
		this.onAllowReadyRefreshListener = onAllowReadyRefreshListener;
	}

	/**
	 * 状态
	 */
	public enum State{
		/**
		 * 正常
		 */
		NORMAL,
		/**
		 * 准备刷新
		 */
		READY_REFRESH,
		/**
		 * 刷新中
		 */
		REFRESHING,
		/**
		 * 刷新中到正常
		 */
		REFRESHING_TO_NORMAL,
		/**
		 * 正常到刷新中
		 */
		NORMAL_TO_REFRESHING;
	}
	
	public interface OnAllowReadyRefreshListener{
		public boolean isAllowReadyRefresh();
	}
}