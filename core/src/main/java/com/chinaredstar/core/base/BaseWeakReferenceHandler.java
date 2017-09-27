package com.chinaredstar.core.base;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by hairui.xiang on 2017/9/27.
 * 1，捕获异常
 * 2，使用activity或者fragment的弱引用，防止因handler延时操作造成内存泄漏
 */
public abstract class BaseWeakReferenceHandler extends Handler {
    public abstract void onHandleMessage(Object o, Message msg);

    public abstract void onException(Object o, Exception e);

    private WeakReference<Object> reference;

    public BaseWeakReferenceHandler(Object o) {
        reference = new WeakReference<Object>(o);
    }

    @Override
    public final void handleMessage(Message msg) {
        Object o = null;
        try {
            o = reference.get();
            if (null != o && null != msg) {
                onHandleMessage(o, msg);
            }
        } catch (Exception e) {
            onException(o, e);
        }
    }
}
