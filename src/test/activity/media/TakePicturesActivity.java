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
package test.activity.media;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.util.AnimationUtils;
import me.xiaopan.easyandroid.util.CameraManager;
import me.xiaopan.easyandroid.util.CameraUtils;
import me.xiaopan.easyandroid.util.SystemUtils;
import me.xiaopan.easyandroid.util.WindowUtils;
import me.xiaopan.easyjava.util.FileUtils;
import test.MyBaseActivity;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * 拍照
 * @author xiaopan
 */
public class TakePicturesActivity extends MyBaseActivity implements CameraManager.CameraCallback, Camera.PictureCallback{
	private SurfaceView surfaceView;
	private Button takeButton;
	private Button confirmButton;
	private Button remakeButton;
	private ImageButton flashModeImageButton;
	private List<String> supportedFlashModes;
	private boolean readTakePhotos;//准备拍照
	private CameraManager cameraManager;

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}

	@Override
	protected boolean isFullScreen() {
		return true;
	}
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_take_pictures);
		surfaceView = (SurfaceView) findViewById(R.id.surface_takePictures);
		takeButton = (Button) findViewById(R.id.button_takePicture_take);
		confirmButton = (Button) findViewById(R.id.button_takePictures_confirm);
		remakeButton = (Button) findViewById(R.id.button_takePictures_remake);
		flashModeImageButton = (ImageButton) findViewById(R.id.imageButton_takePictures_flashMode);
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
		cameraManager = new CameraManager(surfaceView.getHolder(), this);
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
	protected void onDestroy() {
		cameraManager = null;
		super.onDestroy();
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
			if (!WindowUtils.isLandscape(getBaseContext())) {
				camera.setDisplayOrientation(90);
			}
		}
	}

	@Override
	public void onOpenCameraException(Exception e) {
		toastL(R.string.toast_cameraOpenFailed);
		becauseExceptionFinishActivity();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		//如果对焦成功
		if(success){
			//如果准备拍摄照片
			if(readTakePhotos){
				readTakePhotos = false;
				cameraManager.takePicture(null, null, this);
			}
		}else{
			//继续对焦
			cameraManager.autoFocus();
		}
	}

	@Override
	public void onStartPreview() {
		
	}

	@Override
	public void onStopPreview() {
		
	}

	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
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
	
	/**
	 * 设置闪光模式切换按钮
	 * @param falshMode
	 */
	private void setFlashModeImageButton(String falshMode){
		if(Camera.Parameters.FLASH_MODE_AUTO.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.icon_flash_auto);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_AUTO);
		}else if(Camera.Parameters.FLASH_MODE_OFF.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.icon_flash_off);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_OFF);
		}else if(Camera.Parameters.FLASH_MODE_ON.equals(falshMode)){
			flashModeImageButton.setImageResource(R.drawable.icon_flash_on);
			flashModeImageButton.setTag(Camera.Parameters.FLASH_MODE_ON);
		}
	}
}
