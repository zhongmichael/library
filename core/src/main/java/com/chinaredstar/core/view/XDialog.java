package com.chinaredstar.core.view;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.chinaredstar.core.R;
import com.chinaredstar.core.utils.DisplayUtil;

/**
 * Created by hairui.xiang on 2017/9/18.
 */

public class XDialog {

    private XDialog() {
    }

    final public static class Builder {
        private View mView;
        private Activity mActivity;
        private Dialog mDialog;
        private LayoutInflater mInflater;
        private float mRatio;
        private int mOffsetY;
        private boolean mBackgroundDimEnabled;
        private int mGravity = Gravity.CENTER;

        public Builder(Activity activity) {
            this.mActivity = activity;
            this.mInflater = LayoutInflater.from(activity);
        }

        public Builder setView(View view) {
            this.mView = view;
            return this;
        }

        public Builder setOffsetY(int mOffsetY) {
            this.mOffsetY = mOffsetY;
            return this;
        }

        public Builder setWidthRatio(float ratio) {
            this.mRatio = ratio;
            return this;
        }

        public Builder setBackgroundDimEnabled(boolean Enabled) {
            this.mBackgroundDimEnabled = Enabled;
            return this;
        }

        public Builder setGravity(int mGravity) {
            this.mGravity = mGravity;
            return this;
        }

        public Dialog create() {
            if (mBackgroundDimEnabled) {
                mDialog = new Dialog(mActivity, R.style.BaseDialogTheme);
            } else {
                mDialog = new Dialog(mActivity, R.style.BaseDialogTheme_BackgroundDimDisabled);
            }
            if (null == mView) {
                mView = mInflater.inflate(R.layout.libbase_loading_dialog, null);
            }

            mDialog.setContentView(mView);

            Window window = mDialog.getWindow();
            WindowManager.LayoutParams lp = window.getAttributes();
            if (0 != mRatio) {
                int screenWidth = DisplayUtil.getWindowWidth(mActivity);
                lp.width = (int) ((float) screenWidth / mRatio);
            }
            lp.y = mOffsetY;
            lp.gravity = mGravity;
            window.setAttributes(lp);
            return mDialog;
        }

        public Dialog show() {
            final Dialog dialog = create();
            dialog.show();
            return dialog;
        }
    }
}
