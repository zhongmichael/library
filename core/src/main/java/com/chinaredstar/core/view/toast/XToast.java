package com.chinaredstar.core.view.toast;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chinaredstar.core.R;
import com.chinaredstar.core.utils.keyboard.ViewUtil;

/**
 * Created by hairui.xiang on 2017/9/18.
 */

public class XToast {
    public static final int LENGTH_SHORT = 2000;
    public static final int LENGTH_LONG = 3500;
    private ViewGroup mRootView;
    private RelativeLayout mParentView;
    private View mView;
    private TextView mTextView;
    private LayoutInflater mInflater;
    private int mGravity;
    private int mHorizontalMargin;
    private int mVerticalMargin;
    private int mDuration = LENGTH_LONG;
    private int mTextColor = Color.WHITE;
    private int mTextSize = 20;
    private CharSequence mTextContent;
    private OnDisappearListener mListener;
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;
    private int mShowAnimationType = XTostAnimationUtil.ANIMATION_DEFAULT;
    private int mHideAnimationType = XTostAnimationUtil.ANIMATION_DEFAULT;
    private XToastHandler mXToastHandler;

    public interface OnDisappearListener {
        void onDisappear(XToast xToast);
    }

    private XToast() {
    }

    public int getDuration() {
        return mDuration;
    }

    public AnimatorSet getShowAnimatorSet() {
        return this.mShowAnimatorSet;
    }

    public AnimatorSet getHideAnimatorSet() {
        return this.mHideAnimatorSet;
    }

    public int getViewMeasuredHeight() {
        return mView.getMeasuredHeight();
    }

    public ViewGroup getRootView() {
        return mRootView;
    }

    public RelativeLayout getParentView() {
        return mParentView;
    }

    public View getView() {
        return mView;
    }

    public XToastHandler getXToastHandler() {
        return mXToastHandler;
    }

    public OnDisappearListener getOnDisappearListener() {
        return mListener;
    }

    public boolean isShowing() {
        return null != mView && mView.isShown();
    }

    public void dismiss() {
        mXToastHandler.dismiss(this);
    }

    final public static class Builder {
        private XToast mXToast;
        private Context mContext;

        public Builder(Context context) {
            mXToast = new XToast();
            mContext = context;
            mXToast.mInflater = LayoutInflater.from(mContext);
        }

        public Builder setView(View view) {
            mXToast.mView = view;
            return this;
        }

        public Builder setRootView(ViewGroup rootView) {
            mXToast.mRootView = rootView;
            return this;
        }

        public Builder setGravity(int gravity, int xOffset, int yOffset) {
            mXToast.mGravity = gravity;
            mXToast.mHorizontalMargin = xOffset;
            mXToast.mVerticalMargin = yOffset;
            return this;
        }

        public Builder setDuration(int mDuration) {
            mXToast.mDuration = mDuration;
            return this;
        }

        public Builder setTextColor(int mTextColor) {
            mXToast.mTextColor = mTextColor;
            return this;
        }

        public Builder setTextSize(int mTextSize) {
            mXToast.mTextSize = mTextSize;
            return this;
        }

        public Builder setText(CharSequence mTextContent) {
            mXToast.mTextContent = mTextContent;
            return this;
        }

        public Builder setAnimation(int animationType) {
            mXToast.mShowAnimationType = animationType;
            mXToast.mHideAnimationType = animationType;
            return this;
        }

        public Builder setShowAnimation(int animationType) {
            mXToast.mShowAnimationType = animationType;
            return this;
        }

        public Builder setHideAnimation(int animationType) {
            mXToast.mHideAnimationType = animationType;
            return this;
        }

        public Builder setShowAnimatorSet(AnimatorSet mShowAnimatorSet) {
            mXToast.mShowAnimatorSet = mShowAnimatorSet;
            return this;
        }

        public Builder setHideAnimatorSet(AnimatorSet mHideAnimatorSet) {
            mXToast.mHideAnimatorSet = mHideAnimatorSet;
            return this;
        }

        public Builder setOnDisappearListener(OnDisappearListener mListener) {
            mXToast.mListener = mListener;
            return this;
        }

        public Builder setXToastHandler(XToastHandler mXToastHandler) {
            mXToast.mXToastHandler = mXToastHandler;
            return this;
        }

        private Builder create() {
            if (null == mXToast.mRootView) {
                mXToast.mRootView = ((Activity) mContext).findViewById(android.R.id.content);
            }

            if (null == mXToast.mParentView) {
                mXToast.mParentView = new RelativeLayout(mContext);
            }

            if (null == mXToast.mView) {
                mXToast.mView = mXToast.mInflater.inflate(R.layout.libbase_toast_view, mXToast.mParentView, false);
                mXToast.mTextView = mXToast.mView.findViewById(R.id.tv_toast_msg);
                mXToast.mTextView.setTextColor(mXToast.mTextColor);
                mXToast.mTextView.setTextSize(mXToast.mTextSize);
                mXToast.mTextView.setText(mXToast.mTextContent);
            }

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT);

            params.gravity = mXToast.mGravity;

            switch (mXToast.mGravity) {
                case Gravity.TOP:
                case Gravity.TOP | Gravity.LEFT:
                case Gravity.TOP | Gravity.CENTER_HORIZONTAL:
                case Gravity.CENTER:
                    params.leftMargin = mXToast.mHorizontalMargin;
                    params.topMargin = mXToast.mVerticalMargin;
                    break;
                case Gravity.TOP | Gravity.RIGHT:
                    params.rightMargin = mXToast.mHorizontalMargin;
                    params.topMargin = mXToast.mVerticalMargin;
                    break;
                case Gravity.BOTTOM:
                case Gravity.BOTTOM | Gravity.LEFT:
                case Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL:
                    params.leftMargin = mXToast.mHorizontalMargin;
                    params.bottomMargin = mXToast.mVerticalMargin;
                    break;
                case Gravity.BOTTOM | Gravity.RIGHT:
                    params.rightMargin = mXToast.mHorizontalMargin;
                    params.bottomMargin = mXToast.mVerticalMargin;
                    break;
            }

            mXToast.mParentView.setLayoutParams(params);

            mXToast.mParentView.removeView(mXToast.mView);
            mXToast.mRootView.removeView(mXToast.mParentView);

            mXToast.mRootView.addView(mXToast.mParentView);

            ViewUtil.measuredHeight(mXToast.mView);

            if (null == mXToast.mShowAnimatorSet) {
                mXToast.mShowAnimatorSet = XTostAnimationUtil.getShowAnimation(mXToast, mXToast.mShowAnimationType);
            }
            if (null == mXToast.mHideAnimatorSet) {
                mXToast.mHideAnimatorSet = XTostAnimationUtil.getHideAnimation(mXToast, mXToast.mHideAnimationType);
            }
            return this;
        }

        public XToast show() {
            create();
            if (null == mXToast.mXToastHandler)
                mXToast.mXToastHandler = XToastTimelyHandler.getInstance();
            mXToast.mXToastHandler.onProcess(mXToast);
            return mXToast;
        }
    }
}
