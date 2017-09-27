package com.chinaredstar.core.view.toast.tools;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;

import com.chinaredstar.core.view.toast.XToast;

/**
 * Created by hairui.xiang on 2017/9/18.
 */

public class XTostAnimationUtil {
    public static final int ANIMATION_DEFAULT = 0x000;
    public static final int ANIMATION_DRAWER = 0x001;
    public static final int ANIMATION_SCALE = 0x002;
    public static final int ANIMATION_PULL = 0x003;
    public static final int ANIMATION_DURATION = 500;
    public static AnimatorSet getShowAnimation(XToast xToast, int animationType) {
        switch (animationType) {
            case ANIMATION_DRAWER:
                AnimatorSet drawerSet = new AnimatorSet();
                drawerSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "translationY", -xToast.getView().getMeasuredHeight(), 0),
                        ObjectAnimator.ofFloat(xToast.getView(), "alpha", 0, 1)
                );
                drawerSet.setDuration(ANIMATION_DURATION);
                return drawerSet;

            case ANIMATION_SCALE:
                AnimatorSet scaleSet = new AnimatorSet();
                scaleSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "scaleX", 0, 1),
                        ObjectAnimator.ofFloat(xToast.getView(), "scaleY", 0, 1)
                );
                scaleSet.setDuration(ANIMATION_DURATION);
                return scaleSet;

            case ANIMATION_PULL:
                AnimatorSet pullSet = new AnimatorSet();
                pullSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "translationY", xToast.getView().getMeasuredHeight(), 0),
                        ObjectAnimator.ofFloat(xToast.getView(), "alpha", 0, 1)
                );
                pullSet.setDuration(ANIMATION_DURATION);
                return pullSet;

            default:
                AnimatorSet defaultSet = new AnimatorSet();
                defaultSet.play(ObjectAnimator.ofFloat(xToast.getView(), "alpha", 0, 1));
                defaultSet.setDuration(ANIMATION_DURATION);
                return defaultSet;
        }
    }

    public static AnimatorSet getHideAnimation(XToast xToast, int animationType) {
        switch (animationType) {
            case ANIMATION_DRAWER:
                AnimatorSet drawerSet = new AnimatorSet();
                drawerSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "translationY", 0, -xToast.getView().getMeasuredHeight()),
                        ObjectAnimator.ofFloat(xToast.getView(), "alpha", 1, 0)
                );
                drawerSet.setDuration(ANIMATION_DURATION);
                return drawerSet;

            case ANIMATION_SCALE:
                AnimatorSet scaleSet = new AnimatorSet();
                scaleSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "scaleX", 1, 0),
                        ObjectAnimator.ofFloat(xToast.getView(), "scaleY", 1, 0)
                );
                scaleSet.setDuration(ANIMATION_DURATION);
                return scaleSet;

            case ANIMATION_PULL:
                AnimatorSet pullSet = new AnimatorSet();
                pullSet.playTogether(
                        ObjectAnimator.ofFloat(xToast.getView(), "translationY", 0, xToast.getView().getMeasuredHeight()),
                        ObjectAnimator.ofFloat(xToast.getView(), "alpha", 1, 0)
                );
                pullSet.setDuration(ANIMATION_DURATION);
                return pullSet;

            default:
                AnimatorSet defaultSet = new AnimatorSet();
                defaultSet.play(ObjectAnimator.ofFloat(xToast.getView(), "alpha", 1, 0));
                defaultSet.setDuration(ANIMATION_DURATION);
                return defaultSet;
        }
    }
}
