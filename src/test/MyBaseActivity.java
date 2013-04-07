package test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;

import com.umeng.analytics.MobclickAgent;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.app.BaseActivity;
import me.xiaopan.androidlibrary.net.AccessNetworkListener;
import me.xiaopan.androidlibrary.net.Request;
import me.xiaopan.androidlibrary.net.Response;
import test.net.MyResponseHandler;
import android.os.Bundle;
import android.view.MenuItem;

public abstract class MyBaseActivity extends BaseActivity {

	@Override
	protected void onPreInit(Bundle savedInstanceState) {
		ApplicationExceptionHandler.getInstance().setContext(this);
		if(!isRemoveTitleBar()){
			getActionBar().setBackgroundDrawable(getDrawable(R.drawable.shape_titlebar));
			if(isEnableBackHome()){
				getActionBar().setDisplayHomeAsUpEnabled(true);
			}
		}
		MobclickAgent.onError(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected boolean isUseCustomAnimation() {
		return true;
	}

	/**
	 * 判断是否启用返回主页
	 * @return 是否启用返回主页
	 */
	protected boolean isEnableBackHome() {
		return true;
	}

	@Override
	protected int[] onGetDefaultStartActivityAnimation() {
		return new int[]{R.anim.base_slide_to_left_in, R.anim.base_normal};
	}

	@Override
	protected int[] onGetDefaultFinishActivityAnimation() {
		return new int[]{R.anim.base_normal, R.anim.base_slide_to_right_out};
	}

	@Override
	protected int onGetLoadingHintViewId() {
		return R.id.comm_layout_loadingHint;
	}

	@Override
	protected int onGetListEmptyHintViewId() {
		return R.id.comm_layout_listEmptyHint;
	}

	@Override
	protected void onClickListEmptyHintView() {
		toastL(R.string.comm_hint_reloadIn);
	}

	@Override
	protected void onNetworkNotAvailable() {
		toastL(R.string.comm_hint_networkError);
	}

	@Override
	public void onBecauseExceptionFinishActivity() {
		finishActivity(R.anim.base_alpha_show, R.anim.base_alpha_hide);
	}

	@Override
	public String getHostServerAddress(){
		return "http://m.weather.com.cn/data";
	}
	
	@Override
	public void onPromptExitApplication() {
		toastL(R.string.comm_hint_exitHint);
	}

	public MyApplication getMyApplication(){
		return (MyApplication) getApplication();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home: finishActivity(); break;
			case R.id.comm_menu_exception: throw new NullPointerException();
			case R.id.comm_menu_exit: finishApplication(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * 获取拍摄的照片文件
	 * @return 拍摄的照片文件
	 * @throws IOException 
	 */
	public File getPhotoFile() throws IOException{
		File file = getFileFromDynamicCacheDir("card.jpeg");
		if(!file.exists()){
			file.createNewFile();
		}
		return file;
	}
	
	public void accessNetwork(Request request, Class<? extends Response> responseClass, final AccessNetworkListener<?> accessNetworkListener){
		accessNetwork(request, new MyResponseHandler(responseClass), accessNetworkListener);
	}
	
	public void accessNetwork(Request request, Type responseType, final AccessNetworkListener<?> accessNetworkListener){
		accessNetwork(request, new MyResponseHandler(responseType), accessNetworkListener);
	}
}