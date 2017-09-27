package com.chinaredstar.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Environment;
import android.view.View;

import com.baoyz.actionsheet.ActionSheet;
import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.eventbus.EventCenter;
import com.chinaredstar.core.task.CompressImageTask;
import com.chinaredstar.core.utils.LogUtil;
import com.chinaredstar.core.utils.PhotoHelper;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static com.chinaredstar.core.constant.EC.EC_COMPRESS_IMAGE;
import static com.chinaredstar.core.constant.RC.RC_CAMERA_PERM;
import static com.chinaredstar.core.constant.RC.RC_READ_EXTERNAL_STORAGE_PERM;

/**
 * Created by hairui.xiang on 2017/9/6.
 */

public class PhotoGetDemo extends BaseActivity implements PhotoHelper.OnPhotoGetListener, EasyPermissions.PermissionCallbacks {
    @Override
    protected void initValue() {
        //        getFilesDir :/data/user/0/com.chinaredstar.demo/files
        //        getCacheDir :/data/user/0/com.chinaredstar.demo/cache
        //         getExternalStorageDirectory :/storage/emulated/0
        //        getExternalFilesDir :/storage/emulated/0/Android/data/com.chinaredstar.demo/files
        //       getExternalCacheDir :/storage/emulated/0/Android/data/com.chinaredstar.demo/cache
        String r =
                "getFilesDir :" + mActivity.getFilesDir().toString() + "\n" +
                        "getCacheDir :" + mActivity.getCacheDir() + "\n" +
                        "getExternalStorageDirectory :" + Environment.getExternalStorageDirectory() + "\n" +
                        "getExternalFilesDir :" + mActivity.getExternalFilesDir(null) + "\n" +
                        "getExternalCacheDir :" + mActivity.getExternalCacheDir();
        LogUtil.i(r);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_camera;
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void openCamera() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            PhotoHelper.onTakePhotos(this);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_camera),
                    RC_CAMERA_PERM,
                    Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_READ_EXTERNAL_STORAGE_PERM)
    public void openAlbum() {
        System.out.println("--------------readExternalStorage----------------");
        if (EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!
            PhotoHelper.onOpenAlbum(this);
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                    this,
                    "我们需要访问你的相册",
                    RC_READ_EXTERNAL_STORAGE_PERM,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }

    /**
     * 拍照
     */
    public void onTakePhotos(View view) {
        openCamera();
    }

    /**
     * 打开相册
     */
    public void onOpenAlbum(View view) {
        openAlbum();
    }


    public void onOpenPicker(View view) {
        setTheme(R.style.ActionSheetStyleiOS7_);
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setOtherButtonTitles(new String[]{"拍照", "打开相册"}).setListener(new ActionSheet.ActionSheetListener() {
            @Override
            public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

            }

            @Override
            public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                if (0 == index) {
                    openCamera();
                } else if (1 == index) {
                    openAlbum();
                }

            }
        }).setCancelButtonTitle("取消").show();
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