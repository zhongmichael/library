package com.chinaredstar.core.utils.download;

import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

import com.chinaredstar.core.R;
import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.utils.HandlerUtil;
import com.chinaredstar.core.utils.PathUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import static com.chinaredstar.core.constant.EC.EC_DOWNLOAD_APK;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class ApkDownloadUtil {

    private final static ContentObserver mObserver = new ContentObserver(
            HandlerUtil.handler()) {

        @Override
        public void onChange(boolean selfChange) {
            queryProgress();
        }
    };

    /**
     * 不可见更新
     */
    public static void download(String url, String newVersion) {
        download(url, newVersion, "", " ", false);
    }

    /**
     * app更新放到通知栏
     */
    public static void download(String url, String newVersion, String notifyTitle, String notifyDesc) {
        download(url, newVersion, notifyTitle, notifyDesc, true);
    }

    /**
     * 通知栏下载
     * Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
     */
    private static void download(String url, String newVersion, String title, String desc, boolean isShowNotiUI) {
        if (!enabledDownloadManager()) {
            Toast.makeText(BaseApplication.getInstance(), BaseApplication.getInstance().getString(R.string.dm_disable), Toast.LENGTH_SHORT).show();
            return;
            //
        }
        //注册内容改变监听
        BaseApplication.getInstance().getContentResolver().registerContentObserver(Uri.parse("content://downloads/my_downloads"), true, mObserver);
        // 创建下载请求
        DownloadManager.Request down = new DownloadManager.Request(Uri.parse(url));
        // 设置允许使用的网络类型，这里是移动网络和wifi都可以
        down.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);

        int visibility = isShowNotiUI ? DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED : DownloadManager.Request.VISIBILITY_HIDDEN;
        down.setNotificationVisibility(visibility);
        // 设置为可见和可管理
        down.setVisibleInDownloadsUi(isShowNotiUI);
        // 禁止发出通知，既后台下载
        down.setShowRunningNotification(isShowNotiUI);
        down.setTitle(title);// 设置下载中通知栏提示的标题
        down.setDescription(desc);// 设置下载中通知栏提示的介绍
        //String mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        down.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        down.allowScanningByMediaScanner();
        // 设置下载后文件存放的位置
        File apkDir = new File(PathUtil.getAppCacheDir(), PathUtil.DOWNLOAD_DIR);
        if (!apkDir.exists()) {
            apkDir.mkdirs();
        }
        String apkName = "V_" + newVersion + ".apk";
        down.setDestinationInExternalFilesDir(BaseApplication.getInstance(), "/" + PathUtil.DOWNLOAD_DIR, apkName);
        // 将下载请求放入队列
        long refernece = ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(down);
        // 把当前下载的ID保存起来
        SharedPreferencesHelper.putObj("apk_id", refernece);
    }

    private static void queryProgress() {
        Long refernece = SharedPreferencesHelper.getObj("apk_id", Long.class) == null ? -1 : SharedPreferencesHelper.getObj("apk_id", Long.class);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(refernece);
        Cursor c = ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).query(query);
        if (c != null && c.moveToFirst()) {
            int statusIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int reasonIndex = c.getColumnIndex(DownloadManager.COLUMN_REASON);
            int titleIndex = c.getColumnIndex(DownloadManager.COLUMN_TITLE);
            int fileSizeIndex = c.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES);
            int bytesDLIndex = c.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR);
            String title = c.getString(titleIndex);
            // Translate the pause reason to friendly text.
            int reason = c.getInt(reasonIndex);
            int fileSize = c.getInt(fileSizeIndex);// max length
            int bytesDL = c.getInt(bytesDLIndex);// current download length
            int status = c.getInt(statusIndex);  // Display the status
            ////////////////////callback progress
            EventCenter<DownloadProgress> ec = new EventCenter<>();
            ec.code = EC_DOWNLOAD_APK;
            ec.data = new DownloadProgress((float) Math.abs(bytesDL) / Math.abs(fileSize), fileSize);
            EventBus.getDefault().post(ec);
            /////////////////////////
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                    // 正在下载，不做任何事情
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    // 完成
                    BaseApplication.getInstance().getContentResolver().unregisterContentObserver(mObserver);

                    break;
                case DownloadManager.STATUS_FAILED:
                    // 清除已下载的内容，重新下载
                    ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).remove(refernece);
                    break;
            }
        }
        if (null != c) c.close();
    }

    public static boolean enabledDownloadManager() {
        int state = BaseApplication.getInstance().getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
        if (state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                || state == PackageManager.COMPONENT_ENABLED_STATE_DISABLED_UNTIL_USED) {
            return false;
        }
        return true;
    }
}
