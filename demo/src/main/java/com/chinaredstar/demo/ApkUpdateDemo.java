package com.chinaredstar.demo;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.constant.EC;
import com.chinaredstar.core.constant.RC;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.utils.LogUtil;
import com.chinaredstar.core.utils.PathUtil;
import com.chinaredstar.core.utils.download.ApkDownloadUtil;
import com.chinaredstar.core.utils.download.DownloadProgress;
import com.chinaredstar.core.utils.download.DownloadUtil;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class ApkUpdateDemo extends BaseActivity {
    private TextView tv_progress1, tv_progress2;
    final static int EC_DOWNLOAD = 11111;
    int type = 0;
    String url = "http://imtt.dd.qq.com/16891/D8A0D91C87420C8B7FA66806015C4D94.apk?fsname=com.istone.activity_6.4.2_53.apk&csr=97c2";

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_dowload;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tv_progress1 = findViewById(R.id.tv_progress1);
        tv_progress2 = findViewById(R.id.tv_progress2);
    }

    @Override
    protected String[] iNeedPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    protected void onUserPermitPermissionsDothing() {
        LogUtil.d("用户同意了。。。。。。。。。。。。。。。。。");
        if (type == 0) {
            ApkDownloadUtil.download(url, "@2.0");
        } else if (type == 1) {
            DownloadUtil.download(this, url, PathUtil.getAppCacheDir() + "/" + PathUtil.DOWNLOAD_DIR, "V_" + 2.0 + ".apk", EC_DOWNLOAD);
        }
    }

    @Override
    protected void onUserRejectPermissionDothing() {
        super.onUserRejectPermissionDothing();
        LogUtil.d("用户拒绝了。。。。。。。。。。。。。。。。。");
    }


    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, "当前需要读写权限！如果没有权限，应用将不能正常工作")
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

    @Override
    protected void onEventCallback(EventCenter event) {
        switch (event.code) {
            case EC.EC_DOWNLOAD_APK://  downloadmanager
                if (event.data instanceof DownloadProgress) {
                    DownloadProgress mProgress = (DownloadProgress) event.data;
                    tv_progress1.setText(mProgress.progress * 100 + "%");
                }
                break;
            case EC_DOWNLOAD: // okhttp3
                if (event.data instanceof DownloadProgress) {
                    DownloadProgress mProgress = (DownloadProgress) event.data;
                    tv_progress2.setText(mProgress.progress * 100 + "%");
                }
                break;
        }
    }

    public void download1(View v) {//downloadmanager
        type = 0;
        requestPermissions();
    }

    public void download2(View v) {//okhttp3
        type = 1;
        requestPermissions();
    }

    @Override
    protected boolean enabledEventBus() {
        return true;
    }
}
