package me.xiaopan.androidlibrary.util;

import java.util.List;

import android.content.Context;
import android.hardware.Camera;

/**
 * 相机工具箱
 * @author xiaopan
 *
 */
public class CameraUtils {
	/**
	 * 获取最佳的预览尺寸
	 * @param camera
	 * @return
	 */
	public static Camera.Size getOptimalPreviewSize(Context context, Camera camera) {
		Camera.Size optimalSize = null;
		List<Camera.Size> supportedPreviewSizes = camera.getParameters().getSupportedPreviewSizes();
		if (supportedPreviewSizes != null && supportedPreviewSizes.size() > 0){
			Size screenSize = AndroidUtils.getScreenSize(context);
			int screenWidth = screenSize.getHeight();
			int screenHeight = screenSize.getWidth();
			final double ASPECT_TOLERANCE = 0.1;
			double minDiff = Double.MAX_VALUE;
			
			//计算最佳的宽高比例
			double targetRatio = (double) screenWidth / screenHeight;
			int targetHeight = screenHeight;
			
			//视图找到一个宽高和屏幕最接近的尺寸
			for (Camera.Size size : supportedPreviewSizes) {
				double ratio = (double) size.width / size.height;
				if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
				if (Math.abs(size.height - targetHeight) < minDiff) {
					optimalSize = size;
					minDiff = Math.abs(size.height - targetHeight);
				}
			}
			
			//当找不到的时候
			if (optimalSize == null) {
				minDiff = Double.MAX_VALUE;
				for (Camera.Size size : supportedPreviewSizes) {
					if (Math.abs(size.height - targetHeight) < minDiff) {
						optimalSize = size;
						minDiff = Math.abs(size.height - targetHeight);
					}
				}
			}
		}

        return optimalSize;
    }
}