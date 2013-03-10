package test.activity;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.PicturePlayer;
import me.xiaopan.androidlibrary.widget.PicturePlayer.Picture;
import me.xiaopan.javalibrary.util.NetUtils;
import test.MyBaseActivity;
import test.widget.PointIndicator;
import android.os.Bundle;

/**
 * 图片播放器
 * @author xiaopan
 */
public class PicturePlayerActivity extends MyBaseActivity {
	private PicturePlayer picturePlayer;
	private List<Picture> pictures;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.picture_player);
		picturePlayer = (PicturePlayer) findViewById(R.id.picturePlayer_picturePlayer);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		pictures = new ArrayList<Picture>();
		for(String url : getStringArray(R.array.autoPlayGallery_urls2)){
			Picture picture = new Picture(url);
			picture.setFile(getFileFromExternalCacheDir(NetUtils.getFileNameFromURL(url)));
			pictures.add(picture);
		} 
		picturePlayer.setDefaultImageResId(R.drawable.image_default);
		picturePlayer.setPictures(pictures);
		picturePlayer.setIndicator(new PointIndicator(getBaseContext()));
		picturePlayer.setTowardsTheRight(false);
	}

	@Override
	protected void onResume() {
		picturePlayer.startPaly();
		super.onPause();
	}

	@Override
	protected void onPause() {
		picturePlayer.stopPaly();
		super.onPause();
	}
}