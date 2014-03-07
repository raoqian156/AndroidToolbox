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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.app.EasyActivity;
import me.xiaopan.android.easy.inject.Inject;
import me.xiaopan.android.easy.inject.InjectContentView;
import me.xiaopan.android.easy.inject.InjectExtra;
import me.xiaopan.android.easy.inject.InjectPreference;
import me.xiaopan.android.easy.inject.InjectResource;
import me.xiaopan.android.easy.inject.InjectView;
import me.xiaopan.java.easy.util.ReflectUtils;
import me.xiaopan.java.easy.util.Stopwatch;
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
	@InjectView(R.id.text_main1) private TextView textView1;
	@InjectView(R.id.text_main2) private TextView textView2;
	@InjectView(R.id.text_main3) private TextView textView3;
	@InjectView(R.id.text_main4) private TextView textView4;
	@InjectView(R.id.text_main5) private TextView textView5;

	@InjectExtra(MainActivity.PARAM_BYTE) private byte byteField;
	@InjectExtra(MainActivity.PARAM_BYTE_ARRAY) private byte[] byteFields;
	@InjectExtra(MainActivity.PARAM_SHORT) private short shortField;
	@InjectExtra(MainActivity.PARAM_SHORT_ARRAY) private short[] shortFields;
	@InjectExtra(MainActivity.PARAM_INT) private int intField;
	@InjectExtra(MainActivity.PARAM_INT_ARRAY) private int[] intFields;
	@InjectExtra(MainActivity.PARAM_LONG) private long longField;
	@InjectExtra(MainActivity.PARAM_LONG_ARRAY) private long[] longFields;
	@InjectExtra(MainActivity.PARAM_CHAR) private char charField;
	@InjectExtra(MainActivity.PARAM_CHAR_ARRAY) private char[] charFields;
	@InjectExtra(MainActivity.PARAM_FLOAT) private float floatField;
	@InjectExtra(MainActivity.PARAM_FLOAT_ARRAY) private float[] floatFields;
	@InjectExtra(MainActivity.PARAM_DOUBLE) private double doubleField;
	@InjectExtra(MainActivity.PARAM_DOUBLE_ARRAY) private double[] doubleFields;
	@InjectExtra(MainActivity.PARAM_BOOLEAN) private boolean booleanField;
	@InjectExtra(MainActivity.PARAM_BOOLEAN_ARRAY) private boolean[] booleanFields;
	@InjectExtra(MainActivity.PARAM_STRING) private String stringField;
	@InjectExtra(MainActivity.PARAM_STRING_ARRAY) private String[] stringFields;
	@InjectExtra(MainActivity.PARAM_STRING_ARRAY_LIST) private ArrayList<String> stringFieldList;
	@InjectExtra(MainActivity.PARAM_CHAR_SEQUENCE) private CharSequence charSequenceField;
	@InjectExtra(MainActivity.PARAM_CHAR_SEQUENCE_ARRAY) private CharSequence[] charSequenceFields;

	@Inject private AccessibilityManager accessibilityManager;
	@Inject private AccountManager accountManager;
	@Inject private ActivityManager activityManager;
	@Inject private AlarmManager alarmManager;
	@Inject private AudioManager audioManager;
	@Inject private ConnectivityManager connectivityManager;
	@Inject private DevicePolicyManager devicePolicyManager;
	@Inject private DropBoxManager dropBoxManager;
	@Inject private InputMethodManager inputMethodManager;
	@Inject private KeyguardManager keyguardManager;
	@Inject private LayoutInflater layoutInflater;
	@Inject private LocationManager locationManager;
	@Inject private NotificationManager notificationManager;
	@Inject private PowerManager powerManager;
	@Inject private SearchManager searchManager;
	@Inject private SensorManager sensorManager;
	@Inject private TelephonyManager telephonyManager;
	@Inject private UiModeManager uiModeManager;
	@Inject private Vibrator vibrator;
	@Inject private WallpaperManager wallpaperManager;
	@Inject private WifiManager wifiManager;
	@Inject private WindowManager windowManager;
	
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
		
		Stopwatch stopwatch = new Stopwatch();
		
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
				if(field.isAnnotationPresent(Inject.class)){
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
		
		System.out.println("初始化数据耗时："+stopwatch.lap().getIntervalMillis()+"毫秒");
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		long useMillis = Second.SECOND_CHRONOGRAPH.lap().getIntervalMillis();
		textView1.setText("启动耗时："+useMillis+"毫秒");
	}
}
