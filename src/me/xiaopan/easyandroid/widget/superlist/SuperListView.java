package me.xiaopan.easyandroid.widget.superlist;

import me.xiaopan.easyandroid.widget.AbsPullListHeaderAndFoooter.State;
import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Scroller;

/**
 * 这是一个超级ListView，支持下拉刷新和滚动到底部自动加载更多
 */
public class SuperListView extends ListView implements OnScrollListener, GestureDetector.OnGestureListener{
	public static final int ROLLBACK_DURATION = 300;//回滚持续时间
	private boolean init;
	private Scroller rollBackScroller;//回滚滚动器
	private GestureDetector gestureDetector;
	private OnScrollListener onScrollListener;
	private BasePulldownRefershListHeader pulldownRefershListHeader;
	private boolean pulldown;
	private OnRefreshListener onRefreshListener; 
	
	public SuperListView(Context context) {
		super(context);
		init();
	}

	public SuperListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	/**
	 * 初始化
	 */
	private void init(){
		rollBackScroller = new Scroller(getContext(), new AccelerateDecelerateInterpolator());
		gestureDetector = new GestureDetector(this);
		
		//设置滚动监听器
		init = true;
		setOnScrollListener(this);
		init = false;
	}
	
	@Override
	public void setOnScrollListener(OnScrollListener onScrollListener) {
		if(init){
			super.setOnScrollListener(onScrollListener);
		}else{
			this.onScrollListener = onScrollListener;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(pulldownRefershListHeader != null){
			gestureDetector.onTouchEvent(ev);
			if(ev.getAction() == MotionEvent.ACTION_CANCEL || ev.getAction() == MotionEvent.ACTION_UP){
				upEventHandle();
			}
		}
		return super.onTouchEvent(ev);
	}
	
	/**
	 * 弹起事件处理
	 */
	private void upEventHandle(){
		//如果当前是在第一行并且开启了下拉刷新功能
		if(getFirstVisiblePosition() == 0){
			//如果下拉刷新头正处于准备刷新状态，就进入刷新模式
			if(pulldownRefershListHeader.getState() == State.READY_LOAD){
				pulldownRefershListHeader.intoLoadingState();
				if(onRefreshListener != null){
					onRefreshListener.onRefresh();
				}
			}
			//尝试回滚下拉刷新列表头
			tryRollbackPulldownRefreshListHeader();
		}
	}
	
	/**
	 * 尝试回滚下拉刷新列表头
	 */
	private void tryRollbackPulldownRefreshListHeader(int begainHeight, int endHeight){
		if(begainHeight != endHeight){
			rollBackScroller.startScroll(0, begainHeight, 0, endHeight - begainHeight, ROLLBACK_DURATION);
			invalidate();
		}
	}
	
	/**
	 * 尝试回滚下拉刷新列表头
	 */
	private void tryRollbackPulldownRefreshListHeader(int begainHeight){
		tryRollbackPulldownRefreshListHeader(begainHeight, pulldownRefershListHeader.getRollbackFinalHeight());
	}
	
	/**
	 * 尝试回滚下拉刷新列表头
	 */
	private void tryRollbackPulldownRefreshListHeader(){
		tryRollbackPulldownRefreshListHeader(pulldownRefershListHeader.getHeight());
	}
	
	/**
	 * 完成刷新
	 */
	public void finishRefresh(){
		if(pulldownRefershListHeader != null){
			pulldownRefershListHeader.intoLoadingToNormalState();
			//如果当前位置在列表的顶部，就回滚，否则直接更改为最小高度
			if(getFirstVisiblePosition() == 0){
				//如果下拉刷新列表头的顶部没有跟列表的顶部正好对齐
				if((pulldownRefershListHeader.getTop() + getPaddingTop()) == getTop()){
					tryRollbackPulldownRefreshListHeader();	//回滚下拉刷新列表头
				}else{
					int begainHeight = (pulldownRefershListHeader.getContentHeight() + pulldownRefershListHeader.getTop()) - getPaddingTop();//因为当前下拉刷新列表头只显示了一部分，所以开始高度就是显示出来的那部分的高度
					pulldownRefershListHeader.updateHeight(begainHeight);//先将下拉刷新列表头的高度设为刚刚计算出来的开始高度
					setSelection(0);//定位到顶部
					tryRollbackPulldownRefreshListHeader(begainHeight);	//回滚下拉刷新列表头
				}
			}else{
				pulldownRefershListHeader.updateHeight(pulldownRefershListHeader.getRollbackFinalHeight());
				invalidate();
			}
		}
	}
	
	/**
	 * 开启刷新
	 */
	public void startRefresh(){
		if(pulldownRefershListHeader != null && pulldownRefershListHeader.getState() == State.NORMAL && onRefreshListener != null){
			setSelection(0);
			pulldownRefershListHeader.setState(State.NORMAL_TO_LOADING);
			tryRollbackPulldownRefreshListHeader(pulldownRefershListHeader.getHeight(), pulldownRefershListHeader.getContentHeight());
			onRefreshListener.onRefresh();
		}
	}
	
	@Override
	public void computeScroll() {
		if (pulldownRefershListHeader != null) {	//如果有滚动的对象
			if(rollBackScroller.computeScrollOffset()){
				pulldownRefershListHeader.updateHeight(rollBackScroller.getCurrY());
			}
		}
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		//添加下拉刷新列表头
		if(pulldownRefershListHeader != null){
			addHeaderView(pulldownRefershListHeader);
		}
		super.setAdapter(adapter);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if(onScrollListener != null){
			onScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		if(onScrollListener != null){
			onScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
		}
	}

	@Override
	public boolean onDown(MotionEvent e) {
		if(rollBackScroller.computeScrollOffset()){
			rollBackScroller.abortAnimation();
		}
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return false;
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		pulldown = distanceY < 0;	//记录拉的方向

		//如果当前在第一行并且开启了下拉刷新模式
		if(getFirstVisiblePosition() == 0){
			if(pulldown){	//如果是下拉，直接更新下拉刷新列表头的高度
				pulldownRefershListHeader.updateHeightOffset((int) distanceY);
			}else{	//如果是上拉的话，如果当前下拉刷新列表头的高度大于其回滚的最终高度，就更新下拉刷新列表头的高度
				if(pulldownRefershListHeader.getHeight() > pulldownRefershListHeader.getRollbackFinalHeight()){
					pulldownRefershListHeader.updateHeightOffset((int) distanceY);
					setSelection(0);
				}
			}
			if(!pulldown){
			}
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		return false;
	}
	
	/**
	 * @return the onRefreshListener
	 */
	public OnRefreshListener getOnRefreshListener() {
		return onRefreshListener;
	}

	/**
	 * @param onRefreshListener the onRefreshListener to set
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	public interface OnRefreshListener{
		public void onRefresh();
	}

	/**
	 * @return the pulldownRefershListHeader
	 */
	public BasePulldownRefershListHeader getPulldownRefershListHeader() {
		return pulldownRefershListHeader;
	}

	/**
	 * @param pulldownRefershListHeader the pulldownRefershListHeader to set
	 */
	public void setPulldownRefershListHeader(
			BasePulldownRefershListHeader pulldownRefershListHeader) {
		this.pulldownRefershListHeader = pulldownRefershListHeader;
	}
}