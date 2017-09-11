package com.chinaredstar.core.view.pulltorefresh.header;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.chinaredstar.core.R;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.indicator.PtrIndicator;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class ThreeColourDotsHeader extends FrameLayout implements PtrUIHandler {
    private ImageView iv_orange_dot;
    private ImageView iv_green_dot;
    private ImageView iv_blue_dot;
    private AnimatorSet mAppearAnimatorSet; // 显示
    private AnimatorSet mOrangeDotAnimatorSet; // 刷新中动画
    private AnimatorSet mGreenDotAnimatorSet; // 刷新中动画
    private AnimatorSet mBlueDotAnimatorSet; // 刷新中动画
    /**
     * 回弹时长
     */
    private int mAppearAniTime = 500;
    /**
     * Scale time
     */
    private int mScaleAniTime = 300;

    private int mTotalDragDistance = 0;

    private boolean isInitAnimation = false;

    private float mOrangeY;
    private float mGreenY;
    private float mBlueY;

    public ThreeColourDotsHeader(@NonNull Context context) {
        super(context);
        init();
    }

    public ThreeColourDotsHeader(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThreeColourDotsHeader(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (!isInitAnimation) {
            isInitAnimation = true;
            mTotalDragDistance = getMeasuredHeight();
            mOrangeY = iv_orange_dot.getY();
            mGreenY = iv_green_dot.getY();
            mBlueY = iv_blue_dot.getY();
            hideDots();
            animationSetting();
        }
    }

    private void init() {
        inflate(getContext(), R.layout.libbase_pull_header, this);
        iv_orange_dot = findViewById(R.id.iv_orange_dot);
        iv_green_dot = findViewById(R.id.iv_green_dot);
        iv_blue_dot = findViewById(R.id.iv_blue_dot);
        setDotVisibility(View.INVISIBLE);
    }

    private void setDotVisibility(int visibility) {
        iv_orange_dot.setVisibility(visibility);
        iv_green_dot.setVisibility(visibility);
        iv_blue_dot.setVisibility(visibility);
    }

    private boolean dotIsVisible() {
        return iv_orange_dot.getVisibility() == View.VISIBLE &&
                iv_green_dot.getVisibility() == View.VISIBLE &&
                iv_blue_dot.getVisibility() == View.VISIBLE;
    }

    private void hideDots() {
        iv_orange_dot.setY(mOrangeY - mTotalDragDistance);
        iv_green_dot.setY(mGreenY - mTotalDragDistance);
        iv_blue_dot.setY(mBlueY - mTotalDragDistance);
    }

    /***
     * 创建动画
     */
    private void animationSetting() {
        mAppearAnimatorSet = new AnimatorSet();
        mOrangeDotAnimatorSet = new AnimatorSet();
        mGreenDotAnimatorSet = new AnimatorSet();
        mBlueDotAnimatorSet = new AnimatorSet();
        //下拉动画
        ObjectAnimator orange = ObjectAnimator.ofFloat(iv_orange_dot, "translationY", -mTotalDragDistance, 0);
        orange.setInterpolator(new AccelerateDecelerateInterpolator());
        orange.setDuration(mAppearAniTime);
        orange.setStartDelay(mAppearAniTime / 10);
        ObjectAnimator green = ObjectAnimator.ofFloat(iv_green_dot, "translationY", -mTotalDragDistance, 0);
        green.setInterpolator(new AccelerateDecelerateInterpolator());
        green.setDuration(mAppearAniTime);
        green.setStartDelay(mAppearAniTime / 5);
        ObjectAnimator blue = ObjectAnimator.ofFloat(iv_blue_dot, "translationY", -mTotalDragDistance, 0);
        blue.setInterpolator(new AccelerateDecelerateInterpolator());
        blue.setDuration(mAppearAniTime);
        mAppearAnimatorSet.playTogether(blue, green, orange);

        //刷新中动画
        ObjectAnimator yellowX = ObjectAnimator.ofFloat(iv_orange_dot, "scaleX", 1.0f, 1.5f);
        yellowX.setDuration(mScaleAniTime);
        yellowX.setRepeatCount(ValueAnimator.INFINITE);
        yellowX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator yellowY = ObjectAnimator.ofFloat(iv_orange_dot, "scaleY", 1.0f, 1.5f);
        yellowY.setDuration(mScaleAniTime);
        yellowY.setRepeatCount(ValueAnimator.INFINITE);
        yellowY.setRepeatMode(ValueAnimator.REVERSE);
        mOrangeDotAnimatorSet.play(yellowX).with(yellowY);

        ObjectAnimator greenX = ObjectAnimator.ofFloat(iv_green_dot, "scaleX", 1.0f, 1.5f);
        greenX.setDuration(mScaleAniTime);
        greenX.setRepeatCount(ValueAnimator.INFINITE);
        greenX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator greenY = ObjectAnimator.ofFloat(iv_green_dot, "scaleY", 1.0f, 1.5f);
        greenY.setDuration(mScaleAniTime);
        greenY.setRepeatCount(ValueAnimator.INFINITE);
        greenY.setRepeatMode(ValueAnimator.REVERSE);
        mGreenDotAnimatorSet.play(greenX).with(greenY);
        mGreenDotAnimatorSet.setStartDelay(mScaleAniTime);

        ObjectAnimator blueX = ObjectAnimator.ofFloat(iv_blue_dot, "scaleX", 1.0f, 1.5f);
        blueX.setDuration(mScaleAniTime);
        blueX.setRepeatCount(ValueAnimator.INFINITE);
        blueX.setRepeatMode(ValueAnimator.REVERSE);
        ObjectAnimator blueY = ObjectAnimator.ofFloat(iv_blue_dot, "scaleY", 1.0f, 1.5f);
        blueY.setDuration(mScaleAniTime);
        blueY.setRepeatCount(ValueAnimator.INFINITE);
        blueY.setRepeatMode(ValueAnimator.REVERSE);
        mBlueDotAnimatorSet.play(blueX).with(blueY);
    }

    /***
     * 重置view状态
     */
    private void endAnimation() {
        hideDots();
        if (mAppearAnimatorSet.isStarted()) {
            mAppearAnimatorSet.end();
        }
        if (mOrangeDotAnimatorSet.isStarted()) {
            mOrangeDotAnimatorSet.end();
        }
        if (mGreenDotAnimatorSet.isStarted()) {
            mGreenDotAnimatorSet.end();
        }
        if (mBlueDotAnimatorSet.isStarted()) {
            mBlueDotAnimatorSet.end();
        }
    }

    @Override
    public void onUIReset(PtrFrameLayout frame) {
        setDotVisibility(View.INVISIBLE);
        endAnimation();
    }

    @Override
    public void onUIRefreshPrepare(PtrFrameLayout frame) {
    }

    @Override
    public void onUIRefreshBegin(PtrFrameLayout frame) {
        mOrangeDotAnimatorSet.start();
        mGreenDotAnimatorSet.start();
        mBlueDotAnimatorSet.start();
    }

    @Override
    public void onUIRefreshComplete(PtrFrameLayout frame) {
        endAnimation();
    }

    @Override
    public void onUIPositionChange(PtrFrameLayout frame, boolean isUnderTouch, byte status, PtrIndicator ptrIndicator) {
        if (!frame.isRefreshing()) {
            if (ptrIndicator.getCurrentPosY() >= mTotalDragDistance / 2 && !dotIsVisible()) {
                mAppearAnimatorSet.start();
                setDotVisibility(View.VISIBLE);
            } else if (ptrIndicator.getCurrentPosY() <= mTotalDragDistance / 2 && dotIsVisible()) {
                setDotVisibility(View.INVISIBLE);
                endAnimation();
            }
        }
    }
}
