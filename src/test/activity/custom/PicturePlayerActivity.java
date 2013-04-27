package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.widget.PicturePlayAdapter;
import me.xiaopan.androidlibrary.widget.PicturePlayAdapter.Picture;
import me.xiaopan.androidlibrary.widget.ViewPlayer.PlayWay;
import me.xiaopan.javalibrary.util.NetUtils;
import test.MyBaseActivity;
import test.widget.PointViewPlayer;
import android.os.Bundle;

/**
 * 图片播放器
 */
public class PicturePlayerActivity extends MyBaseActivity {
	private PointViewPlayer picturePlayerCircleLeftToRight;
	private PointViewPlayer picturePlayerCircleRightToLeft;
	private PointViewPlayer picturePlayerSwingLeftToRight;
	private PointViewPlayer picturePlayerSwingRightToLeft;
	private List<Picture> pictures;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_picture_player);
		picturePlayerCircleLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_leftToRight);
		picturePlayerCircleRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_rightToLeft);
		picturePlayerSwingLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_leftToRight);
		picturePlayerSwingRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_rightToLeft);
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
		
		
		picturePlayerCircleLeftToRight.setPlayerAdapter(new PicturePlayAdapter(getBaseContext(), pictures, R.drawable.image_default));
		picturePlayerCircleLeftToRight.setPlayWay(PlayWay.CIRCLE_LEFT_TO_RIGHT);
		
		picturePlayerCircleRightToLeft.setPlayerAdapter(new PicturePlayAdapter(getBaseContext(), pictures, R.drawable.image_default));
		picturePlayerCircleRightToLeft.setPlayWay(PlayWay.CIRCLE_RIGHT_TO_LEFT);
		
		picturePlayerSwingLeftToRight.setPlayerAdapter(new PicturePlayAdapter(getBaseContext(), pictures, R.drawable.image_default));
		picturePlayerSwingLeftToRight.setPlayWay(PlayWay.SWING_LEFT_TO_RIGHT);
		
		picturePlayerSwingRightToLeft.setPlayerAdapter(new PicturePlayAdapter(getBaseContext(), pictures, R.drawable.image_default));
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