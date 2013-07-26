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

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.WindowManager;

/**
 * 相机工具箱
 * @author xiaopan
 *
 */
public class CameraUtils {
	/**
	 * 获取最佳的预览尺寸
	 * @param context
	 * @param camera
	 * @return
	 */
	public static Camera.Size getOptimalPreviewSize(Context context, Camera camera) {
		Camera.Size optimalSize = null;
		List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
		if (supportedPreviewSizes != null && supportedPreviewSizes.size() > 0){
			Size screenSize = DeviceUtils.getScreenSize(context);
			int screenWidth = screenSize.getHeight();
			int screenHeight = screenSize.getWidth();
			final double ASPECT_TOLERANCE = 0.1;
			double minDiff = Double.MAX_VALUE;
			
			//计算最佳的宽高比例
			double targetRatio = (double) screenWidth / screenHeight;
			int targetHeight = screenHeight;
			
			//视图找到一个宽高和屏幕最接近的尺寸
			for (Camera.Size size : supportedPreviewSizes) {
				double ratio = (double) size.width / size.height;
				if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
			
			//当找不到的时候
			if (optimalSize == null) {
				minDiff = Double.MAX_VALUE;
				for (Camera.Size size : supportedPreviewSizes) {
					if (Math.abs(size.height - targetHeight) < minDiff) {
						optimalSize = size;
						minDiff = Math.abs(size.height - targetHeight);
					}
				}
			}
		}

        return optimalSize;
    }
	
	/**
	 * 试图找到一个最接近屏幕的预览和输出尺寸
	 * @param context
	 * @param camera
	 * @return
	 */
	public static Camera.Size getBestPreviewAndPictureSize(Context context, Camera camera){
		Size screenSize = DeviceUtils.getScreenSize(context);
		boolean landscape = screenSize.getWidth() > screenSize.getHeight();
		
		//如果是竖屏就将宽高互换
		if(!landscape){
			screenSize.setWidth(screenSize.getWidth() + screenSize.getHeight());
			screenSize.setHeight(screenSize.getWidth() - screenSize.getHeight());
			screenSize.setWidth(screenSize.getWidth() - screenSize.getHeight());
		}
		
		Camera.Parameters cameraParameters = camera.getParameters();
		List<Camera.Size> supportPreviewSizes = cameraParameters.getSupportedPreviewSizes();
		List<Camera.Size> supportPictureSizes = cameraParameters.getSupportedPictureSizes();
		Comparator<Camera.Size> comparator = new Comparator<Camera.Size>() {
			@Override
			public int compare(android.hardware.Camera.Size lhs, android.hardware.Camera.Size rhs) {
				return (lhs.width - rhs.width) * -1;
			}
		};
		Collections.sort(supportPreviewSizes, comparator);
		Collections.sort(supportPictureSizes, comparator);
		
		Iterator<Camera.Size> supportPreviewSizeIterator;
		Iterator<Camera.Size> supportPictureSizeIterator;
		Camera.Size currentPreviewSize;
		Camera.Size currentPictureSize;
		
		//先剔除预览尺寸集合中大于屏幕尺寸的
		supportPreviewSizeIterator = supportPreviewSizes.iterator();
		while(supportPreviewSizeIterator.hasNext()){
			currentPreviewSize = supportPreviewSizeIterator.next();
			if(currentPreviewSize.width > screenSize.getWidth() || currentPreviewSize.height > screenSize.getHeight()){
				supportPreviewSizeIterator.remove();
			}
		}
		
		//然后剔除输出尺寸集合中大于屏幕尺寸的
		supportPictureSizeIterator = supportPictureSizes.iterator();
		while(supportPictureSizeIterator.hasNext()){
			currentPictureSize = supportPictureSizeIterator.next();
			if(currentPictureSize.width > screenSize.getWidth() || currentPictureSize.height > screenSize.getHeight()){
				supportPictureSizeIterator.remove();
			}
		}
		
		//最后找到相同的
		Camera.Size result = null;
		supportPreviewSizeIterator = supportPreviewSizes.iterator();
		while(supportPreviewSizeIterator.hasNext()){
			currentPreviewSize = supportPreviewSizeIterator.next();
			supportPictureSizeIterator = supportPictureSizes.iterator();
			while(supportPictureSizeIterator.hasNext()){
				currentPictureSize = supportPictureSizeIterator.next();
				if(currentPreviewSize.width == currentPictureSize.width && currentPreviewSize.height == currentPictureSize.height){
					result = currentPictureSize;
					break;
				}else if(currentPreviewSize.width > currentPictureSize.width || currentPreviewSize.height > currentPictureSize.height){
					supportPreviewSizeIterator.remove();
					break;
				}else{
					supportPictureSizeIterator.remove();
				}
			}
			
			if(result != null){
				break;
			}
		}
		
//		if(result != null && !landscape){
//			result.width = result.width + result.height;
//			result.height = result.width - result.height;
//			result.width = result.width - result.height;
//		}
		
		return result;
	}
	
	/**
	 * 根据当前窗口的显示方向设置相机的显示方向
	 * @param activity 用来获取当前窗口的显示方向
	 * @param cameraId 相机ID，用于区分是前置摄像头还是后置摄像头，在API级别xiaoyu9d系统下此参数无用
	 */
	public static int getOptimalDisplayOrientationByWindowDisplayRotation(Activity activity, int cameraId) {      
		int degrees = WindowUtils.getDisplayRotation(activity);      
		if(SystemUtils.getAPILevel() >= 9){
			Camera.CameraInfo info = new Camera.CameraInfo();      
			Camera.getCameraInfo(cameraId, info);      
			int result;
			if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {          
				result = (info.orientation + degrees) % 360;          
				result = (360 - result) % 360;    
			} else {
				result = (info.orientation - degrees + 360) % 360;      
			}      
			return result;  
		}else{
			return 0; 
		}
	}
	
	/**
	 * 根据当前窗口的显示方向设置相机的显示方向
	 * @param activity 用来获取当前窗口的显示方向
	 * @param cameraId 相机ID，用于区分是前置摄像头还是后置摄像头
	 * @param camera
	 */
	public static void setDisplayOrientationByWindowDisplayRotation(Activity activity, int cameraId, Camera camera) {      
		Camera.CameraInfo info = new Camera.CameraInfo();      
		Camera.getCameraInfo(cameraId, info);      
		int degrees = WindowUtils.getDisplayRotation(activity);      
		int result;
		if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {          
			result = (info.orientation + degrees) % 360;          
			result = (360 - result) % 360;    
		} else {
			result = (info.orientation - degrees + 360) % 360;      
		}      
		camera.setDisplayOrientation(result);  
	}
	
	/**
	 * @param orientation OrientationEventListener类中onOrientationChanged()方法的参数
	 * @param cameraId
	 * @return
	 */
	public static int getOptimalParametersOrientationByWindowDisplayRotation(int orientation, int cameraId) {
		if (orientation != OrientationEventListener.ORIENTATION_UNKNOWN){
			Camera.CameraInfo info = new Camera.CameraInfo();
			Camera.getCameraInfo(cameraId, info);
			orientation = (orientation + 45) / 90 * 90;
			
			//计算方向
			int rotation = 0;
			if (info.facing == CameraInfo.CAMERA_FACING_FRONT) {
				rotation = (info.orientation - orientation + 360) % 360;
			} else {
				rotation = (info.orientation + orientation) % 360;
			}
			return rotation;
		}else{
			return -1;
		}
	}
	
	/**
	 *  OrientationEventListener类中onOrientationChanged()方法的参数
	 * @param orientation
	 * @param cameraId
	 * @param camera
	 */
	public static void setParametersOrientationByWindowDisplayRotation(int orientation, int cameraId, Camera camera) {
		int rotation = getOptimalParametersOrientationByWindowDisplayRotation(orientation, cameraId);
		if(rotation >= 0){
			Camera.Parameters parameters = camera.getParameters();
			parameters.setRotation(rotation);
			camera.setParameters(parameters);
		}
	}
	
	/**
	 * 判断给定的相机是否支持给定的闪光模式
	 * @param camera 给定的相机
	 * @param flashMode 给定的闪光模式
	 * @return
	 */
	public static boolean isSupportFlashMode(Camera camera, String flashMode){
		return camera != null?camera.getParameters().getSupportedFlashModes().contains(flashMode):false;
	}
	
	/**
	 * 根据屏幕分辨率以及相机预览分辨率获取取景框在预览图中的位置
	 * @param context 上下文
	 * @param cameraApertureView 取景框视图
	 * @param cameraPreviewSize 相机预览尺寸
	 * @return 取景框在预览图中的位置
	 */
	public static Rect getCameraApertureRectByScreenAndCameraPreviewSize(Context context, View cameraApertureView, Camera.Size cameraPreviewSize){
		Rect rectInScreen = new Rect();	//扫描框相对于整个屏幕的矩形
		cameraApertureView.getGlobalVisibleRect(rectInScreen);
		Rect rectInPreview= new Rect(rectInScreen);	//扫描框相对于预览界面的矩形
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();//获取屏幕分辨率
		
		if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {//如果是横屏
			rectInPreview.left = rectInPreview.left * cameraPreviewSize.width / display.getWidth();
			rectInPreview.right = rectInPreview.right * cameraPreviewSize.width / display.getWidth();
			rectInPreview.top = rectInPreview.top * cameraPreviewSize.height / display.getHeight();
			rectInPreview.bottom = rectInPreview.bottom * cameraPreviewSize.height / display.getHeight();
		} else {
			rectInPreview.left = rectInPreview.left * cameraPreviewSize.height / display.getWidth();
			rectInPreview.right = rectInPreview.right * cameraPreviewSize.height / display.getWidth();
			rectInPreview.top = rectInPreview.top * cameraPreviewSize.width / display.getHeight();
			rectInPreview.bottom = rectInPreview.bottom * cameraPreviewSize.width / display.getHeight();
		}
		
		return rectInPreview;
	}
	
	/**
	 * 将YUV格式的图片的源数据从横屏模式转为竖屏模式，注意：将源图片的宽高互换一下就是新图片的宽高
	 * @param sourceData YUV格式的图片的源数据
	 * @param width 源图片的宽
	 * @param height 源图片的高
	 * @return 
	 */
	public static final byte[] yuvLandscapeToPortrait(byte[] sourceData, int width, int height){
		byte[] rotatedData = new byte[sourceData.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = sourceData[x + y * width];
		}
		return rotatedData;
	}
}