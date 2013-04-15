package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.PullListView;
import me.xiaopan.androidlibrary.widget.ViewPagerAdapter;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

/**
 * 滑动选项卡
 * @author xiaopan
 */
public class SlideTabHostActivity extends MyBaseActivity{
	private ViewPager viewPager;
	private List<View> tabContentList;
	private List<Tab> tabList;
	private TextView slideBlock;
	private int lastTabIndex = 0;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_slide_tabhost);
		viewPager = (ViewPager) findViewById(R.id.pager_slideTab);
		tabList = new ArrayList<Tab>();
		tabList.add(new Tab(0, (TextView) findViewById(R.id.text_slideTab_title0), (TextView) findViewById(R.id.text_slideTab_slideBlock0)));
		tabList.add(new Tab(1, (TextView) findViewById(R.id.text_slideTab_title1), (TextView) findViewById(R.id.text_slideTab_slideBlock1)));
		tabList.add(new Tab(2, (TextView) findViewById(R.id.text_slideTab_title2), (TextView) findViewById(R.id.text_slideTab_slideBlock2)));
		slideBlock = tabList.get(0).getSlideBlock();
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		for(final Tab tab : tabList){
			tab.getTitle().setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					viewPager.setCurrentItem(Integer.valueOf(String.valueOf(tab.getIndex())));
				}
			});
		}
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int arg0) {
				//获取上一个tab和当前tab
				Tab lastTab = tabList.get(lastTabIndex);
				Tab currentTab = tabList.get(arg0);
				
				//恢复上一个tab的颜色为默认颜色并设置当前tab的颜色为焦点颜色
				lastTab.getTitle().setSelected(false);
				currentTab.getTitle().setSelected(true);
				
				//移动滑块
				TranslateAnimation translateAnimation = new TranslateAnimation(lastTab.getSlideBlock().getLeft(), currentTab.getSlideBlock().getLeft(), 0, 0);
				translateAnimation.setDuration(200);
				translateAnimation.setFillAfter(true);
				slideBlock.startAnimation(translateAnimation);
				
				//记录上一个tab
				lastTabIndex = arg0;
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
	}
	
	@Override
	public void onInitData(Bundle savedInstanceState) {
		tabContentList = new ArrayList<View>();
		tabContentList.add(createTabContent(0));
		tabContentList.add(createTabContent(1));
		tabContentList.add(createTabContent(2));
		viewPager.setAdapter(new ViewPagerAdapter(tabContentList));
		tabList.get(lastTabIndex).getTitle().setSelected(true);
	}
	
	private View createTabContent(int index){
		index++;
		List<String> contents = new ArrayList<String>(20);
		for(int w = 0; w < 20; w++){
			contents.add("这是选项卡 "+index+" 的第 "+w+" 条数据");
		}
		PullListView pullListView = (PullListView) getViewByLayout(R.layout.list);
		pullListView.openListHeaderReboundMode();
		pullListView.openListFooterReboundMode();
		pullListView.setAdapter(new SimpleAdapter(getBaseContext(), contents));
		return pullListView;
	}

	private class Tab{
		private int index;
		private TextView title;
		private TextView slideBlock;
		
		public Tab(int index, TextView title, TextView slideBlock){
			setIndex(index);
			setTitle(title);
			setSlideBlock(slideBlock);
		}
		
		public int getIndex() {
			return index;
		}
		
		public void setIndex(int index) {
			this.index = index;
		}
		
		public TextView getTitle() {
			return title;
		}
		
		public void setTitle(TextView title) {
			this.title = title;
		}
		
		public TextView getSlideBlock() {
			return slideBlock;
		}
		
		public void setSlideBlock(TextView slideBlock) {
			this.slideBlock = slideBlock;
		}
	}
}