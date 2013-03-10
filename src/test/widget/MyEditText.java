package test.widget;

import me.xiaopan.androidlibrary.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

public class MyEditText extends EditText {
	private boolean me;
	private boolean hasEditName;//是否拥有编辑器名称
	private float editNameX;//编辑器名称的X坐标
	private float editNameHeight;//编辑器名称的高度
	private float fontMetricsBottom;
	private String editName;//编辑器名称
	private Paint editNamePaint;//用来绘制编辑器名称的画笔
	private Drawable clearDrawable;//清除图标
	private MyTextWatcher myTextWatcher;
	private MyOnTouchListener myOnTouchListener;
	private OnTouchListener onTouchListener;
	private Drawable nameDrawable;
	
	public MyEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}

	public MyEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(attrs);
	}

	public MyEditText(Context context) {
		super(context);
		init(null);
	}
	
	/**
	 * 初始化
	 * @param attrs
	 */
	private void init(AttributeSet attrs){
		//获取编辑器名称
		if(attrs != null){
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.MyEditText);
			editName = typedArray.getString(R.styleable.MyEditText_editName);
			typedArray.recycle();
		}
		//尝试初始化编辑器名称
		tryInitEditName();
		
		//初始化文本改变监听器和触摸监听器
		myTextWatcher = new MyTextWatcher();
		myOnTouchListener = new MyOnTouchListener();
		me = true;
		setOnTouchListener(myOnTouchListener);
		
		//获取清除图标
		clearDrawable = getCompoundDrawables()[2];
		//尝试初始化清除图标
		tryInitClearDrawable();	
	}
	
	/**
	 * 尝试初始化编辑器名称
	 */
	private void tryInitEditName(){
		//名字不为null也不为空就执行初始化
		if(hasEditName = (editName != null && !"".equals(editName))){
			editNameX = getPaddingLeft();//名字的X坐标就是文本编辑器的内边距
			editNamePaint = new Paint();
			editNamePaint.setColor(getContext().getResources().getColor(R.color.base_gray_dark));
			editNamePaint.setTextSize(getTextSize());

			FontMetrics fontMetrics = editNamePaint.getFontMetrics(); 
			editNameHeight = fontMetrics.bottom - fontMetrics.top; //计算字体高度
			fontMetricsBottom = fontMetrics.bottom;//记录底边距
			
//			setPadding((int) (getPaddingLeft() + editNamePaint.measureText(editName)), getPaddingTop(), getPaddingRight(), getPaddingBottom());//因为要在左边绘制名字，所以文本编辑器的内边距要在原先的基础上再加上名字的宽度
		
//			Bitmap bitmap = Bitmap.createBitmap(50, 50, Config.ARGB_8888);
//			Canvas canvas = new Canvas(bitmap);
//			canvas.drawText(editName, 0, 0, editNamePaint);
//			canvas.save();
//			nameDrawable = new BitmapDrawable(bitmap);
			nameDrawable = getBitmapDrawable(getContext(), editName, getTextSize());
			Drawable[] drawables = getCompoundDrawables();
			setCompoundDrawablesWithIntrinsicBounds(nameDrawable, drawables[1], drawables[2], drawables[3]);
//			setBackgroundDrawable(nameDrawable);
		}
	}
	
	public static BitmapDrawable getBitmapDrawable(Context context, String text, float textSize){
		Paint paint = new Paint();
		paint.setColor(context.getResources().getColor(R.color.base_gray_dark));
		paint.setTextSize(textSize);
		paint.setAntiAlias(true);//去除锯齿
		paint.setFilterBitmap(true);//对文字进行滤波处理，增强绘制效果
		int width = (int) paint.measureText(text);
		FontMetrics fontMetrics = paint.getFontMetrics(); 
		int height = (int) (fontMetrics.bottom - fontMetrics.top);
		
		float x = (width - getFontlength(paint, text))/2;
		float Y = (height - getFontHeight(paint))/2+getFontLeading(paint);
		
		Log.i("测试", "文字大小="+textSize+", 宽="+width+", 高="+height);

		Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		canvas.drawText(text, x, Y, paint);
		canvas.save();
		return new BitmapDrawable(context.getResources(), bitmap);
	}
	
	/**  
     * @return 返回指定笔和指定字符串的长度  
     */  
    public static float getFontlength(Paint paint, String str) {  
        return paint.measureText(str);  
    }
    
    /**  
     * @return 返回指定笔的文字高度  
     */  
    public static float getFontHeight(Paint paint)  {    
        FontMetrics fm = paint.getFontMetrics();   
        return fm.descent - fm.ascent;    
    }   
    /**  
     * @return 返回指定笔离文字顶部的基准距离  
     */  
    public static float getFontLeading(Paint paint)  {    
        FontMetrics fm = paint.getFontMetrics();   
        return fm.leading- fm.ascent;    
    }
	
	/**
	 * 尝试初始化清除图标
	 */
	private void tryInitClearDrawable(){
//		if(clearDrawable != null){
//			//添加文本改变监听器
//			addTextChangedListener(myTextWatcher);
//			//刺激一下刚刚添加的内容改变监听器，这样做的效果是当在布局中设置的有默认值时，会立即显示出清除图标
//			setText(getText().toString());
//		}else{
//			if(myTextWatcher != null){
//				removeTextChangedListener(myTextWatcher);
//			}
//		}
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
//		//如果拥有编辑器名称
//		if(hasEditName){
//			//在画布上垂直居中的位置绘制编辑器名称（因为文本编辑器的高度会因内容的多少而变化，所以Y坐标也要动态的计算）
//			canvas.drawText(editName, editNameX, (getHeight() - (getHeight() - editNameHeight) / 2 - fontMetricsBottom), editNamePaint);
//		}
		
//		Log.i("测试", "X="+editNameX+", width="+getWidth());
		
		super.onDraw(canvas);
	}

	/**
	 * 内容改变监听器，当文本编辑器的内容为空时不显示清除图标，反之显示清除图标
	 */
	private class MyTextWatcher implements TextWatcher{
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {}
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
		@Override
		public void afterTextChanged(Editable s) {
			if(TextUtils.isEmpty(s)) {
				setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
			} else {
				setCompoundDrawablesWithIntrinsicBounds(null, null, clearDrawable, null);
			}
		}
	}
	
	/**
	 * 当触摸了文本编辑器的时候，会判断是否触摸到了清除图标上，是的话就会尝试清除文本编辑器的内容
	 */
	private class MyOnTouchListener implements OnTouchListener{
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			boolean result = false;
			if(clearDrawable != null){
				switch (event.getAction()) {
				case MotionEvent.ACTION_UP:
					int curX = (int) event.getX();//转换触摸位置的X坐标
					int curY = (int) event.getY();//转换触摸位置的Y坐标
					int rightDrawableTop = (getHeight() - clearDrawable.getIntrinsicHeight()) / 2;//计算清除图标的Top值
					//如果点击范围正好在清除图标上并且文本编辑器的内容不为空
					if(curX >= getWidth() - (clearDrawable.getIntrinsicWidth() + getPaddingRight()) && 
							curX <= getWidth() - getPaddingRight() && 
							curY >= rightDrawableTop && 
							curY <= getHeight() - rightDrawableTop  && 
							!TextUtils.isEmpty(getText())) {
						setText("");
						result = true;
					}	
					break;
				}
			}
			if(!result && onTouchListener != null){
				result = onTouchListener.onTouch(v, event);
			}
			return result;
		}
	}
	
	@Override
	public void setOnTouchListener(OnTouchListener l) {
		if(me){
			super.setOnTouchListener(l);
			me = false;
		}else{
			this.onTouchListener = l;
		}
	}

	public String getEditName() {
		return editName;
	}

	public void setEditName(String editName) {
		this.editName = editName;
		tryInitEditName();
		invalidate();
	}

	public Drawable getClearDrawable() {
		return clearDrawable;
	}

	public void setClearDrawable(Drawable clearDrawable) {
		this.clearDrawable = clearDrawable;
		tryInitClearDrawable();
		invalidate();
	}
}