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
package test.activity.adapter;

import java.util.List;

import test.activity.fragment.ImageFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ImageFragmentPagerAdapter extends FragmentPagerAdapter {

	private List<ImageFragment> testFragmentList;
	
	public ImageFragmentPagerAdapter(FragmentManager fragmentManager,  List<ImageFragment> testFragmentList){
		super(fragmentManager);
		this.testFragmentList = testFragmentList;
	}
	
	@Override
	public Fragment getItem(int arg0) {
		return testFragmentList.get(arg0);
	}
	
	@Override
    public CharSequence getPageTitle(int position) {
        return testFragmentList.get(position).getTitle();
    }

	@Override
	public int getCount() {
		return testFragmentList.size();
	}
}
