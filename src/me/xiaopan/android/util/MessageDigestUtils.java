package me.xiaopan.android.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.content.Context;
import android.net.Uri;

/**
 * 消息摘要工具箱，专为各种文件计算MD5和SHA-1摘要信息
 */
public class MessageDigestUtils {
	public static String getDigest(MessageDigest digest, InputStream inputStream) throws IOException{
		digest.reset();
		
		// 读取数据
		byte[] data = new byte[1024];
		int length = 0;
		while((length = inputStream.read(data)) > 0){
			digest.update(data, 0, length);
		}
		
		// 计算
		BigInteger bigInteger = new BigInteger(1, digest.digest());
		return bigInteger.toString(16);
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
