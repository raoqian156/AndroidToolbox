# ![Logo](https://github.com/xiaopansky/EasyAndroid/raw/master/res/drawable-mdpi/ic_launcher.png) EasyAndroid

这是一个Android开发框架和基础工具类库，旨在让Android开发变得更简单，最低兼容Android2.2

##Features
>* 提供了一系列的EasyActivity、EasyFragment、EasyService等超类，继承后可以使用注入功能；
>* Easy系列的类另外提供了一些扩展功能，比如：toast，startActiivty，showDialog等等；
>* 提供了简单的SQL框架，便于自动生成sql代码，快捷执行增、删、改、查操作；
>* 提供了Camera相关的工具，包括Camera管理类CameraManager、Camera最佳预览以及输出尺寸计算类CameraOptimalSizeCalculator、Camera自动对焦管理器AutoFocusManager等；
>* 提供了Android开发中常用的工具类，例如：ActivityUtils、AndroidLogger、BitmapDecoder、AnimationUtils、NetworkUtils、IntentUtils、ViewUtils等；
>* 提供了纯Java开发中常用的工具类，例如：ArrayUtils、FileUtils、AnnotationUtils、ReflectUtils、StringUtils等；

##Usage Guide
###1.使用注入功能
####目前可继承并使用注入功能的超类分以下四类

Activity：
>* EasyAccountAuthenticatorActivity
>* EasyActionBarActivity
>* EasyActivity
>* EasyActivityGroup
>* EasyExpandableListActivity
>* EasyFragmentActivity
>* EasyLauncherActivity
>* EasyListActivity
>* EasyMapActivity
>* EasyPreferenceActivity
>* EasyTabActivity

Fragment：
>* EasyFragment
>* EasyDialogFragment
>* EasyListFragment

Service：
>* EasyIntentService
>* EasyService

BroadcastRecevier：
>* EasyBroadcastReceiver

ContextProvidet：
>* EasyAppWidgetProvider
>* EasyContentProvider

Loader：
>* EasyAsyncTaskLoader
>* EasyLoader

####可使用的注解有以下几种：
>* DisableInjector：禁用注入功能。因为注解功能默认是开启的，如果你不想使用注入的话就可是使用此注解来禁用注入功能；
>* Inject：注入系统服务（通过getService()的各种Manager）或者ShardPreferences；在注入ShardPreferences时你可以通过sharedPreferencesName参数指定名称，不指定时将注入默认的ShardPreferences；
>* InjectContentView：注入内容视图。只支持以下两种类型：
    1. Activity
    2. Fragment
>* InjectExtra：注入Bundle中的参数。只支持以下三种类型：
    1. Activity：Bundle来自getIntent().getExtras()
    2. Fragment：Bundle来自getArguments()
    3. BroadcastReceiver：Bundle来自onReceive()方法中intent参数的getExtras()
>* InjectFragment：注入Fragment。只支持FragmentActivity中Fragment类型的字段；
>* InjectParentMember：注入父类的成员变量。默认是不注入父类的成员变量的；
>* InjectPreference：注入SharedPreferences中的参数。SharedPreferencees的名称可通过sharedPreferencesName参数指定，不指定时将从默认的SharedPreferencees中获取参数；
>* InjectResource：注入资源。支持以下资源类型：
    1. boolean
    2. String
    3. String[]
    4. Integer
    5. Integer[]
    6. Drawable
    7. ColorStateList
    8. Animation
    9. Movie
>* InjectView：注入View。只支持以下两种类型：
    1. Activity
    2. Fragment
    
####示例
```java
@InjectContentView(R.layout.activity_main)
public class MainActivity extends EasyFragmentActivity{
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
	public static final String KEY_BOOLEAN = "KEY_BOOLEAN";
	public static final String KEY_FLOAT = "KEY_FLOAT";
	public static final String KEY_INT = "KEY_INT";
	public static final String KEY_LONG = "KEY_LONG";
	public static final String KEY_STRING = "KEY_STRING";
	public static final String KEY_STRING_SET = "KEY_STRING_SET";

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
	}
```


##Downloads
**[android-easy-4.1.9.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.9.jar)**

**[android-easy-4.1.9-with-src.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.9-with-src.jar)**

##Change log
###4.1.9
>* 修改Easy系列Activity中Handler的实现方式避免内存泄露
>* 修复注入KeyguardManager时的BUG
>* 增加最佳预览分辨率计算器类BestPreviewSizeCalculator.java

###4.1.8
>* CameraManager改为始终设置Type

###4.1.7
>* 在Easy系列的Activity中增加NoTitle和FullScreen注解替换removeTitleBar()和hiddenStatusBar()方法来实现去掉标题栏和隐藏状态栏功能

###4.1.6
>* 修复Fragment注入BUG（当Fragment第二次执行onCrateVie()的时候无法注入View字段）

###4.1.5
>* 优化注入功能效率，结果是比非注入还要快，所以强烈推荐更新
>* 更新了SQL框架，新的框架功能更加强大

###4.1.4
>* 增加InjectFragment支持

###4.1.3
>* 增加Recevier、Service等组件的注入支持

###4.1.2
>* 修改版权信息

###4.1.1
>* 重命名秒表类由SecondChronograph改为Stopwatch，并重命名计次方法由count改为lap

###4.1.0
>* 完善注入功能，支持注入Extra参数，SharedPreference、各种Manager、Resource

###4.0.4
>* 去除RoboGuide库，改用自己实现注入功能，原因是RoboGuice有点庞大，导致Activity启动时间过长。
>* 去除RoboGuice后目前只实现了InjectContentView和InjectView。InjectExtra等其它注入功能会后续加上。

###4.0.3
>* 在Widget包下增加ViewPager切换动画DepthPageTransformer和ZoomOutPageTransformer

###4.0.2
>* 优化AnimationUtils默认动画持续时间，由1000毫秒减少到500毫秒
>* ViewAnimationUtils所有方法增加重载函数，增加控制在动画执行期间禁止点击的参数
>* 修复setEnableDoubleClickExitAcpplication()方法命名错误
>* 优化ViewAnimationUtils透明度渐变系列方法的处理逻辑，使连续多次调用也可正常运行

###4.0.1
>* 修复ActivityUtils.startForResult()方法的Intent FlagBUG
>* SinpleFragmentPagerAdapter增加重载构造函数
>* startActivity()系列方法不再放到Handler中执行

###4.0.0

##Depend
>* **[android-support-v4.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/android-support-v4.jar)** 可选的。如果你要使用EasyFragmentActivity、EasyFragment就必须要引入此类库
>* **[android-support-v7-appcompat.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/android-support-v7-appcompat.jar)**
可选的。如果你要使用EasyActionBarActivity就必须要引入此类库，值的注意的是使用此类库的时候一定要使用sdk\extras\android\support\v7\appcompat\libs目录下的android-support-v4.jar（因为这个目录下的android-support-v4.jar包含有android-support-v7-appcompat.jar需要的类）
>* **[gson-2.2.2.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/gson-2.2.2.jar)** 可选的。如果你要使用PreferenceUtils的putObject()和getObject()方法的话就必须要引入此类库
>* **[pinyin4j-2.5.0.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/pinyin4j-2.5.0.jar)** 可选的。此类库用于实现为中文匹配拼音或者比较两个中文的大小，所以如果你要使用CharUtils的getPinyin系列方法或者StringUtils的compare()方法的话就必须要引入此类库

##License
```java
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
```