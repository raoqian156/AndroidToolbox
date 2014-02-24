package me.xiaopan.android.easy.inject;

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
