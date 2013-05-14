package test.activity.other;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.PullListView;
import test.MyBaseActivity;
import test.adapter.ImageAdapter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 图片加载器界面
 * @author xiaopan
 *
 */
public class ImageLoaderActivity extends MyBaseActivity {
	private PullListView pullListView;
	private ImageAdapter imageAdapter;
	private Button button;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_image_loader);
		pullListView = (PullListView) findViewById(android.R.id.list);
		button = (Button) findViewById(R.id.imageLoader_button);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handleButton(!((Boolean) button.getTag()));
				imageAdapter.notifyDataSetChanged();
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		pullListView.openListHeaderReboundMode();
		pullListView.openListFooterReboundMode();
		pullListView.setAdapter(imageAdapter = new ImageAdapter(getBaseContext()));
		handleButton(true);
	}
	
	private void handleButton(boolean isFull){
		button.setTag(isFull);
		imageAdapter.setFull((Boolean) button.getTag());
		if((Boolean) button.getTag()){
			button.setText("变少");
		}else{
			button.setText("变多");
		}
	}
}