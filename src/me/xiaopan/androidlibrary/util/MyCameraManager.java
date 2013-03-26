package me.xiaopan.androidlibrary.util;

import java.io.IOException;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.OnZoomChangeListener;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.view.SurfaceHolder;

/**
 * 相机管理器
 * @author xiaopan
 */
public class MyCameraManager implements SurfaceHolder.Callback{
	private SurfaceHolder surfaceHolder;
	private Camera camera;
	private int frontCameraId = -1;
	private int backCameraId = -1;
	private int currentCameraId = -1;
	private PreviewCallback previewCallback;
	private PreviewStateCallback previewStateCallback;
	private RawPictureCallback rawPictureCallback;
	private JpegPictureCallback jpegPictureCallback;
	private Camera.PictureCallback cameraRawPictureCallback;
	private Camera.PictureCallback cameraJpegPictureCallback;
	private ShutterCallback shutterCallback;
	private OpenCameraFailCallback openCameraFailCallback;
	private InitCameraCallback initCameraCallback;
	private AutoFocusCallback autoFocusCallback;
	private SurfaceHolder.Callback surfaceHolderCallback;
	private ErrorCallback errorCallback;
	private FaceDetectionListener faceDetectionListener;
	private OnZoomChangeListener zoomChangeListener;
	private boolean resumeRestore;//是否需要在Activity Resume的时候恢复
	
	public MyCameraManager(SurfaceHolder surfaceHolder){
		this.surfaceHolder = surfaceHolder;
		this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.surfaceHolder.addCallback(this);
		
		//获取前置和后置摄像头的ID
		if(SystemUtils.getAPILevel() > 8){
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
		
		cameraRawPictureCallback = new Camera.PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				if(rawPictureCallback != null){
					rawPictureCallback.onPictureTakenRaw(data, camera);
				}
			}
		};
		
		cameraJpegPictureCallback = new Camera.PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				if(jpegPictureCallback != null){
					jpegPictureCallback.onPictureTakenJpeg(data, camera);
				}
			}
		};
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
			if(openCameraFailCallback != null){
				openCameraFailCallback.onOpenCameraFail(e);
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
				if(openCameraFailCallback != null){
					openCameraFailCallback.onOpenCameraFail(e);
				}
			}
		}else{
			throw new Exception();
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceCreated(holder);
		}
		initCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceChanged(holder, format, width, height);
		}
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceDestroyed(holder);
		}
		stopPreview();
		resumeRestore = false;
	}

	/**
	 * 开始预览
	 */
	public void startPreview(){
		if(camera != null){
			camera.startPreview();
			autoFocus();
			if(previewStateCallback != null){
				previewStateCallback.onStartPreview();
			}
		}
	}
	
	/**
	 * 停止预览
	 */
	public void stopPreview(){
		if(camera != null){
			camera.stopPreview();
			if(previewStateCallback != null){
				previewStateCallback.onStopPreview();
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
			camera.autoFocus(autoFocusCallback);
		}
	}
	
	/**
	 * 拍照
	 */
	public void takePicture(){
		if(camera != null){
			camera.takePicture(shutterCallback, cameraRawPictureCallback, cameraJpegPictureCallback);
		}
	}
	
	/**
	 * 设置闪光模式
	 * @param newFlashMode
	 */
	public void setFlashMode(String newFlashMode){
		stopPreview();
		Parameters parameters = camera.getParameters();
		parameters.setFlashMode(newFlashMode);
		camera.setParameters(parameters);
		startPreview();
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
				camera.setPreviewCallback(previewCallback);
				camera.setErrorCallback(errorCallback);
				camera.setFaceDetectionListener(faceDetectionListener);
				camera.setZoomChangeListener(zoomChangeListener);
				if(initCameraCallback != null){
					initCameraCallback.onInitCamera(camera);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * RAW图片回调
	 */
	public interface RawPictureCallback{
		public void onPictureTakenRaw(byte[] data, Camera camera);
	}

	/**
	 * JPEG图片回调
	 */
	public interface JpegPictureCallback{
		public void onPictureTakenJpeg(byte[] data, Camera camera);
	}
	
	/**
	 * 打开摄像头失败回调
	 */
	public interface OpenCameraFailCallback{
		public void onOpenCameraFail(Exception e);
	}
	
	/**
	 * 初始化相机回调
	 */
	public interface InitCameraCallback{
		public void onInitCamera(Camera camera);
	}
	
	/**
	 * 预览状态回调
	 */
	public interface PreviewStateCallback{
		public void onStartPreview();
		public void onStopPreview();
	}
	
	public void setPreviewCallback(PreviewCallback previewCallback) {
		this.previewCallback = previewCallback;
	}

	public void setJpegPictureCallback(JpegPictureCallback jpegPictureCallback) {
		this.jpegPictureCallback = jpegPictureCallback;
	}

	public void setRawPictureCallback(RawPictureCallback rawPictureCallback) {
		this.rawPictureCallback = rawPictureCallback;
	}

	public void setShutterCallback(ShutterCallback shutterCallback) {
		this.shutterCallback = shutterCallback;
	}

	public void setOpenCameraFailCallback(
			OpenCameraFailCallback openCameraFailCallback) {
		this.openCameraFailCallback = openCameraFailCallback;
	}

	public void setInitCameraCallback(InitCameraCallback initCameraCallback) {
		this.initCameraCallback = initCameraCallback;
	}

	public void setAutoFocusCallback(AutoFocusCallback autoFocusCallback) {
		this.autoFocusCallback = autoFocusCallback;
	}

	public void setSurfaceHolderCallback(SurfaceHolder.Callback surfaceHolderCallback) {
		this.surfaceHolderCallback = surfaceHolderCallback;
	}

	public void setErrorCallback(ErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	public void setFaceDetectionListener(FaceDetectionListener faceDetectionListener) {
		this.faceDetectionListener = faceDetectionListener;
	}

	public void setZoomChangeListener(OnZoomChangeListener zoomChangeListener) {
		this.zoomChangeListener = zoomChangeListener;
	}

	public void setPreviewStateCallback(PreviewStateCallback previewStateCallback) {
		this.previewStateCallback = previewStateCallback;
	}

	public int getCurrentCameraId() {
		return currentCameraId;
	}
}