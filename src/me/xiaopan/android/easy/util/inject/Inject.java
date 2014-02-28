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

package me.xiaopan.android.easy.util.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注入系统常用类，支持以下类型
 * <br> SharedPreferences
 * <br> AccessibilityManager
 * <br> AccountManager
 * <br> ActivityManager
 * <br> AlarmManager
 * <br> AppOpsManager
 * <br> AudioManager
 * <br> BluetoothManager
 * <br> CaptioningManager
 * <br> ConnectivityManager
 * <br> ConsumerIrManager
 * <br> DevicePolicyManager
 * <br> DisplayManager
 * <br> DropBoxManager
 * <br> InputManager
 * <br> InputMethodManager
 * <br> KeyguardManager
 * <br> LayoutInflater
 * <br> LocationManager
 * <br> MediaRouter
 * <br> NotificationManager
 * <br> NsdManager
 * <br> PowerManager
 * <br> PrintManager
 * <br> SearchManager
 * <br> SensorManager
 * <br> TelephonyManager
 * <br> UiModeManager
 * <br> Vibrator
 * <br> WallpaperManager
 * <br> WifiManager
 * <br> WindowManager
 * <br> DownloadManager
 * <br> StorageManager
 * <br> NfcManager
 * <br> ClipboardManager
 * <br> UsbManager
 * <br> TextServicesManager
 * <br> WifiP2pManager
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Inject {
	/**
	 * 当要注入SharedPreferences时，此参数值就是SharedPreferences的name
	 * @return
	 */
	public String sharedPreferencesName() default "";
}
