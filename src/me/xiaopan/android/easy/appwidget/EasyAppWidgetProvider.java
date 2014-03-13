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

package me.xiaopan.android.easy.appwidget;

import me.xiaopan.android.easy.inject.DisableInjector;
import me.xiaopan.android.easy.inject.Injector;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;

/**
 * 提供注入功能的提供注入功能的AppWidgetProvider
 */
public abstract class EasyAppWidgetProvider extends AppWidgetProvider {
	
	@Override
	public final void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		if(!getClass().isAnnotationPresent(DisableInjector.class)){
			new Injector(this, context).injectOtherMembers();
		}
		onHandleUpdate(context, appWidgetManager, appWidgetIds);
	}

    public void onHandleUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // proper template method to handle the receive
    }
}
