package com.chinaredstar.core.view.banner;

import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/9/11.
 * <p>
 * banner 循环播放使用
 */

public abstract class BannerAdapter<T> extends PagerAdapter {
    protected List<T> mList = new ArrayList<>();
    protected ViewPager mViewPager;
    private boolean mIsCycle = false;
    private boolean mIsChanged = false;
    private int mCycleWhat = 89757;
    private long mDuration = 4000;
    private int mCurrentPagePosition;
    private int mDotsSize;

    public BannerAdapter(List<T> list) {
        if (null != mList) {
            this.mList.addAll(list);
            cycleJudge();
        }
    }

    public void binding(ViewPager vp) {
        this.mViewPager = vp;
        this.mViewPager.setOffscreenPageLimit(mDotsSize);
        this.mViewPager.setOnPageChangeListener(mOnPageChangeListener);
    }


    protected abstract View getView(T data, int position);

    protected abstract void setCurrentDot(int position);

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager parent = (ViewPager) container;
        View child = (View) object;
        parent.removeView(child);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ViewPager parent = (ViewPager) container;
        View child = getView(mList.get(position), position);
        if (null != child)
            parent.addView(child, 0);
        return child;
    }

    /**
     * 判断是否满足循环滚动的条件
     */
    private void cycleJudge() {
        mDotsSize = null == mList ? 0 : mList.size();
        if (null != mList && mList.size() > 1) {
            T first = mList.get(0);
            T last = mList.get(mList.size() - 1);
            mList.add(0, last);
            mList.add(first);
            mIsCycle = true;
        }
    }

    public void startCycle() {
        if (mIsCycle) {
            if (null != mViewPager) {
                mViewPager.setCurrentItem(1, false);
                setCurrentDot(0);
            }
            startScroll();
        }
    }

    public void stopCycle() {
        if (mIsCycle) {
            if (null != mCycleHandler)
                mCycleHandler.removeMessages(mCycleWhat);
        }
    }

    /**
     * 实现ViewPager实例默认自动滚动 + 按键事件控制滚动(手指在屏幕上时候取消自动滚动,手指离开恢复自动滚动)
     */
    private void startScroll() {
        viewPagerAutoScrollDefault();
        viewPagerAutoScrollControl();
    }

    private Handler mCycleHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            if (msg.what == mCycleWhat) {
                mCycleHandler.removeMessages(mCycleWhat);
                if (null != mViewPager) {
                    if (mViewPager instanceof BannerViewPager) {
                        ((BannerViewPager) mViewPager).setCustomScrollSpeed();
                    }
                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1, true);
                }
                mCycleHandler.sendEmptyMessageDelayed(mCycleWhat, mDuration);
            }
        }
    };

    private void viewPagerAutoScrollDefault() {
        mCycleHandler.sendEmptyMessageDelayed(mCycleWhat, mDuration);
    }

    private void viewPagerAutoScrollControl() {
        mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        if (mViewPager instanceof BannerViewPager) {
                            ((BannerViewPager) mViewPager).setDefaultScrollSpeed();
                        }
                        mCycleHandler.removeMessages(mCycleWhat);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mCycleHandler.removeMessages(mCycleWhat);
                        mCycleHandler.sendEmptyMessageDelayed(mCycleWhat, mDuration);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        private int state;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            state = arg0;
            // SCROLL_STATE_IDLE(0) 停止滚动
            if (ViewPager.SCROLL_STATE_IDLE == arg0) {
                if (mIsChanged) {
                    mIsChanged = false;
                    mViewPager.setCurrentItem(mCurrentPagePosition, false);
                }
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        /**
         * A B C 0 1 2 => C A B C A 0 1 2 3 4
         */
        @Override
        public void onPageSelected(int arg0) {
            if (mIsCycle && arg0 == 0) {
                mCurrentPagePosition = getCount() - 2;
                mIsChanged = true;
            } else if (mIsCycle && arg0 == getCount() - 1) {
                mIsChanged = true;
                mCurrentPagePosition = 1;
            } else {
                mCurrentPagePosition = arg0;
            }
            // if (mIsCycle) {
            // arg0 = arg0 % dotsSize;
            // (arg0 % dotsSize - 1) % (dotsSize - 2)
            // }
            if (state != ViewPager.SCROLL_STATE_IDLE) {
                if (mIsCycle && arg0 == 0) {
                    arg0 = getCount() - 2;
                }
                setCurrentDot((arg0 - 1) % mDotsSize);
            }
        }
    };

    public void setDuration(long duration) {
        this.mDuration = duration;
    }
}
