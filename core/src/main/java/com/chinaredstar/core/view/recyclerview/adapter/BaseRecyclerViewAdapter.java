package com.chinaredstar.core.view.recyclerview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chinaredstar.core.base.BaseBean;
import com.chinaredstar.core.view.recyclerview.LoadMoreRecyclerView;
import com.chinaredstar.core.view.recyclerview.adapter.holder.BaseViewHold;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/22.
 */

public abstract class BaseRecyclerViewAdapter<T extends BaseBean> extends RecyclerView.Adapter<BaseViewHold> {
    private final static int ERROR_PAGE = 404;
    private final static int EMPTY_PAGE = 200;
    private final static int DEFAULT_BACKGROUND = 888;
    private boolean isEmpty;
    private boolean isError;
    private boolean isShowingDefaultBackground;
    protected String emptyTxt = "empty data!";
    protected String errorTxt = "error!";
    protected int defaultBackgroundResId;
    private LoadMoreRecyclerView mLoadMoreRecyclerView;
    protected List<T> mDatas = new ArrayList<>();
    private LayoutInflater mInflater;
    private Context mContext;

    private BaseRecyclerViewAdapter() {
    }

    public BaseRecyclerViewAdapter(Context context, LoadMoreRecyclerView recyclerView, List<T> datas) {
        this(context, recyclerView, 0, datas);
    }

    public BaseRecyclerViewAdapter(Context context, LoadMoreRecyclerView recyclerView, int defaultBackgroundResId, List<T> datas) {
        this.mContext = context;
        this.mLoadMoreRecyclerView = recyclerView;
        this.defaultBackgroundResId = defaultBackgroundResId;
        this.mInflater = LayoutInflater.from(mContext);
        if (0 != defaultBackgroundResId) {
            isShowingDefaultBackground = true;
        } else {
            isShowingDefaultBackground = false;
        }
        if (null != datas) {
            this.mDatas.addAll(datas);
        }
    }

    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return mInflater.inflate(resource, root, attachToRoot);
    }

    private void isEmpty() {
        this.isEmpty = true;
        this.isError = false;
        this.isShowingDefaultBackground = false;
        this.hideLoadMoreFooterView(true);
    }

    private void isError() {
        this.isEmpty = false;
        this.isError = true;
        this.isShowingDefaultBackground = false;
        this.hideLoadMoreFooterView(true);
    }

    private void isNormal() {
        this.isEmpty = false;
        this.isError = false;
        this.isShowingDefaultBackground = false;
        this.hideLoadMoreFooterView(false);
    }

    public void hideLoadMoreFooterView(boolean hide) {
        this.mLoadMoreRecyclerView.setHideViewWhenNoMore(hide);
    }

    public void notifyEmptyDataSetChanged() {
        this.isEmpty();
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    public void notifyErrorDataSetChanged() {
        this.isError();
        this.mDatas.clear();
        this.notifyDataSetChanged();
    }

    public void notifyNormalDataSetChanged() {
        this.isNormal();
        this.notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return mDatas;
    }

    public void setDefaultBackgroundResId(int defaultBackgroundResId) {
        this.defaultBackgroundResId = defaultBackgroundResId;
    }

    public void setEmptyTxt(String emptyTxt) {
        this.emptyTxt = emptyTxt;
    }

    public void setErrorTxt(String errorTxt) {
        this.errorTxt = errorTxt;
    }

    @Override
    public int getItemCount() {
        if (isError || isEmpty || isShowingDefaultBackground)
            return 1;
        return this.mDatas == null ? 0 : this.mDatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isEmpty) {
            return EMPTY_PAGE;
        }
        if (isError) {
            return ERROR_PAGE;
        }
        if (isShowingDefaultBackground) {
            return DEFAULT_BACKGROUND;
        }
        return super.getItemViewType(position);
    }

    @Override
    public BaseViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case EMPTY_PAGE:
                return onCreateEmptyViewHolder(parent, viewType);
            case ERROR_PAGE:
                return onCreateErrorViewHolder(parent, viewType);
            case DEFAULT_BACKGROUND:
                return onCreateDefaulBackgoundViewHolder(parent, viewType);
        }
        return onCreateNormalViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHold holder, int position) {
        holder.onBindViewHolder(position, mDatas);
    }

    public BaseViewHold onCreateErrorViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHold(getItemView(getErrorContentView())) {
            @Override
            public void onBindViewHolder(int position, List mData) {

            }
        };
    }

    public BaseViewHold onCreateEmptyViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHold(getItemView(getEmptyContentView())) {
            @Override
            public void onBindViewHolder(int position, List mData) {

            }
        };
    }

    public BaseViewHold onCreateDefaulBackgoundViewHolder(ViewGroup parent, int viewType) {
        return new BaseViewHold(getItemView(getDefaultBackgroudView())) {
            @Override
            public void onBindViewHolder(int position, List mData) {

            }
        };
    }

    public abstract BaseViewHold onCreateNormalViewHolder(ViewGroup parent, int viewType);

    protected View getErrorContentView() {
        return null;
    }

    protected View getEmptyContentView() {
        return null;
    }

    protected View getDefaultBackgroudView() {
        return null;
    }

    private View getItemView(View contentView) {
        FrameLayout itemView = new FrameLayout(mContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        itemView.addView(contentView, params);
        return itemView;
    }
}
