package test.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.widget.LiXueAutoLoadListView;
import me.xiaopan.easyandroid.widget.LiXueFooterControl;
import me.xiaopan.easyandroid.widget.LiXueFooterControl.onloadListener;

import android.R;
import android.os.Bundle;
import android.widget.ListView;
import test.MyBaseActivity;
import test.MyBaseListActivity;
import test.adapter.SimpleAdapter;

public class LixueLoadMore extends MyBaseListActivity{

	private LiXueAutoLoadListView liXueAutoLoadListView;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(me.xiaopan.easyandroid.R.layout.acttivity_lixueloadmore);
		liXueAutoLoadListView=(LiXueAutoLoadListView)getListView();
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		LiXueFooterControl liXueFooterControl=new LiXueFooterControl(getBaseContext());
		liXueAutoLoadListView.SetFooterControl(liXueFooterControl);
		liXueFooterControl.setOnListener(new onloadListener() {
			
			@Override
			public void onStartload(LiXueFooterControl liXueFooterControl) {
				// TODO Auto-generated method stub
				liXueFooterControl.Endload();
			}
		});
		
		
		
		List<String> contents=new ArrayList<String>();
		for(int w = 0; w < 20; w++){
			contents.add("这是第"+(w+1)+"条数据");
		}
		getListView().setAdapter(new SimpleAdapter(getBaseContext(), contents));
	}

}
