package com.chinaredstar.demo;

import android.view.Gravity;
import android.view.View;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.view.toast.XToast;
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
     /*   LayoutInflater inflate = (LayoutInflater) BaseApplication.getInstance().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflate.inflate(R.layout.w_toast, null);
        TextView tv = (TextView) v.findViewById(R.id.tv_toast_message);
        tv.setText("gogogo");
        ToastUtil.showCenterToast(this, v, "tosssss", 5000);*/
        XToast.create(mActivity)
                .setText("gogoog")
                .setAnimation(XTostAnimationUtil.ANIMATION_PULL)
                .setDuration(1000)
                .setGravity(Gravity.CENTER, 0, 400)
                .show();
    }

    @Override
    protected boolean enabledImmersiveStyle() {
        return false;
    }
}
