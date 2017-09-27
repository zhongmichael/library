package com.chinaredstar.demo;

import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.view.pulltorefresh.t1.PulToRefreshLayout;
import com.chinaredstar.core.view.recyclerview.DefaultLoadMoreView;
import com.chinaredstar.core.view.recyclerview.LoadMoreRecyclerView;
import com.chinaredstar.core.view.recyclerview.OnLoadMoreListener;
import com.chinaredstar.push.utils.JHandler;
import com.scrollablelayout.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by hairui.xiang on 2017/9/12.
 */

public class ScrollableLayoutDemo extends BaseActivity {
    ScrollableLayout sl_root;
    PulToRefreshLayout ptr_view;
    LoadMoreRecyclerView lmr_list;
    private List<String> mDatas = new ArrayList<>();
    LoadMoreAdapter mLoadMoreAdapter;
    int count = 0;

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_sl;
    }

    @Override
    protected void initWidget() {
        sl_root = (ScrollableLayout) findViewById(R.id.sl_root);
        ptr_view = (PulToRefreshLayout) findViewById(R.id.ptr_view);
        lmr_list = (LoadMoreRecyclerView) findViewById(R.id.lmr_list);
        sl_root.getHelper().setCurrentScrollableContainer(lmr_list);

        lmr_list.setLayoutManager(new LinearLayoutManager(this));
        lmr_list.setLoadMoreView(new DefaultLoadMoreView(this));
        lmr_list.setHideViewWhenNoMore(false);
        lmr_list.addItemDecoration(new SpaceItemDecoration(10));

        ptr_view.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return sl_root.isCanPullToRefresh();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                count = 0;
                JHandler.handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        for (int i = 0; i < 20; i++) {
                            mDatas.add("");
                        }
                        ptr_view.refreshComplete();
                        mLoadMoreAdapter.notifyDataSetChanged();
                        lmr_list.setHasLoadMore(true);
                    }
                }, 1000);
            }
        });
       /* ptr_view.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                count = 0;
                JHandler.handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDatas.clear();
                        for (int i = 0; i < 20; i++) {
                            mDatas.add("");
                        }
                        ptr_view.refreshComplete();
                        mLoadMoreAdapter.notifyDataSetChanged();
                        lmr_list.setHasLoadMore(true);
                    }
                }, 1000);
            }
        });*/
//        mLoadMoreRecyclerView.addItemDecoration(new StickyItemDecoration(this));
        lmr_list.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoad() {
                JHandler.handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        for (int i = 0; i < 10; i++) {
                            mDatas.add("");
                        }
                        mLoadMoreAdapter.notifyDataSetChanged();
                        if (3 == count) {
                            lmr_list.setHasLoadMore(false);
                        } else {
                            lmr_list.setHasLoadMore(true);
                        }
                    }
                }, 2000);
            }
        });

        JHandler.handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 20; i++) {
                    mDatas.add("");
                }
                mLoadMoreAdapter.notifyDataSetChanged();
                lmr_list.setHasLoadMore(true);
            }
        });
        mLoadMoreAdapter = new LoadMoreAdapter();
        lmr_list.setAdapter(mLoadMoreAdapter);
    }

    class LoadMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        class ItemView extends RecyclerView.ViewHolder {
            TextView tv_index;

            public ItemView(View itemView) {
                super(itemView);
                tv_index = itemView.findViewById(R.id.tv_index);
            }
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ItemView(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ItemView item = (ItemView) holder;
            item.tv_index.setText(String.valueOf(position));
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        int mSpace;

        /**
         * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
         * the number of pixels that the item view should be inset by, similar to padding or margin.
         * The default implementation sets the bounds of outRect to 0 and returns.
         * <p>
         * <p>
         * If this ItemDecoration does not affect the positioning of item views, it should set
         * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
         * before returning.
         * <p>
         * <p>
         * If you need to access Adapter for additional data, you can call
         * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
         * View.
         *
         * @param outRect Rect to receive the output.
         * @param view    The child view to decorate
         * @param parent  RecyclerView this ItemDecoration is decorating
         * @param state   The current state of RecyclerView.
         */
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.left = mSpace;
            outRect.right = mSpace;
            outRect.bottom = mSpace;
            if (parent.getChildAdapterPosition(view) == 0) {
                outRect.top = mSpace;
            }

        }

        public SpaceItemDecoration(int space) {
            this.mSpace = space;
        }
    }
}
