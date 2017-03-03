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

package me.xiaopan.android.content;

import me.xiaopan.java.lang.StringUtils;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * 广播工具箱
 * @author xiaopan
 *
 */
public class BroadcastUtils {
	/**
	 * 发送广播
	 * @param context 上下文
	 * @param filterAction 广播过滤器
	 * @param bundle 数据
	 * @return true：发送成功；false：发送失败，原因是context或者filterAction参数不正确
	 */
	public static final boolean sendBroadcast(Context context, String filterAction, Bundle bundle){
		boolean result = false;
		if(context != null && StringUtils.isNotEmpty(filterAction)){
			Intent intent = new Intent(filterAction);
			if(bundle != null){
				intent.putExtras(bundle);
			}
			context.sendBroadcast(intent);
			result = true;
		}
		return result;
	}
	
	/**
	 * 发送广播
	 * @param context 上下文
	 * @param filterAction 广播过滤器
	 * @return true：发送成功；false：发送失败，原因是context或者filterAction参数不正确
	 */
	public static final boolean sendBroadcast(Context context, String filterAction){
		return sendBroadcast(context, filterAction, null);
	}
}
