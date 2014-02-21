package me.xiaopan.easy.android.sample;

import me.xiaopan.android.easy.R;
import me.xiaopan.android.easy.activity.EasyFragmentActivity;
import me.xiaopan.android.easy.inject.InjectView;
import android.widget.Button;
import android.widget.TextView;

public class Activity1 extends EasyFragmentActivity{
	@InjectView(R.id.text_main)
	private TextView text;

	@InjectView(R.id.button_main)
	private Button button;

}
