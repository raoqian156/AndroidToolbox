package me.xiaopan.easy.android.util;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.util.TypedValue;

/**
 * 位图加载器
 */
public class BitmapLoader {
	/**
	 * 单张图片可以占用的最大内存，默认值为当前虚拟机最大可用内存的八分之一
	 */
	public static int maxMemory = (int) (Runtime.getRuntime().maxMemory()/8/4);
	
	/**
	 * 最小边长，默认为-1
	 */
	public static int minSlideLength = -1;
	
	/**
	 * 从字节数组中解码位图
	 * @param data
	 * @param offset
	 * @param length
	 * @param options
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data, int offset, int length, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, offset, length, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, offset, length, options);
	}
	
	/**
	 * 从字节数组中解码位图
	 * @param data
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data, int offset, int length){
		return decodeByteArray(data, offset, length, null);
	}
	
	/**
	 * 从字节数组中解码位图
	 * @param data
	 * @param options
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data, Options options){
		return decodeByteArray(data, 0, data.length, options);
	}
	
	/**
	 * 从字节数组中解码位图
	 * @param data
	 * @return
	 */
	public static Bitmap decodeByteArray(byte[] data){
		return decodeByteArray(data, 0, data.length);
	}
	
	/**
	 * 从文件中解码位图
	 * @param filePath
	 * @param options
	 * @return
	 */
	public static Bitmap decodeFile(String filePath, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}
	
	/**
	 * 从文件中解码位图
	 * @param filePath
	 * @return
	 */
	public static Bitmap decodeFile(String filePath){
		return decodeFile(filePath, null);
	}
	
	/**
	 * 从文件描述符中解码位图
	 * @param fd
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Bitmap decodeFileDescriptor(FileDescriptor fd, Rect outPadding, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, outPadding, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFileDescriptor(fd, outPadding, options);
	}
	
	/**
	 * 从文件描述符中解码位图
	 * @param fd
	 * @return
	 */
	public static Bitmap decodeFileDescriptor(FileDescriptor fd){
		return decodeFileDescriptor(fd, null, null);
	}
	
	/**
	 * 从资源文件中解码位图
	 * @param resource
	 * @param id
	 * @param options
	 * @return
	 */
	public static Bitmap decodeResource(Resources resource, int id, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resource, id, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(resource, id, options);
	}
	
	/**
	 * 从资源文件中解码位图
	 * @param resource
	 * @param id
	 * @return
	 */
	public static Bitmap decodeResource(Resources resource, int id){
		return decodeResource(resource, id, null);
	}
	
	/**
	 * 从资源文件流中解码位图
	 * @param resource
	 * @param value
	 * @param is
	 * @param pad
	 * @param options
	 * @return
	 */
	public static Bitmap decodeResourceStream(Resources resource, TypedValue value, InputStream is, Rect pad, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResourceStream(resource, value, is, pad, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResourceStream(resource, value, is, pad, options);
	}
	
	/**
	 * 从流中解码位图
	 * @param inputStream
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Bitmap decodeStream(InputStream inputStream, Rect outPadding, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, outPadding, options);
		options.inSampleSize = computeSampleSize(options, minSlideLength, maxMemory);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeStream(inputStream, outPadding, options);
	}
	
	/**
	 * 从流中解码位图
	 * @param inputStream
	 * @return
	 */
	public static Bitmap decodeStream(InputStream inputStream){
		return decodeStream(inputStream, null, null);
	}
	
	/**
	 * 从Assets中解码位图
	 * @param context
	 * @param fileName
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Bitmap decodeFromAssets(Context context, String fileName, Rect outPadding, Options options){
		Bitmap bitmap = null;
		InputStream inputStream = null;
		try {
			inputStream = context.getAssets().open(fileName);
			bitmap = decodeStream(inputStream, outPadding, options);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return bitmap;
	}
	
	/**
	 * 从Assets中解码位图
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap decodeFromAssets(Context context, String fileName){
		return decodeFromAssets(context, fileName, null, null);
	}
	
	/**
	 * 从字节数组中解码位图的尺寸
	 * @param data
	 * @param offset
	 * @param length
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromByteArray(byte[] data, int offset, int length, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, offset, length, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从字节数组中解码位图的尺寸
	 * @param data
	 * @param offset
	 * @param length
	 * @return
	 */
	public static Options decodeSizeFromByteArray(byte[] data, int offset, int length){
		return decodeSizeFromByteArray(data, offset, length, null);
	}
	
	/**
	 * 从文件中解码位图的尺寸
	 * @param filePath
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromFile(String filePath, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从文件中解码位图的尺寸
	 * @param filePath
	 * @return
	 */
	public static Options decodeSizeFromFile(String filePath){
		return decodeSizeFromFile(filePath, null);
	}
	
	/**
	 * 从文件描述符中解码位图的尺寸
	 * @param fd
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromFileDescriptor(FileDescriptor fd, Rect outPadding, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFileDescriptor(fd, outPadding, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从文件描述符中解码位图的尺寸
	 * @param fd
	 * @return
	 */
	public static Options decodeSizeFromFileDescriptor(FileDescriptor fd){
		return decodeSizeFromFileDescriptor(fd, null, null);
	}
	
	/**
	 * 从资源文件中解码位图的尺寸
	 * @param resource
	 * @param id
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromResource(Resources resource, int id, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(resource, id, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从资源文件中解码位图的尺寸
	 * @param resource
	 * @param id
	 * @return
	 */
	public static Options decodeSizeFromResource(Resources resource, int id){
		return decodeSizeFromResource(resource, id, null);
	}
	
	/**
	 * 从资源流中解码位图的尺寸
	 * @param resource
	 * @param value
	 * @param is
	 * @param pad
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromResourceStream(Resources resource, TypedValue value, InputStream is, Rect pad, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResourceStream(resource, value, is, pad, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从流中解码位图的尺寸
	 * @param inputStream
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromStream(InputStream inputStream, Rect outPadding, Options options){
		if(options == null){
			options = new Options();
		}
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeStream(inputStream, outPadding, options);
		options.inJustDecodeBounds = false;
		return options;
	}
	
	/**
	 * 从流中解码位图的尺寸
	 * @param inputStream
	 * @return
	 */
	public static Options decodeSizeFromStream(InputStream inputStream){
		return decodeSizeFromStream(inputStream, null, null);
	}
	
	/**
	 * 从Assets中解码位图的尺寸
	 * @param context
	 * @param fileName
	 * @param outPadding
	 * @param options
	 * @return
	 */
	public static Options decodeSizeFromAssest(Context context, String fileName, Rect outPadding, Options options){
		InputStream inputStream = null;
		try {
			if(options == null){
				options = new Options();
			}
			options.inJustDecodeBounds = true;
			inputStream = context.getAssets().open(fileName);
			BitmapFactory.decodeStream(inputStream, outPadding, options);
			options.inJustDecodeBounds = true;
		} catch (IOException e) {
			e.printStackTrace();
			options = null;
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
		return options;
	}
	
	/**
	 * 从Assets中解码位图的尺寸
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Options decodeSizeFromAssest(Context context, String fileName){
		return decodeSizeFromAssest(context, fileName, null, null);
	}
	
	/**
	 * 计算合适的缩放比例，注意在调用此方法之前一定要先通过Options.inJustDecodeBounds属性来获取Bitmap的宽高
	 * @param options
	 * @param minSideLength 用于指定最小宽度或最小高度
	 * @param maxNumOfPixels 最大尺寸，由最大宽高相乘得出
	 * @return
	 */
	public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
	    int initialSize = computeInitialSampleSize(options, minSideLength, maxNumOfPixels);
	    int roundedSize;
	    if (initialSize <= 8) {
	        roundedSize = 1;
	        while (roundedSize < initialSize) {
	            roundedSize <<= 1;
	        }
	    } else {
	        roundedSize = (initialSize + 7) / 8 * 8;
	    }
	    return roundedSize;
	}
	
	private static int computeInitialSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
	    double w = options.outWidth;
	    double h = options.outHeight;
	    int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math.sqrt(w * h / maxNumOfPixels));
	    int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(Math.floor(w / minSideLength), Math.floor(h / minSideLength));
	    if (upperBound < lowerBound) {
	        return lowerBound;
	    }

	    if ((maxNumOfPixels == -1) &&
	            (minSideLength == -1)) {
	        return 1;
	    } else if (minSideLength == -1) {
	        return lowerBound;
	    } else {
	        return upperBound;
	    }
	}
}