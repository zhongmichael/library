package com.chinaredstar.core.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.cache.ss.SharedPreferencesHelper;
import com.chinaredstar.core.utils.ApkUtil;

import java.io.File;

/**
 * Created by hairui.xiang on 2017/9/20.
 */

public class InstallAPKReceiver extends BroadcastReceiver {
    public final static String ACTION_DOWNLOAD_COMPLETE = "com.chinaredstar.hrxlibcore.action.DOWNLOAD_COMPLETE";

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            //downloadmanager
            if (null != intent && DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                final long dwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                final long refernece = SharedPreferencesHelper.getObj("apk_id", Long.class);
                if (refernece == dwonloadID) {
                    Uri apkUri = ((DownloadManager) BaseApplication.getInstance().getSystemService(Context.DOWNLOAD_SERVICE)).getUriForDownloadedFile(refernece);
                    ApkUtil.install(apkUri);
                }
            }
            // okttp3
            else if (null != intent && ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())) {
                String path = intent.getStringExtra("okhttp3_apk_download_path");
                File apkFile = new File(path);
                if (apkFile.exists() && apkFile.isFile()) {
                    ApkUtil.install(apkFile);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
