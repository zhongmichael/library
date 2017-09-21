package com.chinaredstar.demo;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.constant.EC;
import com.chinaredstar.core.constant.RC;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.callback.FileCallBack;
import com.chinaredstar.core.utils.ApkUtil;
import com.chinaredstar.core.utils.LogUtil;
import com.chinaredstar.core.utils.PathUtil;
import com.chinaredstar.core.utils.download.ApkDownloadUtil;
import com.chinaredstar.core.utils.download.DownloadProgress;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class ApkUpdateDemo extends BaseActivity {
    private TextView tv_progress1, tv_progress2;
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
            download(url, PathUtil.getAppCacheDir() + "/" + PathUtil.DOWNLOAD_DIR, "V_" + 2.0 + ".apk");
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

    /***
     * okttp3
     * @param url 下载地址
     * @param destFileDir 目标文件存储的文件夹路径
     * @param destFileName 目标文件存储的文件名
     * */
    public void download(String url, final String destFileDir, final String destFileName) {
        OkHttpUtils.get().url(url).build().execute(new FileCallBack(destFileDir, destFileName) {
            @Override
            public void onError(Call call, Exception e, int id) {

            }

            @Override
            public void onResponse(File response, int id) {
                ApkUtil.install(response);
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                tv_progress2.setText(progress * 100 + "%");
            }
        });
    }
}
