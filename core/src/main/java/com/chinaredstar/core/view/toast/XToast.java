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
    private Context mContext;
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

    public interface OnDisappearListener {
        void onDisappear(XToast xToast);
    }

    public static XToast create(Context context) {
        return new XToast(context);
    }

    private XToast(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        ;
    }

    public XToast setView(View view) {
        this.mView = view;
        return this;
    }

    public XToast setRootView(ViewGroup rootView) {
        this.mRootView = rootView;
        return this;
    }

    public XToast setGravity(int gravity, int xOffset, int yOffset) {
        this.mGravity = gravity;
        this.mHorizontalMargin = xOffset;
        this.mVerticalMargin = yOffset;
        return this;
    }

    public XToast setDuration(int mDuration) {
        this.mDuration = mDuration;
        return this;
    }

    public XToast setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
        return this;
    }

    public XToast setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
        return this;
    }

    public XToast setText(CharSequence mTextContent) {
        this.mTextContent = mTextContent;
        return this;
    }

    public int getDuration() {
        return mDuration;
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

    public XToast setAnimation(int animationType) {
        this.mShowAnimationType = animationType;
        this.mHideAnimationType = animationType;
        return this;
    }

    public XToast setShowAnimation(int animationType) {
        this.mShowAnimationType = animationType;
        return this;
    }

    public XToast setHideAnimation(int animationType) {
        this.mHideAnimationType = animationType;
        return this;
    }

    public void setShowAnimatorSet(AnimatorSet mShowAnimatorSet) {
        this.mShowAnimatorSet = mShowAnimatorSet;
    }

    public void setHideAnimatorSet(AnimatorSet mHideAnimatorSet) {
        this.mHideAnimatorSet = mHideAnimatorSet;
    }

    public AnimatorSet getShowAnimatorSet() {
        return this.mShowAnimatorSet;
    }

    public AnimatorSet getHideAnimatorSet() {
        return this.mHideAnimatorSet;
    }

    public OnDisappearListener getOnDisappearListener() {
        return mListener;
    }

    public void setOnDisappearListener(OnDisappearListener mListener) {
        this.mListener = mListener;
    }

    private void buidler() {
        if (null == mRootView) {
            mRootView = ((Activity) mContext).findViewById(android.R.id.content);
        }

        if (null == mParentView) {
            mParentView = new RelativeLayout(mContext);
        }

        if (null == mView) {
            mView = mInflater.inflate(R.layout.libbase_toast_view, mParentView, false);
            mTextView = mView.findViewById(R.id.tv_toast_msg);
            mTextView.setTextColor(mTextColor);
            mTextView.setTextSize(mTextSize);
            mTextView.setText(mTextContent);
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);

        params.gravity = mGravity;

        switch (mGravity) {
            case Gravity.TOP:
            case Gravity.TOP | Gravity.LEFT:
            case Gravity.TOP | Gravity.CENTER_HORIZONTAL:
            case Gravity.CENTER:
                params.leftMargin = mHorizontalMargin;
                params.topMargin = mVerticalMargin;
                break;
            case Gravity.TOP | Gravity.RIGHT:
                params.rightMargin = mHorizontalMargin;
                params.topMargin = mVerticalMargin;
                break;
            case Gravity.BOTTOM:
            case Gravity.BOTTOM | Gravity.LEFT:
            case Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL:
                params.leftMargin = mHorizontalMargin;
                params.bottomMargin = mVerticalMargin;
                break;
            case Gravity.BOTTOM | Gravity.RIGHT:
                params.rightMargin = mHorizontalMargin;
                params.bottomMargin = mVerticalMargin;
                break;
        }

        mParentView.setLayoutParams(params);

        if (mView.getParent() instanceof ViewGroup) {
            ((ViewGroup) mView.getParent()).removeAllViews();
        }

        if (mParentView.getParent() instanceof ViewGroup) {
            ((ViewGroup) mParentView.getParent()).removeView(mParentView);
        }

        mRootView.addView(mParentView);

        ViewUtil.measuredHeight(mView);

        if (null == mShowAnimatorSet) {
            this.mShowAnimatorSet = XTostAnimationUtil.getShowAnimation(this, mShowAnimationType);
        }
        if (null == mHideAnimatorSet) {
            this.mHideAnimatorSet = XTostAnimationUtil.getHideAnimation(this, mHideAnimationType);
        }
    }

    public boolean isShowing() {
        return null != mView && mView.isShown();
    }

    private void dismiss() {
        XToastHandler.getInstance().hideToast(this);
    }

    public void show() {
        buidler();
        XToastHandler.getInstance().add(this);
    }
}
