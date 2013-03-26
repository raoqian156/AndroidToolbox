package test.activity.other;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.MyCameraManager;
import me.xiaopan.androidlibrary.util.barcode.Decoder;
import me.xiaopan.androidlibrary.util.barcode.Decoder.DecodeListener;
import me.xiaopan.androidlibrary.util.barcode.ScanFrameView;
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
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * 条码扫描器
 * @author xiaopan
 *
 */
public class BarcodeScannerActivity extends MyBaseActivity implements Camera.ErrorCallback, Camera.PreviewCallback, Camera.AutoFocusCallback, MyCameraManager.InitCameraCallback, MyCameraManager.OpenCameraFailCallback, MyCameraManager.PreviewStateCallback, ResultPointCallback, DecodeListener{
	private SurfaceView surfaceView;	//显示画面的视图
	private ScanFrameView scanFrameView;//扫描框（取景器）
	private TextView resultText;	//显示扫描结果
	private boolean allowDecode;	//允许解码（当解码成功后要停止解码，只有点击屏幕后才会再次开始解码）
	private Decoder decoder;	//解码器
	private SoundPool soundPool;//音效池
	private int beepId;//哔哔音效
	private MyCameraManager cameraManager;
	private long lastFocusTime;
	private RefreshScanFrameRunnable refreshScanFrameRunnable;
	private Handler handler;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.barcode_scanner);
		surfaceView = (SurfaceView) findViewById(R.id.surface_barcodeScanner);
		scanFrameView = (ScanFrameView) findViewById(R.id.scanningFrame_barcodeScanner);
		resultText = (TextView) findViewById(R.id.text_barcodeScanner_result);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		//当点击的是开始对焦
		scanFrameView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startDecode();//开始解码
			}
		});
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		//初始化相机管理器
		cameraManager = new MyCameraManager(surfaceView.getHolder());
		cameraManager.setAutoFocusCallback(this);
		cameraManager.setInitCameraCallback(this);
		cameraManager.setOpenCameraFailCallback(this);
		cameraManager.setPreviewCallback(this);
		cameraManager.setPreviewStateCallback(this);
		cameraManager.setErrorCallback(this);
		
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
	public void onInitCamera(Camera camera) {
		//设置最佳的预览分辨率
		Camera.Size optimalPreviewSize = CameraUtils.getOptimalPreviewSize(getBaseContext(), camera);
		if(optimalPreviewSize != null){
			Camera.Parameters parameters = camera.getParameters();
			parameters.setPreviewSize(optimalPreviewSize.width, optimalPreviewSize.height);
			camera.setParameters(parameters);
		}
		
		cameraManager.setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(this, cameraManager.getCurrentCameraId()));
		
		//如果解码器尚未创建的话，就创建解码器并设置其监听器
		if(decoder == null){
			decoder = new Decoder(getBaseContext(), camera.getParameters(), scanFrameView);
			decoder.setResultPointCallback(this);	//设置可疑点回调
			decoder.setDecodeListener(this);	//设置解码监听器
		}
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		//如果没有对好就继续对
		if (!success) {
			autoFocus();
		}
	}

	/**
	 * 自动对焦
	 */
	private void autoFocus() {
		long currentTime = System.currentTimeMillis();
		if(lastFocusTime == 0 || currentTime - lastFocusTime >= 3000){
			resultText.setText(null);
			cameraManager.autoFocus();
			lastFocusTime = currentTime;
		}
	}
	
	/**
	 * 开始解码
	 */
	private void startDecode(){
		if(decoder != null){
			allowDecode = true;//设置允许解码
			startRefreshScanFrame();//开始刷新扫描框
			autoFocus();// 自动对焦
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
	
	/**
	 * 处理结果
	 * @param result
	 * @param barcodeBitmap
	 */
	private void handleResult(Result result, Bitmap barcodeBitmap){
		//显示扫描结果
		resultText.setText(result.getBarcodeFormat().toString() + "：" + result.getText());
		//将条码图片显示在扫描框中
		scanFrameView.drawResultBitmap(barcodeBitmap);
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

	@Override
	public void onDecodeFail() {
		autoFocus();//继续对焦
	}

	@Override
	public void onOpenCameraFail(Exception e) {
		toastL(R.string.comm_hint_cameraOpenFailed);
		becauseExceptionFinishActivity();
	}

	@Override
	protected boolean isRemoveTitleBar() {
		return true;
	}

	@Override
	protected boolean isFullScreen() {
		return true;
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

	@Override
	protected void onDestroy() {
		cameraManager = null;
		soundPool.release();
		soundPool = null;
		decoder = null;
		refreshScanFrameRunnable = null;
		handler = null;
		super.onDestroy();
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
	public void onError(int error, Camera camera) {
		
	}
}