package test.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.AnimationUtils;
import me.xiaopan.androidlibrary.util.CameraManager;
import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.Size;
import me.xiaopan.javalibrary.util.FileUtils;
import test.MyBaseActivity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 自定义相机
 * @author xiaopan
 *
 */
public class CustomCameraActivity extends MyBaseActivity implements CameraManager.Listener, SurfaceHolder.Callback{
	private SurfaceView surfaceView;
	private Button takeButton;
	private Button confirmButton;
	private Button remakeButton;
	private ImageButton flashModeImageButton;
	private List<String> supportedFlashModes;
	private boolean readTakePhotos;//准备拍照
	private CameraManager cameraManager;
	private boolean hasSurface;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.custom_camera);
		surfaceView = (SurfaceView) findViewById(R.id.surface_customCamera);
		takeButton = (Button) findViewById(R.id.button_customCamera_take);
		confirmButton = (Button) findViewById(R.id.button_customCamera_confirm);
		remakeButton = (Button) findViewById(R.id.button_customCamera_remake);
		flashModeImageButton = (ImageButton) findViewById(R.id.imageButton_customCamera_flashMode);
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
		
		//按下拍摄按钮的时候会先对焦，对完焦再拍照
		takeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				readTakePhotos = true;
				cameraManager.autoFocus();
			}
		});
		
		//点击确定跳转到拍名片预登记界面
		confirmButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setResult(RESULT_OK);
				finishActivity();
			}
		});
		
		//点击重拍，显示拍摄按钮遮盖确定、重拍按钮并开始预览
		remakeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				AlphaAnimation alphaAnimation = AnimationUtils.getShowAlphaAnimation();
				alphaAnimation.setAnimationListener(new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {}
					
					@Override
					public void onAnimationRepeat(Animation animation) {}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						takeButton.setVisibility(View.VISIBLE);
						confirmButton.setVisibility(View.INVISIBLE);
						remakeButton.setVisibility(View.INVISIBLE);
					}
				});
				takeButton.startAnimation(alphaAnimation);
				cameraManager.startPreview();
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
		//初始化相机管理器
		cameraManager = new CameraManager();
		cameraManager.setOutPictureSize(new Size(640, 480));
		cameraManager.setListener(this);
		
		//初始化Surface持有器
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
		surfaceView.getHolder().addCallback(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		/*
		 * 如果当前拥有Surface（在此之前Surface没有被销毁），就在这里去打开相机并开始预览、对焦
		 */
		if(hasSurface){
			cameraManager.openCamera(surfaceView.getHolder());
			cameraManager.startPreview();
			cameraManager.autoFocus();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		/*
		 * 如果是第一次或者Surface之前被销毁了，就在这里去打开相机并开始预览、对焦
		 */
		if(!hasSurface){
			hasSurface = true;
			cameraManager.openCamera(surfaceView.getHolder());
			cameraManager.startPreview();
			cameraManager.autoFocus();
		}
	}

	@Override
	protected void onPause() {
		/*
		 * 当暂停的时候释放相机
		 */
		if(cameraManager != null){
			cameraManager.release();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*
		 * 当要销毁Activity的时候记得释放相机管理器
		 */
		cameraManager = null;
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
		}
		
		camera.setParameters(parameters);
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
		//如果对焦成功
		if(success){
			//如果准备拍摄照片
			if(readTakePhotos){
				readTakePhotos = false;
				cameraManager.takePicture();
			}
		}else{
			//继续对焦
			cameraManager.autoFocus();
		}
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		
	}

	@Override
	public void onShutter() {
		
	}

	@Override
	public void onPictureTakenJpeg(byte[] data, Camera camera) {
		//隐藏拍摄按钮露出确定、重拍按钮
		confirmButton.setVisibility(View.VISIBLE);
		remakeButton.setVisibility(View.VISIBLE);
		AlphaAnimation alphaAnimation = AnimationUtils.getHiddenAlphaAnimation();
		alphaAnimation.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {}
			
			@Override
			public void onAnimationRepeat(Animation animation) {}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				takeButton.setVisibility(View.GONE);
			}
		});
		takeButton.startAnimation(alphaAnimation);
		
		//保存名片
		try {
			FileUtils.writeByte(getPhotoFile(), data, false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPictureTakenRaw(byte[] data, Camera camera) {
		
	}

	@Override
	public void onException(Exception e) {
		toastL(R.string.comm_hint_cameraOpenFailed);
		becauseExceptionFinishActivity();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}
}