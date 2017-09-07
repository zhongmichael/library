package com.chinaredstar.demo;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.qrcode.QRCodeScanActivity;

/**
 * Created by hairui.xiang on 2017/9/7.
 */

public class ScanDemo extends BaseActivity {
    @Override
    protected String[] iNeedPermissions() {
        return new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_scan;
    }

    public void onScan(View view) {
        startActivity(new Intent(this, QRCodeScanActivity.class));
    }
}
