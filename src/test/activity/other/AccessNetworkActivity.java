package test.activity.other;

import me.xiaopan.easyandroid.R;
import me.xiaopan.easynetwork.android.EasyHttpClient;
import me.xiaopan.easynetwork.android.StringHttpListener;

import org.apache.http.HttpResponse;

import test.MyBaseActivity;
import android.os.Bundle;
import android.text.Html;
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
		setContentView(R.layout.activity_network);
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
		EasyHttpClient.getInstance().get("http://www.miui.com/forum.php", new StringHttpListener(){
			@Override
			public void onStart() {
				showLoadingHintView();
			}

			@Override
			public void onSuccess(String responseContent) {
				text.setText(Html.fromHtml("成功了："+responseContent));
			}

			@Override
			public void onFailure(HttpResponse httpResponse) {
				text.setText("失败了："+httpResponse.getStatusLine().getStatusCode());
			}

			@Override
			public void onException(Throwable e) {
				e.printStackTrace();
				text.setText("异常了："+e.getMessage());
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