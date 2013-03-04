package me.xiaopan.androidlibrary.util;

import me.xiaopan.javalibrary.util.ClassUtils;
import me.xiaopan.javalibrary.util.StringUtils;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 * 视图工具箱
 * @author xiaopan
 *
 */
public class ViewUtils {
	/**
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height));
		return linearLayout;
	}
	
	/**
	 * 
	 * 获取一个LinearLayout
	 * @param context 上下文
	 * @param orientation 流向
	 * @param width 宽
	 * @param height 高
	 * @param weight 权重
	 * @return LinearLayout
	 */
	public static LinearLayout createLinearLayout(Context context, int orientation, int width, int height, int weight){
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setOrientation(orientation);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(width, height, weight));
		return linearLayout;
	}
	
	/**
	 * 根据ListView的所有子项的高度设置其高度
	 * @param listView
	 */
	public static void setListViewHeightByAllChildrenViewHeight(ListView listView) {  
		ListAdapter listAdapter = listView.getAdapter();   
	    if (listAdapter != null) {  
	    	int totalHeight = 0;  
	    	for (int i = 0; i < listAdapter.getCount(); i++) {  
	    		View listItem = listAdapter.getView(i, null, listView);  
	    		listItem.measure(0, 0);  
	    		totalHeight += listItem.getMeasuredHeight();  
	    	}  
	    	
	    	ViewGroup.LayoutParams params = listView.getLayoutParams();  
	    	params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));  
	    	((MarginLayoutParams)params).setMargins(10, 10, 10, 10);
	    	listView.setLayoutParams(params); 
	    }  
    }
	
	/**
	 * 设置给定的对话框点击是否关闭
	 * @param alertDialog 给定的对话框
	 * @param close 点击是否关闭
	 */
	public static void setDialogClickClose(AlertDialog alertDialog, boolean close){
		ClassUtils.setField(alertDialog, "mShowing", close, true, true);
	}
	
	/**
	 * 给给定的视图注册长按提示监听器
	 * @param context 上下文
	 * @param view 给定的视图
	 * @param resourceId 提示内容的ID
	 */
	public static void setLongClickHintListener(final Context context, View view, final int resourceId){
		view.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				AndroidUtils.toastS(context, resourceId);
				return true;
			}
		});
	}
	
	/**
	 * 给给定的图片按钮注册切换图片监听器
	 * @param imageButton 给定的图片按钮
	 * @param notStateImageResourceId 没有状态时显示的图片
	 * @param pressStateImageResourceId 按下时显示的图片
	 */
	public static void setImageButtonSwitchImageListener(final ImageButton imageButton, final int notStateImageResourceId, final int pressStateImageResourceId){
		imageButton.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN : 
						imageButton.setImageResource(pressStateImageResourceId);
						break;
					case MotionEvent.ACTION_UP : 
						imageButton.setImageResource(notStateImageResourceId);
						break;
				}
				return false;
			}
		});
	}
	
	/**
	 * 设置给定视图的高度
	 * @param view 给定的视图
	 * @param newHeight 新的高度
	 */
	public static void setViewHeight(View view, int newHeight){
		ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		layoutParams.height = newHeight; 
		view.setLayoutParams(layoutParams);
	}
	
	/**
	 * 将给定视图的高度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewHeight(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.height += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 设置给定视图的宽度
	 * @param view 给定的视图
	 * @param newWidth 新的宽度
	 */
	public static void setViewWidth(View view, int newWidth){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width = newWidth; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 将给定视图的宽度增加一点
	 * @param view 给定的视图
	 * @param increasedAmount 增加多少
	 */
	public static void addViewWidth(View view, int increasedAmount){
		ViewGroup.LayoutParams headerLayoutParams = (ViewGroup.LayoutParams) view.getLayoutParams();  
		headerLayoutParams.width += increasedAmount; 
		view.setLayoutParams(headerLayoutParams);
	}
	
	/**
	 * 获取流布局的底部外边距
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutBottomMargin(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).bottomMargin;
	}
	
	/**
	 * 设置流布局的底部外边距
	 * @param linearLayout
	 * @param newBottomMargin
	 */
	public static void setLinearLayoutBottomMargin(LinearLayout linearLayout, int newBottomMargin) {
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)linearLayout.getLayoutParams();
		lp.bottomMargin = newBottomMargin;
		linearLayout.setLayoutParams(lp);
	}
	
	/**
	 * 获取流布局的高度
	 * @param linearLayout
	 * @return
	 */
	public static int getLinearLayoutHiehgt(LinearLayout linearLayout) {
		return ((LinearLayout.LayoutParams)linearLayout.getLayoutParams()).height;
	}
	
	/**
	 * 将一个编辑器和一个视图绑定，当编辑器的内容为空时隐藏视图，反之显示视图，并且点击视图的时候清空编辑器的内容，另外视图的显示或隐藏都会伴随透明度渐变动画
	 * @param editText
	 * @param clearView
	 */
	public static void editClearBindByAlpha(final EditText editText, final View clearView){
		editText.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				if(StringUtils.isNotNullAndEmpty(s.toString())){
					if(clearView.getVisibility() != View.VISIBLE && clearView.getTag() == null){
						clearView.setTag(false);
						AnimationUtils.visibleViewByAlpha(clearView, new Animation.AnimationListener() {
							public void onAnimationStart(Animation animation) {
								
							}
							
							@Override
							public void onAnimationRepeat(Animation animation) {
								
							}
							
							@Override
							public void onAnimationEnd(Animation animation) {
								clearView.setTag(null);
							}
						});
					}
				}else{
						clearView.clearAnimation();
						clearView.setTag(null);
						AnimationUtils.invisibleViewByAlpha(clearView);
				}
			}
		});
		
		clearView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				editText.setText("");
			}
		});
		
		editText.setText(editText.getEditableText().toString());
	}
}