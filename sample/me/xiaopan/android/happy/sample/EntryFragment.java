package me.xiaopan.android.happy.sample;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.android.happy.R;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class EntryFragment extends Fragment{
	public static final String PARAM_STRING_JSON_ITEMS = "PARAM_STRING_JSON_ITEMS";
	private List<EntryItem> entryItems;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments() != null){
			String itemJson = getArguments().getString(PARAM_STRING_JSON_ITEMS);
			if(itemJson != null && !"".equals(itemJson)){
				entryItems = new Gson().fromJson(itemJson, new TypeToken<List<EntryItem>>(){}.getType());
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(entryItems == null || entryItems.size() == 0){
			Toast.makeText(getActivity(), "没有选项", Toast.LENGTH_SHORT).show();
			return null;
		}
		
		final ListView listView = new ListView(getActivity());
		listView.setBackgroundColor(Color.BLACK);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, getTitles()));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				arg2 -= listView.getHeaderViewsCount();
				EntryItem entryItem = entryItems.get(arg2);
				Class<?> targetClass = entryItem.getTargetClass();
				
				if(targetClass == null){
					Toast.makeText(getActivity(), "未知类型", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(Fragment.class.isAssignableFrom(targetClass)){
					try {
						Fragment fragment = (Fragment) targetClass.newInstance();
						if(entryItem.getChildItems() != null){
							Bundle bundle = new Bundle();
							bundle.putString(PARAM_STRING_JSON_ITEMS, new Gson().toJson(entryItem.getChildItems()));
							fragment.setArguments(bundle);
						}
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.addToBackStack(null);
						transaction.setCustomAnimations(R.anim.base_slide_to_left_in, R.anim.base_slide_to_left_out, R.anim.base_slide_to_right_in, R.anim.base_slide_to_right_out);
						transaction.add(R.id.frame_main_content, fragment);
						transaction.commitAllowingStateLoss();
					} catch (Exception e) {
						e.printStackTrace();
						Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}
				}else if(Activity.class.isAssignableFrom(targetClass)){
					startActivity(new Intent(getActivity(), targetClass));
				}else{
					Toast.makeText(getActivity(), "未知类型", Toast.LENGTH_SHORT).show();
				}
			}
		});
		return listView;
	}
	
	private String[] getTitles(){
		String[] titles = new String[entryItems.size()];
		for(int w = 0, length = entryItems.size(); w < length; w++){
			titles[w] = entryItems.get(w).getTitle();
		}
		return titles;
	}
	
	public static class EntryItem {
		private String title;
		private String targetClassName;
		private List<EntryItem> childItems;
		
		public EntryItem(String title, Class<?> targetClass, List<EntryItem> childItems) {
			this.title = title;
			this.targetClassName = targetClass.getName();
			this.childItems = childItems;
		}
		
		public EntryItem(String title, Class<?> targetClass) {
			this.title = title;
			this.targetClassName = targetClass!=null?targetClass.getName():null;
		}
		
		public EntryItem() {
		}

		public String getTitle() {
			return title;
		}
		
		public void setTitle(String title) {
			this.title = title;
		}
		
		public Class<?> getTargetClass() {
			try {
				return targetClassName!=null?getClass().getClassLoader().loadClass(targetClassName):null;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		
		public void setTargetClass(Class<?> targetClass) {
			this.targetClassName = targetClass!=null?targetClass.getName():null;
		}
		
		public void addChildItem(EntryItem entryItem){
			if(childItems == null){
				childItems = new ArrayList<EntryItem>();
			}
			childItems.add(entryItem);
		}

		public List<EntryItem> getChildItems() {
			return childItems;
		}
	}
}
