package com.chinaredstar.core.qrcode;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.chinaredstar.core.R;
import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.base.BaseApplication;
import com.chinaredstar.core.utils.PhotoHelper;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

/**
 * https://github.com/bingoogolapple/BGAQRCode-Android
 * Created by hairui.xiang on 2017/9/7.
 */

public class QRCodeScanActivity extends BaseActivity implements QRCodeView.Delegate, PhotoHelper.OnPhotoGetListener {
    private static final String TAG = QRCodeScanActivity.class.getSimpleName();
    private QRCodeView mQRCodeView;

    public void openGallery(View view) {
        chooseQRCodeFromGallery();
    }

    @Override
    protected void initWidget() {
        mQRCodeView = (ZXingView) findViewById(R.id.zxingview);
        // 设置扫描二维码的代理
        mQRCodeView.setDelegate(this);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_qrcode_scan;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开后置摄像头开始预览，但是并未开始识别
        mQRCodeView.startCamera();
        // 打开指定摄像头开始预览，但是并未开始识别
//        mQRCodeView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT);
        // 显示扫描框
        mQRCodeView.showScanRect();

        // 延迟1.5秒后开始识别
        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        // 关闭摄像头预览，并且隐藏扫描框
        mQRCodeView.stopCamera();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    /**
     * 处理扫描结果
     *
     * @param result
     */
    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
        vibrate();
        // 延迟1.5秒后开始识别
        mQRCodeView.startSpot();
    }

    /**
     * 处理打开相机出错
     */
    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错!");
    }

    public void startSpot() {
        // 延迟1.5秒后开始识别
        mQRCodeView.startSpot();
    }

    public void stopSpot() {
        // 停止识别
        mQRCodeView.stopSpot();
    }

    public void startSpotAndShowRect() {
        // 显示扫描框，并且延迟1.5秒后开始识别
        mQRCodeView.startSpotAndShowRect();
    }

    public void stopSpotAndHiddenRect() {
        // 停止识别，并且隐藏扫描框
        mQRCodeView.stopSpotAndHiddenRect();
    }

    public void showScanRect() {
        // 显示扫描框
        mQRCodeView.showScanRect();
    }

    public void hiddenScanRect() {
        // 隐藏扫描框
        mQRCodeView.hiddenScanRect();
    }

    public void startCamera() {
        // 打开后置摄像头开始预览，但是并未开始识别
        mQRCodeView.startCamera();
    }

    public void stopCamera() {
        // 关闭摄像头预览，并且隐藏扫描框
        mQRCodeView.stopCamera();
    }

    public void openFlashlight() {
        // 打开闪光灯
        mQRCodeView.openFlashlight();
    }

    public void closeFlashlight() {
        // 关闭散光灯
        mQRCodeView.closeFlashlight();
    }

    public void changeToScanBarcodeStyle() {
        // 扫描条码
        mQRCodeView.changeToScanBarcodeStyle();
    }

    public void changeToScanQRCodeStyle() {
        // 扫描二维码
        mQRCodeView.changeToScanQRCodeStyle();
    }

    public void chooseQRCodeFromGallery() {
        //选择相册二维码扫描
        PhotoHelper.onOpenAlbum(this);
    }

    @Override
    public void onGetPhotoPath(String path) {
        mDecodeQRCodeTask = new DecodeQRCodeTask();
        mDecodeQRCodeTask.execute(path);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 显示扫描框
        mQRCodeView.showScanRect();
        PhotoHelper.onActivityResult(requestCode, resultCode, data, null, this);
    }

    private static DecodeQRCodeTask mDecodeQRCodeTask;

    private static final class DecodeQRCodeTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return QRCodeDecoder.syncDecodeQRCode(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if (TextUtils.isEmpty(result)) {
                Toast.makeText(BaseApplication.getInstance(), "未发现二维码!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseApplication.getInstance(), result, Toast.LENGTH_SHORT).show();
            }
        }
    }
}
