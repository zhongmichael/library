package com.chinaredstar.demo;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.utils.QRCodeHelper;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hairui.xiang on 2017/9/7.
 */

public class QRcodeScanDemo extends BaseActivity implements QRCodeHelper.OnQRCodeGetListener {
    protected String[] iNeedPermissions() {
        return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_scan;
    }

    public void onScan(View view) {
        openQRcodeScanner();
    }

    @AfterPermissionGranted(2002)
    public void openQRcodeScanner() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            QRCodeHelper.openQRcodeScanner(this);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, "我们需要访问你的存储卡。", R.string.open_permission, R.string.cancel, 2002, Manifest.permission.CAMERA);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        QRCodeHelper.onActivityResult(requestCode, resultCode, data, this);
    }

    @Override
    public void onGetQRCode(String result) {
        Toast.makeText(this, "result: " + result, Toast.LENGTH_SHORT).show();
    }
}
