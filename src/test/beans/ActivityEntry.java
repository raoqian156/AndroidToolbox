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

package test.beans;

import test.adapter.TextAdapter.Text;
import android.os.Bundle;

public class ActivityEntry implements Text{
	private String name;
	private Class<?> action;
	private Bundle bundle;
	private boolean checkNetwork;
	
	public ActivityEntry(String name, Class<?> action, Bundle bundle){
		setName(name);
		setAction(action);
		setBundle(bundle);
	}
	
	public ActivityEntry(String name, Class<?> action, Bundle bundle, boolean isCheckNetwork){
		setName(name);
		setAction(action);
		setBundle(bundle);
		setCheckNetwork(isCheckNetwork);
	}
	
	public ActivityEntry(String name, Class<?> action){
		setName(name);
		setAction(action);
	}
	
	public ActivityEntry(String name, Class<?> action, boolean isCheckNetwork){
		setName(name);
		setAction(action);
		setCheckNetwork(isCheckNetwork);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Class<?> getAction() {
		return action;
	}
	public void setAction(Class<?> action) {
		this.action = action;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}

	@Override
	public String getText() {
		return getName();
	}

	@Override
	public boolean isShowArrow() {
		return true;
	}

	public boolean isCheckNetwork() {
		return checkNetwork;
	}

	public void setCheckNetwork(boolean checkNetwork) {
		this.checkNetwork = checkNetwork;
	}
}