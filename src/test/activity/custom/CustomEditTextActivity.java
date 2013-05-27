package test.activity.custom;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easyandroid.widget.ClearEditText;
import test.MyBaseActivity;
import android.os.Bundle;

/**
 * 自定义文本编辑器
 * @author xiaopan
 *
 */
public class CustomEditTextActivity extends MyBaseActivity {
	private ClearEditText editText;
	
	@Override
	protected void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.activity_custom_edit_text);
		editText = (ClearEditText) findViewById(R.id.edit_customEditText_account);
	}

	@Override
	protected void onInitListener(Bundle savedInstanceState) {
	}

	@Override
	protected void onInitData(Bundle savedInstanceState) {
		editText.setLeftDrawableResId(R.drawable.icon_user_name);
	}
}