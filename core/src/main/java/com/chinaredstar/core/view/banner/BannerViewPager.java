package com.chinaredstar.core.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

/**
 * Created by hairui.xiang on 2017/9/11.
 * <p>
 * banner 循环播放使用
 * <p>
 * mSwitchDuration > mScrollSpeed
 * </p>
 */

public class BannerViewPager extends ViewPager {
    private BannerAdapter mAdapter;
    private static final int mScrollSpeed = 1200;
    private static final int mSwitchDuration = 4000;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public <T> void setAdapter(BannerAdapter<T> adapter) {
        this.mAdapter = adapter;
        this.mAdapter.binding(this);
        this.setScrollSpeed(mScrollSpeed);
        this.setSwitchDuration(mSwitchDuration);
        super.setAdapter(this.mAdapter);
    }

    /**
     * @param duration 上一张图片切换为下一张图片的时间间隔,值越大下一张图片出现的时间越晚
     */
    public void setSwitchDuration(long duration) {
        this.mAdapter.setDuration(duration);
    }

    /**
     * @param scrollSpeed ViewPager滚动速度, 值越大滚动越慢
     */
    public void setScrollSpeed(int scrollSpeed) {
        new BannerSpeedScroller(getContext()).setScrollSpeed(this, scrollSpeed);
    }

    public void startRun() {
        this.mAdapter.startCycle();
    }

    public void stopRun() {
        this.mAdapter.stopCycle();
    }

}
