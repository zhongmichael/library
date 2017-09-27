package com.chinaredstar.core.view.pulltorefresh.t2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class IPullableListView extends ListView implements IPullable {

    private boolean canPullUp;
    private boolean canPullDown;

    public IPullableListView(Context context) {
        super(context);
    }

    public IPullableListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IPullableListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (canPullDown) {
            if (getCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            } else if (getFirstVisiblePosition() == 0 && null != getChildAt(0)
                    && getChildAt(0).getTop() >= 0) {
                // 滑到ListView的顶部了
                return true;
            } else
                return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean canPullUp() {
        if (canPullUp) {
            if (getCount() == 0) {
                // 没有item的时候也可以上拉加载
                return true;
            } else if (getLastVisiblePosition() == (getCount() - 1)) {
                // 滑到底部了
                if (getChildAt(getLastVisiblePosition()
                        - getFirstVisiblePosition()) != null
                        && getChildAt(
                        getLastVisiblePosition()
                                - getFirstVisiblePosition())
                        .getBottom() <= getMeasuredHeight())
                    return true;
            }
            return false;
        } else {
            return false;
        }
    }

    /**
     * 自行设定是否可以下拉刷新 和 上拉加载
     *
     * @param canPullDown
     * @param canPullUp
     */
    public void setMode(boolean canPullDown, boolean canPullUp) {
        this.canPullDown = canPullDown;
        this.canPullUp = canPullUp;
    }

}
