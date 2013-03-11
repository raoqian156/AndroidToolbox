package test.activity;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.util.AndroidUtils;
import me.xiaopan.androidlibrary.util.CameraManager;
import me.xiaopan.androidlibrary.util.CameraUtils;
import me.xiaopan.androidlibrary.util.Size;
import me.xiaopan.androidlibrary.util.barcode.Decoder;
import me.xiaopan.androidlibrary.util.barcode.Decoder.DecodeListener;
import me.xiaopan.androidlibrary.util.barcode.ScanFrameView;
import test.MyBaseActivity;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;

/**
 * 条码扫描仪
 * @author xiaopan
 *
 */
public class BarcodeScannerActivity extends MyBaseActivity implements DecodeListener, CameraManager.Listener, ResultPointCallback, SurfaceHolder.Callback{
	private SurfaceView surfaceView;	//显示画面的视图
	private ScanFrameView scanFrameView;//扫描框（取景器）
	private TextView resultText;	//显示扫描结果
	private boolean allowDecode;	//允许解码（当解码成功后要停止解码，只有点击屏幕后才会再次开始解码）
	private Decoder decoder;	//解码器
	private SoundPool soundPool;//音效池
	private int beepId;//哔哔音效
	private CameraManager cameraManager;
	private boolean hasSurface;
	private long lastFocusTime;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.barcode_scanner);
		surfaceView = (SurfaceView) findViewById(R.id.surface_barCodeScanner);
		scanFrameView = (ScanFrameView) findViewById(R.id.scanningFrame_barCodeScanner);
		resultText = (TextView) findViewById(R.id.text_barCodeScanner_result);
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		//设置扫描框的描边宽度
		scanFrameView.setStrokeWidth(2);
		
		//初始化音效
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		beepId = soundPool.load(getBaseContext(), R.raw.beep, 100);
		
		//初始化相机管理器
		cameraManager = new CameraManager();
		cameraManager.setListener(this);
		
		//初始化Surface持有器
		surfaceView.getHolder().addCallback(this);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		//当点击的是开始对焦
		scanFrameView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//继续解码
				allowDecode = true;
				//对焦
				focus();
			}
		});
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
		super.onPause();
		/*
		 * 当暂停的时候释放相机，并暂停解码器，并停止解码
		 */
		if(cameraManager != null){
			cameraManager.release();
		}
		if(decoder != null){
			decoder.pause();
		}
		allowDecode = false;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		/*
		 * 当要销毁Activity的时候记得释放相机管理器、音效池以及解码器
		 */
		cameraManager = null;
		soundPool.release();
		soundPool = null;
		decoder = null;
	}

	@Override
	public void onInitCamera(Camera camera) {
		//如果是当前竖屏就将预览角度顺时针旋转90度
		if (!AndroidUtils.isLandscape(getBaseContext())) {
			camera.setDisplayOrientation(90);
		}
		
		//设置最佳的预览分辨率
		Camera.Parameters parameters = camera.getParameters();
		Size bestSize = CameraUtils.getBestPreviewSize(getBaseContext(), parameters);
		parameters.setPictureSize(bestSize.getWidth(), bestSize.getHeight());
		camera.setParameters(parameters);
		
		//如果解码器尚未创建的话，就创建解码器并设置其监听器
		if(decoder == null){
			decoder = new Decoder(getBaseContext(), camera.getParameters(), scanFrameView);
			decoder.setResultPointCallback(this);	//设置可疑点回调
			decoder.setDecodeListener(this);	//设置解码监听器
		}
		
		//允许解码
		allowDecode = true;
	}

	@Override
	public void onAutoFocus(boolean success, Camera camera) {
		//如果没有对好就继续对
		if (!success) {
			focus();
		}
	}

	/**
	 * 对焦
	 */
	private void focus() {
		long currentTime = System.currentTimeMillis();
		if(lastFocusTime == 0 || currentTime - lastFocusTime >= 3000){
			resultText.setText(null);
			cameraManager.autoFocus();
			lastFocusTime = currentTime;
		}
	}
	
	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		//如果允许解码，就尝试解码并刷新扫描框
		if (allowDecode) {
			decoder.tryDecode(data);
			scanFrameView.refresh();
		}
	}
	
	@Override
	public void foundPossibleResultPoint(ResultPoint arg0) {
		scanFrameView.addPossibleResultPoint(arg0);
	}

	@Override
	public void onDecodeSuccess(Result result, Bitmap barcode) {
		//显示扫描结果
		resultText.setText(result.getBarcodeFormat().toString() + "：" + result.getText());
		//将条码图片显示在扫描框中
		scanFrameView.drawResultBitmap(barcode);
		//播放音效
		AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
		soundPool.play(beepId, audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), audioManager.getStreamVolume(AudioManager.STREAM_MUSIC), 100, 0, 1);
		//发出震动提示
		((Vibrator) getSystemService(VIBRATOR_SERVICE)).vibrate(200);
		//停止解码
		allowDecode = false;
	}

	@Override
	public void onDecodeFail() {
		//继续解码
		allowDecode = true;
		//对焦
		focus();
	}

	@Override
	public void onException(Exception e) {
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}
	
	@Override
	public void onShutter() {}

	@Override
	public void onPictureTakenJpeg(byte[] data, Camera camera) {}

	@Override
	public void onPictureTakenRaw(byte[] data, Camera camera) {}
}