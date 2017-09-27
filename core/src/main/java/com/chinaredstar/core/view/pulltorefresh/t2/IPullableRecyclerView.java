package com.chinaredstar.core.view.pulltorefresh.t2;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

public class IPullableRecyclerView extends RecyclerView implements IPullable {
    private boolean isCanPullup = false;
    private boolean isCanPuldown = false;

    public IPullableRecyclerView(Context arg0, AttributeSet arg1, int arg2) {
        super(arg0, arg1, arg2);
        // TODO Auto-generated constructor stub
    }

    public IPullableRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public IPullableRecyclerView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean canPullUp() {
        // TODO Auto-generated method stub
        try {
            if (!isCanPullup) {
                return false;
            }
            if (getAdapter().getItemCount() == 0) {
                return true;
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager manager = ((LinearLayoutManager) getLayoutManager());
                int lastVisiblePosition = manager.findLastVisibleItemPosition();
                int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                View view = manager.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (null != view) {
                    return view.getBottom() <= getMeasuredHeight()
                            && (lastVisiblePosition == getAdapter().getItemCount() - 1);
                }
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager manager = ((GridLayoutManager) getLayoutManager());
                int lastVisiblePosition = manager.findLastVisibleItemPosition();
                int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                View view = manager.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (null != view) {
                    return view.getBottom() <= getMeasuredHeight()
                            && (lastVisiblePosition == getAdapter().getItemCount() - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean canPullDown() {
        // TODO Auto-generated method stub
        try {
            if (!isCanPuldown) {
                return false;
            }
            if (getAdapter().getItemCount() == 0) {
                // 没有item的时候也可以下拉刷新
                return true;
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager manager = ((LinearLayoutManager) getLayoutManager());
                return manager.findFirstCompletelyVisibleItemPosition() == 0;
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager manager = ((GridLayoutManager) getLayoutManager());
                return manager.findFirstCompletelyVisibleItemPosition() == 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setCanPullup(boolean isCanPullup) {
        this.isCanPullup = isCanPullup;
    }

    public void setCanPulldown(boolean isCanPuldown) {
        this.isCanPuldown = isCanPuldown;
    }

    public boolean isBottom() {
        // TODO Auto-generated method stub
        try {
            if (null != getAdapter() && getAdapter().getItemCount() == 0) {
                return true;
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof LinearLayoutManager) {
                LinearLayoutManager manager = ((LinearLayoutManager) getLayoutManager());
                int lastVisiblePosition = manager.findLastVisibleItemPosition();
                int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                View view = manager.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (null != view) {
                    return view.getBottom() <= getMeasuredHeight()
                            && (lastVisiblePosition == getAdapter().getItemCount() - 1);
                }
            }
            if (null != getLayoutManager() && getLayoutManager() instanceof GridLayoutManager) {
                GridLayoutManager manager = ((GridLayoutManager) getLayoutManager());
                int lastVisiblePosition = manager.findLastVisibleItemPosition();
                int firstVisiblePosition = manager.findFirstVisibleItemPosition();
                View view = manager.getChildAt(lastVisiblePosition - firstVisiblePosition);
                if (null != view) {
                    return view.getBottom() <= getMeasuredHeight()
                            && (lastVisiblePosition == getAdapter().getItemCount() - 1);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
