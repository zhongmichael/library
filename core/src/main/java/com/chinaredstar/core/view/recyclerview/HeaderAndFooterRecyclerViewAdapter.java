package com.chinaredstar.core.view.recyclerview;

import android.support.v4.util.SparseArrayCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.chinaredstar.core.R;

/**
 * Created by hairui.xiang on 2017/8/7.
 */

public class HeaderAndFooterRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER_VIEW = 100000;
    private static final int TYPE_FOOTER_VIEW = 200000;

    /**
     * The real adapter for RecyclerView
     */
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mAdapter;

    private SparseArrayCompat<View> mHeaderViews = new SparseArrayCompat<>();
    private SparseArrayCompat<View> mFooterViews = new SparseArrayCompat<>();

    private RecyclerView.LayoutManager mLayoutManager;
    private LoadMoreRecyclerView.SpanSizeLookup mSpanSizeLookup;

    public HeaderAndFooterRecyclerViewAdapter() {
    }

    public HeaderAndFooterRecyclerViewAdapter(RecyclerView.Adapter adapter) {
        setAdapter(adapter);
    }

    /**
     * Set the adapter for RecyclerView
     *
     * @param adapter
     */
    public void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        if (null != adapter) {
            if (!(adapter instanceof RecyclerView.Adapter)) {
                throw new RuntimeException("A RecyclerView.Adapter is Need");
            }

            if (null != mAdapter) {
                notifyItemRangeRemoved(getHeadersCount(), mAdapter.getItemCount());
                mAdapter.unregisterAdapterDataObserver(mDataObserver);
            }

            mAdapter = adapter;
            mAdapter.registerAdapterDataObserver(mDataObserver);
            notifyItemRangeInserted(getHeadersCount(), mAdapter.getItemCount());
        }
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        mLayoutManager = layout;
    }

    public void setSpanSizeLookup(final LoadMoreRecyclerView.SpanSizeLookup spanSizeLookup) {
        this.mSpanSizeLookup = spanSizeLookup;
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return mGridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return mGridLayoutManager.getSpanCount();
                    }
                    return spanSizeLookup.getSpanSize(position);
                }
            });
        }
    }

    /**
     * @return RecyclerView.Adapter
     */
    public RecyclerView.Adapter getAdapter() {
        return mAdapter;
    }

    /**
     * remove view From headerViews
     *
     * @param v
     * @return
     */
    public boolean removeHeaderView(View v) {
        if (v.getTag(R.id.loadmorerecyclerview_adpater_viewtype) instanceof Integer) {
            int viewType = (int) v.getTag(R.id.loadmorerecyclerview_adpater_viewtype);
            mHeaderViews.remove(viewType);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * remove view From footerViews
     *
     * @param v
     * @return
     */
    public boolean removeFooterView(View v) {
        if (v.getTag(R.id.loadmorerecyclerview_adpater_viewtype) instanceof Integer) {
            int viewType = (int) v.getTag(R.id.loadmorerecyclerview_adpater_viewtype);
            mFooterViews.remove(viewType);
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    /**
     * Add a header for RefreshRecyclerView
     *
     * @param v
     */
    public void addHeaderView(View v) {
        if (null != v) {
            if (v.getTag(R.id.loadmorerecyclerview_adpater_viewtype) instanceof Integer) {
                int viewType = (int) v.getTag(R.id.loadmorerecyclerview_adpater_viewtype);
                mHeaderViews.remove(viewType);
            }
            int viewType = mHeaderViews.size() + TYPE_HEADER_VIEW;
            v.setTag(R.id.loadmorerecyclerview_adpater_viewtype, viewType);
            mHeaderViews.put(viewType, v);
            notifyDataSetChanged();
        }
    }

    /**
     * Add a footer for RefreshRecyclerView
     *
     * @param v
     */
    public void addFooterView(View v) {
        if (null != v) {
            if (v.getTag(R.id.loadmorerecyclerview_adpater_viewtype) instanceof Integer) {
                int viewType = (int) v.getTag(R.id.loadmorerecyclerview_adpater_viewtype);
                mFooterViews.remove(viewType);
            }
            int viewType = mFooterViews.size() + TYPE_FOOTER_VIEW;
            v.setTag(R.id.loadmorerecyclerview_adpater_viewtype, viewType);
            mFooterViews.put(viewType, v);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (null != mAdapter) {
            return getHeadersCount() + getFootersCount() + mAdapter.getItemCount();
        }
        return getHeadersCount() + getFootersCount();
    }


    @Override
    public int getItemViewType(int position) {
        if (isHeader(position)) {
            return mHeaderViews.keyAt(position);
        } else if (isFooter(position)) {
            return mFooterViews.keyAt(position - getHeadersCount() - mAdapter.getItemCount());
        }
        return mAdapter.getItemViewType(position - getHeadersCount());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (null != mHeaderViews.get(viewType)) {
            return new ViewHolder(mHeaderViews.get(viewType));
        } else if (null != mFooterViews.get(viewType)) {
            return new ViewHolder(mFooterViews.get(viewType));
        }
        return null == mAdapter ? null : mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (isHeader(position) || isFooter(position)) {
            /*if (mLayoutManager instanceof GridLayoutManager) {
                final GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
                final GridLayoutManager.SpanSizeLookup mOldSpanSizeLookup = mGridLayoutManager.getSpanSizeLookup();
                mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                    @Override
                    public int getSpanSize(int position) {
                        int viewType = getItemViewType(position);
                        if (mHeaderViews.get(viewType) != null) {
                            return mGridLayoutManager.getSpanCount();
                        } else if (mFooterViews.get(viewType) != null) {
                            return mGridLayoutManager.getSpanCount();
                        }
                        if (null != mOldSpanSizeLookup)
                            return mOldSpanSizeLookup.getSpanSize(position);
                        return 1;
                    }
                });
            } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                    StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                    params.setFullSpan(true);
                }
            }*/

          /*  if (mLayoutManager instanceof StaggeredGridLayoutManager) {
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) mLayoutManager;
                if (isHeader(position) || isFooter(position) || null != mSpanSizeLookup && manager.getSpanCount() == mSpanSizeLookup.getSpanSize(position)) {
                    ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
                    if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                        StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                        params.setFullSpan(true);
                    }
                }
            }*/


        } else {
            mAdapter.onBindViewHolder(holder, position - getHeadersCount());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        if (mLayoutManager instanceof GridLayoutManager) {
            final GridLayoutManager mGridLayoutManager = (GridLayoutManager) mLayoutManager;
            final GridLayoutManager.SpanSizeLookup mOldSpanSizeLookup = mGridLayoutManager.getSpanSizeLookup();
            mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int viewType = getItemViewType(position);
                    if (mHeaderViews.get(viewType) != null) {
                        return mGridLayoutManager.getSpanCount();
                    } else if (mFooterViews.get(viewType) != null) {
                        return mGridLayoutManager.getSpanCount();
                    }
                    if (null != mOldSpanSizeLookup)
                        return mOldSpanSizeLookup.getSpanSize(position);
                    return 1;
                }
            });
        }
    }

    /**
     * 用于StaggeredGridLayoutManager的情况下 itemview铺满一行
     */
    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mAdapter.onViewAttachedToWindow(holder);
        int position = holder.getAdapterPosition();
//        int position = holder.getLayoutPosition();
        if (isHeader(position) || isFooter(position)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams params = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                params.setFullSpan(true);
            }
        }
    }


    /**
     * @return headerView's counts
     */
    public Integer getHeadersCount() {
        return null == mHeaderViews ? 0 : mHeaderViews.size();
    }

    public SparseArrayCompat<View> getHeaderViews() {
        return mHeaderViews;
    }

    /**
     * @return footerView's counts
     */
    public Integer getFootersCount() {
        return null == mFooterViews ? 0 : mFooterViews.size();
    }

    public SparseArrayCompat<View> getFooterViews() {
        return mFooterViews;
    }

    /**
     * 判断当前是否是header
     *
     * @param position
     * @return
     */
    public boolean isHeader(int position) {
        return getHeadersCount() > 0 && position <= getHeadersCount() - 1;
    }

    /**
     * 判断当前是否是footer
     *
     * @param position
     * @return
     */
    public boolean isFooter(int position) {
        int lastPosition = getItemCount() - getFootersCount();
        return getFootersCount() > 0 && position >= lastPosition;
    }

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {

        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeadersCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            notifyItemRangeChanged(fromPosition + getHeadersCount(), toPosition + getHeadersCount() + itemCount);
        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}