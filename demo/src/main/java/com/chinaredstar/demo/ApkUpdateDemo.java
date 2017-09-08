package com.chinaredstar.demo;

import android.Manifest;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.constant.RC;
import com.chinaredstar.core.utils.ApkUtil;
import com.chinaredstar.core.utils.LogUtil;

import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hairui.xiang on 2017/9/1.
 */

public class ApkUpdateDemo extends BaseActivity {
    @Override
    protected void initWidget() {
        super.initWidget();
//        checkStoragePermissions();
    }

    @Override
    protected String[] iNeedPermissions() {
        return new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};
    }

    @Override
    protected void onUserPermitPermissionsDothing() {
        LogUtil.d("用户同意了。。。。。。。。。。。。。。。。。");
        ApkUtil.download("http://imtt.dd.qq.com/16891/D8A0D91C87420C8B7FA66806015C4D94.apk?fsname=com.istone.activity_6.4.2_53.apk&csr=97c2", "2.0", "update", "ap d a.....");
    }

    @Override
    protected void onUserRejectPermissionDothing() {
        super.onUserRejectPermissionDothing();
        LogUtil.d("用户拒绝了。。。。。。。。。。。。。。。。。");
    }

    /*  @AfterPermissionGranted(RC_EXTERNAL_STORAGE_PERM)
    public void checkStoragePermissions() {
        if (EasyPermissions.hasPermissions(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Have permission, do the thing!

        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_storage),
                    RC_EXTERNAL_STORAGE_PERM,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
    }*/

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
}
