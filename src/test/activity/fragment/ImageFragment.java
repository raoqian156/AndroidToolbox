package test.activity.fragment;

import me.xiaopan.easyandroid.app.BaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * 图片碎片
 */
public class ImageFragment extends BaseFragment {
	private String title;
	private int resId;
	
	public ImageFragment(String title, int resId){
		this.title = title;
		this.resId = resId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		ImageView imageView = new ImageView(getActivity().getBaseContext());
		imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
		imageView.setImageResource(resId);
		return imageView;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}