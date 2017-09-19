package com.chinaredstar.core.view.toast;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/**
 * Created by hairui.xiang on 2017/9/18.
 * <p>
 * 不需要等待上一个toast消失,立即显示
 */

public class XToastTimelyHandler extends Handler implements XToastHandler {
    private final static int SHOW_TOAST = 0x123;
    private final static int HIDE_TOAST = 0x456;
    private final static int SHOWNEXT_TOAST = 0X789;
    private static XToastTimelyHandler mToastHandler;

    @Override
    public void handleMessage(Message msg) {
        XToast xToast = (XToast) msg.obj;
        switch (msg.what) {
            case SHOW_TOAST:
                showToast(xToast);
                break;
            case HIDE_TOAST:
                hideToast(xToast);
                break;
        }
    }

    private XToastTimelyHandler(Looper looper) {
        super(looper);
    }

    public synchronized static XToastTimelyHandler getInstance() {
        if (null == mToastHandler) {
            mToastHandler = new XToastTimelyHandler(Looper.getMainLooper());
        }
        return mToastHandler;
    }

    public void showToast(XToast xToast) {
        if (!xToast.isShowing()) {
            //把mView添加到界面中，实现Toast效果
            xToast.getParentView().addView(xToast.getView());
            //获取动画效果
            AnimatorSet set = xToast.getShowAnimatorSet();
            set.start();

            Message message = Message.obtain();
            message.what = HIDE_TOAST;
            message.obj = xToast;
            sendMessageDelayed(message, xToast.getDuration());
        }
    }


    public void hideToast(final XToast xToast) {
        if (!xToast.isShowing()) {
            return;
        }

        AnimatorSet set = xToast.getHideAnimatorSet();
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //如果动画结束了，界面中移除mView
                xToast.getRootView().removeView(xToast.getParentView());
                if (xToast.getOnDisappearListener() != null) {
                    xToast.getOnDisappearListener().onDisappear(xToast);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
    }

    @Override
    public void onCancel(XToast xToast) {
        if (!xToast.isShowing()) {
            return;
        }
        xToast.getRootView().removeView(xToast.getParentView());
        if (xToast.getOnDisappearListener() != null) {
            xToast.getOnDisappearListener().onDisappear(xToast);
        }
    }

    @Override
    public void onProcess(XToast xToast) {
        if (!xToast.isShowing()) {
            Message message = Message.obtain();
            message.what = SHOW_TOAST;
            message.obj = xToast;
            sendMessage(message);
        }
    }
}
