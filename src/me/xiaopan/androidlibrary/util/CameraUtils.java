package me.xiaopan.androidlibrary.util;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.view.OrientationEventListener;

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
			Size screenSize = AndroidUtils.getScreenSize(context);
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
	 * 根据当前窗口的显示方向设置相机的显示方向
	 * @param activity 用来获取当前窗口的显示方向
	 * @param cameraId 相机ID，用于区分是前置摄像头还是后置摄像头
	 */
	public static int getOptimalDisplayOrientationByWindowDisplayRotation(Activity activity, int cameraId) {      
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
		return result;  
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
}