/*
 * Copyright (C) 2013 Peng fei Pan <sky@xiaopan.me>
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

package me.xiaopan.android.app;

import android.annotation.TargetApi;
import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.File;

import static android.app.DownloadManager.Request.NETWORK_WIFI;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DownloadManagerUtils {

    /**
     * @param downPath "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Long downFile(Context context, String downPath, String saveName,
                                String notificationTitle, String notificationMessage) {
        return downFile(context, downPath, saveName, notificationTitle, notificationMessage, null);

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Long downFile(final Context outContext, String downPath, String saveName,
                                String notificationTitle, String notificationMessage, final DownListener listener) {
        final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downPath));
        //设置在什么网络情况下进行下载
//        NETWORK_MOBILE、NETWORK_WIFI  如果只允许wifi下载，而当前网络为3g，则下载会等待
        request.setAllowedNetworkTypes(NETWORK_WIFI);
        //设置通知栏标题
//        表示下载进行中和下载完成的通知栏是否显示。默认只显示下载中通知。VISIBILITY_VISIBLE_NOTIFY_COMPLETED表示下载完成后显示通知栏提示。
//          VISIBILITY_HIDDEN表示不显示任何通知栏提示，这个需要在AndroidMainfest中添加权限android.permission.DOWNLOAD_WITHOUT_NOTIFICATION.
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(notificationTitle);
        request.setDescription(notificationMessage);
        request.setAllowedOverRoaming(false);
        String dirPath = Environment.getExternalStorageState();//文件夹位置
        File file = new File(dirPath);
        if (!file.exists()) {//确保文件夹存在
            file.mkdirs();
        }
        final DownloadManager manager = (DownloadManager) outContext.getSystemService(Context.DOWNLOAD_SERVICE);

        //设置文件存放目录
        request.setDestinationInExternalFilesDir(outContext, Environment.getExternalStorageState(), saveName);
        final long downId = manager.enqueue(request);
        if (listener != null) {
            IntentFilter downTaskFilter = new IntentFilter();
            downTaskFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            downTaskFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
            DownLoadCompleteReceiver receiver = new DownLoadCompleteReceiver(outContext, downId, manager, listener);
            outContext.registerReceiver(receiver, downTaskFilter);
        }
        return downId;
    }

    private static class DownLoadCompleteReceiver extends BroadcastReceiver {
        private long downId = 0;
        private Context outContext;
        private DownloadManager manager;
        private DownListener listener;

        public DownLoadCompleteReceiver(Context outContext, long id, DownloadManager manager, DownListener listener) {
            this.downId = id;
            this.outContext = outContext;
            this.manager = manager;
            this.listener = listener;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            long reference = intent.getLongExtra(DownloadManager.ACTION_NOTIFICATION_CLICKED, -1);
            Log.e("DownloadManagerUtils", "reference = " + reference + "   downId = " + downId);
            if (reference == downId) {
                Log.e("DownloadManagerUtils", "ACTION_NOTIFICATION_CLICKED = " + DownloadManager.ACTION_NOTIFICATION_CLICKED);
                manager.remove(downId);
                return;
            }
            listener.onFinish();
            outContext.unregisterReceiver(this);
        }
    }


    private static DownListener mDownListener;

    public interface DownListener {
        void onFinish();
    }

    /**
     * 根据请求ID获取本地文件的Uri
     *
     * @return 不存在
     */
    public static String getLocalFileUriByRequestId(Context context, long requestId) {
        String result = null;
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            if (cursor.moveToFirst()) {
                result = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
            }
            cursor.close();
        }
        return result;
    }

    /**
     * 根据请求ID判断下载是否完成
     *
     * @param context
     * @param requestId
     * @return
     */
    public static boolean isFinish(Context context, long requestId) {
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            boolean result = cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_SUCCESSFUL;
            cursor.close();
            return result;
        } else {
            return false;
        }
    }

    /**
     * 根据请求ID判断是否正在下载
     *
     * @param context
     * @param requestId
     * @return
     */
    public static boolean isDownloading(Context context, long requestId) {
        if (Build.VERSION.SDK_INT >= 9) {
            Query query = new Query();
            query.setFilterById(requestId);
            Cursor cursor = ((DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
            boolean result = cursor.moveToFirst() && cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS)) == DownloadManager.STATUS_RUNNING;
            cursor.close();
            return result;
        } else {
            return false;
        }
    }
}