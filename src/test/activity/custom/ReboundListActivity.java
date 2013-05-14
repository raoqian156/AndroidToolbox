package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.SimpleAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 反弹列表
 * @author xiaopan
 *
 */
public class ReboundListActivity extends MyBaseActivity {
	private PullListView pullListView;
	private SimpleAdapter textAdapter;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_rebound_list);
		pullListView = (PullListView) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.pullList_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleButton(!((Boolean) button.getTag()));
				textAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		List<String> contents = new ArrayList<String>();
		for(String string : PullDownRefreshListActivity.getContents()){
			contents.add(string);
		}
		pullListView.setAdapter(textAdapter = new SimpleAdapter(getBaseContext(), contents));
		
		handleButton(true);
	}
	
	private void handleButton(boolean isFull){
		button.setTag(isFull);
		textAdapter.setFull((Boolean) button.getTag());
		if((Boolean) button.getTag()){
			button.setText("变少");
		}else{
			button.setText("变多");
		}
	}
}