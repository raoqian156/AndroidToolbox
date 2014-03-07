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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import android.app.Activity;

public class ActivityPool {
	private static Map<Integer, Activity> activityMap = new ConcurrentHashMap<Integer, Activity>();
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
	
	public void finish(int... ids){
		for(int id : ids){
			finish(id);
		}
	}
	
	public void finish(Set<Integer> ids){
		for(int id : ids){
			finish(id);
		}
	}
	
	public void finishOther(int activityId){
		Set<Integer> ids = activityMap.keySet();
		for(Integer id : ids){
			if(id != activityId){
				finish(id);
			}
		}
	}
	
	public void finishAll(){
		Set<Integer> ids = activityMap.keySet();
		for(Integer id : ids){
			finish(id);
		}
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
