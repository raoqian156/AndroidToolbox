package me.xiaopan.easyandroid.widget;

import me.xiaopan.easyandroid.util.ViewUtils;
import me.xiaopan.easyandroid.widget.AbsClickLoadMoreListFooter.ClickLoadMoreFinishListener;
import me.xiaopan.easyandroid.widget.AbsClickLoadMoreListFooter.ClickLoadMoreListener;
import me.xiaopan.easyandroid.widget.AbsPullDownRefreshListHeader.PullDownRefreshFinishListener;
import me.xiaopan.easyandroid.widget.AbsPullDownRefreshListHeader.PullDownRefreshListener;
import me.xiaopan.easyandroid.widget.AbsPullListHeaderAndFoooter.OnStateChangeListener;
import me.xiaopan.easyandroid.widget.AbsPullListHeaderAndFoooter.State;
import me.xiaopan.easyandroid.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreFinishListener;
import me.xiaopan.easyandroid.widget.AbsPullUpLoadMoreListFooter.PullUpLoadMoreListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 可拉伸的列表
 */
public class PullListView extends ListView implements PullDownRefreshFinishListener, PullUpLoadMoreFinishListener, ClickLoadMoreFinishListener{
	public static final float OFFSET_RADIO = 0.35f;//拉伸偏移，模拟出拉橡皮筋的感觉来
	public static final int ROLLBACK_DURATION = 300;//回滚持续时间
	private int listFooterTempHeight;//上拉加载更多列表尾临时高度
	private float lastYPosition;//上一个触摸点的Y坐标
	private boolean init;//是否正在初始化
	boolean full;//列表充满
	private boolean firstScroll = true;//第一次滚动
	private boolean rollBacking;//回滚中
	private boolean autoRefresh;
	private boolean autoLoadMore; 
	private boolean openedPullDownRefreshMode;//打开了下拉刷新模式
	private boolean openedPullUpLoadMoreMode;//打开了上拉加载更多模式
	private boolean openedListHeaderReboundMode;//打开了列表头反弹模式
	private boolean openedListFooterReboundMode;//打开了列表尾反弹模式
	private boolean openedClickLoadMoreMode;//打开了点击加载更多模式
	private boolean allowRollToBotttomLoadMore;//允许滚动到底部的时候直接开始加载更多
	private boolean handleHeaderOrFooter;//处理列表头或者列表尾
	private Scroller rollBackScroller;//回滚滚动器
	private OperandEnum currentOperand = OperandEnum.NONE;//当前操作对象
	private OperandEnum lastOperand = OperandEnum.NONE;//上次操作对象，当开始回滚时将该对象赋值为当前操作的对象，并将当前操作对象清空，如果在回滚的过程中中断了那么需要将用上次操作对象来恢复当前操作对象
	private AbsPullUpLoadMoreListFooter pullUpLoadMoreListFooter;//上拉加载更多列表尾
	private PullUpLoadMoreListener pullUpLoadMoreListener;//上拉加载更多监听器
	private AbsPullDownRefreshListHeader pullDownRefreshListHeader;//下拉刷新列表头
	private PullDownRefreshListener pullDownRefreshListener;//下拉刷新监听器
	private AbsPullListHeaderAndFoooter rollbackObject;//回滚对象
	private AbsClickLoadMoreListFooter clickLoadMoreListFooter; //点击加载更多列表尾
	private ClickLoadMoreListener clickLoadMoreListener;//点击加载更多列表尾监听器
	private PullScrollListener pullScrollListener;
	OnScrollListener onScrollListener;//滚动监听器
	
	public PullListView(Context context) {
		super(context);
		init();
	}

	public PullListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	/**
	 * 初始化
	 */
	private void init(){
		//初始化回滚滚动器
		rollBackScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
		//标记为初始化开始
		init = true;
		setOnScrollListener(pullScrollListener = new PullScrollListener(this));
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev){
		//如果需要处理列表头或者列表尾
		if(handleHeaderOrFooter){
			switch (ev.getAction()) {
				case MotionEvent.ACTION_DOWN: touchDownHandle(ev); break;
				case MotionEvent.ACTION_MOVE: touchMoveHandle(ev); break;
				case MotionEvent.ACTION_UP: touchUpHandle(ev); break;
			}	
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 按下触摸事件处理
	 * @param motionEvent
	 */
	private void touchDownHandle(MotionEvent motionEvent){
		//记录上一个触摸点的Y坐标
		lastYPosition = motionEvent.getRawY();
		
		//如果正处于滚动中
		if(!firstScroll && rollBackScroller.computeScrollOffset()){
			//如果是充满状态
			if(full){
				//强制停止滚动
				rollBackScroller.abortAnimation(); 
			}else{
				/**
				 * 此举为了防止在未充满状态下列表头或者列表尾在由加载中状态回滚时被打断 
				 */
				//如果正在回滚下拉刷新列表头
				if(lastOperand == OperandEnum.HEADER){
					//如果下拉刷新列表头不在由加载中变为正常的状态中
					if(!getPullDownRefreshListHeader().isLoadingToNormalState()){
						//强制停止滚动
						rollBackScroller.abortAnimation(); 
						//恢复当前操作的对象，因为之后弹起的时候处理回滚时要用到
						currentOperand = lastOperand;
					}
				//如果正在回滚上拉加载更多列表尾
				}else if(lastOperand == OperandEnum.FOOTER){
					//如果上拉加载更多列表尾不在由加载中变为正常的状态中
					if(!getPullUpLoadMoreListFooter().isLoadingToNormalState()){
						//强制停止滚动
						rollBackScroller.abortAnimation(); 
						//恢复当前操作的对象，因为之后弹起的时候处理回滚时要用到
						currentOperand = lastOperand;
					}
				}
			}
		}else{
			firstScroll = false;
			//判断列表是否充满
			full = pullScrollListener.isFull();
		}
	}
	
	/**
	 * 滑动触摸事件处理
	 * @param ev
	 */
	private void touchMoveHandle(MotionEvent ev){
		/**
		 * 计算滑动距离并记录触摸点的Y坐标
		 */
		//取出当前触摸位置的Y坐标
		float currentY = ev.getRawY();
		//计算在Y轴上移动的距离
		float distanceY = currentY - lastYPosition; 
		//更新记录的触摸点的Y坐标
		lastYPosition = currentY; 
		
		//如果列表是充满状态
		if(full){
			//如果当前位置是在列表的顶部，就试图处理下拉刷新列表头
			if(getFirstVisiblePosition() == 0){
				tryHandlePullDownRefreshListHeader(distanceY);
			//如果当前位置是在列表的底部，就试图处理上拉加载更多列表尾
			}else if(getLastVisiblePosition() == getCount() - 1){
				tryHandlePullUpLoadMoreListFooter(distanceY);
			}
		}else{
			//如果是第一次滑动
			if(currentOperand == OperandEnum.NONE){
				//如果是上拉
				if(distanceY < 0){
					//如果下拉刷新列表头正在刷新中就禁止滑动上拉刷新列表尾
//					if(!(isOpenedPullDownRefreshMode() && getPullDownRefreshListHeader().isLoadingState())){
						//标记当前操作的对象是上拉加载更多列表尾
						currentOperand = OperandEnum.FOOTER;
						//试图处理上拉加载更多列表尾
						tryHandlePullUpLoadMoreListFooter(distanceY);
//					}
				//如果是下拉
				}else if(distanceY > 0){
					//标记当前操作的对象是下拉刷新列表头
					currentOperand = OperandEnum.HEADER;
					//试图处理下拉刷新列表头
					tryHandlePullDownRefreshListHeader(distanceY);
				}
			//如果当前操作的对象是下拉刷新列表头
			}else if(currentOperand == OperandEnum.HEADER){
				//试图处理下拉刷新列表头
				tryHandlePullDownRefreshListHeader(distanceY);
			//如果当前处理的对象是上拉加载更多列表尾
			}else if(currentOperand == OperandEnum.FOOTER){
				//试图处理上拉加载更多列表尾
				tryHandlePullUpLoadMoreListFooter(distanceY);
			}
		}
	}
	
	/**
	 * 弹起触摸事件处理
	 * @param ev
	 */
	private void touchUpHandle(MotionEvent ev){
		//如果列表是充满状态
		if(full){
			//如果当前位置是在列表的顶部
			if(getFirstVisiblePosition() ==0){
				//如果打开了下拉刷新模式
				if(isOpenedPullDownRefreshMode()){
					//试图回滚下拉刷新列表头
					tryRollback(getPullDownRefreshListHeader());
				}
			//如果当前位置是在列表的底部
			}else if(getLastVisiblePosition() == getCount() - 1){
				//如果打开了下拉刷新模式
				if(isOpenedPullUpLoadMoreMode()){
					//试图回滚上拉加载更多列表尾
					tryRollback(getPullUpLoadMoreListFooter());
				}
			}
		}else{
			//如果当前操作的是下拉刷新列表头
			if(currentOperand == OperandEnum.HEADER){
				//如果打开了下拉刷新模式
				if(isOpenedPullDownRefreshMode()){
					//试图回滚下拉刷新列表头
					tryRollback(getPullDownRefreshListHeader());
				}
				//记录当前操作的对象
				lastOperand = currentOperand;
				//将当前操作的对象清空
				currentOperand = OperandEnum.NONE;
			//如果当前操作的是上拉加载更多列表尾
			}else if(currentOperand == OperandEnum.FOOTER){
				//如果打开了下拉刷新模式
				if(isOpenedPullUpLoadMoreMode()){
					//试图回滚上拉加载更多列表尾
					tryRollback(getPullUpLoadMoreListFooter());
				}
				//记录当前操作的对象
				lastOperand = currentOperand;
				//将当前操作的对象清空
				currentOperand = OperandEnum.NONE;
			}
		}
	}
	
	/**
	 * 尝试处理列表头的滑动
	 * @param distanceY 滑动的距离
	 */
	private void tryHandlePullDownRefreshListHeader(float distanceY){
		//如果打开了下拉刷新模式
		if(isOpenedPullDownRefreshMode()){
			//如果是在下拉
			if(distanceY > 0){
				//增加下拉刷新列表头的高度
				getPullDownRefreshListHeader().setVisiableHeight(getPullDownRefreshListHeader().getVisiableHeight() + (int) (distanceY * OFFSET_RADIO));
			//如果是在上拉（手动回滚）
			}else if(distanceY < 0){
				//如果当前高度大于回滚高度
				if(getPullDownRefreshListHeader().getVisiableHeight() > getPullDownRefreshListHeader().getRollbackFinalHeight()){
					//减小下拉刷新列表头的高度
					getPullDownRefreshListHeader().setVisiableHeight(getPullDownRefreshListHeader().getVisiableHeight() + (int) (distanceY * OFFSET_RADIO));
					//因为在在减小下拉刷新列表头高度之后我们依然执行了父方法中的内容，致使在下拉刷新列表头高度变小的同时，列表本身也在往上滚动。  
					//于是当在滚动的过程中第一行显示的数据变为第1条的时候我们之前的条件getFirstVisiblePosition() ==0就不成立了，那么接下来就不会再执行减小下拉刷新列表头高度的代码了，但这时下拉刷新列表头的高度还尚未恢复到最小高度。显然这种结果不是我们想要的。
					//最终解决办法就是在上拉的过程中通过setSelection()方法将列表第一行显示的数据始终设置为第0条。
					setSelection(0);
				}
			}
		}
	}
	
	/**
	 * 尝试处理列尾的滑动
	 * @param distanceY 滑动的距离
	 */
	private void tryHandlePullUpLoadMoreListFooter(float distanceY){
		//如果打开了上拉加载更多模式
		if(isOpenedPullUpLoadMoreMode()){
			//如果是在往上拉
			if(distanceY < 0){
				//增加上拉加载更多列表尾的高度
				getPullUpLoadMoreListFooter().setVisiableHeight(getPullUpLoadMoreListFooter().getVisiableHeight() + (int) (-distanceY * OFFSET_RADIO));
			//如果是在下拉（手动回滚）
			}else if(distanceY > 0){
				//如果当前高度大于回滚高度
				if(getPullUpLoadMoreListFooter().getVisiableHeight() > getPullUpLoadMoreListFooter().getRollbackFinalHeight()){
					//减小上拉加载更多列表尾的高度
					getPullUpLoadMoreListFooter().setVisiableHeight(getPullUpLoadMoreListFooter().getVisiableHeight() + (int) (-distanceY * OFFSET_RADIO));
					//因为在在减小上拉加载更多列表尾高度之后我们依然执行了父方法中的内容，致使在上拉加载更多列表尾高度变小的同时，列表本身也在往下滚动。  
					//于是当在滚动的过程中最后一行显示的数据变为第getCount() - 2条的时候我们的之前的条件getLastVisiblePosition() == getCount() - 1就不成立了，那么接下来就不会再执行减小上拉加载更多列表尾高度的代码了，但这时上拉加载更多列表尾的高度还尚未恢复到最小高度。显然这种结果不是我们想要的。
					//最终解决办法就是在下拉的过程中通过setSelection()方法将列表最后一行显示的数据始终设置为第getCount() - 1条。
					setSelection(getCount() - 1);
				}
			}
		}
	}
	
	/**
	 * 试图回滚
	 */
	private void tryRollback(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter){
		//如果打开了下拉刷新模式并且下拉刷新列表头的高度大于回滚高度
		if(pullListHeaderAndFoooter.getVisiableHeight() > pullListHeaderAndFoooter.getRollbackFinalHeight()){
			//如果当前下拉刷新列表头正处于准备加载状态，那么接下来将进入加载中状态
			if(pullListHeaderAndFoooter.isReadyLoadState()){
				//进入加载中状态
				pullListHeaderAndFoooter.intoLoadingState();
				
				if(pullListHeaderAndFoooter.isListHeader()){
					//如果有上拉刷新监听器，就调用相应回调函数进行刷新
					if(getPullDownRefreshListener() != null){
						getPullDownRefreshListener().onRefresh(this);
					}
				}else{
					//如果有上拉加载更多监听器，就调用相应回调函数进行加载
					if(getPullUpLoadMoreListener() != null){
						getPullUpLoadMoreListener().onLoadMore(this);
					}
				}
			}
			
			//开始回滚下拉刷新列表头
			rollback(pullListHeaderAndFoooter);
		}
	}
	
	/**
	 * 回滚
	 * @param pullListHeaderAndFoooter 列表头或列表尾
	 * @param begaunHeight 开始高度
	 * @param endHeight 结束高度
	 * @param duration 持续时间
	 */
	private void rollback(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter, int begainHeight, int endHeight, long duration){
		if(begainHeight != endHeight){
			rollbackObject = pullListHeaderAndFoooter;
			rollBackScroller.startScroll(0, begainHeight, 0, endHeight - begainHeight, (int) duration);
			invalidate();
		}
	}
	
	/**
	 * 回滚到指定的最终高度
	 * @param pullListHeaderAndFoooter 列表头或列表尾
	 * @param finalHeight 最终高度
	 * @param duration 持续时间
	 */
	private void rollbackToFinalHeight(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter, int finalHeight, long duration){
		rollback(pullListHeaderAndFoooter, pullListHeaderAndFoooter.getVisiableHeight(), finalHeight, ROLLBACK_DURATION);
	}

	/**
	 * 回滚到指定的最终高度
	 * @param pullListHeaderAndFoooter 列表头或列表尾
	 * @param finalHeight 最终高度
	 */
	private void rollbackToFinalHeight(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter, int finalHeight){
		rollbackToFinalHeight(pullListHeaderAndFoooter, finalHeight, ROLLBACK_DURATION);
	}

	/**
	 * 回滚
	 * @param pullListHeaderAndFoooter 列表头或列表尾
	 * @param duration 持续时间
	 */
	void rollback(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter, long duration){
		rollbackToFinalHeight(pullListHeaderAndFoooter, pullListHeaderAndFoooter.getRollbackFinalHeight(), duration);
	}

	/**
	 * 回滚
	 * @param pullListHeaderAndFoooter 列表头或列表尾
	 */
	private void rollback(AbsPullListHeaderAndFoooter pullListHeaderAndFoooter){
		rollbackToFinalHeight(pullListHeaderAndFoooter, pullListHeaderAndFoooter.getRollbackFinalHeight());
	}
	
	@Override
	public void computeScroll() {
		//如果有滚动的对象
		if (rollbackObject != null) {
			//如果正在滚动
			if(rollBackScroller.computeScrollOffset()){
				//标记为正在滚动中
				rollBacking = true;
				//更新滚动对象的高度
				rollbackObject.setVisiableHeight(rollBackScroller.getCurrY());
				
				//如果正在自动滚动家在更多列表尾并且列表正处于列表尾就显示最后一行，模拟出向上滚动的效果
				if(autoLoadMore && getLastVisiblePosition() == getCount() - 1){
					setSelection(getCount() - 1);
				}
				
				invalidate();
			//如果没有滚动
			}else{
				//如果之前发生过滚动行为
				if(rollBacking){
					//标记为已经停止滚动了
					rollBacking = false;
					lastOperand = OperandEnum.NONE;
					//如果之前滚动的是列表头
					if(rollbackObject.isListHeader()){
						//如果之前执行的是自动滚动刷新列表头，就再次滚动执行刷新
						if(autoRefresh){
							autoRefresh = false;
							tryRollback(getPullDownRefreshListHeader());
						}
					//如果之前滚动的是列表尾
					}else{
						//如果列表未充满就定位到第一行
						if(!full){
							setSelection(0);
						}
						
						//如果之前执行的时候自动滚动加载更多列表尾，就再次滚动执行加载
						if(autoLoadMore){
							autoLoadMore = false;
							tryRollback(getPullUpLoadMoreListFooter());
						}
					}
				}
				//清空滚动对象
				rollbackObject = null;
			}
		}
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		//添加下拉刷新列表头
		if(isOpenedPullDownRefreshMode()){
			//设置状态改变监听器，当列表未充满时，随着列表头高度的改变，列表尾也要相应的做出改变
			getPullDownRefreshListHeader().setOnStateChangeListener(new OnStateChangeListener() {
				@Override
				public void onReadyLoadToLoadingState() {
					//如果列表是未充满状态
					if(isOpenedPullUpLoadMoreMode() && !full){
						//记录旧的高度
						listFooterTempHeight = getPullUpLoadMoreListFooter().getMinHeight();
						//计算新的高度并绑定到上拉加载更多列表尾上
						getPullUpLoadMoreListFooter().setMinHeight(getPullUpLoadMoreListFooter().getVisiableHeight() - getPullDownRefreshListHeader().getOriginalHeight());
						
						//以最快的速度回滚上拉加载更多列表尾到新的最小高度
						getPullUpLoadMoreListFooter().setVisiableHeight(getPullUpLoadMoreListFooter().getMinHeight());
						invalidate();
						rollback(getPullUpLoadMoreListFooter(), 0l);
					}
				}

				@Override
				public void onLoadingToNormalState() {
					//如果列表是未充满状态
					if(isOpenedPullUpLoadMoreMode() && !full && listFooterTempHeight != -5){
						//恢复上拉加载更多列表尾的最小高度
						getPullUpLoadMoreListFooter().setMinHeight(listFooterTempHeight);
						listFooterTempHeight = -5;
						
						//以最快的速度回滚上拉加载更多列表尾到新的最小高度
						getPullUpLoadMoreListFooter().setVisiableHeight(getPullUpLoadMoreListFooter().getMinHeight());
						invalidate();
						rollback(getPullUpLoadMoreListFooter(), 0l);
					}
				}
			});
			
			//设置触摸事件监听器，使可以从列表头开始滑动
			getPullDownRefreshListHeader().setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()){
						case MotionEvent.ACTION_DOWN : touchDownHandle(event); break;
					}
					return false;
				}
			});
			
			getPullDownRefreshListHeader().setClickable(true);
			getPullDownRefreshListHeader().setListHeader(true);
			addHeaderView(getPullDownRefreshListHeader());
			handleHeaderOrFooter = true;
		}
		
		//添加点击加载更多列表尾
		if(isOpenedClickLoadMoreMode()){
			//设置点击监听器，点击的时候加载
			getClickLoadMoreListFooter().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if(getClickLoadMoreListFooter().getState() == AbsClickLoadMoreListFooter.State.NORMAL && getClickLoadMoreListener() != null){
						getClickLoadMoreListFooter().intoLoadingState();
						getClickLoadMoreListener().onLoadMore(PullListView.this);
					}
				}
			});
			getClickLoadMoreListFooter().setClickable(true);
			addFooterView(getClickLoadMoreListFooter());
			handleHeaderOrFooter = true;
		}
		
		//添加上拉加载更多列表尾
		if(isOpenedPullUpLoadMoreMode()){
			//设置触摸事件监听器，使可以从列表尾开始滑动
			getPullUpLoadMoreListFooter().setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					switch(event.getAction()){
						case MotionEvent.ACTION_DOWN : touchDownHandle(event); break;
					}
					return false;
				}
			});
			
			getPullUpLoadMoreListFooter().setClickable(true);
			getPullUpLoadMoreListFooter().setListHeader(false);
			addFooterView(getPullUpLoadMoreListFooter());
			handleHeaderOrFooter = true;
		}
		
		super.setAdapter(adapter);
	}
	
	@Override
	public void setOnScrollListener(OnScrollListener l) {
		if(init){
			init = false;
			super.setOnScrollListener(l);
		}else{
			onScrollListener = l;
		}
	}
	
	/**
	 * 开启刷新
	 */
	public void startRefresh(){
		if(isOpenedPullDownRefreshMode() && getPullDownRefreshListHeader().getState() == State.NORMAL && getPullDownRefreshListener() != null){
			autoRefresh = true;
			setSelection(0);
			rollbackToFinalHeight(getPullDownRefreshListHeader(), getPullDownRefreshListHeader().getOriginalHeight());
		}
	}
	
	/**
	 * 开启加载更多
	 */
	public void startLoadMore(){
		if(isOpenedPullUpLoadMoreMode() && getPullUpLoadMoreListFooter().getState() == State.NORMAL && getPullUpLoadMoreListener() != null){
			autoLoadMore = true;
			setSelection(getCount() - 1);
			rollbackToFinalHeight(getPullUpLoadMoreListFooter(), getPullUpLoadMoreListFooter().getOriginalHeight());
		}
	}
	
	/**
	 * 开启点击加载更多
	 */
	public void startClickLoadMore(){
		if(isOpenedClickLoadMoreMode() && getClickLoadMoreListFooter().getState() == AbsClickLoadMoreListFooter.State.NORMAL && getClickLoadMoreListener() != null){
			setSelection(getCount() - 1);
			getClickLoadMoreListFooter().intoLoadingState();
			getClickLoadMoreListener().onLoadMore(PullListView.this);
		}
	}
	
	@Override
	public void finishRefresh(){
		if (isOpenedPullDownRefreshMode()) {
			getPullDownRefreshListHeader().intoLoadingToNormalState();
			//如果当前位置在列表的顶部，就回滚
			if(getFirstVisiblePosition() == 0){
				//如果下拉刷新列表头的顶部跟列表的顶部正好对齐
				if((getPullDownRefreshListHeader().getTop() + getPaddingTop()) == getTop()){
					//回滚下拉刷新列表头
					rollback(getPullDownRefreshListHeader());
				}else{
					//因为当前下拉刷新列表头只显示了一部分，所以开始高度就是显示出来的那部分的高度
					int begainHeight = (getPullDownRefreshListHeader().getOriginalHeight() + getPullDownRefreshListHeader().getTop()) - getPaddingTop();
					
					//先将下拉刷新列表头的高度设为刚刚计算出来的开始高度
					getPullDownRefreshListHeader().setVisiableHeight(begainHeight);
					//定位到顶部
					setSelection(0);
					
					//开始回滚到最小高度
					rollback(getPullDownRefreshListHeader(), begainHeight, getPullDownRefreshListHeader().getMinHeight(), ROLLBACK_DURATION);
				}
			}else{
				//以最短的时间回滚下拉刷新列表头
				getPullDownRefreshListHeader().setVisiableHeight(getPullDownRefreshListHeader().getMinHeight());
				invalidate();
				rollback(getPullDownRefreshListHeader(), 0l);
			}
		}
	}
	
	@Override
	public void finishLoadMore(){
		if (isOpenedPullUpLoadMoreMode()) {
			getPullUpLoadMoreListFooter().intoLoadingToNormalState();
			rollback(getPullUpLoadMoreListFooter());
		}
	}
	
	@Override
	public void finishClickLoadMore(){
		if(isOpenedClickLoadMoreMode()){
			getClickLoadMoreListFooter().intoNormalState();
		}
	}
	
	/**
	 * 打开下拉刷新模式，此方法适用于第一次打开时使用
	 * @param pullDownRefreshListHeader 第一次调用此方法的时候参数不能为空，要不然会打开失败
	 * @param pullDownRefreshListener
	 */
	public void openPullDownRefreshMode(AbsPullDownRefreshListHeader pullDownRefreshListHeader, PullDownRefreshListener pullDownRefreshListener){
		if(pullDownRefreshListHeader != null){
			openedPullDownRefreshMode = true;
			this.pullDownRefreshListHeader = pullDownRefreshListHeader;
			if(pullDownRefreshListener != null){
				setPullDownRefreshListener(pullDownRefreshListener);
			}
		}
	}

	/**
	 * 打开上拉加载更多模式，此方法适用于第一次打开时使用
	 * @param pullUpLoadMoreListFooter 第一次调用此方法的时候参数不能为空，要不然会打开失败
	 * @param pullUpLoadMoreListener
	 */
	public void openPullUpLoadMoreMode(AbsPullUpLoadMoreListFooter pullUpLoadMoreListFooter, PullUpLoadMoreListener pullUpLoadMoreListener){
		if(pullUpLoadMoreListFooter != null){
			openedPullUpLoadMoreMode = true;
			this.pullUpLoadMoreListFooter = pullUpLoadMoreListFooter;
			if(pullUpLoadMoreListener != null){
				setPullUpLoadMoreListener(pullUpLoadMoreListener);
			}
		}
	}

	/**
	 * 打开列表头反弹模式
	 */
	public void openListHeaderReboundMode(){
		openPullDownRefreshMode(new AbsPullDownRefreshListHeader(getContext()) {
			@Override
			protected LinearLayout onGetContentView(){
				return ViewUtils.createLinearLayout(getContext(), LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			}
			
			@Override
			protected int onGetOriginalHeight(LinearLayout contentView) {
				return Integer.MAX_VALUE;
			}
			
			@Override
			protected void onNormalToReadyLoadState(LinearLayout contentView){}
			@Override
			protected void onReadyLoadToNormalState(LinearLayout contentView){}
			@Override
			protected void onReadyLoadToLoadingState(LinearLayout contentView){}
			@Override
			protected void onLoadingToNormalState(LinearLayout contentView){}
			@Override
			protected void onUpdateLastLoadTime(LinearLayout contentView, long lastLoadTime){}
		}, null);
		this.openedListHeaderReboundMode = true;
	}
	
	/**
	 * 打开列表尾反弹模式
	 */
	public void openListFooterReboundMode(){
		openPullUpLoadMoreMode(new AbsPullUpLoadMoreListFooter(getContext()) {
			@Override
			protected LinearLayout onGetContentView(){
				return ViewUtils.createLinearLayout(getContext(), LinearLayout.HORIZONTAL, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			}

			@Override
			protected int onGetOriginalHeight(LinearLayout contentView) {
				return Integer.MAX_VALUE;
			}
			
			@Override
			protected void onNormalToReadyLoadState(LinearLayout contentView){}
			@Override
			protected void onReadyLoadToNormalState(LinearLayout contentView){}
			@Override
			protected void onReadyLoadToLoadingState(LinearLayout contentView){}
			@Override
			protected void onLoadingToNormalState(LinearLayout contentView){}
			@Override
			protected void onUpdateLastLoadTime(LinearLayout contentView, long lastLoadTime){}
		}, null);
		this.openedListFooterReboundMode = true;
	}

	/**
	 * 打开点击加载更多模式
	 * @param clickLoadMoreListFooter
	 * @param clickLoadMoreListener
	 */
	public void openClickLoadMoreMode(AbsClickLoadMoreListFooter clickLoadMoreListFooter, ClickLoadMoreListener clickLoadMoreListener){
		if(clickLoadMoreListFooter != null){
			openedClickLoadMoreMode = true;
			this.clickLoadMoreListFooter = clickLoadMoreListFooter;
			if(clickLoadMoreListener != null){
				setClickLoadMoreListener(clickLoadMoreListener);
			}
		}
	}
	
	/**
	 * 是否打开了下拉刷新功能
	 * @return 是否打开了下拉刷新功能
	 */
	public boolean isOpenedPullDownRefreshMode() {
		return openedPullDownRefreshMode;
	}
	
	/**
	 * 是否打开了上拉加载更多模式
	 * @return 是否打开了上拉加载更多模式
	 */
	public boolean isOpenedPullUpLoadMoreMode() {
		return openedPullUpLoadMoreMode;
	}

	/**
	 * 是否打开了列表头反弹模式
	 * @return 是否打开了列表头反弹模式
	 */
	public boolean isOpenedListHeaderReboundMode() {
		return openedListHeaderReboundMode;
	}
	
	/**
	 * 是否打开了列表尾反弹模式
	 * @return 是否打开了列表尾反弹模式
	 */
	public boolean isOpenedListFooterReboundMode() {
		return openedListFooterReboundMode;
	}
	
	/**
	 * 判断是否打开了点击加载更多模式
	 * @return 是否打开了点击加载更多模式
	 */
	public boolean isOpenedClickLoadMoreMode(){
		return openedClickLoadMoreMode;
	}

	/**
	 * 判断是否允许当列表滚动到底部的时候加载更多，该属性仅当开启了点击加载更多模式时有效
	 * @return
	 */
	public boolean isAllowRollToBotttomLoadMore() {
		return allowRollToBotttomLoadMore;
	}

	/**
	 * 设置是否允许当列表滚动到底部的时候加载更多，该属性仅当开启了点击加载更多模式时有效
	 * @param allowRollToBotttomLoadMore 是否允许当列表滚动到底部的时候加载更多，该属性仅当开启了点击加载更多模式时有效
	 */
	public void setAllowRollToBotttomLoadMore(boolean allowRollToBotttomLoadMore) {
		this.allowRollToBotttomLoadMore = allowRollToBotttomLoadMore;
	}
	
	/**
	 * 获取下拉刷新列表头
	 * @return 下拉刷新列表头
	 */
	public AbsPullDownRefreshListHeader getPullDownRefreshListHeader() {
		return pullDownRefreshListHeader;
	}

	/**
	 * 获取下拉刷新监听器 
	 * @return 下拉刷新监听器 
	 */
	public PullDownRefreshListener getPullDownRefreshListener() {
		return pullDownRefreshListener;
	}

	/**
	 * 设置下拉刷新监听器 
	 * @param pullDownRefreshListener 下拉刷新监听器 
	 */
	public void setPullDownRefreshListener(PullDownRefreshListener pullDownRefreshListener) {
		this.pullDownRefreshListener = pullDownRefreshListener;
	}
	
	/**
	 * 获取上拉加载更多列表尾
	 * @return 上拉加载更多列表尾
	 */
	public AbsPullUpLoadMoreListFooter getPullUpLoadMoreListFooter() {
		return pullUpLoadMoreListFooter;
	}

	/**
	 * 设置上拉加载更多监听器
	 * @return 上拉加载更多监听器
	 */
	public PullUpLoadMoreListener getPullUpLoadMoreListener() {
		return pullUpLoadMoreListener;
	}

	/**
	 * 设置上拉加载更多监听器
	 * @param pullUpLoadMoreListener 上拉加载更多监听器
	 */
	public void setPullUpLoadMoreListener(PullUpLoadMoreListener pullUpLoadMoreListener) {
		this.pullUpLoadMoreListener = pullUpLoadMoreListener;
	}
	
	/**
	 * 获取点击加载更多列表尾
	 * @return 点击加载更多列表尾
	 */
	public AbsClickLoadMoreListFooter getClickLoadMoreListFooter() {
		return clickLoadMoreListFooter;
	}

	/**
	 * 获取点击加载更多监听器
	 * @return 点击加载更多监听器
	 */
	public ClickLoadMoreListener getClickLoadMoreListener() {
		return clickLoadMoreListener;
	}

	/**
	 * 设置点击加载更多监听器
	 * @param clickLoadMoreListener 点击加载更多监听器
	 */
	public void setClickLoadMoreListener(ClickLoadMoreListener clickLoadMoreListener) {
		this.clickLoadMoreListener = clickLoadMoreListener;
	}
	
	/**
	 * 操作对象
	 * @author xiaopan
	 *
	 */
	private enum OperandEnum{
		NONE, HEADER, FOOTER;
	}
}

class PullScrollListener implements OnScrollListener{
	private PullListView pullListView;
	private int count;//计数
	private int lastListHeight;//记录列表的高度
	private int lastTotalItemCount;///记录列表的总条目数
	private int lastVisibleItemCount;///记录列表每页显示的条目数
	private int clickLoadMoreListFooterLastTop;//记录点击加载更多列表尾的Top
	private int clickLoadMoreListFooterLastBottom;//记录点击加载更多列表尾的Bottom
	private boolean allowHandleClickLoadMoreListFooter;//允许处理点击加载更多列表尾
	
	public PullScrollListener(PullListView pullListView){
		this.pullListView = pullListView;
	}
	
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(pullListView.onScrollListener != null){
			pullListView.onScrollListener.onScrollStateChanged(view, scrollState);
		}
	}
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		/*
		 * 如果开启了上拉加载更多功能的话，就要保证列表是充满状态，如果不充满的话就要用列表尾将列表充满；当列表已充满时将列表尾的高度将恢复为0
		 */
		if(pullListView.isOpenedPullUpLoadMoreMode()){
			/*
			 * 判断是否需要更新列表尾的高度
			 */
			int newListHeight = pullListView.getHeight();					
			lastListHeight = lastListHeight == 0?newListHeight:lastListHeight;					//当第一次执行的时候，初始化上一次滚动时列表的高度
			
			//如果列表高度有变化或者列表总条目数有变化，并且列表的充满状态也发生了改变，那么就开始更新列表尾的高度
			if(newListHeight != lastListHeight || totalItemCount != lastTotalItemCount && !(isFull() && visibleItemCount < totalItemCount)){
				int newMinHeight = 0;//默认新的最小高度为0，适合于列表充满状态
				
				//如果列表没有充满，就修改新的最小高度使其可以正好充满列表
				if(visibleItemCount >= totalItemCount){
					newMinHeight = pullListView.getPullUpLoadMoreListFooter().getVisiableHeight() + (pullListView.getBottom() - pullListView.getPaddingBottom() - pullListView.getPullUpLoadMoreListFooter().getBottom());
				}
				pullListView.getPullUpLoadMoreListFooter().setMinHeight(newMinHeight);
				
				//以最快的速度回滚列表尾
				pullListView.rollback(pullListView.getPullUpLoadMoreListFooter(), 0l);
			}
			
			lastListHeight = newListHeight;																			//记录列表高度
		}
		
		/*
		 * 如果开启了点击加载更多功能也允许当列表滚动到底部的时候自动触发加载更多功能，以及列表是充满状态的话，就判断是否滚动到了底部，条件满足的话就触发加载更多功能
		 */
		if(pullListView.isOpenedClickLoadMoreMode() && pullListView.isAllowRollToBotttomLoadMore() && pullListView.getClickLoadMoreListener() != null && pullListView.full){
			boolean constant = clickLoadMoreListFooterLastTop == pullListView.getClickLoadMoreListFooter().getTop() && clickLoadMoreListFooterLastBottom == pullListView.getClickLoadMoreListFooter().getBottom();
			
			//如果允许加载
			if(allowHandleClickLoadMoreListFooter){
				//如果点击加载列表尾显示出来了并且装态是非加载状态
				if(!constant && pullListView.getClickLoadMoreListFooter().getState() == AbsClickLoadMoreListFooter.State.NORMAL){
					allowHandleClickLoadMoreListFooter = false;
					pullListView.getClickLoadMoreListFooter().intoLoadingState();
					pullListView.getClickLoadMoreListener().onLoadMore(pullListView);
				}
			}else{
				//如果连续三次没有显示出来
				if(constant){
					if(count++ > 9){
						count = 0;
						allowHandleClickLoadMoreListFooter = true;
					}
				}else{
					count = 0;
				}
			}
			
			//记录Top和Bottom
			clickLoadMoreListFooterLastTop = pullListView.getClickLoadMoreListFooter().getTop();
			clickLoadMoreListFooterLastBottom = pullListView.getClickLoadMoreListFooter().getBottom();
		}
		
		//每页显示条目数、总条目数
		lastVisibleItemCount = visibleItemCount;
		lastTotalItemCount = totalItemCount;
		
		if(pullListView.onScrollListener != null){
			pullListView.onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}
	
	/**
	 * 判断列表是否充满
	 * @return 列表是否充满
	 */
	public boolean isFull(){
		return lastVisibleItemCount < lastTotalItemCount;//如果当前显示的条目数小于总条目数说明列表当前是充满状态
	}
}