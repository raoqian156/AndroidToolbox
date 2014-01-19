# ![Logo](https://github.com/ixiaopan/EasyAndroid/raw/master/res/drawable-mdpi/ic_launcher.png) EasyAndroid

这是一个Android开发框架和基础工具类库，旨在让Android开发变得更简单

##Features
>* 提供了一系列的Easy*Activity和Easy*Fragment，并且都继承于Robo*系列，所以你依然可以使用各种注入；
>* Easy系列的Activity增加了注入ContentView的功能，你只需在Activity上加上InjectContentView注解并指定布局ID即可；
>* Easy系列的类另外提供了一些扩展功能，比如：toast，startActiivty，showDialog等等；
>* 提供了简单的SQL框架，便于自动生成sql代码，快捷执行增、删、改、查操作；
>* 提供了Camera相关的工具，包括Camera管理类CameraManager、Camera最佳预览以及输出尺寸计算类CameraOptimalSizeCalculator、Camera自动对焦管理器AutoFocusManager等；
>* 提供了Android开发中常用的工具类，例如：ActivityUtils、AndroidLogger、BitmapDecoder、AnimationUtils、NetworkUtils、IntentUtils、ViewUtils等；
>* 提供了纯Java开发中常用的工具类，例如：ArrayUtils、FileUtils、AnnotationUtils、ReflectUtils、StringUtils等；

##Change log
**[android-easy-4.0.0.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/releases/android-easy-4.0.0.jar)**

##Depend
>* **[robo-guice-2.0.1.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/robo-guice-2.0.1.jar)** Required. 用于实现注入功能
>* **[guice-3.0-no_aop.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/guice-3.0-no_aop.jar)** Required. robo-guice-2.0.1.jar所依赖的类库
>* **[javax.inject-1.jar](https://github.com/xiaopansky/EasyAndroid/raw/master/libs/javax.inject-1.jar)** Required. guice-3.0-no_aop.jar所依赖的类库
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