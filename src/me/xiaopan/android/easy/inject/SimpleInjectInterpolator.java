package me.xiaopan.android.easy.inject;

import java.lang.reflect.Field;

import me.xiaopan.java.easy.util.StringUtils;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.AppOpsManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.ConsumerIrManager;
import android.hardware.SensorManager;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.hardware.usb.UsbManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.MediaRouter;
import android.net.ConnectivityManager;
import android.net.nsd.NsdManager;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.print.PrintManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

/**
 * 注入常见的服务
 * <br>支持注入以下类
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
public class SimpleInjectInterpolator implements InjectInterpolator {
	private Object object;
	private Context context;

	public SimpleInjectInterpolator(Object object, Context context) {
		this.object = object;
		this.context = context;
	}

	@Override
	@SuppressLint("NewApi")
	public void onInject(Field field) {
		Class<?> fieldType = field.getType();
		try {
			if(SharedPreferences.class.isAssignableFrom(fieldType)){
				Inject inject = field.getAnnotation(Inject.class);
				if(StringUtils.isNotEmpty(inject.sharedPreferencesName())){
					field.setAccessible(true);
					field.set(object, context.getSharedPreferences(inject.sharedPreferencesName(), Context.MODE_PRIVATE));
				}else{
					field.setAccessible(true);
					field.set(object, PreferenceManager.getDefaultSharedPreferences(context));
				}
			}else if(AccessibilityManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.ACCESSIBILITY_SERVICE));
			}else if(AccountManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.ACCOUNT_SERVICE));
			}else if(ActivityManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.ACTIVITY_SERVICE));
			}else if(AlarmManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.ALARM_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && AppOpsManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.APP_OPS_SERVICE));
			}else if(AudioManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.AUDIO_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && BluetoothManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.BLUETOOTH_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && CaptioningManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.CAPTIONING_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ClipboardManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.CLIPBOARD_SERVICE));
			}else if(ConnectivityManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.CONNECTIVITY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && ConsumerIrManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.CONSUMER_IR_SERVICE));
			}else if(DevicePolicyManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.DEVICE_POLICY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && DownloadManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.DOWNLOAD_SERVICE));
			}else if(DropBoxManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.DROPBOX_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && DisplayManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.DISPLAY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && InputManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.INPUT_SERVICE));
			}else if(InputMethodManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.INPUT_METHOD_SERVICE));
			}else if(KeyguardManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.KEYGUARD_SERVICE));
			}else if(LayoutInflater.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
			}else if(LocationManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.LOCATION_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1 && NfcManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.NFC_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && MediaRouter.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.MEDIA_ROUTER_SERVICE));
			}else if(NotificationManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.NOTIFICATION_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && NsdManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.NSD_SERVICE));
			}else if(PowerManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.POWER_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && PrintManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.PRINT_SERVICE));
			}else if(SearchManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.SEARCH_SERVICE));
			}else if(SensorManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.SENSOR_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && StorageManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.STORAGE_SERVICE));
			}else if(TelephonyManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.TELEPHONY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && TextServicesManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE));
			}else if(UiModeManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.UI_MODE_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 && UsbManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.USB_SERVICE));
			}else if(Vibrator.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.VIBRATOR_SERVICE));
			}else if(WallpaperManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.WALLPAPER_SERVICE));
			}else if(WifiManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.WIFI_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && WifiP2pManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.WIFI_P2P_SERVICE));
			}else if(WindowManager.class.isAssignableFrom(fieldType)){
				field.setAccessible(true);
				field.set(object, context.getSystemService(Context.WINDOW_SERVICE));
			}
		} catch (Exception e) {
			Log.w(getClass().getSimpleName(), "注入"+object.getClass().getSimpleName()+"."+field.getName()+"出错");
			e.printStackTrace();
		}
	}
}
