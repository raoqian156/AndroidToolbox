package test.activity.other;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.util.BitmapUtils;
import me.xiaopan.easyandroid.util.CameraManager;
import me.xiaopan.easyandroid.util.CameraUtils;
import me.xiaopan.easyandroid.util.SystemUtils;
import me.xiaopan.easyandroid.util.ViewAnimationUtils;
import me.xiaopan.easyandroid.util.WindowUtils;
import me.xiaopan.easyjava.util.IOUtils;
import test.MyBaseActivity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.net.Uri;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.Button;
import android.widget.ImageView;

/**
 * 拍名片
 */
public class TakeBusinessCardActivity extends MyBaseActivity implements CameraManager.CameraCallback, Camera.PictureCallback{
	/**
	 * 返回 - 名片文件路径
	 */
	public static final String RETURN_BUSINESS_CARD_FILE_PATH = "RETURN_BUSINESS_CARD_FILE_PATH";
	private boolean readTakePhotos;//准备拍照
	private View cameraApertureView;	//取景框视图
	private View shutterButton;	//快门按钮
	private View userButton;	//使用按钮
	private View remakeButton;	//重拍按钮
	private Button flashModeButton;	//闪光等控制按钮
	private ImageView previewImage;	//预览图
	private SurfaceView surfaceView;	//Surface视图
	private File localCacheFile;	//本地缓存文件爱你
	private Rect cameraApertureRect;	//取景框的位置
	private List<String> supportedFlashModes;	//当前设备支持的闪光模式
	private CameraManager cameraManager;	//相机管理器
	private Camera.Size previewSize;
	private boolean landscape;

	@Override
	public boolean isRemoveTitleBar() {
		return true;
	}

	@Override
	public boolean isRemoveStatusBar() {
		return true;
	}
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_take_business_card);
		surfaceView = (SurfaceView) findViewById(R.id.surface_takeBusinessCard);
		cameraApertureView = findViewById(R.id.view_takeBusinessCard_cameraAperture);
		shutterButton = findViewById(R.id.button_takeBusinessCard_shutter);
		userButton = findViewById(R.id.button_takeBusinessCard_use);
		remakeButton = findViewById(R.id.button_takeBusinessCard_remake);
		flashModeButton = (Button) findViewById(R.id.button_takeBusinessCard_flashMode);
		previewImage = (ImageView) findViewById(R.id.image_takeBusinessCard_preview);
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
		
		//按下拍摄按钮的时候会先对焦，对完焦再拍照
		shutterButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				readTakePhotos = true;
				cameraManager.autoFocus();
			}
		});
		
		//点击闪光模式按钮，就按照支持的闪光模式依次更新
		flashModeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//新的闪光模式
				String newFlashMode = supportedFlashModes.get((supportedFlashModes.indexOf(flashModeButton.getTag()) + 1) % supportedFlashModes.size());
				setFlashModeImageButton(newFlashMode);
				cameraManager.setFlashMode(newFlashMode);
			}
		});
		
		//按钮使用按钮后，先对裁剪后的图片进行缩小，然后输出到本地缓存文件中
		userButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bitmap sourceBitmap = null;
				Bitmap finalBitmap = null;
				FileOutputStream fileOutputStream = null;
				try {
					//对原图进行缩小
					sourceBitmap = BitmapFactory.decodeFile(localCacheFile.getPath());
					finalBitmap = BitmapUtils.scale(sourceBitmap, 648, 388);	//对裁剪后的图片进行缩小
					sourceBitmap.recycle();
					
					//将最终得到的图片输出到本地缓存文件中
					if(!localCacheFile.exists()) localCacheFile.createNewFile();
					fileOutputStream = new FileOutputStream(localCacheFile);
					finalBitmap.compress(CompressFormat.JPEG, 90, fileOutputStream);	//输出到本地缓存文件中
					fileOutputStream.flush();
					fileOutputStream.close();
					finalBitmap.recycle();
					
					//返回结果
					getIntent().putExtra(RETURN_BUSINESS_CARD_FILE_PATH, localCacheFile.getPath());
					setResult(RESULT_OK, getIntent());
					finishActivity();
				} catch (IOException e) {
					e.printStackTrace();
					if(finalBitmap != null){
						finalBitmap.recycle();
					}
					if(sourceBitmap != null){
						sourceBitmap.recycle();
					}
					if(fileOutputStream != null){
						try {
							fileOutputStream.flush();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						try {
							fileOutputStream.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
					toastS("保存失败，请重试");
				}
			}
		});
		
		//按钮重拍按钮后，先释放裁剪后的图片，然后隐藏使用、重拍按钮并显示快门按钮，最后在动画执行完毕之后将预览视图清空
		remakeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//渐隐快门按钮并渐现使用、重拍按钮
				ViewAnimationUtils.visibleViewByAlpha(shutterButton);
				ViewAnimationUtils.invisibleViewByAlpha(userButton);
				ViewAnimationUtils.invisibleViewByAlpha(remakeButton, new AnimationListener() {
					@Override
					public void onAnimationStart(Animation animation) {}
					@Override
					public void onAnimationRepeat(Animation animation) {}
					@Override
					public void onAnimationEnd(Animation animation) {
						previewImage.setImageDrawable(null);	//将预览视图清空
						cameraManager.startPreview();
					}
				});
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		landscape = WindowUtils.isLandscape(getBaseContext());
		cameraManager = new CameraManager(surfaceView.getHolder(), this);
		shutterButton.setVisibility(View.VISIBLE);
		userButton.setVisibility(View.INVISIBLE);
		remakeButton.setVisibility(View.INVISIBLE);
		localCacheFile =getFileFromDynamicCacheDir("BusinessCardCache.jpeg");
		if(!localCacheFile.exists()){
			try {
				localCacheFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				becauseExceptionFinishActivity();
			}
		}
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

		//设置最佳预览和图片输出分辨率
		Camera.Size bestSize = CameraUtils.getBestPreviewAndPictureSize(getBaseContext(), camera);
		if(bestSize != null){
			parameters.setPreviewSize(bestSize.width, bestSize.height);
			parameters.setPictureSize(bestSize.width, bestSize.height);
		}else{
			parameters.setPreviewSize(640, 480);
			parameters.setPictureSize(640, 480);
		}
		
		camera.setParameters(parameters);

		previewSize = parameters.getPictureSize();
		
		//设置预览界面旋转角度
		if(SystemUtils.getAPILevel() >= 9){
			cameraManager.setDisplayOrientation(CameraUtils.getOptimalDisplayOrientationByWindowDisplayRotation(this, cameraManager.getCurrentCameraId()));
		}else{
			//如果是当前竖屏就将预览角度顺时针旋转90度
			if (!landscape) {
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
	public void onStartPreview() {}

	@Override
	public void onStopPreview() {}
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		OutputStream fileOutputStream = null;
		try {
			/* 如果是竖屏就将图片旋转90度 */
			if(!landscape){
				data = CameraUtils.yuvLandscapeToPortrait(data, previewSize.width, previewSize.height);
			}
			
			/* 根据取景框对原图进行截取，只要取景框内的部分 */
			if(cameraApertureRect == null) cameraApertureRect = CameraUtils.getCameraApertureRectByScreenAndCameraPreviewSize(getBaseContext(), cameraApertureView, cameraManager.getCamera().getParameters().getPreviewSize()); //初始化取景框的位置
			Bitmap srcBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			Bitmap cutBitmap = Bitmap.createBitmap(srcBitmap, cameraApertureRect.left, cameraApertureRect.top, cameraApertureRect.width(), cameraApertureRect.height());
			srcBitmap.recycle();
			
			//将裁减后的图片输出到本地缓存文件中
			if(!localCacheFile.exists()) localCacheFile.createNewFile();
			fileOutputStream = IOUtils.openOutputStream(localCacheFile, false);
			cutBitmap.compress(CompressFormat.JPEG, 100, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			//显示到预览图上
			previewImage.setImageURI(Uri.fromFile(localCacheFile));
			
			//渐隐快门按钮并渐现使用、重拍按钮
			ViewAnimationUtils.invisibleViewByAlpha(shutterButton);
			ViewAnimationUtils.visibleViewByAlpha(userButton);
			ViewAnimationUtils.visibleViewByAlpha(remakeButton);
		} catch (Exception e) {
			e.printStackTrace();
			if(fileOutputStream != null){
				try {
					fileOutputStream.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				try {
					fileOutputStream.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				toastL("拍摄失败，请重拍");
			}
		}
	}
	
	/**
	 * 设置闪光模式切换按钮
	 * @param falshMode
	 */
	private void setFlashModeImageButton(String falshMode){
		if(Camera.Parameters.FLASH_MODE_AUTO.equals(falshMode)){
			flashModeButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.icon_flash_auto), null, null, null);
			flashModeButton.setTag(Camera.Parameters.FLASH_MODE_AUTO);
			flashModeButton.setText(R.string.base_auto);
		}else if(Camera.Parameters.FLASH_MODE_OFF.equals(falshMode)){
			flashModeButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.icon_flash_off), null, null, null);
			flashModeButton.setTag(Camera.Parameters.FLASH_MODE_OFF);
			flashModeButton.setText(R.string.base_close);
		}else if(Camera.Parameters.FLASH_MODE_ON.equals(falshMode)){
			flashModeButton.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.icon_flash_on), null, null, null);
			flashModeButton.setTag(Camera.Parameters.FLASH_MODE_ON);
			flashModeButton.setText(R.string.base_open);
		}
	}
}