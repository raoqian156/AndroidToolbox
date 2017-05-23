package me.xiaopan.android.toolbox.sample.show;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.xiaopan.android.app.DownloadManagerUtils;
import me.xiaopan.android.toolbox.sample.R;

/**
 * Created by liuyichuanmei on 2017/5/23.
 */

public class DownLoadTest extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_text);
    }

    public void onClick(View v) {
        DownloadManagerUtils.downFile(DownLoadTest.this, "http://120.26.57.153/webyikuaiwan/ykw.apk", "testdown.apk", "测试标题", "测试提示语", new DownloadManagerUtils.DownListener() {
            @Override
            public void onFinish() {
                Log.e("DownLoadTest", "listener = onFinish");
            }
        });
    }
}
