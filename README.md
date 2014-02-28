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
>* DisableInject：禁用注入功能。因为注解功能默认是开启的，如果你不想使用注入怕拖慢启动速度的话就可是使用此注解来禁用注入功能；
>* Inject：注入系统服务（通过getService()的各种Manager）或者ShardPreferences时；在注入ShardPreferences时你可以通过sharedPreferencesName参数指定名称，不指定时将注入默认的ShardPreferences；
>* InjectContentView：注入内容视图。只支持以下两种类型：
    1. Activity
    2. Fragment
>* InjectExtra：注入Bundle中的参数。只支持以下三种类型：
    1. Activity：Bundle来自getIntent().getExtras()
    2. Fragment：Bundle来自getArguments()
    3. BroadcastReceiver：Bundle来自onReceive()方法中intent参数的getExtras()
>* InjectFragment：注入Fragment。注意：此注解只有用在FragmentActivity中Fragment类型的字段上才有效；
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

##Downloads
**[android-easy-4.1.4.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.4.jar)**

**[android-easy-4.1.4-with-src.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.3-with-src.jar)**

##Change log
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