# ![Logo](https://github.com/xiaopansky/HappyAndroid/raw/master/res/drawable-mdpi/ic_launcher.png) HappyAndroid

这是一个Android工具类库，旨在让Android开发变得更简单，最低兼容Android2.2

##Features
>* 提供了一系列的EasyActivity、EasyFragment超类，并且内置了toast，startActiivty，showDialog等方法，可以简化一些操作；
>* 另外提供了一系列的EasyInjectActivity和EasyInjectFragment，都继承自HappyInject中的Activity、Fragment可实现注入功能；
>* 提供了Camera相关的工具，包括Camera管理类CameraManager、Camera最佳预览以及输出尺寸计算类CameraOptimalSizeCalculator、Camera循环对焦管理器LoopFocusManager等；
>* 提供了Android开发中常用的工具类，例如：ActivityUtils、AndroidLogger、BitmapDecoder、AnimationUtils、NetworkUtils、IntentUtils、ViewUtils等；
>* 提供了纯Java开发中常用的工具类，例如：ArrayUtils、FileUtils、AnnotationUtils、ReflectUtils、StringUtils等；

要详细了解注入功能的使用请访问[Android-HappyInject](https://github.com/xiaopansky/Android-HappyInject)

##Downloads
>* [android-happy-4.3.3.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/releases/android-happy-4.3.3.jar)
>* [android-happy-4.3.3-with-src.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/releases/android-happy-4.3.3-with-src.jar)

##Depend
>* [android-happy-inject.jar](https://github.com/xiaopansky/Android-HappyInject) 可选的。如果你要使用EasyInjectActivity和EasyInjectFragment就必须引入此类库
>* [android-support-v4.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/android-support-v4.jar) 可选的。如果你要使用EasyFragmentActivity、EasyFragment、EasyListFragment、EastDialogFragment、EasyInjectFragmentActivity、EasyInjectFragment、EasyInjectListFragment、EastInjectDialogFragment就必须要引入此类库
>* [android-support-v7-appcompat.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/android-support-v7-appcompat.jar)
可选的。如果你要使用EasyActionBarActivity、EasyInjectActionBarActivity就必须要引入此类库，值的注意的是使用此类库的时候一定要使用sdk\extras\android\support\v7\appcompat\libs目录下的android-support-v4.jar（因为这个目录下的android-support-v4.jar包含有android-support-v7-appcompat.jar需要的类）
>* [gson-2.2.2.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/gson-2.2.2.jar) 可选的。如果你要使用PreferenceUtils的putObject()和getObject()方法的话就必须要引入此类库
>* [pinyin4j-2.5.0.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/pinyin4j-2.5.0.jar) 可选的。此类库用于实现为中文匹配拼音或者比较两个中文的大小，所以如果你要使用CharUtils的getPinyin系列方法或者StringUtils的compare()方法的话就必须要引入此类库

##Change log
###4.3.3
>* 增加VersionManager，用来检测版本是否更新，常用来做应用初始化

###4.3.2
>* 去除内置的SQL框架，将其独立出来作为一个新的项目[Android-HappySql](https://github.com/xiaopansky/Android-HappySql)

###4.3.1
>* 增加DateJudging，用来判定日期

###4.3.0
>* 剥离注入功能
>* 更改包名

###4.2.4
>* 增加SimpleFragmentPagerAdapter.java、SimpleFragmentStatePagerAdapter.java、FragmentListPagerAdapter.java、ViewListPagerAdapter.java

###4.2.3
>* 新增BuildConfigUtils类讲Utils中的isDebugPackage()移到BuildConfigUtils中病改名为isDebug()
>* 新增RUtils类，用于获取R类中的字段的值

###4.2.2
>* Utils中增加isDebugPackage()方法，用于判断是否是开发包

###4.2.1
>* CameraManager改为延迟适用于延迟打开Camera的场景
>* 增加LoopFocusManager，用于循环对焦，适用于扫描条码
>* 删除AutoFocusManager
>* 改DeviceUtils.getScreenSize()方法返回值的类型，由int[]改为Point

###4.2.0
>* 增加InjectExtraJson和@InjectPreferenceJson注解，用于支持将Bundle或SharedPreference中的字符串转换成对象
>* 优化注入部分处理逻辑

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