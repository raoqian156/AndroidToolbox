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
package me.xiaopan.android.easy.util.camera;

import java.io.IOException;

import me.xiaopan.android.easy.util.WindowUtils;
import android.annotation.TargetApi;
import android.app.Activity;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * 相机管理器
 */
public class CameraManager implements SurfaceHolder.Callback, Camera.AutoFocusCallback{
	private int frontCameraId = -1;	//前置摄像头的ID
	private int backCameraId = -1;	//后置摄像头的ID
	private int currentCameraId = -1;	//当前摄像头的ID
	private int displayOrientation;	//显示方向
	private boolean resumeRestore;//是否需要在Activity Resume的时候恢复
	private boolean debugMode;	//Debug模式，开启后将输出运行日志
	private String logTag = "CameraManager";
	private Camera camera;	//Camera
	private Activity activity;	
	private SurfaceHolder surfaceHolder;
	private CameraCallback cameraCallback;
	
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("deprecation")
	public CameraManager(Activity activity, SurfaceHolder surfaceHolder, CameraCallback cameraCallback){
		this.activity = activity;
		this.surfaceHolder = surfaceHolder;
		this.cameraCallback = cameraCallback;
		
		if(Build.VERSION.SDK_INT < 11){
			this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		this.surfaceHolder.addCallback(this);
		
		//获取前置和后置摄像头的ID
		if(Build.VERSION.SDK_INT >= 9){
			int cameraIds = Camera.getNumberOfCameras();
			CameraInfo cameraInfo = new CameraInfo();
			for(int cameraId = 0; cameraId < cameraIds; cameraId++){
				Camera.getCameraInfo(cameraId, cameraInfo);
				if(cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT){
					frontCameraId = cameraId;
				}else if(cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK){
					backCameraId = cameraId;
				}
			}
		}
	}
	
	/**
	 * 打开后置摄像头
	 * @param isResume 是否是在onResume()方法中调用此方法的
	 * @throws CamreaBeingUsedException 相机被占用
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void openBackCamera(boolean isResume) throws CamreaBeingUsedException{
		if(debugMode){
			Log.d(logTag, "openBackCamera");
		}
		release();
		try {
			camera = Build.VERSION.SDK_INT >= 9 && backCameraId != -1?Camera.open(backCameraId):Camera.open();
		} catch (RuntimeException e) {
			throw new CamreaBeingUsedException();
		}
		currentCameraId = backCameraId;
		tryRestore(isResume);
	}
	
	/**
	 * 打开前置摄像头
	 * @param isResume 是否是在onResume()方法中调用此方法的
	 * @throws NoFoundFrontCamera 没有找到前置摄像头
	 * @throws CamreaBeingUsedException 前置摄像头被占用
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public void openForntCamera(boolean isResume) throws Exception, NoFoundFrontCamera, CamreaBeingUsedException{
		if(debugMode){
			Log.d(logTag, "openBackCamera");
		}
		release();
		if(Build.VERSION.SDK_INT >= 9 && frontCameraId != -1){
			try {
				camera = Camera.open(frontCameraId);
			} catch (RuntimeException e) {
				throw new CamreaBeingUsedException();
			}
			currentCameraId = frontCameraId;
			tryRestore(isResume);
		}else{
			throw new NoFoundFrontCamera();
		}
	}
	
	/**
	 * 初始化Camera的方法是在surfaceCreated()方法里调用的，开启预览是在surfaceChanged()方法中调用的，
	 *	<br>当屏幕是竖屏的时候按下电源键系统会锁屏，并且Activity会进入onPause()中并释放相机，
	 *	<br>然而再解锁回到应用的时候只会调用onResume()方法，而不会调用surfaceCreated()和surfaceChanged()方法，所以Camera不会被初始化，也不会开启预览，显示这样是不行的。
	 *	<br>所以我们要在Activity暂停释放Camera的时候做一个标记，当再次在onResume()中执行本方法打开摄像头的时候要初始化Camera并开启预览
	 *	<br>另外当SurfaceView被销毁的时候要标记为不需要恢复，因为只要SurfaceView被销毁那么接下来必然会执行surfaceCreated()和surfaceChanged()方法
	 * @param isResume
	 */
	private void tryRestore(boolean isResume){
		if(isResume && resumeRestore){
			if(debugMode){
				Log.d(logTag, "resumeRestore");
			}
			resumeRestore = false;
			initCamera();
			startPreview();
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(debugMode){
			Log.d(logTag, "surfaceCreated");
		}
		initCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(debugMode){
			Log.d(logTag, "surfaceChanged");
		}
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(debugMode){
			Log.d(logTag, "surfaceDestroyed");
		}
		stopPreview();
		resumeRestore = false;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if(debugMode){
			Log.d(logTag, "自定对焦"+(success?"成功":"失败"));
		}
		if(cameraCallback != null){
			cameraCallback.onAutoFocus(success, camera);
		}
	}

	/**
	 * 开始预览
	 */
	public void startPreview(){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "startPreview");
			}
			camera.startPreview();
			if(cameraCallback != null){
				cameraCallback.onStartPreview();
			}
		}
	}
	
	/**
	 * 停止预览
	 */
	public void stopPreview(){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "stopPreview");
			}
			camera.stopPreview();
			if(cameraCallback != null){
				cameraCallback.onStopPreview();
			}
		}
	}
	
	/**
	 * 自动对焦
	 */
	public void autoFocus(){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "autoFocus");
			}
			try{
				camera.autoFocus(this);
			}catch(Throwable throwable){
				throwable.printStackTrace();
			}
		}
	}
	
	/**
	 * 拍照
	 * @param shutter 快门回调
	 * @param raw RAW格式图片回调
	 * @param jpeg JPEG格式图片回调
	 */
	public void takePicture(ShutterCallback shutter, PictureCallback raw, PictureCallback jpeg){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "takePicture");
			}
			camera.takePicture(shutter, raw, jpeg);
		}
	}
	
	/**
	 * 设置闪光模式
	 * @param newFlashMode
	 */
	public void setFlashMode(String newFlashMode){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "setFlashMode："+newFlashMode);
			}
			Camera.Parameters cameraParameters = camera.getParameters();
			cameraParameters.setFlashMode(newFlashMode);
			camera.setParameters(cameraParameters);
		}
	}
	
	/**
	 * 设置是闪光灯常亮
	 * @param enable
	 * @return 不支持闪光灯常亮或尚未打开Camera
	 */
	public boolean setTorchFlash(boolean enable){
		if(camera != null){
			if(enable){
				if(CameraUtils.isSupportFlashMode(getCamera(), Camera.Parameters.FLASH_MODE_TORCH)){
					if(debugMode){
						Log.d(logTag, "打开闪光灯");
					}
					setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
					return true;
				}else{
					if(debugMode){
						Log.d(logTag, "打开闪光灯失败，因为当前机器不支持闪光灯常亮");
					}
					return false;
				}
			}else{
				setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
				return true;
			}	
		}else{
			return false;
		}
	}

	/**
	 * 设置显示方向
	 * @param displayOrientation
	 */
	public void setDisplayOrientation(int displayOrientation){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "setDisplayOrientation："+displayOrientation);
			}
			
			this.displayOrientation = displayOrientation;
			if(Build.VERSION.SDK_INT >= 9){
				camera.setDisplayOrientation(displayOrientation);
			}else{
				Camera.Parameters cameraParameters = camera.getParameters();
				cameraParameters.setRotation(displayOrientation);
				camera.setParameters(cameraParameters);
			}
		}
	}
	
	/**
	 * 初始化Camera
	 * @return true：调用成功；false：调用失败，原因是camera尚未初始化
	 */
	private void initCamera(){
		if(camera != null){
			if(debugMode){
				Log.d(logTag, "initCamera");
			}
			
			try {
				camera.setPreviewDisplay(surfaceHolder);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			//如果是当前竖屏就将预览角度顺时针旋转90度
			if(Build.VERSION.SDK_INT >= 9){
				setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(activity, getCurrentCameraId()));
			}else if (!WindowUtils.isLandscape(activity)) {
				setDisplayOrientation(90);
			}
			
			if(cameraCallback != null){
				cameraCallback.onInitCamera(camera);	//回调初始化
			}
			
			Camera.Parameters parameters = camera.getParameters();
			Camera.Size previewSize = parameters.getPreviewSize();
			Camera.Size pictureSize = parameters.getPictureSize();
			if(debugMode){
				Log.d(logTag, "previewSize："+previewSize.width+"x"+previewSize.height+"; pictureSize："+pictureSize.width+"x"+pictureSize.height);
			}
		}
	}
	
	/**
	 * 释放
	 */
	public void release(){
		if (camera != null) {
			if(debugMode){
				Log.d(logTag, "release");
			}
			stopPreview();
			try {
				camera.setPreviewDisplay(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			camera.setPreviewCallback(null);
			camera.setErrorCallback(null);
			camera.setOneShotPreviewCallback(null);
			camera.setPreviewCallbackWithBuffer(null);
			camera.setZoomChangeListener(null);
			camera.release();
			camera = null;
			resumeRestore = true;
		}
	}
	
	/**
	 * 获取Camera
	 * @return
	 */
	public Camera getCamera() {
		return camera;
	}

	/**
	 * 获取当前Camera的ID
	 * @return
	 */
	public int getCurrentCameraId() {
		return currentCameraId;
	}

	/**
	 * 设置Camera回调
	 * @param cameraCallback
	 */
	public void setCameraCallback(CameraCallback cameraCallback) {
		this.cameraCallback = cameraCallback;
	}
	
	/**
	 * 获取屏幕方向
	 * @return
	 */
	public int getDisplayOrientation() {
		return displayOrientation;
	}

	/**
	 * 是否是Debug模式
	 * @return
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * 设置是否是Debug模式
	 * @param debugMode
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
	}

	/**
	 * 获取日志标签
	 * @return
	 */
	public String getLogTag() {
		return logTag;
	}

	/**
	 * 设置日志标签
	 * @param logTag
	 */
	public void setLogTag(String logTag) {
		this.logTag = logTag;
	}
	
	/**
	 * Camera相关事件回调
	 */
	public interface CameraCallback{
		public void onInitCamera(Camera camera);
		public void onStartPreview();
		public void onAutoFocus(boolean success, Camera camera);
		public void onStopPreview();
	}
	
	/**
	 * 相机被占用异常
	 */
	public class CamreaBeingUsedException extends Throwable{
		private static final long serialVersionUID = -410101242781061339L;
	}
	
	/**
	 * 没有找到前置摄像头异常
	 */
	public class NoFoundFrontCamera extends Throwable{
		private static final long serialVersionUID = -410101242781061339L;
	}
}