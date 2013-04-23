package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.PicturePlayer.Picture;
import me.xiaopan.androidlibrary.widget.PicturePlayer.PlayWay;
import me.xiaopan.javalibrary.util.NetUtils;
import test.MyBaseActivity;
import test.widget.PointPicturePlayer;
import android.os.Bundle;

/**
 * 图片播放器
 * @author xiaopan
 */
public class PicturePlayerActivity extends MyBaseActivity {
	private PointPicturePlayer picturePlayerCircleLeftToRight;
	private PointPicturePlayer picturePlayerCircleRightToLeft;
	private PointPicturePlayer picturePlayerSwingLeftToRight;
	private PointPicturePlayer picturePlayerSwingRightToLeft;
	private List<Picture> pictures;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_picture_player);
		picturePlayerCircleLeftToRight = (PointPicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_leftToRight);
		picturePlayerCircleRightToLeft = (PointPicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_rightToLeft);
		picturePlayerSwingLeftToRight = (PointPicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_leftToRight);
		picturePlayerSwingRightToLeft = (PointPicturePlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_rightToLeft);
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
		
		picturePlayerCircleLeftToRight.setPictures(pictures);
		picturePlayerCircleRightToLeft.setPictures(pictures);
		picturePlayerSwingLeftToRight.setPictures(pictures);
		picturePlayerSwingRightToLeft.setPictures(pictures);
		
		picturePlayerCircleLeftToRight.setPlayWay(PlayWay.CIRCLE_LEFT_TO_RIGHT);
		picturePlayerCircleRightToLeft.setPlayWay(PlayWay.CIRCLE_RIGHT_TO_LEFT);
		picturePlayerSwingLeftToRight.setPlayWay(PlayWay.SWING_LEFT_TO_RIGHT);
		picturePlayerSwingRightToLeft.setPlayWay(PlayWay.SWING_RIGHT_TO_LEFT);
	}

	@Override
	protected void onResume() {
		picturePlayerCircleLeftToRight.startPaly();
		picturePlayerCircleRightToLeft.startPaly();
		picturePlayerSwingLeftToRight.startPaly();
		picturePlayerSwingRightToLeft.startPaly();
		super.onPause();
	}

	@Override
	protected void onPause() {
		picturePlayerCircleLeftToRight.stopPaly();
		picturePlayerCircleRightToLeft.stopPaly();
		picturePlayerSwingLeftToRight.stopPaly();
		picturePlayerSwingRightToLeft.stopPaly();
		super.onPause();
	}
}