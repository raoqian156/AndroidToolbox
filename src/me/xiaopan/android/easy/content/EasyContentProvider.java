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

package me.xiaopan.android.easy.content;

import me.xiaopan.android.easy.util.inject.DisableInject;
import me.xiaopan.android.easy.util.inject.InjectUtils;
import android.content.ContentProvider;

/**
 * 提供注入功能的提供注入功能的ContentProvider
 * <br>如果你继承了这个类，请务必重写onCrate()方法并调用super.onCreate()方法
 */
public abstract class EasyContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
    	if(getClass().getAnnotation(DisableInject.class) == null){
    		InjectUtils.injectMembers(this, getContext(), null);
    	}
        return true;
    }
}
