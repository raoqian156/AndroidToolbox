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
package test.activity.other;

import me.xiaopan.easy.android.util.CameraManager;
import me.xiaopan.easy.android.util.CameraUtils;
import me.xiaopan.easy.android.util.barcode.BarcodeCameraApertureView;
import me.xiaopan.easy.android.util.barcode.Decoder;
import me.xiaopan.easy.android.util.barcode.Decoder.DecodeListener;
import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * 条码扫描仪
 */
public class BarcodeScannerActivity extends MyBaseActivity implements CameraManager.CameraCallback, Camera.PreviewCallback, ResultPointCallback, DecodeListener{
	private static final String STATE_FLASH_CHECKED = "STATE_FLASH_CHECKED";
	public static final String RETURN_BARCODE_CONTENT = "RETURN_BARCODE_CONTENT";
	private int beepId;//哔哔音效
	private boolean allowDecode;	//允许解码（当解码成功后要停止解码，只有点击屏幕后才会再次开始解码）
	private SurfaceView surfaceView;	//显示画面的视图
	private BarcodeCameraApertureView scanFrameView;//扫描框（取景器）
	private Decoder decoder;	//解码器
	private SoundPool soundPool;//音效池
	private CameraManager cameraManager;
	private RefreshScanFrameRunnable refreshScanFrameRunnable;
	private Handler handler;
	private ToggleButton flashButton;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		hiddenTitleBar();
		hiddenStatusBar();
		setContentView(R.layout.activity_barcode_scanner);
		surfaceView = (SurfaceView) findViewById(R.id.surface_barcodeScanner);
		scanFrameView = (BarcodeCameraApertureView) findViewById(R.id.scanningFrame_barcodeScanner);
		flashButton = (ToggleButton) findViewById(R.id.checkBox_barcodeScanner_flash);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		//当点击的是开始对焦
		scanFrameView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDecode();//开始解码
			}
		});
		
		flashButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				setEnableTorckFlashMode(isChecked);
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		if(savedInstanceState != null){
			flashButton.setChecked(savedInstanceState.getBoolean(STATE_FLASH_CHECKED));
		}
		
		//初始化相机管理器
		cameraManager = new CameraManager(this, surfaceView.getHolder(), this);
		cameraManager.setFocusIntervalTime(3000);
		
		//初始化刷新扫描框的处理器
		handler = new Handler();
		refreshScanFrameRunnable = new RefreshScanFrameRunnable();
		
		//设置扫描框的描边宽度
		scanFrameView.setStrokeWidth(2);
		
		//初始化音效
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		beepId = soundPool.load(getBaseContext(), R.raw.beep, 100);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		cameraManager.openBackCamera();
		setEnableTorckFlashMode(flashButton.isChecked());
	}	
	
	@Override
	public void onPause() {
		super.onPause();
		cameraManager.release();
	}

	@Override
	public void onDestroy() {
		cameraManager = null;
		soundPool.release();
		soundPool = null;
		decoder = null;
		refreshScanFrameRunnable = null;
		handler = null;
		super.onDestroy();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean(STATE_FLASH_CHECKED, flashButton.isChecked());
		super.onSaveInstanceState(outState);
	}
	
	@Override
	public void onInitCamera(Camera camera, Camera.Parameters cameraParameters) {
		//设置预览回调
		camera.setPreviewCallback(this);
		
		//如果解码器尚未创建的话，就创建解码器并设置其监听器
		if(decoder == null){
			decoder = new Decoder(getBaseContext(), camera.getParameters(), scanFrameView);
			decoder.setResultPointCallback(this);	//设置可疑点回调
			decoder.setDecodeListener(this);	//设置解码监听器
		}
	}

	@Override
	public void onOpenCameraException(Exception e) {
		toastL(R.string.toast_cameraOpenFailed);
		becauseExceptionFinishActivity();
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		//如果没有对好就继续对
		if (!success) {
			cameraManager.autoFocus();
		}
	}

	@Override
	public void onStartPreview() {
		startDecode();//开始解码
	}

	@Override
	public void onStopPreview() {
		stopDecode();//停止解码
	}
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		//如果允许解码，就尝试解码
		if (allowDecode) {
			decoder.tryDecode(data);
		}
	}
	
	@Override
	public void foundPossibleResultPoint(ResultPoint arg0) {
		scanFrameView.addPossibleResultPoint(arg0);
	}

	@Override
	public void onDecodeSuccess(Result result, Bitmap barcodeBitmap) {
		stopDecode();//停止解码
		playSound();//播放音效
		playVibrator();//发出震动提示
		handleResult(result, barcodeBitmap);//处理结果
	}

	@Override
	public void onDecodeFail() {
		if(cameraManager != null){
			cameraManager.autoFocus();//继续对焦
		}
	}

	private class RefreshScanFrameRunnable implements Runnable{
		@Override
		public void run() {
			if(scanFrameView != null && handler != null){
				scanFrameView.refresh();
				handler.postDelayed(refreshScanFrameRunnable, 50);
			}
		}
	}

	/**
	 * 开始解码
	 */
	private void startDecode(){
		if(decoder != null){
			allowDecode = true;//设置允许解码
			startRefreshScanFrame();//开始刷新扫描框
			cameraManager.autoFocus();// 自动对焦
		}
	}
	
	/**
	 * 停止解码
	 */
	private void stopDecode(){
		if(decoder != null){
			stopRefreshScanFrame();//停止刷新扫描框
			allowDecode = false;
			decoder.pause();//停止解码器
		}
	}
	
	/**
	 * 开始刷新扫描框
	 */
	public void startRefreshScanFrame(){
		handler.post(refreshScanFrameRunnable);
	}
	
	/**
	 * 停止刷新扫描框
	 */
	public void stopRefreshScanFrame(){
		handler.removeCallbacks(refreshScanFrameRunnable);
	}
	
	/**
	 * 处理结果
	 * @param result
	 * @param barcodeBitmap
	 */
	private void handleResult(Result result, Bitmap barcodeBitmap){
		scanFrameView.drawResultBitmap(barcodeBitmap);
		getIntent().putExtra(RETURN_BARCODE_CONTENT, result.getText());
		setResult(RESULT_OK, getIntent());
		finishActivity();
	}
	
	/**
	 * 播放音效
	 */
	private void playSound(){
		//播放音效
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		if(audioManager.getRingerMode() == AudioManager.RINGER_MODE_NORMAL){
			float volume = (float) (((float) audioManager.getStreamVolume(AudioManager.STREAM_MUSIC) / 15) / 3.0);
			soundPool.play(beepId, volume, volume, 100, 0, 1);
		}
	}
	
	/**
	 * 震动
	 */
	private void playVibrator(){
		((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(200);//发出震动提示
	}
	
	/**
	 * 设置是否激活常亮闪光模式
	 * @param enable
	 */
	private void setEnableTorckFlashMode(boolean enable){
		if(cameraManager != null){
			if(enable){
				if(CameraUtils.isSupportFlashMode(cameraManager.getCamera(), Camera.Parameters.FLASH_MODE_TORCH)){
					cameraManager.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
				}else{
					toastL(R.string.toast_barcodeScanner_notSupport);
					flashButton.setChecked(false);
				}
			}else{
				cameraManager.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			}
		}
	}
}