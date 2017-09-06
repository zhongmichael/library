package com.chinaredstar.demo;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.utils.LogUtil;
import com.chinaredstar.core.utils.PhotoHelper;

/**
 * Created by hairui.xiang on 2017/9/6.
 */

public class CameraDemo extends BaseActivity implements PhotoHelper.OnPhotoGetListener {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected String[] iNeedPermissions() {
        return new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    }

    @Override
    protected void onUserPermitPermissionsDothing() {
        LogUtil.d("用户同意了。。。。。。。。。。。。。。。。。");
    }

    @Override
    protected void onUserRejectPermissionDothing() {
        super.onUserRejectPermissionDothing();
    }

    /**
     * 拍照
     */
    public void onTakePhotos(View view) {
        PhotoHelper.onTakePhotos(this);
    }

    /**
     * 打开相册
     */
    public void onOpenAlbum(View view) {
        PhotoHelper.onOpenAlbum(this);
    }

    @Override
    public void onGetPhotoPath(Uri uri) {
        System.out.println("onGet: " + uri.toString());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PhotoHelper.onActivityResult(this, requestCode, resultCode, data, true, this);
    }

}