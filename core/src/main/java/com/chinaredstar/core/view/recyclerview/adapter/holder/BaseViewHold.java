package com.chinaredstar.core.view.recyclerview.adapter.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chinaredstar.core.base.BaseBean;

import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/22.
 */

public abstract class BaseViewHold<T extends BaseBean> extends RecyclerView.ViewHolder {
    public BaseViewHold(View itemView) {
        super(itemView);
    }

    public abstract void onBindViewHolder(int position, List<T> mData);
}

