package com.chinaredstar.demo;

import android.content.res.AssetManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.view.recyclerview.LoadMoreRecyclerView;
import com.chinaredstar.core.view.recyclerview.OnLoadMoreListener;
import com.chinaredstar.push.utils.JHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/8/16.
 */

public class LoadMoreDemo extends BaseActivity {
    private LoadMoreRecyclerView mLoadMoreRecyclerView;
    private List<String> mDatas = new ArrayList<>();
    LoadMoreAdapter mLoadMoreAdapter;
    int count = 0;
    int[] hs = new int[]{12, 24, 16, 28, 10, 12, 14, 18};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadmore);
        mLoadMoreRecyclerView = (LoadMoreRecyclerView) findViewById(R.id.lrv_list);
        final LinearLayoutManager layout = new LinearLayoutManager(this);
//        StaggeredGridLayoutManager layout = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
//        layout.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        mLoadMoreRecyclerView.setLayoutManager(layout);
//        mLoadMoreRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mLoadMoreRecyclerView.setLoadMoreView(new DefaultLoadMoreView(this));
//        mLoadMoreRecyclerView.setHideViewWhenNoMore(true);
//        mLoadMoreRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mLoadMoreRecyclerView.addItemDecoration(new StickyItemDecoration(this));
        mLoadMoreRecyclerView.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoad() {
              /*  JHandler.handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        count++;
                        for (int i = 0; i < 10; i++) {
                            mDatas.add("");
                        }
                        mLoadMoreAdapter.notifyDataSetChanged();
                        if (4 == count) {
                            mLoadMoreRecyclerView.setHasLoadMore(false);
                        } else {
                            mLoadMoreRecyclerView.setHasLoadMore(true);
                        }
                    }
                }, 2000);*/
            }
        });

        JHandler.handler().post(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    mDatas.add("");
                }
                mLoadMoreAdapter.notifyDataSetChanged();
//                mLoadMoreRecyclerView.setHasLoadMore(true);
            }
        });
        mLoadMoreAdapter = new LoadMoreAdapter();
        mLoadMoreRecyclerView.setAdapter(mLoadMoreAdapter);
        mLoadMoreRecyclerView.setSpanSizeLookup(new LoadMoreRecyclerView.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return position == 20 || position == 68 ? 2 : 1;
            }
        });

        isFileExists("h5_5");
    }

    /**
     * 判断assets文件夹下的文件是否存在
     *
     * @return false 不存在    true 存在
     */
    private boolean isFileExists(String filename) {
        AssetManager assetManager = getAssets();
        try {
            String[] names = assetManager.list("h5/h6");
            for (int i = 0; i < names.length; i++) {
                System.out.println("------------------name:  " + names[i]);
                if (names[i].equals(filename.trim())) {
                    System.out.println(filename + "存在");
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(filename + "不存在");
            return false;
        }
        System.out.println(filename + "不存在");
        return false;
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
            item.tv_index.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180 + hs[position % hs.length]));
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
