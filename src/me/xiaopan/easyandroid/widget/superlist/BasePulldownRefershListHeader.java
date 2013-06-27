package me.xiaopan.easyandroid.widget.superlist;

import me.xiaopan.easyandroid.util.ViewUtils;
import me.xiaopan.easyandroid.widget.AbsPullListHeaderAndFoooter.State;
import android.content.Context;
import android.view.Gravity;
import android.widget.LinearLayout;

public abstract class BasePulldownRefershListHeader extends LinearLayout{
	public static final float OFFSET_RADIO = 0.4f;//拉伸偏移，模拟出拉橡皮筋的感觉来
	private State state;
	private int contentHeight;
	private int contentMinHeight;
	protected LinearLayout contentView;
	
	public BasePulldownRefershListHeader(Context context) {
		super(context);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		setOrientation(LinearLayout.HORIZONTAL);//设置流向
		setGravity(Gravity.BOTTOM);
		state = State.NORMAL;//初始化状态
		
		contentView = onGetContentView();
		if(contentView != null){
			contentHeight = ViewUtils.getViewMeasureHeight(contentView);
			
			//设置视图的高度为最小高度
			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, contentMinHeight);
			contentView.setLayoutParams(layoutParams);
			
			addView(contentView);//将内容视图添加到下拉刷新布局上
		}
	}
	
	/**
	 * 更新高度
	 * @param newHeight
	 */
	public void updateHeight(int newHeight){
		//如果新的高度小于回滚的最终高度就将其设置为回滚的最终高度
		if(newHeight < getRollbackFinalHeight()){
			newHeight = getRollbackFinalHeight();
		}
		
		//更新高度
		ViewUtils.setViewHeight(contentView, newHeight);
		
		//如果当前状态是正常
		if(state == State.NORMAL){
			if(newHeight >= contentHeight){	//如果新的高度大于原始高度，说明是要从正常状态进入准备加载状态了
				state = State.READY_LOAD;	//设置状态为准备加载
				onNormalToReadyState();	//调用相应回调方法修改界面显示
			}
		//如果当前状态是准备加载
		}else if(state == State.READY_LOAD){
			if(newHeight < contentHeight){	//如果新的高度小于原始高度，说明是要从准备加载状态变为正常状态了
				state = State.NORMAL;	//设置状态为正常
				onReadyToNormalState();	//调用相应回调方法修改界面显示
			}
		//如果正在加载
		}else if(state == State.LOADING){
			
		//正在由加载中变为正常
		}else if(state == State.LOADING_TO_NORMAL){
			if(newHeight <= contentMinHeight){	//如果已经回滚到最小高度了
				state = State.NORMAL;	//设置状态为正常
				onRefreshToNormalState();	//调用相应回调方法修改界面显示
			}
		}else if(state == State.NORMAL_TO_LOADING){
			if(newHeight >= contentHeight){	//如果新的高度大于原始高度，说明是要从正常状态进入准备加载状态了
				intoLoadingState();
			}
		}
	}
	
	public void updateHeightOffset(int newOffSetHeight){
		updateHeight((int) (contentView.getHeight() - (newOffSetHeight * OFFSET_RADIO)));
	}
	
	/**
	 * 进入加载中状态
	 */
	public void intoLoadingState(){
		//设置状态为正在加载
		setState(State.LOADING);
		//调用相应回调方法修改界面显示
		onReadyToRefreshState();
	}
	
	/**
	 * 进入正在加载变为正常状态
	 */
	public void intoLoadingToNormalState(){
		//设置状态为由加载中转为正常
		setState(State.LOADING_TO_NORMAL);
	}
	
	/**
	 * 获取回滚高度
	 * @return 回滚高度，当是加载中状态时回滚高度是原始高度，否则一律是最小高度
	 */
	public int getRollbackFinalHeight(){
		//如果正在加载就回滚到原始高度，否则回滚到最小高度
		return state == State.LOADING?contentHeight:contentMinHeight;
	}
	
	public abstract LinearLayout onGetContentView();
	
	public abstract void onNormalToReadyState();
	
	public abstract void onReadyToNormalState();

	public abstract void onReadyToRefreshState();
	
	public abstract void onRefreshToNormalState();

	/**
	 * @return the state
	 */
	public State getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(State state) {
		this.state = state;
	}

	/**
	 * @return the contentHeight
	 */
	public int getContentHeight() {
		return contentHeight;
	}

	/**
	 * @param contentHeight the contentHeight to set
	 */
	public void setContentHeight(int contentHeight) {
		this.contentHeight = contentHeight;
	}
}