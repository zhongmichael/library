package com.chinaredstar.core.view.toast;

/**
 * Created by hairui.xiang on 2017/9/19.
 */

public interface XToastHandler {
    void onProcess(XToast xToast);

    void showToast(XToast xToast);

    void hideToast(XToast xToast);

    void onCancel();

    void dismiss(XToast xToast);
}
