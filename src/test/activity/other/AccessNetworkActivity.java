package test.activity.other;

import me.xiaopan.androidlibrary.R;
import me.xiaopan.androidlibrary.net.ErrorInfo;
import test.MyBaseActivity;
import test.net.MyAccessNetworkListener;
import test.net.request.WeatherRequest;
import test.net.response.WeatherResponse;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

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
		accessNetwork(new WeatherRequest(), WeatherResponse.class, new MyAccessNetworkListener<WeatherResponse>(true) {
			@Override
			public void onStart() {
				showLoadingHintView();
			}
			
			@Override
			public void onSuccess(WeatherResponse t) {
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