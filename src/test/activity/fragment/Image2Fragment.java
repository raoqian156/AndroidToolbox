package test.activity.fragment;

import me.xiaopan.androidlibrary.app.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片碎片
 */
public class Image2Fragment extends BaseFragment {
	public static final String PARAM_REQUIRED_IMAHE_RES_ID = "PARAM_REQUIRED_IMAHE_RES_ID";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ImageView imageView = new ImageView(getActivity().getBaseContext());
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		imageView.setImageResource(getArguments().getInt(PARAM_REQUIRED_IMAHE_RES_ID));
		return imageView;
	}
}