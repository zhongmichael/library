package com.chinaredstar.demo;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.utils.QRCodeHelper;

/**
 * Created by hairui.xiang on 2017/9/7.
 */

public class QRcodeScanDemo extends BaseActivity implements QRCodeHelper.OnQRCodeGetListener {
    @Override
    protected String[] iNeedPermissions() {
        return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_scan;
    }

    public void onScan(View view) {
        QRCodeHelper.openQRcodeScanner(this);
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
