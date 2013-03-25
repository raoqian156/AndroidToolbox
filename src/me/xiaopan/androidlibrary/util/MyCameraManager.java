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
	private JpegPictureCallback jpegPictureCallback;
	private RawPictureCallback rawPictureCallback;
	private ShutterCallback shutterCallback;
	private OpenCameraFailCallback openCameraFailCallback;
	private InitCameraCallback initCameraCallback;
	private AutoFocusCallback autoFocusCallback;
	private SurfaceHolder.Callback surfaceHolderCallback;
	private ErrorCallback errorCallback;
	private FaceDetectionListener faceDetectionListener;
	private OnZoomChangeListener zoomChangeListener;
	
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
	}
	
	/**
	 * 打开后置摄像头
	 */
	public void openBackCamera(){
		try {
			camera = backCameraId != -1?Camera.open(backCameraId):Camera.open();
			if(initCameraCallback != null){
				initCameraCallback.onInitCamera(camera);
			}
			currentCameraId = backCameraId;
			Logger.w("打开后置摄像头");
		} catch (Exception e) {
			e.printStackTrace();
			if(camera != null){
				camera.release();
				camera = null;
			}
			if(openCameraFailCallback != null){
				openCameraFailCallback.onOpenCameraFail(e);
			}
			Logger.w("打开后置摄像头失败");
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
				camera.setPreviewDisplay(surfaceHolder);
				camera.setPreviewCallback(previewCallback);
				if(initCameraCallback != null){
					initCameraCallback.onInitCamera(camera);
				}
				currentCameraId = frontCameraId;
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

	/**
	 * 开始预览
	 */
	public void startPreview(){
		Logger.w("开始预览");
		if(camera != null){
			camera.startPreview();
		}
	}
	
	/**
	 * 停止预览
	 */
	public void stopPreview(){
		Logger.w("停止预览");
		if(camera != null){
			camera.stopPreview();
		}
	}
	
	/**
	 * 释放
	 */
	public void release(){
		Logger.w("释放");
		if (camera != null) {
			camera.stopPreview();
			try {
				camera.setPreviewDisplay(null);
			} catch (IOException e) {
				e.printStackTrace();
			}
			camera.setPreviewCallback(null);
			camera.release();
			camera = null;
		}
	}
	
	/**
	 * 自动对焦
	 */
	public void autoFocus(){
		Logger.w("自动对焦");
		if(camera != null){
			camera.autoFocus(autoFocusCallback);
		}
	}
	
	/**
	 * 拍照
	 */
	public void takePicture(){
		Logger.w("拍照");
		if(camera != null){
			camera.takePicture(shutterCallback, rawPictureCallback, jpegPictureCallback);
		}
	}
	
	/**
	 * 设置闪光模式
	 * @param newFlashMode
	 */
	public void setFlashMode(String newFlashMode){
		Logger.w("设置闪光模式："+newFlashMode);
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
		Logger.w("设置方向："+orientation);
		if(camera != null){
			camera.setDisplayOrientation(orientation);
		}
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Logger.w("SurfaceView创建");
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceCreated(holder);
		}
		if(camera != null){
			try {
				camera.setPreviewDisplay(surfaceHolder);
				camera.setPreviewCallback(previewCallback);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		Logger.w("SurfaceView改变");
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceChanged(holder, format, width, height);
		}
		startPreview();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Logger.w("SurfaceView销毁");
		if(surfaceHolderCallback != null){
			surfaceHolderCallback.surfaceDestroyed(holder);
		}
		stopPreview();
	}
	
	/**
	 * RAW图片回调
	 */
	public interface RawPictureCallback extends Camera.PictureCallback{}

	/**
	 * JPEG图片回调
	 */
	public interface JpegPictureCallback extends Camera.PictureCallback{}
	
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

	public PreviewCallback getPreviewCallback() {
		return previewCallback;
	}

	public void setPreviewCallback(PreviewCallback previewCallback) {
		this.previewCallback = previewCallback;
	}

	public JpegPictureCallback getJpegPictureCallback() {
		return jpegPictureCallback;
	}

	public void setJpegPictureCallback(JpegPictureCallback jpegPictureCallback) {
		this.jpegPictureCallback = jpegPictureCallback;
	}

	public RawPictureCallback getRawPictureCallback() {
		return rawPictureCallback;
	}

	public void setRawPictureCallback(RawPictureCallback rawPictureCallback) {
		this.rawPictureCallback = rawPictureCallback;
	}

	public ShutterCallback getShutterCallback() {
		return shutterCallback;
	}

	public void setShutterCallback(ShutterCallback shutterCallback) {
		this.shutterCallback = shutterCallback;
	}

	public OpenCameraFailCallback getOpenCameraFailCallback() {
		return openCameraFailCallback;
	}

	public void setOpenCameraFailCallback(
			OpenCameraFailCallback openCameraFailCallback) {
		this.openCameraFailCallback = openCameraFailCallback;
	}

	public InitCameraCallback getInitCameraCallback() {
		return initCameraCallback;
	}

	public void setInitCameraCallback(InitCameraCallback initCameraCallback) {
		this.initCameraCallback = initCameraCallback;
	}

	public AutoFocusCallback getAutoFocusCallback() {
		return autoFocusCallback;
	}

	public void setAutoFocusCallback(AutoFocusCallback autoFocusCallback) {
		this.autoFocusCallback = autoFocusCallback;
	}

	public SurfaceHolder.Callback getSurfaceHolderCallback() {
		return surfaceHolderCallback;
	}

	public void setSurfaceHolderCallback(
			SurfaceHolder.Callback surfaceHolderCallback) {
		this.surfaceHolderCallback = surfaceHolderCallback;
	}

	public ErrorCallback getErrorCallback() {
		return errorCallback;
	}

	public void setErrorCallback(ErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	public FaceDetectionListener getFaceDetectionListener() {
		return faceDetectionListener;
	}

	public void setFaceDetectionListener(FaceDetectionListener faceDetectionListener) {
		this.faceDetectionListener = faceDetectionListener;
	}

	public OnZoomChangeListener getZoomChangeListener() {
		return zoomChangeListener;
	}

	public void setZoomChangeListener(OnZoomChangeListener zoomChangeListener) {
		this.zoomChangeListener = zoomChangeListener;
	}

	public int getCurrentCameraId() {
		return currentCameraId;
	}
}