/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.xiaopan.easyandroid.util;

import java.io.File;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

/**
 * Intent工具箱
 * @author xiaopan
 *
 */
public class IntentUtils {
	
	/**
	 * 获取使用相机拍照的Intent
	 * @param saveFileUri 保存照片的文件
	 * @return 使用相机拍照的Intent
	 */
	public static Intent getTakePhotosIntent(Uri saveFileUri){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		if(saveFileUri != null){
			intent.putExtra(MediaStore.EXTRA_OUTPUT, saveFileUri);
		}
		return intent;
	}
	
	/**
	 * 获取使用相机拍照的Intent
	 * @return 使用相机拍照的Intent
	 */
	public static Intent getTakePhotosIntent(){
		return getTakePhotosIntent(null);
	}
	
	/**
	 * 获取录音Intent
	 * @return 录音Intent
	 */
	public static Intent getRecordingIntent(){
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType("audio/amr");
		return intent;
	}
	
	/**
	 * 获取安装给定APK文件的应用程序的Intent
	 * @param fileUri 给定APK文件的Uri
	 * @return 安装给定APK文件的Intent
	 */
	public static Intent getInstallAppplicationIntent(Uri fileUri){
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
		return intent;
	}
	
	/**
	 * 获取安装给定APK文件的应用程序的Intent
	 * @param apkFile 给定APK文件
	 * @return 安装给定APK文件的Intent
	 */
	public static Intent getInstallAppplicationIntent(File apkFile){
		return getInstallAppplicationIntent(Uri.fromFile(apkFile));
	}
	
	/**
	 * 获取安装给定的apk文件路径的应用程序的Intent
	 * @param apkFilePath 给定的apk文件路径
	 * @return 安装给定的apk文件路径的应用程序的Intent
	 */
	public static Intent getInstallAppplicationIntent(String apkFilePath){
		return getInstallAppplicationIntent(new File(apkFilePath));
	}
	
	/**
	 * 获取卸载给定包名的应用程序的Intent
	 * @param applicationPackageName 给定包名
	 * @return 卸载给定包名的应用程序的Intent
	 */
	public static Intent getUninstallApplicationIntent(String applicationPackageName){
		 return new Intent(Intent.ACTION_DELETE, Uri.parse("package: "+applicationPackageName));
	}
	
	/**
	 * 从图库获取图片Intent构造器
	 * @author panpf
	 *
	 */
	public static class FromGalleryGetImageIntentBuilder{
		/**
		 * 是否裁剪
		 */
		private boolean crop;
		/**
		 * 裁剪后的得到的图片的宽度
		 */
		private int width;
		/**
		 * 裁剪后的得到的图片的高度
		 */
		private int height;
		/**
		 * 保存裁剪后的图片的文件的Uri
		 */
		private Uri saveFileUri;
		/**
		 * 设置是否将裁剪后得到的图片缩放后再放在Intent中返回
		 */
		private boolean scale;
		
		public Intent createIntent(){
			Intent intent = new Intent(Intent.ACTION_PICK);
			intent.setType("image/*");
			if(isCrop()){
				//设置裁剪框的宽高比例
				intent.putExtra("aspectX", getWidth());
				intent.putExtra("aspectY",getHeight());
				//设置裁剪后得到的图片的宽度
				intent.putExtra("outputX", getWidth());
				//设置裁剪后得到的图片的高度
				intent.putExtra("outputY", getHeight());
				if(getSaveFileUri() != null){
					//设置将裁剪后得到的图片保存在给东Uri的文件中，此文件必须存在。”output“与“return-data”只能任选其一
					intent.putExtra("output", getSaveFileUri());
				}else{
					//设置将裁剪后得到的图片保存在Inteng中返回。“return-data”与”output“只能任选其一
					intent.putExtra("return-data", true);
					//设置是否将裁剪后得到的图片缩放后再放在Intent中返回
					intent.putExtra("scale", isScale());
				}
			}
			return intent;
		}

		public boolean isCrop() {
			return crop;
		}

		public void setCrop(boolean crop) {
			this.crop = crop;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}
		
		public Uri getSaveFileUri() {
			return saveFileUri;
		}
		
		public void setSaveFileUri(Uri saveFileUri) {
			this.saveFileUri = saveFileUri;
		}

		public boolean isScale() {
			return scale;
		}

		public void setScale(boolean scale) {
			this.scale = scale;
		}
	}
	
	
	
	/**
	 * 图片裁剪Intent构造器
	 * @author panpf
	 */
	public static class ImageCropIntentBuilder{
		/**
		 * 要裁剪的文件的Uri
		 */
		private Uri sourceFileUri;
		/**
		 * 保存裁剪后的图片的文件的Uri
		 */
		private Uri saveFileUri;
		/**
		 * 裁剪后的得到的图片的宽度
		 */
		private int width;
		/**
		 * 裁剪后的得到的图片的高度
		 */
		private int height;
		/**
		 * 设置是否将裁剪后得到的图片缩放后再放在Intent中返回
		 */
		private boolean scale;
		
		/**
		 * 创建图片裁剪Intent构造器
		 * @param sourceFileUri 要裁剪的图片的Uri
		 * @param saveFileUri 保存裁剪后得到的图片的文件的Uri
		 * @param width 裁剪后得到的图片的宽度
		 * @param height 裁剪后得到的图片的高度
		 */
		public ImageCropIntentBuilder(Uri sourceFileUri, Uri saveFileUri, int width, int height){
			setSourceFileUri(sourceFileUri);
			setSaveFileUri(saveFileUri);
			setWidth(width);
			setHeight(height);
		}
		
		/**
		 * 创建图片裁剪Intent构造器
		 * @param sourceFileUri 要裁剪的图片的Uri
		 * @param width 裁剪后得到的图片的宽度
		 * @param height 裁剪后得到的图片的高度
		 */
		public ImageCropIntentBuilder(Uri sourceFileUri, int width, int height){
			this(sourceFileUri, null, width, height);
		}
		
		/**
		 * 创建Intent
		 * @return
		 */
		public Intent createIntent(){
			Intent intent = new Intent("com.android.camera.action.CROP");
			intent.setDataAndType(getSourceFileUri(), "image/*");
			//设置要让系统的camea执行裁剪操作
			intent.putExtra("crop", true);
			//设置裁剪框的宽高比例
			intent.putExtra("aspectX", getWidth());
			intent.putExtra("aspectY",getHeight());
			//设置裁剪后得到的图片的宽度
			intent.putExtra("outputX", getWidth());
			//设置裁剪后得到的图片的高度
			intent.putExtra("outputY", getHeight());
			if(getSaveFileUri() != null){
				//设置将裁剪后得到的图片保存在给东Uri的文件中，此文件必须存在。”output“与“return-data”只能任选其一
				intent.putExtra("output", getSaveFileUri());
			}else{
				//设置将裁剪后得到的图片保存在Inteng中返回。“return-data”与”output“只能任选其一
				intent.putExtra("return-data", true);
				//设置是否将裁剪后得到的图片缩放后再放在Intent中返回
				intent.putExtra("scale", isScale());
			}
			return intent;
		}
		
		public Uri getSourceFileUri() {
			return sourceFileUri;
		}
		
		public void setSourceFileUri(Uri sourceFileUri) {
			this.sourceFileUri = sourceFileUri;
		}
		
		public Uri getSaveFileUri() {
			return saveFileUri;
		}
		
		public void setSaveFileUri(Uri saveFileUri) {
			this.saveFileUri = saveFileUri;
		}
		
		public int getWidth() {
			return width;
		}
		
		public void setWidth(int width) {
			this.width = width;
		}
		
		public int getHeight() {
			return height;
		}
		
		public void setHeight(int height) {
			this.height = height;
		}

		public boolean isScale() {
			return scale;
		}

		public void setScale(boolean scale) {
			this.scale = scale;
		}
	}
}
