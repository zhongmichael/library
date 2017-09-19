package com.chinaredstar.core.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

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
    private int mScrollSpeed = 1000;
    private int mSwitchDuration = 3500;
    private Scroller mScroller;

    public BannerViewPager(Context context) {
        super(context);
    }

    public BannerViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public <T> void setAdapter(BannerAdapter<T> adapter) {
        this.mAdapter = adapter;
        this.mAdapter.binding(this);
        this.setScrollSpeed(1000);
        this.setSwitchDuration(3500);
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
        mScrollSpeed = scrollSpeed;
        setCustomScrollSpeed();
    }

    public void setCustomScrollSpeed() {
        if (!(mScroller instanceof BannerSpeedScroller)) {
            try {
                Field mField = ViewPager.class.getDeclaredField("mScroller");
                mField.setAccessible(true);
                mScroller = new BannerSpeedScroller(getContext()).setScrollSpeed(mScrollSpeed);//, new AccelerateInterpolator()
                mField.set(this, mScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setDefaultScrollSpeed() {
        if (mScroller instanceof BannerSpeedScroller) {
            try {
                Field mField = ViewPager.class.getDeclaredField("mScroller");
                mField.setAccessible(true);
                mScroller = new Scroller(getContext(), sInterpolator);
                mField.set(this, mScroller);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final Interpolator sInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };

    public void startRun() {
        this.mAdapter.startCycle();
    }

    public void stopRun() {
        this.mAdapter.stopCycle();
    }
}
