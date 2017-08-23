package com.chinaredstar.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.view.View;

/**
 * Created by hairui.xiang on 2017/8/18.
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    //ItemView左边的间距
    private float mOffsetLeft;
    //ItemView右边的间距
    private float mOffsetTop;
    //时间轴结点的半径
    private float mNodeRadius;
    Bitmap mIcon;
    private int mHeaderHeight = 100;
    private float mOffsetL = 40;
    private TextPaint mTextPaint;

    public StickyItemDecoration(Context context) {
        super();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(Color.RED);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(40);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);
            System.out.println("view: " + view.getTop());
            int index = parent.getChildAdapterPosition(view);
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            if (i == 0) { //屏幕上第一个可见的 ItemView 时，i == 0;
                int top = parent.getPaddingTop();
                System.out.println("top: " + top);
                if ((index + 1) % 5 == 0) {
                    int suggestTop = view.getBottom() - mHeaderHeight;
                    // 当 ItemView 与 Header 底部平齐的时候，判断 Header 的顶部是否小于
                    // parent 顶部内容开始的位置，如果小于则对 Header.top 进行位置更新，
                    //否则将继续保持吸附在 parent 的顶部
                    if (suggestTop < top) {
                        top = suggestTop;
                    }
                }
                int bottom = top + mHeaderHeight;
                mPaint.setColor(Color.YELLOW);
                c.drawRect(left, top, right, bottom, mPaint);
             /*   mPaint.setColor(Color.RED);
                c.drawText("A" + (index / 5), mOffsetL, bottom, mPaint);
*/
                Rect rect = new Rect();
                mTextPaint.getTextBounds("A" + (index / 5), 0, ("A" + (index / 5)).length(), rect);
                c.drawText("A" + (index / 5), view.getWidth() / 2 - rect.width() / 2, bottom - (mHeaderHeight - rect.height()) / 2, mTextPaint);//绘制居中标题
            } else {
                if (index % 5 == 0) {
                    float fx = mOffsetL;
                    float fy = view.getTop();

                    mPaint.setColor(Color.YELLOW);
                    c.drawRect(left, fy - mHeaderHeight, right, fy, mPaint);
//                    mPaint.setColor(Color.RED);
//                    c.drawText("A" + (index / 5), fx, fy, mPaint);
                    Rect rect = new Rect();
                    mTextPaint.getTextBounds("A" + (index / 5), 0, ("A" + (index / 5)).length(), rect);
                    c.drawText("A" + (index / 5), view.getWidth() / 2 - rect.width() / 2, fy - (mHeaderHeight - rect.height()) / 2, mTextPaint);//绘制居中标题
                }
            }

        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int index = parent.getChildAdapterPosition(view);
        if (index % 5 == 0) {
            outRect.top = mHeaderHeight;
        }
        outRect.bottom = 1;
    }
}
