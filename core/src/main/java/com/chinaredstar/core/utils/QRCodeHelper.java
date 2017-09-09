package com.chinaredstar.core.utils;

import android.app.Activity;
import android.content.Intent;

import com.chinaredstar.core.qrcode.QRCodeScanActivity;

import static android.app.Activity.RESULT_OK;
import static com.chinaredstar.core.constant.RC.RC_GET_QRCODE;

/**
 * Created by hairui.xiang on 2017/9/8.
 */
public class QRCodeHelper {

    public interface OnQRCodeGetListener {
        void onGetQRCode(String result);
    }

    public static void openQRcodeScanner(Activity activity) {
        activity.startActivityForResult(new Intent(activity, QRCodeScanActivity.class), RC_GET_QRCODE);
    }

    public static void onActivityResult(int requestCode, int resultCode, Intent data, OnQRCodeGetListener l) {
        if (RESULT_OK == resultCode && null != data) {
            switch (requestCode) {
                case RC_GET_QRCODE:
                    if (null != l) {
                        l.onGetQRCode(data.getStringExtra(QRCodeScanActivity.KEY_SCAN_RESULT));
                    }
                    break;
            }
        }
    }
}
