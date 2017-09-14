package com.chinaredstar.demo;

import android.Manifest;
import android.content.Intent;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.task.CompressImageTask;
import com.chinaredstar.core.utils.PhotoHelper;

import static com.chinaredstar.core.constant.EC.EC_COMPRESS_IMAGE;

/**
 * Created by hairui.xiang on 2017/9/6.
 */

public class PhotoGetDemo extends BaseActivity implements PhotoHelper.OnPhotoGetListener {

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    protected String[] iNeedPermissions() {
        return new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    }

    @Override
    protected void onUserPermitPermissionsDothing() {
    }

    @Override
    protected void onUserRejectPermissionDothing() {
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
    public void onGetPhotoPath(String uri) {
        System.out.println("url: " + uri);
        execTask(new CompressImageTask(EC_COMPRESS_IMAGE, uri));
    }

    @Override
    protected void onEventCallback(EventCenter event) {
        if (EC_COMPRESS_IMAGE == event.code) {
            System.out.println("data: " + event.data);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //执行裁剪
       /* PhotoHelper.onActivityResult(requestCode, resultCode, data, new PhotoHelper.IUCrop() {
            @Override
            public boolean onStartUCrop(@NonNull Uri source, @NonNull Uri destination) {
                Crop.of(source, destination)
                        .withAspectRatio(1, 1)
                        .withMaxResultSize(400, 400)
                        .withOptions(PhotoHelper.getDefaultUCropOptions())
                        .withTargetActivity(CropActivity.class)
                        .start(mActivity);
                return true;
            }
        }, this);*/


        //原图
        PhotoHelper.onActivityResult(requestCode, resultCode, data, null, this);
    }

    @Override
    protected boolean enabledEventBus() {
        return true;
    }
}