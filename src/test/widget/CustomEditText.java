package test.widget;

import me.xiaopan.androidlibrary.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class CustomEditText extends EditText {
	private boolean init;
	private float nameX;
	private float nameHeight;
	private float fontMetricsBottom;
	private String name;
	private Paint namePaint;
	private Drawable rightDrawable;
	
	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public CustomEditText(Context context) {
		super(context);
		init(null);
	}
	
	private void init(AttributeSet attrs){
		if(attrs != null){
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomEditText);
			name = typedArray.getString(R.styleable.CustomEditText_editName);
			typedArray.recycle();
		}
		
		//获取右边的图片
		rightDrawable = getCompoundDrawables()[2];
		if(rightDrawable != null){
			//添加内容改变监听器，当文本编辑器的内容为空时不显示清除图标，反之当不为空是显示清除图标
			addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					if (TextUtils.isEmpty(s)) {
						setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
					} else {
						setCompoundDrawablesWithIntrinsicBounds(null, null, rightDrawable, null);
					}
				}
			});
			
			//当触摸了文本编辑器的时候，会判断是否触摸到了清除图标上，是的话就会尝试清除文本编辑器的内容
			setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					boolean result = false;
					switch (event.getAction()) {
						case MotionEvent.ACTION_UP:
							int curX = (int) event.getX();//转换触摸位置的X坐标
							int curY = (int) event.getY();//转换触摸位置的Y坐标
							int rightDrawableTop = (getHeight() - rightDrawable.getIntrinsicHeight()) / 2;//计算清除图标的Top值
							//如果点击范围正好在清除图标上并且文本编辑器的内容不为空
							if (curX > v.getWidth() - rightDrawable.getIntrinsicWidth() && curY >= rightDrawableTop && curY <= getHeight() - rightDrawableTop  && !TextUtils.isEmpty(getText())) {
								setText("");
								result = true;
							}	
						break;
					}
					return result;
				}
			});
			
			setText(getText().toString());
		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//如果尚未初始化并且名字不为null也不为空就执行初始化
		if(!init && name != null && !"".equals(name)){
			init = true;
			nameX = getPaddingLeft();//名字的X坐标就是文本编辑器的内边距
			namePaint = new Paint();
			namePaint.setColor(getContext().getResources().getColor(R.color.base_gray_dark));
			namePaint.setTextSize(getTextSize());

			FontMetrics fontMetrics = namePaint.getFontMetrics(); 
			nameHeight = fontMetrics.bottom - fontMetrics.top; //计算字体高度
			fontMetricsBottom = fontMetrics.bottom;//记录底边距
			
			setPadding((int) (getPaddingLeft() + namePaint.measureText(name)), getPaddingTop(), getPaddingRight(), getPaddingBottom());//因为要在左边绘制名字，所以文本编辑器的内边距要在原先的基础上再加上名字的宽度
		}
		//如果已经初始化完毕，那么接下来就要把名字画到画布上了
		if(init){
			//在画布上绘制名字（垂直居中），因为文本编辑器的高度是会因内容的多少而变化的所以名字的Y坐标也要动态的计算
			canvas.drawText(name, nameX, (getHeight() - (getHeight() - nameHeight) / 2 - fontMetricsBottom), namePaint);
		}
		
		super.onDraw(canvas);
	}
}