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
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class DownloadManagerUtils {

    /**
     * @param urlPath "http://gdown.baidu.com/data/wisegame/55dc62995fe9ba82/jinritoutiao_448.apk"
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static Long downFile(Context context, String urlPath, String savePath,
                                String notificationTitle, String notificationMessage) {


        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(urlPath));
        //设置在什么网络情况下进行下载
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        //设置通知栏标题
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle(notificationTitle);
        request.setDescription(notificationMessage);
        request.setAllowedOverRoaming(false);
        //设置文件存放目录
        request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, savePath);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        return manager.enqueue(request);
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

//    private class DownLoadCompleteReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
//                long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//                Toast.makeText(MainActivity.this, "编号：" + id + "的下载任务已经完成！", Toast.LENGTH_SHORT).show();
//            } else if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
//                Toast.makeText(MainActivity.this, "别瞎点！！！", Toast.LENGTH_SHORT).show();
//            }
//            DownloadManager downManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
//            id = downManager.enqueue(request);
//        }
//    }

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