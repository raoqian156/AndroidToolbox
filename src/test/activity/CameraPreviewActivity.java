package test.activity;

import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.Logger;
import me.xiaopan.androidlibrary.util.MyCameraManager;
import test.MyBaseActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 自定义相机
 * @author xiaopan
 *
 */
public class CameraPreviewActivity extends MyBaseActivity implements SurfaceHolder.Callback, Camera.ShutterCallback, Camera.ErrorCallback, Camera.FaceDetectionListener, Camera.OnZoomChangeListener, Camera.PreviewCallback, Camera.AutoFocusCallback, MyCameraManager.InitCameraCallback, MyCameraManager.JpegPictureCallback, MyCameraManager.OpenCameraFailCallback, MyCameraManager.RawPictureCallback{
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private MyCameraManager cameraManager;
	private MyScreenReceiver myScreenReceiver;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		surfaceView = new SurfaceView(getBaseContext());
		surfaceHolder = surfaceView.getHolder();
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
		cameraManager = new MyCameraManager(surfaceHolder);
		cameraManager.setAutoFocusCallback(this);
		cameraManager.setInitCameraCallback(this);
		cameraManager.setJpegPictureCallback(this);
		cameraManager.setOpenCameraFailCallback(this);
		cameraManager.setPreviewCallback(this);
		cameraManager.setRawPictureCallback(this);
		cameraManager.setShutterCallback(this);
		cameraManager.setSurfaceHolderCallback(this);
		cameraManager.setErrorCallback(this);
		cameraManager.setFaceDetectionListener(this);
		cameraManager.setZoomChangeListener(this);
		
		myScreenReceiver = new MyScreenReceiver();
		registerReceiver(myScreenReceiver, new IntentFilter(Intent.ACTION_SCREEN_ON));
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
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(myScreenReceiver);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		cameraManager.setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(this, cameraManager.getCurrentCameraId()));
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {

	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		
	}

	@Override
	public void onOpenCameraFail(Exception e) {
		
	}

	@Override
	public void onInitCamera(Camera camera) {
		
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
	
	class MyScreenReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getAction().equals(Intent.ACTION_SCREEN_OFF)){
				Logger.w("锁屏");
			}else if(intent.getAction().equals(Intent.ACTION_SCREEN_ON)){
				Logger.w("开屏");
//				int fangxiang = CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(CameraPreviewActivity.this, cameraManager.getCurrentCameraId());
//				if(fangxiang == 90 ||fangxiang == 270){
//				}
			}
		}
	}
}