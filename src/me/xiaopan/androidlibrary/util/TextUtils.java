package me.xiaopan.androidlibrary.util;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Paint.FontMetrics;

/**
 * 文本工具箱
 * @author xiaopan
 *
 */
public class TextUtils {
	/**
	 * 计算文本的宽度
	 * @param text 要计算的文本
	 * @param textSize 文本大小
	 * @return 文本的宽度
	 */
	public static float measureTextWidth(String text, float textSize){
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		return paint.measureText(text);
	}
	
	/**
	 * 计算文本的高度
	 * @param text 要计算的文本
	 * @param textSize 文本大小
	 * @return 文本的高度
	 */
	public static float measureTextHeight(String text, float textSize){
		Paint paint = new Paint();
		paint.setTextSize(textSize);
		FontMetrics fontMetrics = paint.getFontMetrics(); 
		return fontMetrics.bottom - fontMetrics.top;
	}
	
	/**
	 * 计算文本的高度
	 * @param paint 画笔
	 * @param text 要计算的文本
	 * @return 文本的高度
	 */
	public static float measureTextHeight(Paint paint, String text){
		FontMetrics fontMetrics = paint.getFontMetrics(); 
		return fontMetrics.bottom - fontMetrics.top;
	}
	
	/**
	 * 计算文本的宽度
	 * @param text 要计算的文本
	 * @param textSize 文本大小
	 * @return 文本的宽度
	 */
	public static int measureTextWidthByBounds(String text, float textSize){
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.width();
	}
	
	/**
	 * 计算文本的高度
	 * @param text 要计算的文本
	 * @param textSize 文本大小
	 * @return 文本的高度
	 */
	public static int measureTextHeightByBounds(String text, float textSize){
        Paint paint = new Paint();
        Rect bounds = new Rect();
        paint.setTextSize(textSize);
        paint.getTextBounds(text, 0, text.length(), bounds);
        return bounds.height();
	}
}