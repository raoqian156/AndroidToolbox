package me.xiaopan.android.easy.util.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import me.xiaopan.android.easy.util.PreferenceUtils;
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
import android.content.res.ColorStateList;
import android.graphics.Movie;
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
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.preference.PreferenceManager;
import android.print.PrintManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.CaptioningManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;

public class Injector {
	private List<Field> injectFields;
	private InjectInterpolator findViewListener;
	private FragmentInjectInterpolator fragmentInjectInterpolator;
	
	public Injector(Activity activity){
		this.injectFields = getFields(activity.getClass());
		this.findViewListener = new ActivityViewInjectInterpolator(activity);
		if(activity instanceof FragmentActivity){
			this.fragmentInjectInterpolator = new FragmentInjectInterpolator((FragmentActivity) activity);
		}
	}
	
	public Injector(Fragment fragment){
		this.injectFields = getFields(fragment.getClass());
		this.findViewListener = new FragmentViewInjectInterpolator(fragment);
	}
	
	/**
	 * 注入View字段
	 * @param isInjectOtherMembers 是否注入其它成员
	 */
	public void injectViewMembers(){
		if(injectFields.size() > 0){
			Iterator<Field> fieldIterator = injectFields.iterator();
			Field field = null;
			while(fieldIterator.hasNext()){
				field = fieldIterator.next();
				if(field.isAnnotationPresent(InjectView.class)){
					findViewListener.onInject(field);
					fieldIterator.remove();
				}else if(fragmentInjectInterpolator != null && field.isAnnotationPresent(InjectFragment.class) && Fragment.class.isAssignableFrom(field.getType())){
					fragmentInjectInterpolator.onInject(field);
					fieldIterator.remove();
				}
			}
		}
	}
	
	/**
	 * 注入其他成员，例如Resource、Extra等
	 * @param object
	 * @param context
	 * @param bundle
	 */
	public void injectMembers(Object object, Context context, Bundle bundle){
		for(Field field : getFields(object.getClass())){
			if(bundle != null && field.isAnnotationPresent(InjectExtra.class)){
				injectExtra(field, object, bundle);
			}else if(field.isAnnotationPresent(InjectResource.class)){
				injectResource(field, object, context);
			}else if(field.isAnnotationPresent(Inject.class)){
				inject(field, object, context);
			}else if(field.isAnnotationPresent(InjectPreference.class)){
				injectPreference(field, object, context);
			}
		}
	}
	
	/**
	 * 注入参数
	 * @param field
	 * @param object
	 * @param extra
	 * @return
	 */
	private static void injectExtra(Field field, Object object, Bundle extra){
		if(extra != null){
			InjectExtra injectExtra = field.getAnnotation(InjectExtra.class);
			if(StringUtils.isNotEmpty(injectExtra.value())){
				field.setAccessible(true);
				try {
					field.set(object, extra.get(injectExtra.value()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 注入资源
	 * @param field
	 * @param object
	 * @param context
	 * @return
	 */
	private static void injectResource(Field field, Object object, Context context){
		InjectResource injectResource = field.getAnnotation(InjectResource.class);
		Class<?> fieldType = field.getType();
		field.setAccessible(true);
		try {
			if(String.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getString(injectResource.value()));
			}else if(String[].class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getStringArray(injectResource.value()));
			}else if(Drawable.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getDrawable(injectResource.value()));
			}else if(int.class.isAssignableFrom(fieldType) || Integer.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getInteger(injectResource.value()));
			}else if(int[].class.isAssignableFrom(fieldType) || Integer[].class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getIntArray(injectResource.value()));
			}else if(boolean.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getBoolean(injectResource.value()));
			}else if(ColorStateList.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getColorStateList(injectResource.value()));
			}else if(Animation.class.isAssignableFrom(fieldType)){
				field.set(object, AnimationUtils.loadAnimation(context, injectResource.value()));
			}else if(Movie.class.isAssignableFrom(fieldType)){
				field.set(object, context.getResources().getMovie(injectResource.value()));
			}
//			resources.getAnimation(id)
//			resources.getColor(id)
//			resources.getDimension(id)
//			resources.getDimensionPixelOffset(id)
//			resources.getDimensionPixelSize(id)
//			resources.getDrawableForDensity(id, density)
//			resources.getFraction(id, base, pbase)
//			resources.getIdentifier(name, defType, defPackage)
//			resources.getLayout(id)
//			resources.getMovie(id)
//			resources.getQuantityString(id, quantity)
//			resources.getQuantityString(id, quantity, formatArgs)
//			resources.getQuantityText(id, quantity)
//			resources.getResourceEntryName(resid)
//			resources.getString(id, formatArgs)
//			resources.getText(id)
//			resources.getTextArray(id)
//			resources.getValue(id, outValue, resolveRefs)
//			resources.getValue(name, outValue, resolveRefs)
//			resources.getValueForDensity(id, density, outValue, resolveRefs)
//			resources.getXml(id)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注入Preference
	 * @param field
	 * @param object
	 * @param context
	 * @return
	 */
	private static void injectPreference(Field field, Object object, Context context){
		InjectPreference injectPreference = field.getAnnotation(InjectPreference.class);
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
			}else if(Set.class.isAssignableFrom(fieldType)){
				Class<?> first = (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
				if(String.class.isAssignableFrom(first)){
					field.set(object, PreferenceUtils.getStringSet(sharedPreferences, injectPreference.value(), null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 注入
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
	 * @return
	 */
	@SuppressLint("NewApi")
	private static void inject(Field field, Object object, Context context){
		Class<?> fieldType = field.getType();
		field.setAccessible(true);
		try {
			if(SharedPreferences.class.isAssignableFrom(fieldType)){
				Inject inject = field.getAnnotation(Inject.class);
				if(StringUtils.isNotEmpty(inject.sharedPreferencesName())){
					field.set(object, context.getSharedPreferences(inject.sharedPreferencesName(), Context.MODE_PRIVATE));
				}else{
					field.set(object, PreferenceManager.getDefaultSharedPreferences(context));
				}
			}else if(AccessibilityManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.ACCESSIBILITY_SERVICE));
			}else if(AccountManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.ACCOUNT_SERVICE));
			}else if(ActivityManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.ACTIVITY_SERVICE));
			}else if(AlarmManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.ALARM_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && AppOpsManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.APP_OPS_SERVICE));
			}else if(AudioManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.AUDIO_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2 && BluetoothManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.BLUETOOTH_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && CaptioningManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.CAPTIONING_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ClipboardManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.CLIPBOARD_SERVICE));
			}else if(ConnectivityManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.CONNECTIVITY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && ConsumerIrManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.CONSUMER_IR_SERVICE));
			}else if(DevicePolicyManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.DEVICE_POLICY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && DownloadManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.DOWNLOAD_SERVICE));
			}else if(DropBoxManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.DROPBOX_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 && DisplayManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.DISPLAY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && InputManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.INPUT_SERVICE));
			}else if(InputMethodManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.INPUT_METHOD_SERVICE));
			}else if(KeyguardManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.KEYGUARD_SERVICE));
			}else if(LayoutInflater.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.LAYOUT_INFLATER_SERVICE));
			}else if(LocationManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.LOCATION_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1 && NfcManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.NFC_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && MediaRouter.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.MEDIA_ROUTER_SERVICE));
			}else if(NotificationManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.NOTIFICATION_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && NsdManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.NSD_SERVICE));
			}else if(PowerManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.POWER_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && PrintManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.PRINT_SERVICE));
			}else if(SearchManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.SEARCH_SERVICE));
			}else if(SensorManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.SENSOR_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && StorageManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.STORAGE_SERVICE));
			}else if(TelephonyManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.TELEPHONY_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && TextServicesManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE));
			}else if(UiModeManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.UI_MODE_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1 && UsbManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.USB_SERVICE));
			}else if(Vibrator.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.VIBRATOR_SERVICE));
			}else if(WallpaperManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.WALLPAPER_SERVICE));
			}else if(WifiManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.WIFI_SERVICE));
			}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH && WifiP2pManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.WIFI_P2P_SERVICE));
			}else if(WindowManager.class.isAssignableFrom(fieldType)){
				field.set(object, context.getSystemService(Context.WINDOW_SERVICE));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取可注入的Fields
	 * @param classs
	 * @return
	 */
	public static List<Field> getFields(Class<?> classs){
		List<Field> fields = new LinkedList<Field>();
		int modifiers;
		while(true){
			if(classs != null){
				for(Field field : classs.getDeclaredFields()){
					modifiers = field.getModifiers();
					if(!Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers)){
						fields.add(field);
					}
				}
				
				if(classs.isAnnotationPresent(InjectParentMember.class)){
					classs = classs.getSuperclass();
				}else{
					break;
				}
			}else{
				break;
			}
		}
		return fields;
	}
}