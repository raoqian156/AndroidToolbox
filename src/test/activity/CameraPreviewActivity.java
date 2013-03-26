package test.activity;

import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.MyCameraManager;
import test.MyBaseActivity;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 自定义相机
 * @author xiaopan
 *
 */
public class CameraPreviewActivity extends MyBaseActivity implements Camera.ShutterCallback, Camera.ErrorCallback, Camera.FaceDetectionListener, Camera.OnZoomChangeListener, Camera.PreviewCallback, Camera.AutoFocusCallback, MyCameraManager.InitCameraCallback, MyCameraManager.JpegPictureCallback, MyCameraManager.OpenCameraFailCallback, MyCameraManager.RawPictureCallback{
	private SurfaceView surfaceView;
	private MyCameraManager cameraManager;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		surfaceView = new SurfaceView(getBaseContext());
		setContentView(surfaceView);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		surfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cameraManager.autoFocus();
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		cameraManager = new MyCameraManager(surfaceView.getHolder());
		cameraManager.setAutoFocusCallback(this);
		cameraManager.setInitCameraCallback(this);
		cameraManager.setJpegPictureCallback(this);
		cameraManager.setOpenCameraFailCallback(this);
		cameraManager.setPreviewCallback(this);
		cameraManager.setRawPictureCallback(this);
		cameraManager.setShutterCallback(this);
		cameraManager.setErrorCallback(this);
		cameraManager.setFaceDetectionListener(this);
		cameraManager.setZoomChangeListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		cameraManager.openBackCamera();
	}

	@Override
	protected void onPause() {
		super.onPause();
		cameraManager.release();
	}
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {

	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		
	}

	@Override
	public void onPictureTakenRaw(byte[] data, Camera camera) {
		
	}

	@Override
	public void onPictureTakenJpeg(byte[] data, Camera camera) {
		
	}

	@Override
	public void onOpenCameraFail(Exception e) {
		
	}

	@Override
	public void onInitCamera(Camera camera) {
		cameraManager.setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(this, cameraManager.getCurrentCameraId()));
	}

	@Override
	public void onShutter() {
		
	}

	@Override
	public void onZoomChange(int zoomValue, boolean stopped, Camera camera) {
		
	}

	@Override
	public void onFaceDetection(Face[] faces, Camera camera) {
		
	}

	@Override
	public void onError(int error, Camera camera) {
		
	}
}