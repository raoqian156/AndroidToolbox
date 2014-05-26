# ![Logo](https://github.com/xiaopansky/HappyAndroid/raw/master/res/drawable-mdpi/ic_launcher.png) HappyAndroid

这是一个Android工具类库，其包含大量在Android开发中经常用到的工具类或方法，旨在让Android开发变得更简单、更随意、更舒心！

##Usage Guide
me.xiaopan.android.adapter
>* [CheckAdapter.java](https://github.com/xiaopansky/HappyAndroid/wiki/CheckAdapter.java)
>* FragmentListPagerAdapter.java
>* SimpleFragmentPagerAdapter.java
>* SimpleFragmentStatePagerAdapter.java
>* ViewAdapter.java
>* ViewListPagerAdapter.java

me.xiaopan.android.app
>* ActivityPool.java
>* ActivityUtils.java.java
>* DialogUtils.java
>* DownloadManagerUtils.java
>* MessageDialogFragment.java
>* ProgressDialogFragment.java

me.xiaopan.android.content
>* BroadcastUtils.java
>* FileUtils.java
>* IntentUtils.java
>* StartAppReceiver.java
>* UriUtils.java

me.xiaopan.android.content.pm
>* VersionManager.java

me.xiaopan.android.content.res
>* AssetsUtils.java
>* DimensUtils.java
>* RUtils.java

me.xiaopan.android.graphics
>* BitmapDecoder.java
>* BitmapUtils.java
>* Colors.java
>* RectUtils.java
>* TextUtils.java

me.xiaopan.android.graphics.drawble
>* DrawabLevelController.java

me.xiaopan.android.hardware
>* DeviceUtils.java

me.xiaopan.android.hardware.camera
>* BasePreviewAndPictureSizeCalculator.java
>* BestPreviewSizeCalculator.java
>* CameraManager.java
>* CameraUtils.java
>* LoopFocusManager.java

me.xiaopan.android.net
>* NetworkUtils.java

me.xiaopan.android.os
>* OSUtils.java
>* SDCardUtils.java

me.xiaopan.android.preference
>* PreferencesUtils.java

me.xiaopan.android.provider
>* PhoneUtils.java
>* SettingsUtils.java

me.xiaopan.android.util
>* AndroidLogger.java
>* BuildConfigUtils.java
>* Countdown.java
>* DoubleClickDetector.java
>* DoubleClickExitDetector.java
>* GeometryUtils.java
>* InputVerifyUtils.java
>* LoopTimer.java
>* OtherUtils.java
>* RebootThreadExceptionHandler.java

me.xiaopan.android.view
>* ViewRefreshHandler.java
>* ViewUtils.java
>* WindowUtils.java

me.xiaopan.android.view.animation
>* AnimationUtils.java
>* ViewAnimationUtils.java

me.xiaopan.android.view.inputmethod
>* InputMethodUtils.java

me.xiaopan.android.webkit
>* WebViewManager.java

me.xiaopan.android.widget
>* DepthPageTransformer.java
>* NestedGridView.java
>* NestedListView.java
>* ToastUtils.java
>* ZoomOutPageTransformer.java

##Downloads
>* [android-happy-4.5.0.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/releases/android-happy-4.5.0.jar)
>* [android-happy-4.5.0-with-src.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/releases/android-happy-4.5.0-with-src.jar)

Dependencies
>* [android-support-v4.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/android-support-v4.jar) 可选的。如果你要使用以下类的话就必须导入此类库
    1. FragmentListPagerAdapter.java
    2. SimpleFragmentPagerAdapter.java
    3. SimpleFragmentStatePagerAdapter.java
    4. ViewListPagerAdapter.java
    5. MessageDialogFragment.java
    6. ProgressDialogFragment.java
    7. DepthPageTransformer.java
    8. ZoomOutPageTransformer.java
    
>* [gson-2.2.4.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/gson-2.2.4.jar) 可选的。如果你要使用PreferenceUtils的putObject()和getObject()方法的话就必须要导入此类库

>* [pinyin4j-2.5.0.jar](https://github.com/xiaopansky/HappyAndroid/raw/master/libs/pinyin4j-2.5.0.jar) 可选的。此类库用于实现为中文匹配拼音或者比较两个中文的大小，所以如果你要使用CharUtils的getPinyin系列方法或者StringUtils的compare()方法的话就必须要导入此类库

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
