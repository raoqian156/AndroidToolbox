package me.xiaopan.easy.android.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import me.xiaopan.easy.java.util.IOUtils;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Assets文件操作工具箱
 */
public class AssetsUtils {
	
	/**
	 * 读取给定文件名的文件的内容并转换成字符串
	 * @param context 上下文
	 * @param fileName 文件名
	 * @param charset 转换编码
	 * @return
	 */
	public static final String getString(Context context, String fileName, Charset charset){
		InputStream inputStream = null;
		try {
			inputStream = context.getAssets().open(fileName);
			String string = new String(IOUtils.read(inputStream), charset);
			inputStream.close();
			return string;
		} catch (IOException e) {
			e.printStackTrace();
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return null;
		}
	}
	
	/**
	 * 读取给定文件名的文件的内容并转换成字符串
	 * @param context 上下文
	 * @param fileName 文件名
	 * @return
	 */
	public static final String getString(Context context, String fileName){
		return getString(context, fileName, Charset.defaultCharset());
	}
	
	/**
	 * 获取位图
	 * @param context 上下文
	 * @param fileName 文件名称
	 * @param outPadding 输出位图的内边距
	 * @param options 加载选项
	 * @return
	 */
	public static final Bitmap getBitmap(Context context, String fileName, Rect outPadding, BitmapFactory.Options options){
		try {
			InputStream inputStream = context.getAssets().open(fileName);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, outPadding, options);
			inputStream.close();
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取位图
	 * @param context 上下文
	 * @param fileName 文件名称
	 * @param outPadding 输出位图的内边距
	 * @return
	 */
	public static final Bitmap getBitmap(Context context, String fileName, Rect outPadding){
		return getBitmap(context, fileName, outPadding, null);
	}
	
	/**
	 * 获取位图
	 * @param context 上下文
	 * @param fileName 文件名称
	 * @param options 加载选项
	 * @return
	 */
	public static final Bitmap getBitmap(Context context, String fileName, BitmapFactory.Options options){
		return getBitmap(context, fileName, null, options);
	}
	
	/**
	 * 获取位图
	 * @param context 上下文
	 * @param fileName 文件名称
	 * @return
	 */
	public static final Bitmap getBitmap(Context context, String fileName){
		return getBitmap(context, fileName, null, null);
	}
}