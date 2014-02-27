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

package me.xiaopan.easy.android.sample;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.activity.EasyActivity;
import me.xiaopan.android.easy.inject.DisableInject;
import me.xiaopan.java.easy.util.Stopwatch;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

@DisableInject
public class NormalActivity extends EasyActivity {
	private TextView textView1;
	private TextView textView2;
	private TextView textView3;
	private TextView textView4;
	private TextView textView5;

//	private byte byteField;
//	private byte[] byteFields;
//	private short shortField;
//	private short[] shortFields;
//	private int intField;
//	private int[] intFields;
//	private long longField;
//	private long[] longFields;
//	private char charField;
//	private char[] charFields;
//	private float floatField;
//	private float[] floatFields;
//	private double doubleField;
//	private double[] doubleFields;
//	private boolean booleanField;
//	private boolean[] booleanFields;
//	private String stringField;
//	private String[] stringFields;
//	private ArrayList<String> stringFieldList;
//	private CharSequence charSequenceField;
//	private CharSequence[] charSequenceFields;
//	
//	@NotNull private AccessibilityManager accessibilityManager;
//	@NotNull private AccountManager accountManager;
//	@NotNull private ActivityManager activityManager;
//	@NotNull private AlarmManager alarmManager;
//	@NotNull private AudioManager audioManager;
//	@NotNull private ConnectivityManager connectivityManager;
//	@NotNull private DevicePolicyManager devicePolicyManager;
//	@NotNull private DropBoxManager dropBoxManager;
//	@NotNull private InputMethodManager inputMethodManager;
//	@NotNull private KeyguardManager keyguardManager;
//	@NotNull private LayoutInflater layoutInflater;
//	@NotNull private LocationManager locationManager;
//	@NotNull private NotificationManager notificationManager;
//	@NotNull private PowerManager powerManager;
//	@NotNull private SearchManager searchManager;
//	@NotNull private SensorManager sensorManager;
//	@NotNull private TelephonyManager telephonyManager;
//	@NotNull private UiModeManager uiModeManager;
//	@NotNull private Vibrator vibrator;
//	@NotNull private WallpaperManager wallpaperManager;
//	@NotNull private WifiManager wifiManager;
//	@NotNull private WindowManager windowManager;
//	
//	private boolean booleanPreference;
//	private float floatPreference;
//	private int intPreference;
//	private long longPreference;
//	private String stringPreference;
//	private Set<String> stringSetPreference;
//	
//	private int integer1;
//	private String string1;
//	private int[] integers1;
//	private String[] strings1;
//	private Drawable launcherDrawable;
	
	@SuppressLint("ServiceCast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Stopwatch secondChronograph = new Stopwatch();
		
		textView1 = (TextView) findViewById(R.id.text_main1);
		textView2 = (TextView) findViewById(R.id.text_main2);
		textView3 = (TextView) findViewById(R.id.text_main3);
		textView4 = (TextView) findViewById(R.id.text_main4);
		textView5 = (TextView) findViewById(R.id.text_main5);
		
//		byteField = getIntent().getExtras().getByte(MainActivity.PARAM_BYTE);
//		byteFields = getIntent().getExtras().getByteArray(MainActivity.PARAM_BYTE_ARRAY);
//		shortField = getIntent().getExtras().getShort(MainActivity.PARAM_SHORT);
//		shortFields = getIntent().getExtras().getShortArray(MainActivity.PARAM_SHORT_ARRAY);
//		intField = getIntent().getExtras().getInt(MainActivity.PARAM_INT);
//		intFields= getIntent().getExtras().getIntArray(MainActivity.PARAM_INT_ARRAY);
//		longField = getIntent().getExtras().getLong(MainActivity.PARAM_LONG);
//		longFields = getIntent().getExtras().getLongArray(MainActivity.PARAM_LONG_ARRAY);
//		charField = getIntent().getExtras().getChar(MainActivity.PARAM_CHAR);
//		charFields = getIntent().getExtras().getCharArray(MainActivity.PARAM_CHAR_ARRAY);
//		floatField = getIntent().getExtras().getFloat(MainActivity.PARAM_FLOAT);
//		floatFields = getIntent().getExtras().getFloatArray(MainActivity.PARAM_FLOAT_ARRAY);
//		doubleField = getIntent().getExtras().getDouble(MainActivity.PARAM_DOUBLE);
//		doubleFields = getIntent().getExtras().getDoubleArray(MainActivity.PARAM_DOUBLE_ARRAY);
//		booleanField = getIntent().getExtras().getBoolean(MainActivity.PARAM_BOOLEAN);
//		booleanFields = getIntent().getExtras().getBooleanArray(MainActivity.PARAM_BOOLEAN_ARRAY);
//		stringField = getIntent().getExtras().getString(MainActivity.PARAM_STRING);
//		stringFields = getIntent().getExtras().getStringArray(MainActivity.PARAM_STRING_ARRAY);
//		stringFieldList = getIntent().getExtras().getStringArrayList(MainActivity.PARAM_STRING_ARRAY_LIST);
//		charSequenceField = getIntent().getExtras().getCharSequence(MainActivity.PARAM_CHAR_SEQUENCE);
//		charSequenceFields = getIntent().getExtras().getCharSequenceArray(MainActivity.PARAM_CHAR_SEQUENCE_ARRAY);
//		
//		accessibilityManager = (AccessibilityManager) getSystemService(Context.ACCESSIBILITY_SERVICE);
//		accountManager = (AccountManager) getSystemService(Context.ACCOUNT_SERVICE);
//		activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//		audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//		connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//		devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//		dropBoxManager = (DropBoxManager) getSystemService(Context.DROPBOX_SERVICE);
//		inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
//		layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//		powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
//		searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
//		telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
//		vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
//		wallpaperManager = (WallpaperManager) getSystemService(Context.WALLPAPER_SERVICE);
//		wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
//		windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
//		
//		booleanPreference = PreferenceUtils.getBoolean(getBaseContext(), MainActivity.KEY_BOOLEAN);
//		floatPreference = PreferenceUtils.getFloat(getBaseContext(), MainActivity.KEY_FLOAT);
//		intPreference = PreferenceUtils.getInt(getBaseContext(), MainActivity.KEY_INT);
//		longPreference = PreferenceUtils.getLong(getBaseContext(), MainActivity.KEY_LONG);
//		stringPreference = PreferenceUtils.getString(getBaseContext(), MainActivity.KEY_STRING);
//		stringSetPreference = PreferenceUtils.getStringSet(getBaseContext(), MainActivity.KEY_STRING_SET);
//		
//		integer1 = getResources().getInteger(R.integer.integer1);
//		string1 = getResources().getString(R.string.string1);
//		integers1 = getResources().getIntArray(R.array.integer_array1);
//		strings1 = getResources().getStringArray(R.array.string_array1);
//		launcherDrawable = getResources().getDrawable(R.drawable.ic_launcher);
		
		StringBuffer extraStringBuffer = new StringBuffer("Extra初始化结果：");
//		extraStringBuffer.append("\n").append("byteField").append("=").append(byteField);
//		extraStringBuffer.append("\n").append("byteField").append("=").append(byteField);
//		extraStringBuffer.append("\n").append("shortField").append("=").append(shortField);
//		extraStringBuffer.append("\n").append("intField").append("=").append(intField);
//		extraStringBuffer.append("\n").append("longField").append("=").append(longField);
//		extraStringBuffer.append("\n").append("charField").append("=").append(charField);
//		extraStringBuffer.append("\n").append("floatField").append("=").append(floatField);
//		extraStringBuffer.append("\n").append("doubleField").append("=").append(doubleField);
//		extraStringBuffer.append("\n").append("booleanField").append("=").append(booleanField);
//		extraStringBuffer.append("\n").append("stringField").append("=").append(stringField);
//		extraStringBuffer.append("\n").append("charSequenceField").append("=").append(charSequenceField);
//		extraStringBuffer.append("\n").append("byteFields").append("=").append(Arrays.toString(byteFields));
//		extraStringBuffer.append("\n").append("shortFields").append("=").append(Arrays.toString(shortFields));
//		extraStringBuffer.append("\n").append("intFields").append("=").append(Arrays.toString(intFields));
//		extraStringBuffer.append("\n").append("longFields").append("=").append(Arrays.toString(longFields));
//		extraStringBuffer.append("\n").append("charFields").append("=").append(Arrays.toString(charFields));
//		extraStringBuffer.append("\n").append("floatFields").append("=").append(Arrays.toString(floatFields));
//		extraStringBuffer.append("\n").append("doubleFields").append("=").append(Arrays.toString(doubleFields));
//		extraStringBuffer.append("\n").append("booleanFields").append("=").append(Arrays.toString(booleanFields));
//		extraStringBuffer.append("\n").append("stringFields").append("=").append(Arrays.toString(stringFields));
//		extraStringBuffer.append("\n").append("stringFieldList").append("=").append(stringFieldList.toString());
//		extraStringBuffer.append("\n").append("charSequenceFields").append("=").append(Arrays.toString(charSequenceFields));
		textView2.setText(extraStringBuffer.toString());
		
		boolean success = true; 
//		try {
//			for(Field field : ReflectUtils.getFields(getClass(), true, false, false, false)){
//				if(field.getAnnotation(NotNull.class) != null){
//					field.setAccessible(true);
//					if(field.get(this) == null){
//						success = false;
//						break;
//					}
//				}
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		textView3.setText("系统服务初始化"+(success?"成功":"失败"));
		
		StringBuffer preferenceStringBuffer = new StringBuffer("Preference初始化结果：");
//		preferenceStringBuffer.append("\n").append("booleanPreference").append("=").append(booleanPreference);
//		preferenceStringBuffer.append("\n").append("floatPreference").append("=").append(floatPreference);
//		preferenceStringBuffer.append("\n").append("intPreference").append("=").append(intPreference);
//		preferenceStringBuffer.append("\n").append("longPreference").append("=").append(longPreference);
//		preferenceStringBuffer.append("\n").append("stringPreference").append("=").append(stringPreference);
//		preferenceStringBuffer.append("\n").append("stringSetPreference").append("=").append(stringSetPreference);
		textView4.setText(preferenceStringBuffer.toString());
		
		StringBuffer resourceStringBuffer = new StringBuffer("Resource初始化结果：");
//		resourceStringBuffer.append("\n").append("integer1").append("=").append(integer1);
//		resourceStringBuffer.append("\n").append("string1").append("=").append(string1);
//		resourceStringBuffer.append("\n").append("integers1").append("=").append(Arrays.toString(integers1));
//		resourceStringBuffer.append("\n").append("strings1").append("=").append(Arrays.toString(strings1));
		textView5.setText(resourceStringBuffer);
//		textView5.setCompoundDrawablePadding(16);
//		textView5.setCompoundDrawablesWithIntrinsicBounds(launcherDrawable, null, null, null);
		
		System.out.println("初始化数据耗时："+secondChronograph.lap().getIntervalMillis()+"毫秒");
	}

	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.lap().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
