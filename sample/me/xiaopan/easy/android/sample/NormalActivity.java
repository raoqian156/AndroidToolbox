/*
 * Copyright 2013 Peng fei Pan
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

package me.xiaopan.easy.android.sample;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.activity.EasyActivity;
import me.xiaopan.android.easy.inject.DisableInject;
import me.xiaopan.android.easy.sql.NotNull;
import me.xiaopan.java.easy.util.ReflectUtils;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.DownloadManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.content.ClipboardManager;
import android.content.Context;
import android.hardware.SensorManager;
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
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.os.storage.StorageManager;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
import android.view.textservice.TextServicesManager;
import android.widget.TextView;

@SuppressLint("InlinedApi")
@DisableInject
public class NormalActivity extends EasyActivity {
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;
	private TextView textView6;
	private TextView textView7;
	private TextView textView8;
	private TextView textView9;
	private TextView textView10;

	private byte byteField;
	private byte[] byteFields;
	private short shortField;
	private short[] shortFields;
	private int intField;
	private int[] intFields;
	private long longField;
	private long[] longFields;
	private char charField;
	private char[] charFields;
	private float floatField;
	private float[] floatFields;
	private double doubleField;
	private double[] doubleFields;
	private boolean booleanField;
	private boolean[] booleanFields;
	private String stringField;
	private String[] stringFields;
	private ArrayList<String> stringFieldList;
	private CharSequence charSequenceField;
	private CharSequence[] charSequenceFields;
	
	@NotNull private AccessibilityManager accessibilityManager;
	@NotNull private AccountManager accountManager;
	@NotNull private ActivityManager activityManager;
	@NotNull private AlarmManager alarmManager;
	@NotNull private AudioManager audioManager;
	@NotNull private ConnectivityManager connectivityManager;
	@NotNull private DevicePolicyManager devicePolicyManager;
	@NotNull private DropBoxManager dropBoxManager;
	@NotNull private InputMethodManager inputMethodManager;
	@NotNull private KeyguardManager keyguardManager;
	@NotNull private LayoutInflater layoutInflater;
	@NotNull private LocationManager locationManager;
	@NotNull private NotificationManager notificationManager;
	@NotNull private PowerManager powerManager;
	@NotNull private SearchManager searchManager;
	@NotNull private SensorManager sensorManager;
	@NotNull private TelephonyManager telephonyManager;
	@NotNull private UiModeManager uiModeManager;
	@NotNull private Vibrator vibrator;
	@NotNull private WallpaperManager wallpaperManager;
	@NotNull private WifiManager wifiManager;
	@NotNull private WindowManager windowManager;
	@NotNull private DownloadManager downloadManager;
	@NotNull private StorageManager storageManager;
	@NotNull private NfcManager nfcManager;
	@NotNull private ClipboardManager clipboardManager;
	@NotNull private UsbManager usbManager;
	@NotNull private TextServicesManager textServicesManager;
	@NotNull private WifiP2pManager wifiP2pManager;
	@NotNull private InputManager inputManager;
	@NotNull private MediaRouter mediaRouter;
	@NotNull private NsdManager nsdManager;
//	@NotNull private DisplayManager displayManager;
//	@NotNull private BluetoothManager bluetoothManager;
//	@NotNull private AppOpsManager appOpsManager;
//	@NotNull private CaptioningManager captioningManager;
//	@NotNull private ConsumerIrManager consumerIrManager;
//	@NotNull private PrintManager printManager;
	
	@SuppressLint("ServiceCast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textView1 = (TextView) findViewById(R.id.text_main1);
		textView2 = (TextView) findViewById(R.id.text_main2);
		textView3 = (TextView) findViewById(R.id.text_main3);
		textView4 = (TextView) findViewById(R.id.text_main4);
		textView5 = (TextView) findViewById(R.id.text_main5);
		textView6 = (TextView) findViewById(R.id.text_main6);
		textView7 = (TextView) findViewById(R.id.text_main7);
		textView8 = (TextView) findViewById(R.id.text_main8);
		textView9 = (TextView) findViewById(R.id.text_main9);
		textView10 = (TextView) findViewById(R.id.text_main10);
		
		byteField = getIntent().getExtras().getByte(InjectActivity.PARAM_BYTE);
		byteFields = getIntent().getExtras().getByteArray(InjectActivity.PARAM_BYTE_ARRAY);
		shortField = getIntent().getExtras().getShort(InjectActivity.PARAM_SHORT);
		shortFields = getIntent().getExtras().getShortArray(InjectActivity.PARAM_SHORT_ARRAY);
		intField = getIntent().getExtras().getInt(InjectActivity.PARAM_INT);
		intFields= getIntent().getExtras().getIntArray(InjectActivity.PARAM_INT_ARRAY);
		longField = getIntent().getExtras().getLong(InjectActivity.PARAM_LONG);
		longFields = getIntent().getExtras().getLongArray(InjectActivity.PARAM_LONG_ARRAY);
		charField = getIntent().getExtras().getChar(InjectActivity.PARAM_CHAR);
		charFields = getIntent().getExtras().getCharArray(InjectActivity.PARAM_CHAR_ARRAY);
		floatField = getIntent().getExtras().getFloat(InjectActivity.PARAM_FLOAT);
		floatFields = getIntent().getExtras().getFloatArray(InjectActivity.PARAM_FLOAT_ARRAY);
		doubleField = getIntent().getExtras().getDouble(InjectActivity.PARAM_DOUBLE);
		doubleFields = getIntent().getExtras().getDoubleArray(InjectActivity.PARAM_DOUBLE_ARRAY);
		booleanField = getIntent().getExtras().getBoolean(InjectActivity.PARAM_BOOLEAN);
		booleanFields = getIntent().getExtras().getBooleanArray(InjectActivity.PARAM_BOOLEAN_ARRAY);
		stringField = getIntent().getExtras().getString(InjectActivity.PARAM_STRING);
		stringFields = getIntent().getExtras().getStringArray(InjectActivity.PARAM_STRING_ARRAY);
		stringFieldList = getIntent().getExtras().getStringArrayList(InjectActivity.PARAM_STRING_ARRAY_LIST);
		charSequenceField = getIntent().getExtras().getCharSequence(InjectActivity.PARAM_CHAR_SEQUENCE);
		charSequenceFields = getIntent().getExtras().getCharSequenceArray(InjectActivity.PARAM_CHAR_SEQUENCE_ARRAY);
		
		accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
		accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
		activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
		dropBoxManager = (DropBoxManager) getSystemService(Context.DROPBOX_SERVICE);
		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
		layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
		searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
		wallpaperManager = (WallpaperManager) getSystemService(Context.WALLPAPER_SERVICE);
		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
		storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
		nfcManager = (NfcManager) getSystemService(Context.NFC_SERVICE);
		clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
		usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
		textServicesManager = (TextServicesManager) getSystemService(Context.TEXT_SERVICES_MANAGER_SERVICE);
		wifiP2pManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
		inputManager = (InputManager) getSystemService(Context.INPUT_SERVICE);
		mediaRouter = (MediaRouter) getSystemService(Context.MEDIA_ROUTER_SERVICE);
		nsdManager = (NsdManager) getSystemService(Context.NSD_SERVICE);
//		displayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
//		bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
//		appOpsManager = (AppOpsManager) getSystemService(Context.APP_OPS_SERVICE);
//		captioningManager = (CaptioningManager) getSystemService(Context.CAPTIONING_SERVICE);
//		consumerIrManager = (ConsumerIrManager) getSystemService(Context.CONSUMER_IR_SERVICE);
//		printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
		
		textView2.setText(
			"byteField="+byteField+"; " +
			"shortField="+shortField+"; " +
			"intField="+intField+"; " +
			"longField="+longField+"; " +
			"charField="+charField+"; " +
			"floatField="+floatField+"; " +
			"doubleField="+doubleField+"; " +
			"booleanField="+booleanField+"; " +
			"stringField="+stringField+"; " +
			"charSequenceField="+charSequenceField+
			"byteFields="+Arrays.toString(byteFields)+"; " +
			"shortFields="+Arrays.toString(shortFields)+"; " +
			"intFields="+Arrays.toString(intFields)+"; " +
			"longFields="+Arrays.toString(longFields)+"; " +
			"charFields="+Arrays.toString(charFields)+"; " +
			"floatFields="+Arrays.toString(floatFields)+"; " +
			"doubleFields="+Arrays.toString(doubleFields)+"; " +
			"booleanFields="+Arrays.toString(booleanFields)+"; " +
			"stringFields="+Arrays.toString(stringFields)+"; " +
			"stringFieldList="+stringFieldList.toString()+"; " +
			"charSequenceFields="+Arrays.toString(charSequenceFields)
		);
		
		boolean success = true; 
		try {
			for(Field field : ReflectUtils.getFields(getClass(), true, false, false, false)){
				if(field.getAnnotation(NotNull.class) != null){
					field.setAccessible(true);
					if(field.get(this) == null){
						success = false;
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		textView3.setText("系统服务注入"+(success?"成功":"失败"));
	}

	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.count().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
