package test.activity.views;

import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.activity.adapter.GalleryAdapter;
import android.os.Bundle;
import android.widget.Gallery;

/**
 * 画廊使用示例
 */
public class GalleryActivity extends MyBaseActivity {
	private Gallery gallery;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_gallery);
		gallery = (Gallery) findViewById(R.id.gallery_gallery);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		gallery.setAdapter(new GalleryAdapter(getBaseContext(), getStringArray(R.array.autoPlayGallery_urls2)));
	}
}