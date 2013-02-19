package test.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.Indicator;
import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 点状指示器
 * @author xiaopan
 */
public class PointIndicator extends Indicator{
	private int lastCheckedPosition;//上次选中的图标的位置
	private LinearLayout pointsLayout;
	
	public PointIndicator(Context context) {
		super(context);
	}
	
	@Override
	public void onInit(int size) {
		//先清空所有的视图
		removeAllViews();
		
		//再设置指示器充满其父视图
		setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		//设置父视图的布局方式为靠在底部
		setGravity(Gravity.BOTTOM);
		
		//再创建包括所有图标部分的布局
		pointsLayout = new LinearLayout(getContext());
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		pointsLayout.setLayoutParams(layoutParams);
		pointsLayout.setGravity(Gravity.CENTER);
		for(int w = 0; w < size; w++){//然后初始化所有的图标并将其放进存放图标的布局中
			ImageView iconImage = new ImageView(getContext());
			iconImage.setImageResource(R.drawable.selector_radio_play_indicator); 
			LinearLayout.LayoutParams iconParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			iconParams.setMargins(5, 5, 5, 5);//设置指示器内图标的外边距
			iconImage.setLayoutParams(iconParams);
			pointsLayout.addView(iconImage);
		}
		
		addView(pointsLayout);
	}

	@Override
	public void onItemSelected(int position) {
		//先将上一个取消
		((ImageView) (pointsLayout.getChildAt(lastCheckedPosition))).setSelected(false);
		//再将当前的选中
		((ImageView) (pointsLayout.getChildAt(position))).setSelected(true);
		//记录本次选中的
		lastCheckedPosition = position;
	}
}