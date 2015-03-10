package me.xiaopan.android.toolbox.sample;

import android.app.Activity;
import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class EntryFragment extends MyFragment{
	public static final String PARAM_STRING_JSON_ENTRY_ITEM = "PARAM_STRING_JSON_ENTRY_ITEM";
	private EntryItem entryItem;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(getArguments() != null){
			String itemJson = getArguments().getString(PARAM_STRING_JSON_ENTRY_ITEM);
			if(itemJson != null && !"".equals(itemJson)){
                entryItem = new Gson().fromJson(itemJson, EntryItem.class);
                setPageName(entryItem.getTitle());
			}
		}
	}

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if(entryItem == null || entryItem.getChildItems() == null || entryItem.getChildItems().size() == 0){
			Toast.makeText(getActivity(), "没有选项", Toast.LENGTH_SHORT).show();
			return null;
		}
		
		final ListView listView = (ListView) inflater.inflate(R.layout.view_list, container, false);
		listView.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_item_text, getTitles()));
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				arg2 -= listView.getHeaderViewsCount();
				EntryItem clickEntryItem = entryItem.getChildItems().get(arg2);
				Class<?> targetClass = clickEntryItem.getTargetClass();
				
				if(targetClass == null){
					Toast.makeText(getActivity(), "未知类型", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(Fragment.class.isAssignableFrom(targetClass)){
					try {
						Fragment fragment = (Fragment) targetClass.newInstance();
						if(clickEntryItem.getChildItems() != null){
							Bundle bundle = new Bundle();
							bundle.putString(PARAM_STRING_JSON_ENTRY_ITEM, new Gson().toJson(clickEntryItem));
							fragment.setArguments(bundle);
						}
						FragmentTransaction transaction = getFragmentManager().beginTransaction();
						transaction.addToBackStack(null);
						transaction.setCustomAnimations(R.anim.window_enter, R.anim.window_exit, R.anim.window_pop_enter, R.anim.window_pop_exit);
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
		String[] titles = new String[entryItem.getChildItems().size()];
		for(int w = 0, length = entryItem.getChildItems().size(); w < length; w++){
			titles[w] = entryItem.getChildItems().get(w).getTitle();
		}
		return titles;
	}
	
	public static class EntryItem {
		private String title;
		private String targetClassName;
		private List<EntryItem> childItems;
		
		public EntryItem(String title, Class<?> targetClass, List<EntryItem> childItems) {
			this.title = title;
			this.targetClassName = targetClass!=null?targetClass.getName():null;
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
