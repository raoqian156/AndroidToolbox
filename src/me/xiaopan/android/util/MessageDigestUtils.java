package me.xiaopan.android.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.Uri;

/**
 * 消息摘要工具箱，专为各种文件计算MD5和SHA-1摘要信息
 */
public class MessageDigestUtils {
	public static String byteArrayToHex(byte[] byteArray) {

		// 首先初始化一个字符数组，用来存放每个16进制字符

		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'A', 'B', 'C', 'D', 'E', 'F' };

		// new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

		char[] resultCharArray = new char[byteArray.length * 2];

		// 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

		int index = 0;

		for (byte b : byteArray) {

			resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

			resultCharArray[index++] = hexDigits[b & 0xf];

		}

		// 字符数组组合成字符串返回

		return new String(resultCharArray);
	}
	
	public static String getDigest(MessageDigest digest, InputStream inputStream) throws IOException{
		digest.reset();
		
		// 读取数据
		byte[] data = new byte[1024];
		int length = 0;
		while((length = inputStream.read(data)) > 0){
			digest.update(data, 0, length);
		}
		
		// 计算
		return byteArrayToHex(digest.digest());
	}
	
	public static String getStringDigest(String string, MessageDigest digest){
		try {
			return getDigest(digest, new ByteArrayInputStream(string.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getUriDigest(Context context, Uri uri, MessageDigest digest){
		InputStream inputStream = null;
		try {
			inputStream = context.getContentResolver().openInputStream(uri);
			return getDigest(digest, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getAssetsFileDigest(Context context, String fileName, MessageDigest digest){
		InputStream inputStream = null;
		try {
			inputStream = context.getAssets().open(fileName);
			return getDigest(digest, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getFileDigest(File file, MessageDigest digest){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			return getDigest(digest, inputStream);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}finally{
			if(inputStream != null){
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static String getStringMD5(String string, String fileName){
		try {
			return getStringDigest(string, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getStringFileSHA1(String string, String fileName){
		try {
			return getStringDigest(string, MessageDigest.getInstance("SHA-1"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getAssetsFileMD5(Context context, String fileName){
		try {
			return getAssetsFileDigest(context, fileName, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getAssetsFileSHA1(Context context, String fileName){
		try {
			return getAssetsFileDigest(context, fileName, MessageDigest.getInstance("SHA-1"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getUriMD5(Context context, Uri uri){
		try {
			return getUriDigest(context, uri, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getUriSHA1(Context context, Uri uri){
		try {
			return getUriDigest(context, uri, MessageDigest.getInstance("SHA-1"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFileMD5(File file){
		try {
			return getFileDigest(file, MessageDigest.getInstance("MD5"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getFileSHA1(File file){
		try {
			return getFileDigest(file, MessageDigest.getInstance("SHA-1"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
