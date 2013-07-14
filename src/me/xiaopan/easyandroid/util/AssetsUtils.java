package me.xiaopan.easyandroid.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import me.xiaopan.easyjava.util.IOUtils;
import android.content.Context;

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
	public static final String readString(Context context, String fileName, Charset charset){
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
	public static final String readString(Context context, String fileName){
		return readString(context, fileName, Charset.defaultCharset());
	}
}