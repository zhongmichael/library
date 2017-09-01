package com.chinaredstar.core.base;

import android.content.DialogInterface;
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

/**
 * Created by hairui.xiang on 2017/8/29.
 */

public class PermissionsActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks {
    protected static final int RC_SETTINGS_SCREEN = 125;
    protected static final int RC_FOR_PERMISSIONS = 124;

    protected String[] iNeedPermissions() {
        return null;
    }

    protected String iNeededReason() {
        return "";
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

    @AfterPermissionGranted(RC_FOR_PERMISSIONS)
    public void requestPermissions() {
        if (EasyPermissions.hasPermissions(this, iNeedPermissions())) {
            // Have permission, do the thing!
            onUserPermitPermissionsDothing();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, iNeededReason(), RC_FOR_PERMISSIONS, iNeedPermissions());
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && null != iNeedPermissions() && iNeedPermissions().length > 0) {//6.0
            requestPermissions();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            // Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
            if (!EasyPermissions.hasPermissions(this, iNeedPermissions())) {
                onUserRejectPermissionDothing();
            } else {
                onUserPermitPermissionsDothing();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        onUserPermitPermissionsDothing();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder(this, getString(R.string.rationale_ask_again))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            onUserRejectPermissionDothing();
                        }
                    } /* click listener */)
                    .setRequestCode(RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }
}
