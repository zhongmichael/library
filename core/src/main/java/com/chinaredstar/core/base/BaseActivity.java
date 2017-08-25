package com.chinaredstar.core.base;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chinaredstar.core.R;
import com.chinaredstar.core.utils.ActivityStack;
import com.chinaredstar.core.utils.StatusBarUtil;

import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by hairui.xiang on 2017/8/1.
 */

public class BaseActivity extends FragmentActivity implements EasyPermissions.PermissionCallbacks {
    private static final int RC_CAMERA_PERM = 123;
    private static final int RC_LOCATION_CONTACTS_PERM = 124;
    private static final int RC_SETTINGS_SCREEN = 125;
    private Handler mHandler;
    private View mStatusBar;
    private LinearLayout mRootView;
    private ViewGroup mHeaderView;
    private ViewGroup mContentView;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityStack.pop(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.push(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        this.setContentView(R.layout.activity_libbase_layout);
        this.mStatusBar = findViewById(R.id.id_statusbar_view);
        this.mRootView = findViewById(R.id.id_root_view);
        if (retainStatusBarHeight()) {
            StatusBarUtil.setImmersiveStatusBar(mStatusBar, this);
        }
        if (getHeaderLayoutId() > -1) {
            this.mHeaderView = (ViewGroup) this.getInflater().inflate(this.getHeaderLayoutId(), null);
            this.mRootView.addView(this.mHeaderView, -1, -2);
        }
        if (this.getContentLayoutId() > -1) {
            this.mContentView = (ViewGroup) this.getInflater().inflate(this.getContentLayoutId(), null);
            this.mRootView.addView(this.mContentView, -1, -1);
        }
        this.initValue();
        this.initWidget();
        this.initListener();
        this.initData();
    }

    protected LayoutInflater getInflater() {
        if (null == this.mLayoutInflater) {
            this.mLayoutInflater = LayoutInflater.from(this);
        }
        return this.mLayoutInflater;
    }

    /**
     * 保留状态栏高度
     *
     * @return <li>true 创建一个和状态栏高度一样的view</li>
     * <li>false 页面扑满屏幕</li>
     */
    protected boolean retainStatusBarHeight() {
        return true;
    }

    protected void setStatusBarBackgroundColor(int color) {
        this.mStatusBar.setBackgroundColor(color);
    }

    protected int getHeaderLayoutId() {
        return -1;
    }

    protected int getContentLayoutId() {
        return -1;
    }

    protected void initValue() {
    }

    protected void initWidget() {
    }

    protected void initListener() {
    }

    protected void initData() {
    }

    public Handler getHandler() {
        if (this.mHandler == null) {
            this.mHandler = new Handler(this.getMainLooper());
        }
        return this.mHandler;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SETTINGS_SCREEN) {
            // Do something after user returned from app settings screen, like showing a Toast.
            Toast.makeText(this, R.string.returned_from_app_settings_to_activity, Toast.LENGTH_SHORT).show();
        }
    }

    @AfterPermissionGranted(RC_CAMERA_PERM)
    public void cameraTask() {
        if (EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_camera), RC_CAMERA_PERM, Manifest.permission.CAMERA);
        }
    }

    @AfterPermissionGranted(RC_LOCATION_CONTACTS_PERM)
    public void locationAndContactsTask() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_CONTACTS};
        if (EasyPermissions.hasPermissions(this, perms)) {
            // Have permissions, do the thing!
            Toast.makeText(this, "TODO: Location and Contacts things", Toast.LENGTH_LONG).show();
        } else {
            // Ask for both permissions
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_location_contacts), RC_LOCATION_CONTACTS_PERM, perms);
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
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

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
