package me.xiaopan.android.easy.hardware.camera;

import java.text.DecimalFormat;

import android.hardware.Camera.Size;

public class CameraSize {
	private Size size;
	private float proportion;
	
	public CameraSize(Size size){
		this.size = size;
		proportion = Float.valueOf(new DecimalFormat("0.00").format((float) size.width / (float) size.height));
	}

	public Size getSize() {
		return size;
	}

	public void setSize(Size size) {
		this.size = size;
	}

	public float getProportion() {
		return proportion;
	}

	public void setProportion(float proportion) {
		this.proportion = proportion;
	}
}
