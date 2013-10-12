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
package test.activity.graphics;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easy.android.R;
import me.xiaopan.easy.android.util.CameraManager;
import test.MyBaseActivity;
import android.hardware.Camera;
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
public class CameraPreviewActivity extends MyBaseActivity implements CameraManager.CameraCallback{
	private SurfaceView surfaceView;
	private ImageButton flashModeImageButton;
	private List<String> supportedFlashModes;
	private CameraManager cameraManager;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		hiddenTitleBar();
		hiddenStatusBar();
		setContentView(R.layout.activity_camera_preview);
		surfaceView = (SurfaceView) findViewById(R.id.surface_cameraPreview);
		flashModeImageButton = (ImageButton) findViewById(R.id.imageButton_cameraPreview_flashMode);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
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
	public void onInitData(Bundle savedInstanceState) {
		cameraManager = new CameraManager(this, surfaceView.getHolder(), this);
	}

	@Override
	public void onResume() {
		super.onResume();
		cameraManager.openBackCamera();
	}

	@Override
	public void onPause() {
		super.onPause();
		cameraManager.release();
	}

	@Override
	public void onDestroy() {
		cameraManager = null;
		super.onDestroy();
	}
	
	@Override
	public void onInitCamera(Camera camera) {
		/* 设置闪光模式 */
		supportedFlashModes = new ArrayList<String>(3);
		supportedFlashModes.add(Camera.Parameters.FLASH_MODE_OFF);
		supportedFlashModes.add(Camera.Parameters.FLASH_MODE_ON);
		Camera.Parameters cameraParameters = camera.getParameters();
		if(cameraParameters.getSupportedFlashModes().contains(Camera.Parameters.FLASH_MODE_AUTO)){
			cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
			supportedFlashModes.add(Camera.Parameters.FLASH_MODE_AUTO);
			setFlashModeImageButton(Camera.Parameters.FLASH_MODE_AUTO);
		}else{
			cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			setFlashModeImageButton(Camera.Parameters.FLASH_MODE_OFF);
		}
		camera.setParameters(cameraParameters);
	}

	@Override
	public void onOpenCameraException(Exception e) {
		toastL(R.string.toast_cameraOpenFailed);
		becauseExceptionFinishActivity();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		
	}

	@Override
	public void onStartPreview() {
		
	}

	@Override
	public void onStopPreview() {
		
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
