package com.chinaredstar.core.utils.download;

import com.chinaredstar.core.base.BaseBean;

/**
 * Created by hairui.xiang on 2017/9/20.
 */

public class DownloadProgress extends BaseBean {
    public float progress;
    public long total;

    public DownloadProgress(float progress, long total) {
        this.progress = progress;
        this.total = total;
    }
}
