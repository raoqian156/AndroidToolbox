package me.xiaopan.android.easy.inject;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Set;

import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.SecondChronograph;
import me.xiaopan.java.easy.util.StringUtils;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.Drawable;
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
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

/**
 * 注入工具箱
 */
public class InjectUtils {
	/**
	 * 注入内容视图
	 * @param activity
	 */
	public static void injectContentView(Activity activity){
		InjectContentView injectContentView = activity.getClass().getAnnotation(InjectContentView.class);
		if(injectContentView != null && injectContentView.value() > 0){
			activity.setContentView(injectContentView.value());
		}
	}
	
	/**
	 * 注入内容视图
	 * @param fragment
	 * @param inflater
	 * @return
	 */
	public static View injectContentView(Fragment fragment, LayoutInflater inflater){
		InjectContentView injectContentView = fragment.getClass().getAnnotation(InjectContentView.class); 
		return injectContentView != null?inflater.inflate(injectContentView.value(), null):null;
	}
	
	/**
	 * 注入View字段
	 * @param activity
	 */
	public static void injectViewMembers(Activity activity){
		SecondChronograph secondChronograph = new SecondChronograph();
		for(Field field : ReflectUtils.getFields(activity.getClass(), true, activity.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView != null){
				field.setAccessible(true);
				try {
					field.set(activity, activity.findViewById(injectView.value()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("注入View耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	/**
	 * 注入View字段
	 * @param fragment
	 */
	public static void injectViewMembers(Fragment fragment){
		for(Field field : ReflectUtils.getFields(fragment.getClass(), true, fragment.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView != null){
				field.setAccessible(true);
				try {
					field.set(fragment, fragment.getView().findViewById(injectView.value()));
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param activity
	 */
	public static void injectMembers(Activity activity){
		SecondChronograph secondChronograph = new SecondChronograph();
		for(Field field : ReflectUtils.getFields(activity.getClass(), true, activity.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			if(!injectExtra(field, activity, activity.getIntent().getExtras())){
				if(!injectResource(field, activity, activity.getBaseContext())){
					if(!injectService(field, activity, activity.getBaseContext())){
						if(!injectPreference(field, activity, activity.getBaseContext())){
						}
					}
				}
			}
		}
		System.out.println("注入成员变量耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param fragment
	 */
	public static void injectMembers(Fragment fragment){
		SecondChronograph secondChronograph = new SecondChronograph();
		for(Field field : ReflectUtils.getFields(fragment.getClass(), true, fragment.getClass().getAnnotation(InjectParentMember.class) != null, false, true)){
			if(!injectExtra(field, fragment, fragment.getArguments())){
				if(!injectResource(field, fragment, fragment.getActivity().getBaseContext())){
					if(!injectService(field, fragment, fragment.getActivity().getBaseContext())){
						if(!injectPreference(field, fragment, fragment.getActivity().getBaseContext())){
						}
					}
				}
			}
		}
		System.out.println("注入成员变量耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	/**
	 * 注入参数
	 * @param field
	 * @param object
	 * @return
	 */
	@SuppressLint("NewApi")
	private static boolean injectExtra(Field field, Object object, Bundle extra){
		InjectExtra injectExtra = field.getAnnotation(InjectExtra.class);
		if(injectExtra != null && StringUtils.isNotEmpty(injectExtra.value()) && extra != null){
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			try {
				if(byte.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getByte(injectExtra.value(), injectExtra.byteDefaultValue()));
				}else if(short.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getShort(injectExtra.value(), injectExtra.shortDefaultValue()));
				}else if(int.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getInt(injectExtra.value(), injectExtra.intDefaultValue()));
				}else if(long.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getLong(injectExtra.value(), injectExtra.longDefaultValue()));
				}else if(float.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getFloat(injectExtra.value(), injectExtra.floatDefaultValue()));
				}else if(double.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getDouble(injectExtra.value(), injectExtra.doubleDefaultValue()));
				}else if(char.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getChar(injectExtra.value(), injectExtra.charDefaultValue()));
				}else if(boolean.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getBoolean(injectExtra.value(), injectExtra.booleanDefaultValue()));
				}else if(String.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getString(injectExtra.value(), injectExtra.stringDefaultValue()));
				}else if(CharSequence.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getCharSequence(injectExtra.value(), injectExtra.charSequenceDefaultValue()));
				}else if(Parcelable.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getParcelable(injectExtra.value()));
				}else if(Serializable.class.isAssignableFrom(fieldType)){
					field.set(object, extra.getSerializable(injectExtra.value()));
				}else if(fieldType.isArray()){
					Class<?> componentClass = fieldType.getComponentType(); 
					if(byte.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getByteArray(injectExtra.value()));
					}else if(short.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getShortArray(injectExtra.value()));
					}else if(int.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getIntArray(injectExtra.value()));
					}else if(long.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getLongArray(injectExtra.value()));
					}else if(float.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getFloatArray(injectExtra.value()));
					}else if(double.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getDoubleArray(injectExtra.value()));
					}else if(char.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getCharArray(injectExtra.value()));
					}else if(boolean.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getBooleanArray(injectExtra.value()));
					}else if(String.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getStringArray(injectExtra.value()));
					}else if(CharSequence.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getCharSequenceArray(injectExtra.value()));
					}else if(Parcelable.class.isAssignableFrom(componentClass)){
						field.set(object, extra.getParcelableArray(injectExtra.value()));
					}
				}else if(ArrayList.class.isAssignableFrom(fieldType)){
					Class<?> first = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
					if(int.class.isAssignableFrom(first)){
						field.set(object, extra.getIntegerArrayList(injectExtra.value()));
					}else if(String.class.isAssignableFrom(first)){
						field.set(object, extra.getStringArrayList(injectExtra.value()));
					}else if(CharSequence.class.isAssignableFrom(first)){
						field.set(object, extra.getCharSequenceArrayList(injectExtra.value()));
					}else if(Parcelable.class.isAssignableFrom(first)){
						field.set(object, extra.getParcelableArrayList(injectExtra.value()));
					}
				}else if(Bundle.class.isAssignableFrom(fieldType)){
					if(!fieldType.isArray()){
						field.set(object, extra.getBundle(injectExtra.value()));
					}
				}else{
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
						if(IBinder.class.isAssignableFrom(fieldType)){
							if(!fieldType.isArray()){
								field.set(object, extra.getBinder(injectExtra.value()));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 注入资源
	 * @param field
	 * @param object
	 * @param context
	 * @return
	 */
	private static boolean injectResource(Field field, Object object, Context context){
		InjectResource injectResource = field.getAnnotation(InjectResource.class);
		if(injectResource != null){
			Resources resources = context.getResources();
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			try {
				if(boolean.class.isAssignableFrom(fieldType)){
					field.set(object, resources.getBoolean(injectResource.value()));
				}else if(String.class.isAssignableFrom(fieldType)){
					field.set(object, resources.getString(injectResource.value()));
				}else if(int.class.isAssignableFrom(fieldType)){
					field.set(object, resources.getInteger(injectResource.value()));
				}else if(Drawable.class.isAssignableFrom(fieldType)){
					field.set(object, resources.getDrawable(injectResource.value()));
				}else if(fieldType.isArray()){
					Class<?> componentClass = fieldType.getComponentType();
					if(String.class.isAssignableFrom(componentClass)){
						field.set(object, resources.getStringArray(injectResource.value()));
					}else if(int.class.isAssignableFrom(componentClass)){
						field.set(object, resources.getIntArray(injectResource.value()));
					}
				}
//				resources.getAnimation(id)
//				resources.getColor(id)
//				resources.getColorStateList(id)
//				resources.getDimension(id)
//				resources.getDimensionPixelOffset(id)
//				resources.getDimensionPixelSize(id)
//				resources.getDrawableForDensity(id, density)
//				resources.getFraction(id, base, pbase)
//				resources.getIdentifier(name, defType, defPackage)
//				resources.getLayout(id)
//				resources.getMovie(id)
//				resources.getQuantityString(id, quantity)
//				resources.getQuantityString(id, quantity, formatArgs)
//				resources.getQuantityText(id, quantity)
//				resources.getResourceEntryName(resid)
//				resources.getString(id, formatArgs)
//				resources.getText(id)
//				resources.getTextArray(id)
//				resources.getValue(id, outValue, resolveRefs)
//				resources.getValue(name, outValue, resolveRefs)
//				resources.getValueForDensity(id, density, outValue, resolveRefs)
//				resources.getXml(id)
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NotFoundException e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 注入Preference
	 * @param field
	 * @param object
	 * @param context
	 * @return
	 */
	@SuppressLint("NewApi")
	private static boolean injectPreference(Field field, Object object, Context context){
		InjectPreference injectPreference = field.getAnnotation(InjectPreference.class);
		if(injectPreference != null){
			SharedPreferences sharedPreferences = null;
			if(StringUtils.isNotEmpty(injectPreference.sharedPreferencesName())){
				sharedPreferences = context.getSharedPreferences(injectPreference.sharedPreferencesName(), injectPreference.mode());
			}else{
				sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
			}
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			try {
				if(boolean.class.isAssignableFrom(fieldType)){
					field.set(object, sharedPreferences.getBoolean(injectPreference.value(), injectPreference.booleanDefaultValue()));
				}else if(float.class.isAssignableFrom(fieldType)){
					field.set(object, sharedPreferences.getFloat(injectPreference.value(), injectPreference.floatDefaultValue()));
				}else if(int.class.isAssignableFrom(fieldType)){
					field.set(object, sharedPreferences.getInt(injectPreference.value(), injectPreference.intDefaultValue()));
				}else if(long.class.isAssignableFrom(fieldType)){
					field.set(object, sharedPreferences.getLong(injectPreference.value(), injectPreference.longDefaultValue()));
				}else if(String.class.isAssignableFrom(fieldType)){
					field.set(object, sharedPreferences.getString(injectPreference.value(), injectPreference.stringDefaultValue()));
				}else{
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
						if(Set.class.isAssignableFrom(fieldType)){
							Class<?> first = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
							if(String.class.isAssignableFrom(first)){
								field.set(object, sharedPreferences.getStringSet(injectPreference.value(), null));
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 注入服务
	 * <br>支持注入以下服务
	 * <br>AccessibilityManager
	 * <br>AccountManager
	 * <br>	ActivityManager
	 * <br>	AlarmManager
	 * <br>	AudioManager
	 * <br>	ConnectivityManager
	 * <br>	DevicePolicyManager
	 * <br>	DropBoxManager
	 * <br>	InputMethodManager
	 * <br>	KeyguardManager
	 * <br>	LayoutInflater
	 * <br>	LocationManager
	 * <br>	NotificationManager
	 * <br>	PowerManager
	 * <br>	SearchManager
	 * <br>	SensorManager
	 * <br>	TelephonyManager
	 * <br>	UiModeManager
	 * <br>	Vibrator
	 * <br>	WallpaperManager
	 * <br>	WifiManager
	 * <br>	WindowManager
	 * <br>	DownloadManager
	 * <br>	StorageManager
	 * <br>	NfcManager
	 * <br>	ClipboardManager
	 * <br>	UsbManager
	 * <br>	TextServicesManager
	 * <br>	WifiP2pManager
	 * <br>	InputManager
	 * <br>	MediaRouter
	 * <br>	NsdManager
	 * <br>	DisplayManager
	 * <br>	BluetoothManager
	 * <br>	AppOpsManager
	 * <br>	CaptioningManager
	 * <br>	ConsumerIrManager
	 * <br>	PrintManager
	 * @return
	 */
	@SuppressLint("NewApi")
	private static boolean injectService(Field field, Object object, Context context){
		InjectService injectService = field.getAnnotation(InjectService.class);
		if(injectService != null){
			Class<?> fieldType = field.getType();
			field.setAccessible(true);
			try {
				if(AccessibilityManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.ACCESSIBILITY_SERVICE));
				}else if(AccountManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.ACCOUNT_SERVICE));
				}else if(ActivityManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.ACTIVITY_SERVICE));
				}else if(AlarmManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.ALARM_SERVICE));
				}else if(AudioManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.AUDIO_SERVICE));
				}else if(ConnectivityManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.CONNECTIVITY_SERVICE));
				}else if(DevicePolicyManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.DEVICE_POLICY_SERVICE));
				}else if(DropBoxManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.DROPBOX_SERVICE));
				}else if(InputMethodManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.INPUT_METHOD_SERVICE));
				}else if(KeyguardManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.KEYGUARD_SERVICE));
				}else if(LayoutInflater.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
				}else if(LocationManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.LOCATION_SERVICE));
				}else if(NotificationManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.NOTIFICATION_SERVICE));
				}else if(PowerManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.POWER_SERVICE));
				}else if(SearchManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.SEARCH_SERVICE));
				}else if(SensorManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.SENSOR_SERVICE));
				}else if(TelephonyManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.TELEPHONY_SERVICE));
				}else if(UiModeManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.UI_MODE_SERVICE));
				}else if(Vibrator.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.VIBRATOR_SERVICE));
				}else if(WallpaperManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.WALLPAPER_SERVICE));
				}else if(WifiManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.WIFI_SERVICE));
				}else if(WindowManager.class.isAssignableFrom(fieldType)){
					field.set(object, context.getSystemService(Context.WINDOW_SERVICE));
				}else{
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD){
						if(DownloadManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.DOWNLOAD_SERVICE));
						}else if(StorageManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.STORAGE_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1){
						if(NfcManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.NFC_SERVICE));
						}
					}

					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
						if(ClipboardManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.CLIPBOARD_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){
						if(UsbManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.USB_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
						if(TextServicesManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE));
						}else if(WifiP2pManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.WIFI_P2P_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
						if(InputManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.INPUT_SERVICE));
						}else if(MediaRouter.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.MEDIA_ROUTER_SERVICE));
						}else if(NsdManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.NSD_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
						if(DisplayManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.DISPLAY_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2){
						if(BluetoothManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.BLUETOOTH_SERVICE));
						}
					}
					
					if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
						if(AppOpsManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.APP_OPS_SERVICE));
						}else if(CaptioningManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.CAPTIONING_SERVICE));
						}else if(ConsumerIrManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.CONSUMER_IR_SERVICE));
						}else if(PrintManager.class.isAssignableFrom(fieldType)){
							field.set(object, context.getSystemService(Context.PRINT_SERVICE));
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
	}
}