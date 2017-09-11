package com.chinaredstar.core.view.banner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by hairui.xiang on 2017/9/11.
 */

public class BannerSpeedScroller extends Scroller {
    private int mScrollSpeed = 1200;

    public BannerSpeedScroller(Context context) {
        super(context);
    }

    public BannerSpeedScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }

    public BannerSpeedScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, mScrollSpeed);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, mScrollSpeed);
    }

    public void setScrollSpeed(ViewPager vp, int scrollSpeed) {
        this.mScrollSpeed = scrollSpeed;
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(vp, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
