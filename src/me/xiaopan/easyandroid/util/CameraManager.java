package me.xiaopan.easyandroid.util;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.view.SurfaceHolder;

/**
 * 相机管理器
 * @author xiaopan
 */
public class CameraManager implements SurfaceHolder.Callback, Camera.AutoFocusCallback{
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private int frontCameraId = -1;
	private int backCameraId = -1;
	private int currentCameraId = -1;
	private CameraCallback cameraCallback;
	private boolean resumeRestore;//是否需要在Activity Resume的时候恢复
	private int focusIntervalTime;//两次对焦的间隔时间
	private long lastFocusTime;//上次对焦的时间
	
	public CameraManager(SurfaceHolder surfaceHolder, CameraCallback cameraCallback){
		this.surfaceHolder = surfaceHolder;
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.surfaceHolder.addCallback(this);
		this.cameraCallback = cameraCallback;
		
		//获取前置和后置摄像头的ID
		if(SystemUtils.getAPILevel() >= 9){
			int cameraNumbers = Camera.getNumberOfCameras();
			CameraInfo cameraInfo = new CameraInfo();
			for(int w = 0; w < cameraNumbers; w++){
				Camera.getCameraInfo(w, cameraInfo);
				if(cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT){
					frontCameraId = w;
				}else if(cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK){
					backCameraId = w;
				}
			}
		}
	}
	
	/**
	 * 打开后置摄像头
	 */
	public void openBackCamera(){
		try {
			camera = backCameraId != -1?Camera.open(backCameraId):Camera.open();
			currentCameraId = backCameraId;
			//初始化Camera的方法是在surfaceCreated()方法里调用的，开启预览是在surfaceChanged()方法中调用的，
			//当屏幕是竖屏的时候按下电源键系统会锁屏，并且Activity会进入onPause()中并释放相机，
			//然而再解锁回到应用的时候只会调用onResume()方法，而不会调用surfaceCreated()和surfaceChanged()方法，所以Camera不会被初始化，也不会开启预览，显示这样是不行的。
			//所以我们要在Activity暂停释放Camera的时候做一个标记，当再次在onResume()中执行本方法打开摄像头的时候要初始化Camera并开启预览
			//另外当SurfaceView被销毁的时候要标记为不需要恢复，因为只要SurfaceView被销毁那么接下来必然会执行surfaceCreated()和surfaceChanged()方法
			if(resumeRestore){
				resumeRestore = false;
				initCamera();
				startPreview();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(camera != null){
				camera.release();
				camera = null;
			}
			if(cameraCallback != null){
				cameraCallback.onOpenCameraException(e);
			}
		}
	}
	
	/**
	 * 打开前置摄像头
	 * @throws Exception 没有前置摄像头 
	 */
	public void openForntCamera() throws Exception{
		if(frontCameraId != -1){
			try {
				camera = Camera.open(frontCameraId);
				currentCameraId = frontCameraId;
				//初始化Camera的方法是在surfaceCreated()方法里调用的，开启预览是在surfaceChanged()方法中调用的，
				//当屏幕是竖屏的时候按下电源键系统会锁屏，并且Activity会进入onPause()中并释放相机，
				//然而再解锁回到应用的时候只会调用onResume()方法，而不会调用surfaceCreated()和surfaceChanged()方法，所以Camera不会被初始化，也不会开启预览，显示这样是不行的。
				//所以我们要在Activity暂停释放Camera的时候做一个标记，当再次在onResume()中执行本方法打开摄像头的时候要初始化Camera并开启预览
				//另外当SurfaceView被销毁的时候要标记为不需要恢复，因为只要SurfaceView被销毁那么接下来必然会执行surfaceCreated()和surfaceChanged()方法
				if(resumeRestore){
					resumeRestore = false;
					initCamera();
					startPreview();
				}
			} catch (Exception e) {
				e.printStackTrace();
				if(camera != null){
					camera.release();
					camera = null;
				}
				if(cameraCallback != null){
					cameraCallback.onOpenCameraException(e);
				}
			}
		}else{
			throw new Exception();
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		stopPreview();
		resumeRestore = false;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		if(cameraCallback != null){
			cameraCallback.onAutoFocus(success, camera);
		}
	}

	/**
	 * 开始预览
	 */
	public void startPreview(){
		if(camera != null){
			camera.startPreview();
			autoFocus();
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
			camera.stopPreview();
			if(cameraCallback != null){
				cameraCallback.onStopPreview();
			}
		}
	}
	
	/**
	 * 释放
	 */
	public void release(){
		if (camera != null) {
			stopPreview();
			try {
				camera.setPreviewDisplay(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
		resumeRestore = true;
	}
	
	/**
	 * 自动对焦
	 */
	public void autoFocus(){
		if(camera != null){
			if(focusIntervalTime > 0){
				long currentTime = System.currentTimeMillis();
				if(lastFocusTime == 0 || currentTime - lastFocusTime >= focusIntervalTime){
					camera.autoFocus(this);
					lastFocusTime = currentTime;
				}
			}else{
				camera.autoFocus(this);
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
			camera.takePicture(shutter, raw, jpeg);
		}
	}
	
	/**
	 * 设置闪光模式
	 * @param newFlashMode
	 */
	public void setFlashMode(String newFlashMode){
		if(camera != null){
			Parameters parameters = camera.getParameters();
			parameters.setFlashMode(newFlashMode);
			camera.setParameters(parameters);
		}
	}
	
	/**
	 * 设置显示方向
	 * @param orientation
	 */
	public void setDisplayOrientation(int orientation){
		if(camera != null){
			camera.setDisplayOrientation(orientation);
		}
	}
	
	/**
	 * 初始化Camera
	 */
	private void initCamera(){
		if(camera != null){
			try {
				camera.setPreviewDisplay(surfaceHolder);
				if(cameraCallback != null){
					cameraCallback.onInitCamera(camera);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取两次对焦的间隔时间
	 * @return 两次对焦的间隔时间，单位毫秒
	 */
	public int getFocusIntervalTime() {
		return focusIntervalTime;
	}

	/**
	 * 设置两次对焦的间隔时间
	 * @param focusIntervalTime 两次对焦的间隔时间，单位毫秒
	 */
	public void setFocusIntervalTime(int focusIntervalTime) {
		this.focusIntervalTime = focusIntervalTime;
	}

	public interface CameraCallback{
		public void onInitCamera(Camera camera);
		public void onAutoFocus(boolean success, Camera camera);
		public void onOpenCameraException(Exception e);
		public void onStartPreview();
		public void onStopPreview();
	}
	
	public Camera getCamera() {
		return camera;
	}

	public void setCameraCallback(CameraCallback cameraCallback) {
		this.cameraCallback = cameraCallback;
	}

	public int getCurrentCameraId() {
		return currentCameraId;
	}
}