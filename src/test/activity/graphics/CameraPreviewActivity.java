package test.activity.graphics;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.CameraManager;
import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.SystemUtils;
import test.MyBaseActivity;
import android.hardware.Camera;
import android.hardware.Camera.Face;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 自定义相机
 * @author xiaopan
 *
 */
public class CameraPreviewActivity extends MyBaseActivity implements Camera.ShutterCallback, Camera.ErrorCallback, Camera.FaceDetectionListener, Camera.OnZoomChangeListener, Camera.PreviewCallback, Camera.AutoFocusCallback, CameraManager.InitCameraCallback, CameraManager.JpegPictureCallback, CameraManager.OpenCameraFailCallback, CameraManager.RawPictureCallback{
	private SurfaceView surfaceView;
	private ImageButton flashModeImageButton;
	private List<String> supportedFlashModes;
	private CameraManager cameraManager;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.camera_preview);
		surfaceView = (SurfaceView) findViewById(R.id.surface_cameraPreview);
		flashModeImageButton = (ImageButton) findViewById(R.id.imageButton_cameraPreview_flashMode);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		//点击显示界面的时候对焦
		surfaceView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				cameraManager.autoFocus();
			}
		});
		
		//点击闪光模式按钮，就按照支持的闪光模式依次更新
		flashModeImageButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新的闪光模式
				String newFlashMode = supportedFlashModes.get((supportedFlashModes.indexOf(flashModeImageButton.getTag()) + 1) % supportedFlashModes.size());
				setFlashModeImageButton(newFlashMode);
				cameraManager.setFlashMode(newFlashMode);
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		cameraManager = new CameraManager(surfaceView.getHolder());
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
	protected boolean isRemoveTitleBar() {
		return true;
	}

	@Override
	protected boolean isFullScreen() {
		return true;
	}
	
	@Override
	public void onInitCamera(Camera camera) {
		Parameters parameters = camera.getParameters();
		
		//设置闪光模式
		supportedFlashModes = new ArrayList<String>(3);
		supportedFlashModes.add(Camera.Parameters.FLASH_MODE_OFF);
		supportedFlashModes.add(Camera.Parameters.FLASH_MODE_ON);
		if(parameters.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_AUTO)){
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
			supportedFlashModes.add(Camera.Parameters.FLASH_MODE_AUTO);
			setFlashModeImageButton(Camera.Parameters.FLASH_MODE_AUTO);
		}else{
			parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			setFlashModeImageButton(Camera.Parameters.FLASH_MODE_OFF);
		}
		
		//设置最佳的预览分辨率
		Camera.Size optimalPreviewSize = CameraUtils.getOptimalPreviewSize(getBaseContext(), camera);
		if(optimalPreviewSize != null){
			parameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
			parameters.setPictureSize(optimalPreviewSize.width, optimalPreviewSize.height);
		}
		
		camera.setParameters(parameters);
		
		//设置预览界面旋转角度
		if(SystemUtils.getAPILevel() >= 9){
			cameraManager.setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(this, cameraManager.getCurrentCameraId()));
		}else{
			//如果是当前竖屏就将预览角度顺时针旋转90度
			if (!AndroidUtils.isLandscape(getBaseContext())) {
				camera.setDisplayOrientation(90);
			}
		}
	}
	
	/**
	 * 设置闪光模式切换按钮
	 * @param falshMode
	 */
	private void setFlashModeImageButton(String falshMode){
		if(Camera.Parameters.FLASH_MODE_AUTO.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.ic_flash_auto);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_AUTO);
		}else if(Camera.Parameters.FLASH_MODE_OFF.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.ic_flash_off);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_OFF);
		}else if(Camera.Parameters.FLASH_MODE_ON.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.ic_flash_on);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_ON);
		}
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {

	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		
	}

	@Override
	public void onShutter() {
		
	}

	@Override
	public void onPictureTakenJpeg(byte[] data, Camera camera) {

	}

	@Override
	public void onPictureTakenRaw(byte[] data, Camera camera) {
		
	}

	@Override
	public void onOpenCameraFail(Exception e) {
		toastL(R.string.comm_hint_cameraOpenFailed);
		becauseExceptionFinishActivity();
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