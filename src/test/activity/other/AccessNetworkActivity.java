package test.activity.other;

import java.util.List;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.net.ErrorInfo;
import me.xiaopan.androidlibrary.util.Logger;
import test.MyBaseActivity;
import test.net.MyAccessNetworkListener;
import test.net.request.QestionnaireRequest;
import test.net.response.QestionnaireResponse;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;

/**
 * 访问网络界面
 * @author xiaopan
 *
 */
public class AccessNetworkActivity extends MyBaseActivity {

	private TextView text;
	
	@Override
	public void onInitLayout(Bundle savedInstanceState) {
		setContentView(R.layout.network);
		text = (TextView) findViewById(R.id.tv_network);
	}

	@Override
	public void onInitListener(Bundle savedInstanceState) {
		
	}

	@Override
	public void onInitData(Bundle savedInstanceState) {
		loadData();
	}
	
	private void loadData(){
		NetworkInfo networkInfo = ((ConnectivityManager) getBaseContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if(networkInfo != null){
			Logger.i("网络状态："+networkInfo.getState().name() + "; 网络类型："+networkInfo.getTypeName()+networkInfo.getType()+"; 网络副类型："+networkInfo.getSubtypeName()+networkInfo.getSubtype());
		}else{
			Logger.i("没有网络连接");
		}
		accessNetwork(new QestionnaireRequest(), new TypeToken<List< QestionnaireResponse>>(){}.getType(), new MyAccessNetworkListener<List< QestionnaireResponse>>() {
			@Override
			public void onStart() {
				showLoadingHintView();
			}
			
			@Override
			public void onSuccess(List< QestionnaireResponse> t) {
				text.setText(t.toString());
			}

			@Override
			public void onError(ErrorInfo errorInfo) {
				text.setText(errorInfo.toString());
			}

			@Override
			public void onEnd() {
				closeLoadingHintView();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.network, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_network_refresh: loadData(); break;
			default: break;
		}
		return super.onOptionsItemSelected(item);
	}
}