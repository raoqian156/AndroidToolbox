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
package me.xiaopan.android.easy.widget;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

public class SimpleFragmentPagerAdapter extends FragmentPagerAdapter {
	private FragmentManager fragmentManager;
	private List<Fragment> fragments;
	
	public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, List<Fragment> fragments) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
		this.fragments = fragments;
	}
	
	public SimpleFragmentPagerAdapter(FragmentManager fragmentManager, Fragment... fragments) {
		super(fragmentManager);
		this.fragmentManager = fragmentManager;
		this.fragments = new ArrayList<Fragment>(fragments.length);
		for(Fragment fragment : fragments){
			this.fragments.add(fragment);
		}
	}

	@Override
	public Fragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}
	
	@Override  
    public int getItemPosition(Object object) {  
        return POSITION_NONE;  
    }
	
	public List<Fragment> getFragments() {
		return fragments;
	}

	public void setFragments(List<Fragment> fragmentsList){
		if (fragments != null && fragments.size() > 0) {
			FragmentTransaction ft = fragmentManager.beginTransaction();
			for (Fragment f : this.fragments) {
				ft.remove(f);
			}
			ft.commit();
			ft = null;
			fragmentManager.executePendingTransactions();
		}
		
		this.fragments = fragmentsList;
		notifyDataSetChanged();
	}
}