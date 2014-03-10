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

package me.xiaopan.android.easy.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Activity;

public class ActivityPool {
	@SuppressLint("UseSparseArrays")
	private static Map<Integer, Activity> activityMap = Collections.synchronizedMap(new HashMap<Integer, Activity>());
	private static Set<Integer> waitFinishActivityIds = Collections.synchronizedSet(new HashSet<Integer>());
	private Activity activity;
	
	public ActivityPool(Activity activity) {
		this.activity = activity;
		put(activity);
	}
	
	public Activity put(Activity activity){
		return activityMap.put(activity.hashCode(), activity);
	}
	
	public Activity get(int activityHashCode){
		return activityMap.get(activityHashCode);
	}
	
	public Activity remove(int activityHashCode){
		waitFinishActivityIds.remove(activityHashCode);
		return activityMap.remove(activityHashCode);
	}
	
	public Activity remove(Activity activity){
		return remove(activity.hashCode());
	}
	
	public void finish(int id){
		Activity activity = get(id);
		if(activity != null){
			remove(activity);
			activity.finish();
		}
	}
	
	public void finish(Set<Integer> ids){
		Iterator<Entry<Integer, Activity>> iterator = activityMap.entrySet().iterator();
		Entry<Integer, Activity> entry = null;
		while(iterator.hasNext()){
			entry = iterator.next();
			if(ids.contains(entry.getKey())){
				ids.remove(entry.getKey());
				if(waitFinishActivityIds != ids){
					waitFinishActivityIds.remove(entry.getKey());
				}
				entry.getValue().finish();
				iterator.remove();
			}
		}
	}
	
	public void finishOther(int activityId){
		Iterator<Entry<Integer, Activity>> iterator = activityMap.entrySet().iterator();
		Entry<Integer, Activity> entry = null;
		while(iterator.hasNext()){
			entry = iterator.next();
			if(entry.getKey() != activityId){
				waitFinishActivityIds.remove(entry.getKey());
				entry.getValue().finish();
				iterator.remove();
			}
		}
	}
	
	public void finishAll(){
		waitFinishActivityIds.clear();
		Iterator<Entry<Integer, Activity>> iterator = activityMap.entrySet().iterator();
		Entry<Integer, Activity> entry = null;
		while(iterator.hasNext()){
			entry = iterator.next();
			entry.getValue().finish();
		}
		activityMap.clear();
	}
	
	public void clearWaitingCircle(){
		waitFinishActivityIds.clear();
	}
	
	public void finishAllWaitingActivity(){
		if(!waitFinishActivityIds.isEmpty()){
			finish(waitFinishActivityIds);
		}
	}
	
	public void finishOther(){
		finishOther(activity.hashCode());
	}
	
	public void removeSelf(){
		remove(activity);
	}
	
	public boolean putSelfToWaitingCircle(){
		return waitFinishActivityIds.add(activity.hashCode());
	}
	
	public boolean removeSelfFromWaitingCircle(){
		return waitFinishActivityIds.remove(activity.hashCode());
	}
}
