package com.chinaredstar.core.view.toast.tools;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.chinaredstar.core.view.toast.XToast;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by hairui.xiang on 2017/9/18.
 * 上一个toast消失，下一个toast才开始
 */

public class XToastSerialHandler extends Handler implements XToastHandler {
    private final static int SHOW_TOAST = 0x123;
    private final static int HIDE_TOAST = 0x456;
    private final static int SHOWNEXT_TOAST = 0X789;
    private static XToastSerialHandler mToastHandler;
    private final Queue<XToast> mQueue;

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

            case SHOWNEXT_TOAST:
                showNextToast();
                break;
        }
    }


    private XToastSerialHandler(Looper looper) {
        super(looper);
        mQueue = new LinkedList<>();
    }

    public synchronized static XToastSerialHandler getInstance() {
        if (null == mToastHandler) {
            mToastHandler = new XToastSerialHandler(Looper.getMainLooper());
        }
        return mToastHandler;
    }


    public void showNextToast() {
        if (mQueue.isEmpty()) return;
        //获取队列头部的XToast
        XToast xToast = mQueue.peek();
        if (!xToast.isShowing()) {
            Message message = Message.obtain();
            message.what = SHOW_TOAST;
            message.obj = xToast;
            sendMessage(message);
        }
    }

    public void hideToast(final XToast xToast) {

        if (!xToast.isShowing()) {
            mQueue.remove(xToast);
            return;
        }

        if (!mQueue.contains(xToast))
            return;

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
                sendEmptyMessage(SHOWNEXT_TOAST);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        set.start();
        mQueue.poll();
    }

    public void showToast(XToast xToast) {
        //如果当前有XToast正在展示，直接返回
        if (xToast.isShowing()) return;
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

    @Override
    public void onProcess(XToast xToast) {
        mQueue.offer(xToast);
        showNextToast();
    }

    public void dismiss(XToast xToast) {
        if (!xToast.isShowing()) {
            return;
        }

        xToast.getRootView().removeView(xToast.getParentView());
        if (xToast.getOnDisappearListener() != null) {
            xToast.getOnDisappearListener().onDisappear(xToast);
        }
    }

    @Override
    public void onCancel() {
        mQueue.clear();
        removeCallbacksAndMessages(null);
    }
}
