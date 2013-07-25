package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.util.Colors;
import me.xiaopan.easyandroid.widget.SlideTitleViewPager;
import me.xiaopan.easyandroid.widget.ViewPagerAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 带滑动标题的ViewPager
 */
public class SlideTitleViewPagerActivity extends Activity {
	private SlideTitleViewPager slideTitlebar;
	private boolean more;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_slide_title_view_pager);
		slideTitlebar = (SlideTitleViewPager) findViewById(R.id.slideTitlePager);
		
		/* 初始化标题 */
		List<View> tabs = new ArrayList<View>();
		tabs.add(createTitle("游戏"));
		tabs.add(createTitle("工具"));
		tabs.add(createTitle("学习"));
		tabs.add(createTitle("美化"));
		tabs.add(createTitle("图书"));
		tabs.add(createTitle("体育"));
		tabs.add(createTitle("机械化"));
		tabs.add(createTitle("医学"));
		tabs.add(createTitle("艺术"));
		tabs.add(createTitle("地理"));
		slideTitlebar.setTitles(tabs);
		
		/* 初始化内容 */
		List<View> views = new ArrayList<View>();
		views.add(getContentView(Colors.SKYBLUE));
		views.add(getContentView(Colors.CHOCOLATE));
		views.add(getContentView(Colors.CYAN));
		views.add(getContentView(Colors.FUCHSIA));
		views.add(getContentView(Colors.GOLD));
		views.add(getContentView(Colors.BLUE));
		views.add(getContentView(Colors.GRAY));
		views.add(getContentView(Colors.GREEN));
		views.add(getContentView(Colors.RED));
		views.add(getContentView(Colors.YELLOW));
		slideTitlebar.getViewPager().setAdapter(new ViewPagerAdapter(views));
	}
	
	private TextView createTitle(String title){
		TextView textView = (TextView) LayoutInflater.from(getBaseContext()).inflate(R.layout.text_slide_title, null);
		textView.setText(title);
		textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
		return textView;
	}
	
	private View getContentView(int color){
		View view = new View(getBaseContext());
		view.setBackgroundColor(color);
		return view;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.slide_title_view_pager, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case R.id.menu_more: 
			if(more){
				/* 初始化标题 */
				List<View> tabs = new ArrayList<View>();
				tabs.add(createTitle("游戏"));
				tabs.add(createTitle("工具"));
				tabs.add(createTitle("天涯海角"));
				tabs.add(createTitle("学习"));
				tabs.add(createTitle("美化"));
				slideTitlebar.setTitles(tabs);
				
				/* 初始化内容 */
				List<View> views = new ArrayList<View>();
				views.add(getContentView(Colors.SKYBLUE));
				views.add(getContentView(Colors.CHOCOLATE));
				views.add(getContentView(Colors.CYAN));
				views.add(getContentView(Colors.FUCHSIA));
				views.add(getContentView(Colors.GOLD));
				slideTitlebar.getViewPager().setAdapter(new ViewPagerAdapter(views));
			}else{
				/* 初始化标题 */
				List<View> tabs = new ArrayList<View>();
				tabs.add(createTitle("游戏"));
				tabs.add(createTitle("工具"));
				tabs.add(createTitle("学习"));
				tabs.add(createTitle("美化"));
				tabs.add(createTitle("图书"));
				tabs.add(createTitle("体育"));
				tabs.add(createTitle("机械化"));
				tabs.add(createTitle("医学"));
				tabs.add(createTitle("艺术"));
				tabs.add(createTitle("地理"));
				slideTitlebar.setTitles(tabs);
				
				/* 初始化内容 */
				List<View> views = new ArrayList<View>();
				views.add(getContentView(Colors.SKYBLUE));
				views.add(getContentView(Colors.CHOCOLATE));
				views.add(getContentView(Colors.CYAN));
				views.add(getContentView(Colors.FUCHSIA));
				views.add(getContentView(Colors.GOLD));
				views.add(getContentView(Colors.BLUE));
				views.add(getContentView(Colors.GRAY));
				views.add(getContentView(Colors.GREEN));
				views.add(getContentView(Colors.RED));
				views.add(getContentView(Colors.YELLOW));
				slideTitlebar.getViewPager().setAdapter(new ViewPagerAdapter(views));
			}
			more = !more;
			item.setTitle(more?"变多":"变少");
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}