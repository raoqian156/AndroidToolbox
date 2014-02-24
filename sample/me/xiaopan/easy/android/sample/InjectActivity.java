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
import java.util.Set;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.activity.EasyActivity;
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectExtra;
import me.xiaopan.android.easy.inject.InjectPreference;
import me.xiaopan.android.easy.inject.InjectResource;
import me.xiaopan.android.easy.inject.InjectService;
import me.xiaopan.android.easy.inject.InjectView;
import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.SecondChronograph;
import android.accounts.AccountManager;
import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.app.UiModeManager;
import android.app.WallpaperManager;
import android.app.admin.DevicePolicyManager;
import android.graphics.drawable.Drawable;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.DropBoxManager;
import android.os.PowerManager;
import android.os.Vibrator;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;
import android.view.inputmethod.InputMethodManager;
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
	
	@InjectPreference(MainActivity.KEY_BOOLEAN) private boolean booleanPreference;
	@InjectPreference(MainActivity.KEY_FLOAT) private float floatPreference;
	@InjectPreference(MainActivity.KEY_INT) private int intPreference;
	@InjectPreference(MainActivity.KEY_LONG) private long longPreference;
	@InjectPreference(MainActivity.KEY_STRING) private String stringPreference;
	@InjectPreference(MainActivity.KEY_STRING_SET) private Set<String> stringSetPreference;
	
	@InjectResource(R.integer.integer1) private int integer1;
	@InjectResource(R.string.string1) private String string1;
	@InjectResource(R.array.integer_array1) private int[] integers1;
	@InjectResource(R.array.string_array1) private String[] strings1;
	@InjectResource(R.drawable.ic_launcher) private Drawable launcherDrawable;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SecondChronograph secondChronograph = new SecondChronograph();
		
		StringBuffer extraStringBuffer = new StringBuffer("Extra注入结果：");
		extraStringBuffer.append("\n").append("byteField").append("=").append(byteField);
		extraStringBuffer.append("\n").append("byteField").append("=").append(byteField);
		extraStringBuffer.append("\n").append("shortField").append("=").append(shortField);
		extraStringBuffer.append("\n").append("intField").append("=").append(intField);
		extraStringBuffer.append("\n").append("longField").append("=").append(longField);
		extraStringBuffer.append("\n").append("charField").append("=").append(charField);
		extraStringBuffer.append("\n").append("floatField").append("=").append(floatField);
		extraStringBuffer.append("\n").append("doubleField").append("=").append(doubleField);
		extraStringBuffer.append("\n").append("booleanField").append("=").append(booleanField);
		extraStringBuffer.append("\n").append("stringField").append("=").append(stringField);
		extraStringBuffer.append("\n").append("charSequenceField").append("=").append(charSequenceField);
		extraStringBuffer.append("\n").append("byteFields").append("=").append(Arrays.toString(byteFields));
		extraStringBuffer.append("\n").append("shortFields").append("=").append(Arrays.toString(shortFields));
		extraStringBuffer.append("\n").append("intFields").append("=").append(Arrays.toString(intFields));
		extraStringBuffer.append("\n").append("longFields").append("=").append(Arrays.toString(longFields));
		extraStringBuffer.append("\n").append("charFields").append("=").append(Arrays.toString(charFields));
		extraStringBuffer.append("\n").append("floatFields").append("=").append(Arrays.toString(floatFields));
		extraStringBuffer.append("\n").append("doubleFields").append("=").append(Arrays.toString(doubleFields));
		extraStringBuffer.append("\n").append("booleanFields").append("=").append(Arrays.toString(booleanFields));
		extraStringBuffer.append("\n").append("stringFields").append("=").append(Arrays.toString(stringFields));
		extraStringBuffer.append("\n").append("stringFieldList").append("=").append(stringFieldList.toString());
		extraStringBuffer.append("\n").append("charSequenceFields").append("=").append(Arrays.toString(charSequenceFields));
		textView2.setText(extraStringBuffer.toString());
		
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
		
		StringBuffer preferenceStringBuffer = new StringBuffer("Preference注入结果：");
		preferenceStringBuffer.append("\n").append("booleanPreference").append("=").append(booleanPreference);
		preferenceStringBuffer.append("\n").append("floatPreference").append("=").append(floatPreference);
		preferenceStringBuffer.append("\n").append("intPreference").append("=").append(intPreference);
		preferenceStringBuffer.append("\n").append("longPreference").append("=").append(longPreference);
		preferenceStringBuffer.append("\n").append("stringPreference").append("=").append(stringPreference);
		preferenceStringBuffer.append("\n").append("stringSetPreference").append("=").append(stringSetPreference);
		textView4.setText(preferenceStringBuffer.toString());
		
		StringBuffer resourceStringBuffer = new StringBuffer("Resource注入结果：");
		resourceStringBuffer.append("\n").append("integer1").append("=").append(integer1);
		resourceStringBuffer.append("\n").append("string1").append("=").append(string1);
		resourceStringBuffer.append("\n").append("integers1").append("=").append(Arrays.toString(integers1));
		resourceStringBuffer.append("\n").append("strings1").append("=").append(Arrays.toString(strings1));
		textView5.setText(resourceStringBuffer);
		textView5.setCompoundDrawablePadding(16);
		textView5.setCompoundDrawablesWithIntrinsicBounds(launcherDrawable, null, null, null);
		
		System.out.println("初始化数据耗时："+secondChronograph.count().getIntervalMillis()+"毫秒");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.count().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
