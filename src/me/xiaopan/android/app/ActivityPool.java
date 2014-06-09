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

package me.xiaopan.android.app;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.annotation.SuppressLint;
import android.app.Activity;

public class ActivityPool {
	private static Map<Integer, Activity> activityMap;
	
	private ActivityPool(){
		
	}
	
	@SuppressLint("UseSparseArrays")
	public static Activity put(Activity activity){
		if(activityMap == null){
			activityMap = Collections.synchronizedMap(new HashMap<Integer, Activity>());
		}
		return activityMap.put(activity.hashCode(), activity);
	}
	
	public static Activity remove(Activity activity){
		if(activityMap == null || activityMap.size() == 0){
			return null;
		}
		return activityMap.remove(activity.hashCode());
	}
	
	public static void finishAll(){
		if(activityMap == null || activityMap.size() == 0){
			return;
		}
		Iterator<Entry<Integer, Activity>> iterator = activityMap.entrySet().iterator();
		while(iterator.hasNext()){
			iterator.next().getValue().finish();
		}
		activityMap.clear();
	}
}
