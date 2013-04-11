package test.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.Colors;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton;
import me.xiaopan.androidlibrary.widget.BaseSlidingToggleButton.OnCheckedChanageListener;
import me.xiaopan.javalibrary.util.StringUtils;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Preference extends LinearLayout{
	/**
	 * 此类型会在选项的右边放置一个箭头
	 */
	public static final int TYPE_NONE = 1;
	/**
	 * 此类型会在选项的右边放置一个突起的按钮，按钮和视图本身都可以点击
	 */
	public static final int TYPE_NEXT = 2;
	/**
	 * 此类型会在选项的右边放置一个可以滑动切换选中状态的开关按钮
	 */
	public static final int TYPE_TOGGLE = 3;
	private TextView titleText;
	private TextView space;
	private TextView subtitleText;
	private ImageButton nextImageButton;
	private ImageView arrowImage;
	private BaseSlidingToggleButton slidingToggleButton;
	private boolean init;
	private boolean clickSwitchToggleState = true;
	private OnClickListener onNextButtonClickListener;
	private OnClickListener onPreferenceClickListener;
	private OnCheckedChanageListener onCheckedChanageListener;
	private int type;
	private boolean defaultChecked;

	public Preference(Context context) {
		super(context);
		init(null);
	}
	
	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		init(typedArray);
		typedArray.recycle();
	}
	
	private void init(TypedArray typedArray){
		setGravity(Gravity.CENTER_VERTICAL);
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		//标题
		titleText = new TextView(getContext());
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		titleText.setTextColor(getContext().getResources().getColor(R.color.base_black));
		titleText.setSingleLine();
		titleText.setEllipsize(TruncateAt.MARQUEE);
		if(typedArray != null){
			titleText.setText(typedArray.getString(R.styleable.Preference_title));
		}
		linearLayout.addView(titleText);
		
		//间隔
		space = new TextView(getContext());
		linearLayout.addView(space, new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 7));
		
		//副标题
		subtitleText = new TextView(getContext());
		subtitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_littleSmall));
		subtitleText.setTextColor(getContext().getResources().getColor(R.color.base_gray_dark));
		subtitleText.setSingleLine();
		subtitleText.setEllipsize(TruncateAt.END);
		if(typedArray != null){
			setSubtitle(typedArray.getString(R.styleable.Preference_subtitle));
		}else{
			setSubtitle(null);
		}
		linearLayout.addView(subtitleText);
		
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		if(typedArray != null){
			defaultChecked = typedArray.getBoolean(R.styleable.Preference_checked, defaultChecked);
		}
		
		//设置类型，会根据不同的类型在右边添加不同的视图
		if(typedArray != null){
			setType(typedArray.getInt(R.styleable.Preference_type, TYPE_NONE));
		}else{
			setType(TYPE_NONE);
		}
		
		//设置点击监听器
		init = true;
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(type == TYPE_TOGGLE && isClickSwitchToggleState() && slidingToggleButton != null){
					slidingToggleButton.toggle();
				}else{
					if(onPreferenceClickListener != null){
						onPreferenceClickListener.onClick(v);
					}
				}
			}
		});
	}
	
	@Override
	public void setOnClickListener(OnClickListener l) {
		if(init){
			init = false;
			super.setOnClickListener(l);
		}else{
			this.onPreferenceClickListener = l;
		}
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		switch(type){
			case TYPE_TOGGLE : 
				slidingToggleButton.setEnabled(enabled);
				break;
			case TYPE_NEXT : 
				nextImageButton.setEnabled(enabled);
				break;
			default : 
				arrowImage.setEnabled(enabled);
				break;
		}
	}

	/**
	 * 设置标题
	 * @param title 标题
	 */
	public void setTitle(String title){
		titleText.setText(title);
	}
	
	/**
	 * 设置副标题
	 * @param subtitle 副标题
	 */
	public void setSubtitle(String subtitle){
		subtitleText.setText(subtitle);
		//刷新副标题
		if(StringUtils.isNotNullAndEmpty((String) subtitleText.getText())){
			subtitleText.setVisibility(View.VISIBLE);
			space.setVisibility(View.VISIBLE);
		}else{
			subtitleText.setVisibility(View.GONE);
			space.setVisibility(View.GONE);
		}
	}

	/**
	 * 获取类型
	 * @return 类型，取值为Preference.TYPE_NONE、Preference.TYPE_NEXT、Preference.TYPE_TOGGLE之一
	 */
	public int getType() {
		return type;
	}

	/**
	 * 设置类型
	 * @param type 类型，取值为Preference.TYPE_NONE、Preference.TYPE_NEXT、Preference.TYPE_TOGGLE之一
	 */
	public void setType(int type) {
		if(this.type != type && (type == TYPE_NONE || type == TYPE_NEXT || type == TYPE_TOGGLE)){
			//删除旧的视图
			switch (this.type) {
				case TYPE_NONE: removeView(arrowImage); arrowImage = null; break;
				case TYPE_NEXT: removeView(nextImageButton); nextImageButton = null; break;
				case TYPE_TOGGLE: removeView(slidingToggleButton); slidingToggleButton = null; break;
			}
			
			//添加新的视图
			switch (type) {
				case TYPE_NONE : 
					//箭头
					arrowImage = new ImageView(getContext());
					arrowImage.setImageResource(R.drawable.ic_arrow_right);
					addView(arrowImage);
					break;
				case TYPE_NEXT : 
					nextImageButton = new ImageButton(getContext());
					nextImageButton.setBackgroundColor(Colors.TRANSPARENT);
					nextImageButton.setImageResource(R.drawable.selector_btn_preference_next);
					nextImageButton.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							if(onNextButtonClickListener != null){
								onNextButtonClickListener.onClick(v);
							}
						}
					});
					addView(nextImageButton, new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
					break;
				case TYPE_TOGGLE : 
					slidingToggleButton = new SlidingToggleButton(getContext());
					slidingToggleButton.setOnCheckedChanageListener(new OnCheckedChanageListener() {
						@Override
						public void onCheckedChanage(BaseSlidingToggleButton slidingToggleButton, boolean isOn) {
							if(onCheckedChanageListener != null){
								onCheckedChanageListener.onCheckedChanage(slidingToggleButton, isOn);
							}
						}
					});
					slidingToggleButton.setChecked(defaultChecked);
					addView(slidingToggleButton);
					break;
			}
			
			this.type = type;
		}
	}
	
	/**
	 * 判断是否选中
	 * @return 是否选中，只有当Type是toggle时才起作用
	 */
	public boolean isChecked(){
		return slidingToggleButton != null?slidingToggleButton.isChecked():false;
	}
	
	/**
	 * 设置是否选中
	 * @param isChecked 是否选中，只有当Type是toggle时才起作用
	 */
	public void setChecked(boolean isChecked){
		if(slidingToggleButton != null){
			slidingToggleButton.setChecked(isChecked);
		}
	}

	/**
	 * 设置下一步按钮的点击监听器
	 * @param onNextButtonClickListener 下一步按钮的点击监听器
	 */
	public void setOnNextButtonClickListener(OnClickListener onNextButtonClickListener) {
		this.onNextButtonClickListener = onNextButtonClickListener;
	}

	/**
	 * 判断当类型为toggle的时候点击当前视图是否切换Toggle的选中状态
	 * @return 当类型为toggle的时候点击当前视图是否切换Toggle的选中状态
	 */
	public boolean isClickSwitchToggleState() {
		return clickSwitchToggleState;
	}

	/**
	 * 设置当类型为toggle的时候点击当前视图是否切换Toggle的选中状态
	 * @param clickSwitchToggleState 当类型为toggle的时候点击当前视图是否切换Toggle的选中状态
	 */
	public void setClickSwitchToggleState(boolean clickSwitchToggleState) {
		this.clickSwitchToggleState = clickSwitchToggleState;
	}

	/**
	 * 设置选中状态改变监听器
	 * @param onCheckedChanageListener
	 */
	public void setOnCheckedChanageListener( OnCheckedChanageListener onCheckedChanageListener) {
		this.onCheckedChanageListener = onCheckedChanageListener;
	}
}