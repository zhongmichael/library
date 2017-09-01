package com.chinaredstar.core.utils;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.base.BaseBean;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.core.eventbus.EventCenter;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class ApkUtil {
    public static final int EVENT_CODE_DOWNLOAD_APK = 999999999;

    public final static class Progress extends BaseBean {
        public int progress;
        public int max;
        public int status;

        public Progress(int progress, int max, int status) {
            this.progress = progress;
            this.max = max;
            this.status = status;
        }
    }

    private final static ContentObserver mObserver = new ContentObserver(
            HandlerUtil.handler()) {

        @Override
        public void onChange(boolean selfChange) {
            queryProgress();
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
        }
    };

    /**
     * 通知栏下载
     * Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE
     */
    public static void download(String url, String title, String desc, boolean isShowNotiUI) {
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
        File apkDir = new File(PathUtil.getAppFilesDir().toString(), "apk/");
        if (!apkDir.exists()) {
            apkDir.mkdirs();
        }
        String apkName = "V_" + DeviceUtil.getVersionName() + ".apk";
        down.setDestinationInExternalPublicDir(apkDir.getAbsolutePath(), apkName);
        // 将下载请求放入队列
        long refernece = ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).enqueue(down);
        // 把当前下载的ID保存起来
        SharedPreferencesHelper.putObj("apk_id", refernece);
    }

    private static void queryProgress() {
        Long refernece = SharedPreferencesHelper.getObj("apk_id", Long.class) == null ? -1 : SharedPreferencesHelper.getObj("apk_id", Long.class);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById();
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
            EventCenter<Progress> ec = new EventCenter<>();
            ec.code = EVENT_CODE_DOWNLOAD_APK;
            ec.data = new Progress(bytesDL, fileSize, status);
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
    }

    /**
     * download manger finished broadcast
     */
    public static class InstallAPKReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (null != intent && DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                    final long dwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                    final long refernece = SharedPreferencesHelper.getObj("apk_id", Long.class);
                    if (refernece == dwonloadID) {
                        Uri apkUri = ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(refernece);
                        install(apkUri);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * apk安装
     *
     * @param file
     */

    public static void install(File file) {
        if (file != null) {
            //apk文件的本地路径
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
            //为这个新apk开启一个新的activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);
            //关闭旧版本的应用程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    public static void install(Uri uri) {
        if (null != uri) {
            //apk文件的本地路径
            //会根据用户的数据类型打开android系统相应的Activity。
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //设置intent的数据类型是应用程序application
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
            //为这个新apk开启一个新的activity栈
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //开始安装
            BaseApplication.getInstance().startActivity(intent);
            //关闭旧版本的应用程序的进程
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }
}
