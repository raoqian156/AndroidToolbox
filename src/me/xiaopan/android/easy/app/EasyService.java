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

package me.xiaopan.android.easy.app;

import me.xiaopan.android.easy.inject.DisableInjector;
import me.xiaopan.android.easy.inject.Injector;
import android.app.Service;

/**
 * 提供注入功能的Service
 */
public abstract class EasyService extends Service{
	private Injector injector;
	
    public EasyService() {
        super();
    	if(!getClass().isAnnotationPresent(DisableInjector.class)){
        	injector = new Injector(this, getBaseContext(), null);
        }
        if(injector != null){
        	injector.injectOtherMembers();
		}
    }
}
