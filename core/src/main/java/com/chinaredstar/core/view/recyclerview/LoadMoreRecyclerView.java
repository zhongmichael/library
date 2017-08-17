package com.chinaredstar.core.view.recyclerview;

import android.content.Context;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hairui.xiang on 2017/8/4.
 */

public class LoadMoreRecyclerView extends NestedRecyclerView {

    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter;
    private boolean isBindAdapter = false;
    /**
     * 是否加载完成
     */
    private boolean isLoadFinished = true;
    /**
     * 是否还有更多数据
     **/
    private boolean isHasMore = true;

    /**
     * 没有更多数据被加载，是否隐藏加载更多view
     */
    private boolean isNoMoreThenHideView;
    /**
     * 是否已经添加了LoadMoreView
     */
    private boolean isAddedLoadMoreView = false;
    /**
     *
     * */
    private ILoadMoreView mLoadMoreView;
    private OnLoadMoreListener mOnLoadMoreListener;

    public LoadMoreRecyclerView(Context context) {
        super(context);
        init(context, null);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter();
//        super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        mLoadMoreView = new DefaultLoadMoreView(context);
        addOnScrollListener(new OnRecyclerViewScrollToBottomListener() {
            @Override
            void onScrollToBottom() {
                executeLoading();
            }
        });
    }

    /**
     * 设置LoadMoreView
     *
     * @param loadMoreView
     */
    public void setLoadMoreView(ILoadMoreView loadMoreView) {
        if (null == loadMoreView)
            throw new RuntimeException("loadMoreView must not null!");
        if (mLoadMoreView != null) {
            try {
                removeFooterView(mLoadMoreView.getFooterView());
                isAddedLoadMoreView = false;
            } catch (Exception e) {

            }
        }
        mLoadMoreView = loadMoreView;
        mLoadMoreView.getFooterView().setOnClickListener(mClickFooterViewClickListener);
    }

    /**
     * 更新当前加载状态
     */
    public void setHasLoadMore(boolean hasMore) {
        if (!hasMore) {
            mLoadMoreView.showNoMore();
            //没有更多数据被加载，隐藏view
            if (isNoMoreThenHideView) {
                removeFooterView(mLoadMoreView.getFooterView());
                isAddedLoadMoreView = false;
            } else {
                if (!isAddedLoadMoreView) {
                    isAddedLoadMoreView = true;
                    addFooterView(mLoadMoreView.getFooterView());
                }
            }
        } else {
            mLoadMoreView.showLoading();
            if (!isAddedLoadMoreView) {
                isAddedLoadMoreView = true;
                addFooterView(mLoadMoreView.getFooterView());
            }
        }
        isHasMore = hasMore;
        isLoadFinished = true;
    }

    /**
     * 当没有更多数据被加载，是否隐藏LoadMoreView
     */
    public void setHideViewWhenNoMore(boolean isNoMoreThenHideView) {
        this.isNoMoreThenHideView = isNoMoreThenHideView;
    }

    /**
     * 加载更多
     */
    private synchronized void executeLoading() {
        if (isLoadFinished && isHasMore) {
            isLoadFinished = false;
            if (null != mOnLoadMoreListener) {
                mOnLoadMoreListener.onLoad();
            }
        }
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }

    private final View.OnClickListener mClickFooterViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            executeLoading();
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        if (!isBindAdapter) {
            isBindAdapter = true;
            super.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
        }
        try {
            adapter.unregisterAdapterDataObserver(mDataObserver);
        } catch (Exception e) {

        } finally {
            adapter.registerAdapterDataObserver(mDataObserver);
            mHeaderAndFooterRecyclerViewAdapter.setAdapter(adapter);
        }
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        mHeaderAndFooterRecyclerViewAdapter.setLayoutManager(layout);
    }

    public void setSpanSizeLookup(final SpanSizeLookup spanSizeLookup) {
        mHeaderAndFooterRecyclerViewAdapter.setSpanSizeLookup(spanSizeLookup);
    }

    public interface SpanSizeLookup {
        int getSpanSize(int position);
    }

    /**
     * 刷新数据时停止滑动,避免出现数组下标越界问题
     */
    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_CANCEL, 0, 0, 0));
        }
    };

    public void addFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.addFooterView(footerView);
    }

    public void addHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.addHeaderView(headerView);
    }

    public void removeFooterView(View footerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeFooterView(footerView);
    }

    public void removeHeaderView(View headerView) {
        mHeaderAndFooterRecyclerViewAdapter.removeHeaderView(headerView);
    }
}
