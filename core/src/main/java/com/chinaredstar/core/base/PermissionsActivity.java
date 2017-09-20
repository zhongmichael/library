package com.chinaredstar.core.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.chinaredstar.core.R;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

import static com.chinaredstar.core.constant.RC.RC_AUTO_CHECKING_PERMISSIONS;
import static com.chinaredstar.core.constant.RC.RC_SETTINGS_SCREEN;
import static com.chinaredstar.core.constant.RC.RC_USER_REQUEST_PERMISSIONS;

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class PermissionsActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks {

    protected String[] iNeedPermissions() {
        return new String[]{};
    }

    protected String iNeededReason() {
        return "no reason...";
    }

    /**
     * 用户同意授权
     */
    protected void onUserPermitPermissionsDothing() {

    }

    /**
     * 用户拒绝授权
     */
    protected void onUserRejectPermissionDothing() {

    }

    @AfterPermissionGranted(RC_USER_REQUEST_PERMISSIONS)
    public void requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (EasyPermissions.hasPermissions(this, iNeedPermissions())) {
                // Have permission, do the thing!
                onUserPermitPermissionsDothing();
            } else {
                // Ask for one permission
                EasyPermissions.requestPermissions(this, iNeededReason(), RC_USER_REQUEST_PERMISSIONS, iNeedPermissions());
            }
        } else {
            // Have permission, do the thing!
            onUserPermitPermissionsDothing();
        }
    }

    @AfterPermissionGranted(RC_AUTO_CHECKING_PERMISSIONS)
    public void checkingPermissions() {
        if (EasyPermissions.hasPermissions(this, iNeedPermissions())) {
            // Have permission, do the thing!
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, iNeededReason(), RC_AUTO_CHECKING_PERMISSIONS, iNeedPermissions());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && //6.0
                iNeedPermissions() != null &&
                iNeedPermissions().length > 0) {
            checkingPermissions();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    /**
     * 同意
     */
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) { //用户操作某项功能时申请的权限
            case RC_USER_REQUEST_PERMISSIONS:
                onUserPermitPermissionsDothing();
                break;
        }
    }

    /**
     * 拒绝
     */
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) { //用户操作某项功能时申请的权限
            case RC_USER_REQUEST_PERMISSIONS:
                onUserRejectPermissionDothing();
                break;
        }
        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }
}
