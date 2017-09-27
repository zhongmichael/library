package com.chinaredstar.demo;

import android.Manifest;
import android.view.View;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.constant.EC;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.okhttp.OkHttpUtils;
import com.chinaredstar.core.okhttp.callback.FileCallBack;
import com.chinaredstar.core.utils.ApkDownloadUtil;
import com.chinaredstar.core.utils.ApkUtil;
import com.chinaredstar.core.utils.PathUtil;

import java.io.File;

import okhttp3.Call;
import pub.devrel.easypermissions.AfterPermissionGranted;
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
        tv_progress1 = (TextView) findViewById(R.id.tv_progress1);
        tv_progress2 = (TextView) findViewById(R.id.tv_progress2);
    }

    @Override
    protected void onEventCallback(EventCenter event) {
        switch (event.code) {
            case EC.EC_DOWNLOAD_APK://  downloadmanager
                if (event.data instanceof ApkDownloadUtil.DownloadProgress) {
                    ApkDownloadUtil.DownloadProgress mProgress = (ApkDownloadUtil.DownloadProgress) event.data;
                    tv_progress1.setText(mProgress.progress * 100 + "%");
                }
                break;
        }
    }

    public void download1(View v) {//downloadmanager
        downloadManeger();
    }

    public void download2(View v) {//okhttp3
        okhttp3();
    }

    @AfterPermissionGranted(1001)
    public void downloadManeger() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            ApkDownloadUtil.download(url, "@2.0");
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "我们需要访问你的存储卡。", R.string.open_permission, R.string.cancel, 1001, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    @AfterPermissionGranted(1002)
    public void okhttp3() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            download(url, PathUtil.getAppFilesDir() + "/apk/", "v2.apk");
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "我们需要访问你的存储卡。", R.string.open_permission, R.string.cancel, 1002, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
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
