package com.chinaredstar.demo;

import android.view.Gravity;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.view.XDialog;
import com.chinaredstar.core.view.toast.XToast;
import com.chinaredstar.core.view.toast.XToastTimelyHandler;
import com.chinaredstar.core.view.toast.XTostAnimationUtil;

/**
 * Created by hairui.xiang on 2017/9/15.
 */

public class WidgetDemo extends BaseActivity {
    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_widget;
    }

    public void showToast(View view) {
        new XToast.Builder(mActivity)
                .setText("gogoog")
                .setAnimation(XTostAnimationUtil.ANIMATION_PULL)
                .setDuration(1000)
                .setXToastHandler(XToastTimelyHandler.getInstance())
                .setGravity(Gravity.CENTER, 0, 400)
                .show();
    }

    public void showDialog(View v) {
        new XDialog.Builder(mActivity)
                .setBackgroundDimEnabled(false)
                .setOffsetY(80)
                .show();
    }

    @Override
    protected boolean enabledImmersiveStyle() {
        return true;
    }
}
