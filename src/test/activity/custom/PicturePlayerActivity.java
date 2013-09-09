/*
 * Copyright 2013 Peng fei Pan
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package test.activity.custom;

import java.util.ArrayList;
import java.util.List;

import me.xiaopan.easy.android.widget.PicturePlayAdapter;
import me.xiaopan.easy.android.widget.ViewPlayer.PlayWay;
import me.xiaopan.easyandroid.R;
import test.MyBaseActivity;
import test.widget.PointViewPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * 图片播放器
 */
public class PicturePlayerActivity extends MyBaseActivity {
	private PointViewPlayer picturePlayerCircleLeftToRight;
	private PointViewPlayer picturePlayerCircleRightToLeft;
	private PointViewPlayer picturePlayerSwingLeftToRight;
	private PointViewPlayer picturePlayerSwingRightToLeft;
	private List<String> pictures;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_picture_player);
		picturePlayerCircleLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_leftToRight);
		picturePlayerCircleRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_circle_rightToLeft);
		picturePlayerSwingLeftToRight = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_leftToRight);
		picturePlayerSwingRightToLeft = (PointViewPlayer) findViewById(R.id.picturePlayer_picturePlayer_swing_rightToLeft);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		picturePlayerCircleLeftToRight.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toastL(""+position);
			}
		});
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		pictures = new ArrayList<String>();
		for(String url : getStringArray(R.array.autoPlayGallery_urls2)){
			pictures.add(url);
		}
		
		picturePlayerCircleLeftToRight.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerCircleLeftToRight.setPlayWay(PlayWay.CIRCLE_LEFT_TO_RIGHT);
		
		picturePlayerCircleRightToLeft.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerCircleRightToLeft.setPlayWay(PlayWay.CIRCLE_RIGHT_TO_LEFT);
		
		picturePlayerSwingLeftToRight.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerSwingLeftToRight.setPlayWay(PlayWay.SWING_LEFT_TO_RIGHT);
		
		picturePlayerSwingRightToLeft.setViewPlayAdapter(new PicturePlayAdapter(getBaseContext(), pictures));
		picturePlayerSwingRightToLeft.setPlayWay(PlayWay.SWING_RIGHT_TO_LEFT);
	}

	@Override
	public void onResume() {
		picturePlayerCircleLeftToRight.startPaly();
		picturePlayerCircleRightToLeft.startPaly();
		picturePlayerSwingLeftToRight.startPaly();
		picturePlayerSwingRightToLeft.startPaly();
		super.onPause();
	}

	@Override
	public void onPause() {
		picturePlayerCircleLeftToRight.stopPaly();
		picturePlayerCircleRightToLeft.stopPaly();
		picturePlayerSwingLeftToRight.stopPaly();
		picturePlayerSwingRightToLeft.stopPaly();
		super.onPause();
	}
}
