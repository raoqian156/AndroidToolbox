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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 提供注入功能的BroadcastReceiver
 */
public abstract class EasyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public final void onReceive(Context context, Intent intent) {
    	if(getClass().getAnnotation(DisableInject.class) == null){
    		InjectUtils.injectMembers(this, context, intent.getExtras());
    	}
        handleReceive(context, intent);
    }

    protected void handleReceive(Context context, Intent intent) {
        // proper template method to handle the receive
    }

}
