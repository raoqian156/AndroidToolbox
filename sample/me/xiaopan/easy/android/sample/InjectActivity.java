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
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectExtra;
import me.xiaopan.android.easy.inject.InjectService;
import me.xiaopan.android.easy.inject.InjectView;
import me.xiaopan.java.easy.util.ReflectUtils;
import android.accounts.AccountManager;
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

@InjectContentView(R.layout.activity_main)
public class InjectActivity extends EasyActivity {
	public static final String PARAM_BYTE = "PARAM_BYTE";
	public static final String PARAM_BYTE_ARRAY = "PARAM_BYTE_ARRAY";
	public static final String PARAM_SHORT = "PARAM_SHORT";
	public static final String PARAM_SHORT_ARRAY = "PARAM_SHORT_ARRAY";
	public static final String PARAM_INT = "PARAM_INT";
	public static final String PARAM_INT_ARRAY = "PARAM_INT_ARRAY";
	public static final String PARAM_LONG = "PARAM_LONG";
	public static final String PARAM_LONG_ARRAY = "PARAM_LONG_ARRAY";
	public static final String PARAM_CHAR = "PARAM_CHAR";
	public static final String PARAM_CHAR_ARRAY = "PARAM_CHAR_ARRAY";
	public static final String PARAM_FLOAT = "PARAM_FLOAT";
	public static final String PARAM_FLOAT_ARRAY = "PARAM_FLOAT_ARRAY";
	public static final String PARAM_DOUBLE = "PARAM_DOUBLE";
	public static final String PARAM_DOUBLE_ARRAY = "PARAM_DOUBLE_ARRAY";
	public static final String PARAM_BOOLEAN = "PARAM_BOOLEAN";
	public static final String PARAM_BOOLEAN_ARRAY = "PARAM_BOOLEAN_ARRAY";
	public static final String PARAM_STRING = "PARAM_STRING";
	public static final String PARAM_STRING_ARRAY = "PARAM_STRING_ARRAY";
	public static final String PARAM_STRING_ARRAY_LIST = "PARAM_STRING_ARRAY_LIST";
	public static final String PARAM_CHAR_SEQUENCE = "PARAM_CHAR_SEQUENCE";
	public static final String PARAM_CHAR_SEQUENCE_ARRAY = "PARAM_CHAR_SEQUENCE_ARRAY";
	
	@InjectView(R.id.text_main1) private TextView textView1;
	@InjectView(R.id.text_main2) private TextView textView2;
	@InjectView(R.id.text_main3) private TextView textView3;
	@InjectView(R.id.text_main4) private TextView textView4;
	@InjectView(R.id.text_main5) private TextView textView5;
	@InjectView(R.id.text_main6) private TextView textView6;
	@InjectView(R.id.text_main7) private TextView textView7;
	@InjectView(R.id.text_main8) private TextView textView8;
	@InjectView(R.id.text_main9) private TextView textView9;
	@InjectView(R.id.text_main10) private TextView textView10;

	@InjectExtra(PARAM_BYTE) private byte byteField;
	@InjectExtra(PARAM_BYTE_ARRAY) private byte[] byteFields;
	@InjectExtra(PARAM_SHORT) private short shortField;
	@InjectExtra(PARAM_SHORT_ARRAY) private short[] shortFields;
	@InjectExtra(PARAM_INT) private int intField;
	@InjectExtra(PARAM_INT_ARRAY) private int[] intFields;
	@InjectExtra(PARAM_LONG) private long longField;
	@InjectExtra(PARAM_LONG_ARRAY) private long[] longFields;
	@InjectExtra(PARAM_CHAR) private char charField;
	@InjectExtra(PARAM_CHAR_ARRAY) private char[] charFields;
	@InjectExtra(PARAM_FLOAT) private float floatField;
	@InjectExtra(PARAM_FLOAT_ARRAY) private float[] floatFields;
	@InjectExtra(PARAM_DOUBLE) private double doubleField;
	@InjectExtra(PARAM_DOUBLE_ARRAY) private double[] doubleFields;
	@InjectExtra(PARAM_BOOLEAN) private boolean booleanField;
	@InjectExtra(PARAM_BOOLEAN_ARRAY) private boolean[] booleanFields;
	@InjectExtra(PARAM_STRING) private String stringField;
	@InjectExtra(PARAM_STRING_ARRAY) private String[] stringFields;
	@InjectExtra(PARAM_STRING_ARRAY_LIST) private ArrayList<String> stringFieldList;
	@InjectExtra(PARAM_CHAR_SEQUENCE) private CharSequence charSequenceField;
	@InjectExtra(PARAM_CHAR_SEQUENCE_ARRAY) private CharSequence[] charSequenceFields;

	@InjectService private AccessibilityManager accessibilityManager;
	@InjectService private AccountManager accountManager;
	@InjectService private ActivityManager activityManager;
	@InjectService private AlarmManager alarmManager;
	@InjectService private AudioManager audioManager;
	@InjectService private ConnectivityManager connectivityManager;
	@InjectService private DevicePolicyManager devicePolicyManager;
	@InjectService private DropBoxManager dropBoxManager;
	@InjectService private InputMethodManager inputMethodManager;
	@InjectService private KeyguardManager keyguardManager;
	@InjectService private LayoutInflater layoutInflater;
	@InjectService private LocationManager locationManager;
	@InjectService private NotificationManager notificationManager;
	@InjectService private PowerManager powerManager;
	@InjectService private SearchManager searchManager;
	@InjectService private SensorManager sensorManager;
	@InjectService private TelephonyManager telephonyManager;
	@InjectService private UiModeManager uiModeManager;
	@InjectService private Vibrator vibrator;
	@InjectService private WallpaperManager wallpaperManager;
	@InjectService private WifiManager wifiManager;
	@InjectService private WindowManager windowManager;
	@InjectService private DownloadManager downloadManager;
	@InjectService private StorageManager storageManager;
	@InjectService private NfcManager nfcManager;
	@InjectService private ClipboardManager clipboardManager;
	@InjectService private UsbManager usbManager;
	@InjectService private TextServicesManager textServicesManager;
	@InjectService private WifiP2pManager wifiP2pManager;
	@InjectService private InputManager inputManager;
	@InjectService private MediaRouter mediaRouter;
	@InjectService private NsdManager nsdManager;
//	@InjectService private DisplayManager displayManager;
//	@InjectService private BluetoothManager bluetoothManager;
//	@InjectService private AppOpsManager appOpsManager;
//	@InjectService private CaptioningManager captioningManager;
//	@InjectService private ConsumerIrManager consumerIrManager;
//	@InjectService private PrintManager printManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
				if(field.getAnnotation(InjectService.class) != null){
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
