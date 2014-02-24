# ![Logo](https://github.com/xiaopansky/EasyAndroid/raw/master/res/drawable-mdpi/ic_launcher.png) EasyAndroid

这是一个Android开发框架和基础工具类库，旨在让Android开发变得更简单

##Features
>* 提供了一系列的EasyActivity和EasyFragment，继承后可以使用注入功能；
>* Easy系列的类另外提供了一些扩展功能，比如：toast，startActiivty，showDialog等等；
>* 提供了简单的SQL框架，便于自动生成sql代码，快捷执行增、删、改、查操作；
>* 提供了Camera相关的工具，包括Camera管理类CameraManager、Camera最佳预览以及输出尺寸计算类CameraOptimalSizeCalculator、Camera自动对焦管理器AutoFocusManager等；
>* 提供了Android开发中常用的工具类，例如：ActivityUtils、AndroidLogger、BitmapDecoder、AnimationUtils、NetworkUtils、IntentUtils、ViewUtils等；
>* 提供了纯Java开发中常用的工具类，例如：ArrayUtils、FileUtils、AnnotationUtils、ReflectUtils、StringUtils等；

##Usage Guide
###使用注入功能
###注入ContentView
你只需继承Easy系列的Activity，然后在Class上加上InjectContentView注解，并在注解中指定布局ID即可

###注入View


##Downloads
**[android-easy-4.1.0.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.0.jar)**

**[android-easy-4.1.0-with-src.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.1.0-with-src.jar)**

##Change log
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
```