package test.activity.custom;

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
	private PicturePlayer picturePlayerToLeft;
	private PicturePlayer picturePlayerToRight;
	private PicturePlayer picturePlayerToNoCycle;
	private List<Picture> pictures;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.picture_player);
		picturePlayerToLeft = (PicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_toLeft);
		picturePlayerToRight = (PicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_toRight);
		picturePlayerToNoCycle = (PicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_noCycle);
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
		
		picturePlayerToLeft.setDefaultImageResId(R.drawable.image_default);
		picturePlayerToRight.setDefaultImageResId(R.drawable.image_default);
		picturePlayerToNoCycle.setDefaultImageResId(R.drawable.image_default);
		
		picturePlayerToLeft.setPictures(pictures);
		picturePlayerToRight.setPictures(pictures);
		picturePlayerToNoCycle.setPictures(pictures);
		
		picturePlayerToLeft.setIndicator(new PointIndicator(getBaseContext()));
		picturePlayerToRight.setIndicator(new PointIndicator(getBaseContext()));
		picturePlayerToNoCycle.setIndicator(new PointIndicator(getBaseContext()));
		
		picturePlayerToLeft.setTowardsTheRight(true);
		picturePlayerToRight.setTowardsTheRight(false);
		picturePlayerToNoCycle.setLoopPlayback(false);
	}

	@Override
	protected void onResume() {
		picturePlayerToLeft.startPaly();
		picturePlayerToRight.startPaly();
		picturePlayerToNoCycle.startPaly();
		super.onPause();
	}

	@Override
	protected void onPause() {
		picturePlayerToLeft.stopPaly();
		picturePlayerToRight.stopPaly();
		picturePlayerToNoCycle.stopPaly();
		super.onPause();
	}
}