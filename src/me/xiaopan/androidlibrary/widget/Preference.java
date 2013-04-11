package me.xiaopan.androidlibrary.widget;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.Colors;
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
	private static final int TYPE_NONE = 1;
	private static final int TYPE_NEXT = 2;
	private static final int TYPE_TOGGLE = 3;
	private TextView titleText;
	private TextView space;
	private TextView subtitleText;
	private SlidingToggleButton slidingToggleButton;
	private boolean init;
	private boolean clickSwitchToggleState = true;
	private OnClickListener onNextButtonClickListener;
	private OnClickListener onPreferenceClickListener;
	private int type;

	public Preference(Context context, AttributeSet attrs) {
		super(context, attrs);
		setGravity(Gravity.CENTER_VERTICAL);
		TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.Preference);
		
		LinearLayout linearLayout = new LinearLayout(getContext());
		linearLayout.setOrientation(LinearLayout.VERTICAL);
		linearLayout.setGravity(Gravity.CENTER_VERTICAL);
		
		//标题
		titleText = new TextView(getContext());
		titleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_default));
		titleText.setTextColor(getContext().getResources().getColor(R.color.base_black));
		titleText.setSingleLine();
		titleText.setEllipsize(TruncateAt.MARQUEE);
		titleText.setText(typedArray.getString(R.styleable.Preference_title));
		linearLayout.addView(titleText);
		
		//间隔
		space = new TextView(getContext());
		linearLayout.addView(space, new LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 7));
		
		//副标题
		subtitleText = new TextView(context);
		subtitleText.setTextSize(TypedValue.COMPLEX_UNIT_PX, getContext().getResources().getDimension(R.dimen.base_textSize_littleSmall));
		subtitleText.setTextColor(getContext().getResources().getColor(R.color.base_gray_dark));
		subtitleText.setSingleLine();
		subtitleText.setEllipsize(TruncateAt.END);
		subtitleText.setText(typedArray.getString(R.styleable.Preference_subtitle));
		linearLayout.addView(subtitleText);
		referesh();
		
		addView(linearLayout, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.FILL_PARENT, 1));
		
		switch(type = typedArray.getInt(R.styleable.Preference_type, TYPE_NONE)){
			case TYPE_TOGGLE : 
				addView(slidingToggleButton = new SlidingToggleButton(getContext()));
				break;
			case TYPE_NEXT : 
				ImageButton nextImageButton = new ImageButton(getContext());
				nextImageButton.setBackgroundColor(Colors.TRANSPARENT);
				nextImageButton.setImageResource(R.drawable.selector_btn_preference_next);
				nextImageButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if(getOnNextButtonClickListener() != null){
							getOnNextButtonClickListener().onClick(v);
						}
					}
				});
				addView(nextImageButton, new LinearLayout.LayoutParams(50, LinearLayout.LayoutParams.WRAP_CONTENT));
				break;
			default : 
				//箭头
				ImageView arrowImage = new ImageView(getContext());
				arrowImage.setImageResource(R.drawable.ic_arrow_right);
				addView(arrowImage);
				break;
		}
		
		typedArray.recycle();
		
		//设置点击监听器
		init = true;
		setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(type == TYPE_TOGGLE && isClickSwitchToggleState() && slidingToggleButton != null){
					slidingToggleButton.switchState();
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
	
	public void setTitle(String title){
		titleText.setText(title);
	}
	
	public void setSubtitle(String intro){
		subtitleText.setText(intro);
		referesh();
	}
	
	private void referesh(){
		//刷新副标题
		if(StringUtils.isNotNullAndEmpty((String) subtitleText.getText())){
			subtitleText.setVisibility(View.VISIBLE);
			space.setVisibility(View.VISIBLE);
		}else{
			subtitleText.setVisibility(View.GONE);
			space.setVisibility(View.GONE);
		}
	}

	public OnClickListener getOnNextButtonClickListener() {
		return onNextButtonClickListener;
	}

	public void setOnNextButtonClickListener(
			OnClickListener onNextButtonClickListener) {
		this.onNextButtonClickListener = onNextButtonClickListener;
	}

	public boolean isClickSwitchToggleState() {
		return clickSwitchToggleState;
	}

	public void setClickSwitchToggleState(boolean clickSwitchToggleState) {
		this.clickSwitchToggleState = clickSwitchToggleState;
	}
}