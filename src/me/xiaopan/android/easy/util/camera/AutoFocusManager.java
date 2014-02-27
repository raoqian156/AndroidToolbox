/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
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

package me.xiaopan.android.easy.util.camera;

import android.hardware.Camera;
import android.os.Handler;

public class AutoFocusManager implements Runnable{
	private Camera camera;
	private Handler handler;
	private int intervalMillisecond;
	
	public AutoFocusManager(Camera camera, int intervalMillisecond){
		this.camera = camera;
		this.intervalMillisecond = intervalMillisecond;
		handler = new Handler();
	}
	
	public AutoFocusManager(Camera camera){
		this(camera, 2000);
	}

	@Override
	public void run() {
		if(camera != null){
			camera.autoFocus(null);
			handler.postDelayed(this, intervalMillisecond);
		}
	}
	
	public void start(){
		handler.removeCallbacks(this);
		handler.postDelayed(this, intervalMillisecond);
	}
	
	public void stop(){
		handler.removeCallbacks(this);
	}

	public Camera getCamera() {
		return camera;
	}

	public void setCamera(Camera camera) {
		this.camera = camera;
	}

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public int getIntervalMillisecond() {
		return intervalMillisecond;
	}

	public void setIntervalMillisecond(int intervalMillisecond) {
		this.intervalMillisecond = intervalMillisecond;
	}
}