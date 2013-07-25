package me.xiaopan.easyandroid.widget;

import java.util.List;

import me.xiaopan.easyandroid.util.Colors;
import me.xiaopan.easyandroid.util.ViewUtils;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 带有滑动标题的ViewPager，你只需调用setTabs
 */
public class SlideTitleViewPager extends LinearLayout implements OnPageChangeListener{
	private HorizontalScrollView horizontalTabsScrollView;
	private LinearLayout titleViewsLayout;
	private ViewPager viewPager;
	private int center;
	private List<View> titles;
	private View lastSelectedTab;
	private OnPageChangeListener onPageChangeListener;
	
	public SlideTitleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	private void init(){
		setOrientation(LinearLayout.VERTICAL);
		
		/* 初始化标题 */
		horizontalTabsScrollView = onCreateHorizontalScrollView();
		horizontalTabsScrollView.setHorizontalScrollBarEnabled(false);	//隐藏横向滑动提示条
		horizontalTabsScrollView.addView(titleViewsLayout = onTitleViewsLayout(), new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));	//添加标题容器
		addView(horizontalTabsScrollView, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		
		/* 初始化内容 */
		viewPager = onCreateViewPager();
		viewPager.setOnPageChangeListener(this);
		addView(viewPager, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, 1));
	}
	
	protected ViewPager onCreateViewPager(){
		return new ViewPager(getContext());
	}
	
	protected HorizontalScrollView onCreateHorizontalScrollView(){
		HorizontalScrollView horizontalScrollView = new HorizontalScrollView(getContext());
		horizontalScrollView.setBackgroundColor(Colors.WHITE);
		return horizontalScrollView;
	}
	
	protected LinearLayout onTitleViewsLayout(){
		return new LinearLayout(getContext());
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		/* 如果当前的标题内容在横向上不能够将SlideTitlebar充满，那么就试图更改所有标题的宽度，来充满SlideTitlebar */
		if(ViewUtils.getMeasureWidth(titleViewsLayout) < r - l){
			int totalWidth = r - l;
			int averageWidth = totalWidth/titleViewsLayout.getChildCount();	//初始化平均宽度
			View titleView;
			
			/* 先计算出剩余可平分的宽度 */
			int currentTitleViewWidth;
			int number = 0;	//记录减去的个数，待会儿算平均值的时候要减去此数
			for(int w = 0; w < titleViewsLayout.getChildCount(); w++){
				titleView = titleViewsLayout.getChildAt(w);
				ViewUtils.measure(titleView);	//测量一下
				currentTitleViewWidth = titleView.getMeasuredWidth();
				if(currentTitleViewWidth > averageWidth){	//如果当前视图的宽度大于平均宽度，就从总宽度中减去当前视图的宽度
					totalWidth -= currentTitleViewWidth;
					number++;
				}
			}
			int newAverageWidth = totalWidth/(titleViewsLayout.getChildCount() - number);	//重新计算宽度
			
			//重新设置宽度小于平均宽度的视图的宽度为新的平均宽度
			for(int w = 0; w < titleViewsLayout.getChildCount(); w++){
				titleView = titleViewsLayout.getChildAt(w);
				if(titleView.getMeasuredWidth() < averageWidth){
					ViewGroup.LayoutParams layoutParams = titleView.getLayoutParams();
					layoutParams.width = newAverageWidth;
					titleView.setLayoutParams(layoutParams);
				}
			}
		}
		
		center = (r + l) / 2;
		super.onLayout(changed, l, t, r, b);
	}
	
	@Override
	public void onPageSelected(int arg0) {
		View titleView = titleViewsLayout.getChildAt(arg0);
		lastSelectedTab.setSelected(false);	//取消上一次的选中的TabView的选中状态
		titleView.setSelected(true);	//将当前点击的TabView设为选中状态
		lastSelectedTab = titleView;	//更新上一次选中的TabView
		horizontalTabsScrollView.smoothScrollTo((titleView.getLeft() + titleView.getRight())/2 - center, titleView.getTop());
	}
	
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		if(onPageChangeListener != null){
			onPageChangeListener.onPageScrolled(arg0, arg1, arg2);
		}
	}
	
	@Override
	public void onPageScrollStateChanged(int arg0) {
		if(onPageChangeListener != null){
			onPageChangeListener.onPageScrollStateChanged(arg0);
		}
	}

	public List<View> getTitleViews() {
		return titles;
	}

	public void setTitles(List<View> titleViews) {
		this.titles = titleViews;
		titleViewsLayout.removeAllViews();
		View titleView = null;
		for(int w = 0; w < titleViews.size(); w++){
			titleView = titleViews.get(w);
			titleView.setTag(w);
			final int tabViewPosition = w;
			titleView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					viewPager.setCurrentItem(tabViewPosition, true);	//更改ViewPager的选中项
				}
			});
			titleViewsLayout.addView(titleView);
			if(w == 0){
				lastSelectedTab = titleView;
				titleView.setSelected(true);
			}
		}
	}

	public ViewPager getViewPager() {
		return viewPager;
	}

	public void setViewPager(ViewPager viewPager) {
		this.viewPager = viewPager;
	}
	
	public OnPageChangeListener getOnPageChangeListener() {
		return onPageChangeListener;
	}

	public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
		this.onPageChangeListener = onPageChangeListener;
	}
}