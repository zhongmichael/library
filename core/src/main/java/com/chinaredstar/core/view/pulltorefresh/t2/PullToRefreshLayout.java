package com.chinaredstar.core.view.pulltorefresh.t2;


import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chinaredstar.core.R;

/**
 * Created by hairui.xiang on 2017/9/27.
 */
public class PullToRefreshLayout extends ViewGroup {
    private static final String TAG = PullToRefreshLayout.class.getSimpleName();
    /**
     * 刷新view
     */
    private View mRefreshView;
    /**
     * 加载view
     */
    private View mLoadmoreView;
    /**
     * 内容view
     */
    private View mPullableView;
    /**
     * 刷新距离
     */
    private int mRefreshDist;
    /**
     * 加载距离
     */
    private int mLoadmoreDist;

    private long delayMillis = 200l;
    // 初始状态
    public static final int INIT = 0;
    // 释放刷新
    public static final int RELEASE_TO_REFRESH = 1;
    // 正在刷新
    public static final int REFRESHING = 2;
    // 释放加载
    public static final int RELEASE_TO_LOAD = 3;
    // 正在加载
    public static final int LOADING = 4;
    // done
    public static final int DONE = 5;
    // 已经没有更多数据了
    public static final int LOAD_OVER = 6;
    // 当前状态
    private int mCurrentState = INIT;
    // 刷新成功
    public static final int SUCCEED = 0;
    // 刷新失败
    public static final int FAIL = 1;

    // 上一次执行onLayout方法是mPullY的值
    private float mPullY;
    private float mLastY;
    private float mLastX;
    private float mDown_X;
    private float mDown_Y;
    private float mMove_X;
    private float mMove_Y;
    private float mRadio = 2;
    private int mEvents;
    private boolean isFirstOnLayout = true;
    private boolean isTouch;
    /**
     * 所有数据是加载完了吗？ true：加载完了
     */
    private boolean isLoadOver;

    private OnRefreshListener mOnRefreshListener;
    private OnClosedListener mOnClosedListener;

    // 刷新头处理
    private ImageView mRefreshingArrow;
    private ProgressBar mRefreshingBar;
    private TextView mRefreshingState;
    private boolean isAutoRefreshing = false;
    // 加载脚
    private ImageView mLoadingArrow;
    private ProgressBar mLoadingBar;
    private TextView mLoadingState;
    private TextView mIsOver;
    // 是否已开始动画
    private boolean animationIsGoing = false;
    /**
     * 顺时针旋转180
     */
    private RotateAnimation clockwiseRotateAnimation;
    /**
     * 逆时针旋转180
     */
    private RotateAnimation anticlockwiseRotateAnimation;

    private GestureDetector mYGestureDetector;

    /**
     * pull值回弹线程
     */
    private Runnable mYRunnable;
    private static final Object lock = new Object();

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.mOnRefreshListener = onRefreshListener;
    }

    public void setOnClosedListener(OnClosedListener onClosedListener) {
        this.mOnClosedListener = onClosedListener;
    }

    /**
     * 刷新头或者脚关闭时回调接口
     **/
    public interface OnClosedListener {
        void onClosed();
    }

    public interface OnRefreshListener {
        void onRefreshing(PullToRefreshLayout pullToRefreshLayout);

        void onLoadding(PullToRefreshLayout pullToRefreshLayout);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PullToRefreshLayout(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (getChildCount() != 3) {
            // 抛出异常
        }
        mYGestureDetector = new GestureDetector(getContext(), new YScrollDetector());
        anticlockwiseRotateAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        anticlockwiseRotateAnimation.setFillAfter(true);
        anticlockwiseRotateAnimation.setInterpolator(new LinearInterpolator());
        anticlockwiseRotateAnimation.setRepeatCount(0);
        anticlockwiseRotateAnimation.setDuration(200);

        clockwiseRotateAnimation = new RotateAnimation(-180f, 0f, Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        clockwiseRotateAnimation.setFillAfter(true);
        clockwiseRotateAnimation.setInterpolator(new LinearInterpolator());
        clockwiseRotateAnimation.setRepeatCount(0);
        clockwiseRotateAnimation.setDuration(200);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            // TODO Auto-generated method stub
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    removeRun();
                    mEvents = 0;
                    isTouch = true;
                    mDown_Y = mLastY = ev.getY();
                    mDown_X = mLastX = ev.getX();
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                case MotionEvent.ACTION_POINTER_UP:
                    // 过滤多点触碰
                    mEvents = -1;
                    break;
                case MotionEvent.ACTION_MOVE:
                    if (mEvents == 0) {
                        mMove_X = Math.abs(ev.getX() - mDown_X);
                        mMove_Y = Math.abs(ev.getY() - mDown_Y);
                        mPullY = mPullY + (ev.getY() - mLastY) / mRadio;
                        // pull down
                        if (((IPullable) mPullableView).canPullDown() && mPullY > 0 && mCurrentState != LOADING
                                && (mMove_Y > mMove_X)) {
                            // 如果当前是处于刷新，下拉时状态不变
                            if (mCurrentState != REFRESHING) {
                                if (mPullY >= mRefreshDist) {
                                    changeState(RELEASE_TO_REFRESH);
                                    if (!animationIsGoing) {
                                        animationIsGoing = true;
                                        mRefreshingArrow.startAnimation(anticlockwiseRotateAnimation);
                                    }
                                } else {
                                    changeState(INIT);
                                    if (animationIsGoing) {
                                        animationIsGoing = false;
                                        mRefreshingArrow.startAnimation(clockwiseRotateAnimation);
                                    }
                                }
                            }
                        }
                        // pull up
                        else if (((IPullable) mPullableView).canPullUp() && mPullY < 0 && mCurrentState != REFRESHING
                                && (mMove_Y > mMove_X)) {
                            if (isLoadOver) {
                                changeState(LOAD_OVER);
                            }
                            // 如果是over和正在加载状态，上拉时状态不变
                            if (mCurrentState != LOAD_OVER && mCurrentState != LOADING) {
                                // original version start
                                if (Math.abs(mPullY) >= mLoadmoreDist) {
                                    changeState(RELEASE_TO_LOAD);
                                    if (!animationIsGoing) {
                                        animationIsGoing = true;
                                        mLoadingArrow.startAnimation(anticlockwiseRotateAnimation);
                                    }

                                } else {
                                    changeState(INIT);
                                    if (animationIsGoing) {
                                        animationIsGoing = false;
                                        mLoadingArrow.startAnimation(clockwiseRotateAnimation);
                                    }
                                }
                                // original version end
                            }
                        }
                        // 既不是上拉又不是下拉
                        else {
                            // 防止触摸关掉头部或者脚部
                            if ((isVisiblity(mRefreshView) || isVisiblity(mLoadmoreView))
                                    && Math.abs(ev.getY() - mLastY) < 4) {
                            } else {
                                // 关掉头部脚部
                                if (0 != mPullY) {
                                    // 滚动中不能出现头和脚
                                    mPullY = 0;
                                    requestLayout();
                                }
                            }
                        }
                    } else {
                        mEvents = 0;
                    }

                    if (Math.abs(mPullY) > 4) {
                        // 防止下拉过程中误触发长按事件
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                        // 防止水平滑动中触发刷新事件
                        if (Math.abs(ev.getX() - mLastX) > Math.abs(mPullY)) {
                            mPullY = 0;
                        }
                        requestLayout();
                    }
                    mLastY = ev.getY();
                    mLastX = ev.getX();

                    break;
                case MotionEvent.ACTION_UP:
                    isTouch = false;
                    // 防止下拉过程中误触发点击事件
                    if (Math.abs(mPullY) > 4) {
                        ev.setAction(MotionEvent.ACTION_CANCEL);
                    }
                    // 下拉释放
                    if (mPullY > 0) {
                        // 如果正在刷新中，触摸屏幕不想刷新头被关掉，使用mPullY >= mRefreshDist
                        if (mPullY >= mRefreshDist) {
                            // mPullY = mRefreshDist;
                            postYDecrease((int) mPullY, mRefreshDist);
                            // 刷新状态下拉,不再触发刷新
                            if (mCurrentState != REFRESHING) {
                                changeState(REFRESHING);
                                if (null != this.mOnRefreshListener) {
                                    this.mOnRefreshListener.onRefreshing(this);
                                }
                            }
                        } else {
                            // mPullY = 0;
                            postYDecrease((int) mPullY, 0);
                            if (mCurrentState != REFRESHING)
                                changeState(INIT);
                        }
                        // requestLayout();
                    }
                    // 上拉释放
                    else if (mPullY < 0) {
                        if (mCurrentState != LOAD_OVER) {
                            // 如果正在加载中，触摸屏幕不想加载脚被关掉，使用Math.abs(mPullY) >=
                            // mRefreshDist
                            // original version start
                            if (Math.abs(mPullY) >= mLoadmoreDist) {
                                // mPullY = -mLoadmoreDist;
                                postYDecrease((int) mPullY, -mLoadmoreDist);
                                // 加载状态，不再触发加载
                                if (mCurrentState != LOADING) {
                                    changeState(LOADING);
                                    if (null != this.mOnRefreshListener) {
                                        this.mOnRefreshListener.onLoadding(this);
                                    }
                                }
                            } else {
                                // mPullY = 0;
                                postYDecrease((int) mPullY, 0);
                                if (mCurrentState != LOADING)
                                    changeState(INIT);
                            }
                            // requestLayout();
                            // original version end
                        } else {
                            // mPullY = 0;
                            // requestLayout();
                            postYDecrease((int) mPullY, 0);
                        }
                    }
                    break;
                default:
                    break;
            }
            // 根据下拉距离改变比例
            mRadio = (float) (2 + 2 * Math.tan(Math.PI / 2 / getMeasuredHeight() * Math.abs(mPullY)));
            super.dispatchTouchEvent(ev);
            return true;
        } catch (Exception e) {
            super.dispatchTouchEvent(ev);
            return true;
        }
    }

    /**
     * 改变状态
     */
    private void changeState(int state) {
        try {
            mCurrentState = state;
            switch (state) {
                case INIT:
                    mRefreshingState.setText("下拉刷新...");
                    mRefreshingBar.setVisibility(View.GONE);
                    mRefreshingArrow.setImageResource(R.drawable.libbase_pull_down);
                    mRefreshingArrow.setVisibility(View.VISIBLE);


                    mLoadingState.setVisibility(View.VISIBLE);
                    mLoadingState.setText("上拉加载...");
                    mLoadingBar.setVisibility(View.GONE);
                    mLoadingArrow.setImageResource(R.drawable.libbase_pull_up);
                    mLoadingArrow.setVisibility(View.VISIBLE);
                    mIsOver.setVisibility(View.GONE);
                    break;
                case RELEASE_TO_REFRESH:
                    mRefreshingState.setText("释放刷新...");
                    mRefreshingBar.setVisibility(View.GONE);
                    mRefreshingArrow.setVisibility(View.VISIBLE);
                    break;
                case REFRESHING:
                    mRefreshingState.setText("刷新中...");
                    mRefreshingBar.setVisibility(View.VISIBLE);
                    mRefreshingArrow.setVisibility(View.GONE);
                    mRefreshingArrow.clearAnimation();

                    break;
                case RELEASE_TO_LOAD:
                    if (null != mRefreshingState)
                        mLoadingState.setText("释放加载...");
                    mLoadingBar.setVisibility(View.GONE);
                    mLoadingArrow.setVisibility(View.VISIBLE);
                    break;
                case LOADING:
                    if (null != mRefreshingState)
                        mLoadingState.setText("正在加载中O(∩_∩)O~~");
                    mLoadingBar.setVisibility(View.VISIBLE);
                    mLoadingArrow.setVisibility(View.GONE);
                    mLoadingArrow.clearAnimation();
                    break;
                case LOAD_OVER:// 上拉加载，没有更多时显示
                    mLoadingBar.setVisibility(View.GONE);
                    mLoadingArrow.setVisibility(View.GONE);
                    if (null != mRefreshingState)
                        mLoadingState.setVisibility(View.GONE);
                    mIsOver.setVisibility(View.VISIBLE);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新完成，关掉刷新头部
     *
     * @param result SUCCEED or FAIL
     * @param isOver 返回true时，上拉不再加载更多了；返回false，上拉加载更多
     */
    public void refreshFinished(int result, final Boolean isOver) {
        try {
            if (null != mRefreshingArrow) {
                mRefreshingArrow.clearAnimation();
                mRefreshingArrow.setVisibility(View.VISIBLE);
            }
            if (null != mRefreshingBar)
                mRefreshingBar.setVisibility(View.GONE);

            switch (result) {
                case SUCCEED:
                    if (null != mRefreshingArrow)
                        mRefreshingArrow.setImageResource(R.drawable.libbase_pull_success);
                    if (null != mRefreshingState)
                        mRefreshingState.setText("刷新成功");
                    break;
                case FAIL:
                    if (null != mRefreshingArrow)
                        mRefreshingArrow.setImageResource(R.drawable.libbase_pull_fail);
                    if (null != mRefreshingState)
                        mRefreshingState.setText("刷新失败");
                    break;
            }
            postDelayed(new Runnable() {

                @Override
                public void run() {
                    done(isOver);
                }
            }, delayMillis);
            // mHandler.sendMessageDelayed(mHandler.obtainMessage(0, isOver),
            // delayMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void refreshFinished(int result) {
        refreshFinished(result, null);
    }

    /**
     * @param result
     * @param isOver true: There isn't any more .
     */
    public void loadFinihsed(int result, final Boolean isOver) {
        try {
            if (null != mLoadingArrow) {
                mLoadingArrow.clearAnimation();
                mLoadingArrow.setVisibility(View.VISIBLE);
            }
            if (null != mLoadingBar)
                mLoadingBar.setVisibility(View.GONE);

            switch (result) {
                case SUCCEED:
                    if (null != mLoadingArrow)
                        mLoadingArrow.setImageResource(R.drawable.libbase_pull_success);
                    if (null != mLoadingState)
                        mLoadingState.setText("加载成功");
                    break;
                case FAIL:
                    if (null != mLoadingArrow)
                        mLoadingArrow.setImageResource(R.drawable.libbase_pull_fail);
                    if (null != mLoadingState)
                        mLoadingState.setText("加载失败");
                    break;
            }

            postDelayed(new Runnable() {

                @Override
                public void run() {
                    done(isOver);
                }
            }, delayMillis);
            // mHandler.sendMessageDelayed(mHandler.obtainMessage(0, isOver),
            // delayMillis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void done(final Boolean isOver) {
        changeState(DONE);
        // mPullY = 0;
        // requestLayout();
        // 恢复上拉加载
        isLoadOver = false;

        if (null != isOver && isOver) {
            isLoadOver = true;
        }

        if (mPullY != 0) {
            // 隐藏脚后，再修改状态
            postYDecrease((int) mPullY, 0, new OnDecreaseZeroListener() {

                @Override
                public void onDecreaseZero() {
                    if (isLoadOver) {
                        changeState(LOAD_OVER);
                    }
                }
            });
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(final android.os.Message msg) {
            changeState(DONE);
            // mPullY = 0;
            // requestLayout();
            // 恢复上拉加载
            isLoadOver = false;

            if (null != msg.obj && (Boolean) msg.obj) {
                isLoadOver = true;
            }
            if (mPullY != 0) {
                // 隐藏脚后，再修改状态
                postYDecrease((int) mPullY, 0, new OnDecreaseZeroListener() {

                    @Override
                    public void onDecreaseZero() {
                        if (null != msg.obj && (Boolean) msg.obj) {
                            changeState(LOAD_OVER);
                        }
                    }
                });
            }

        }

        ;
    };

    public void loadOver(boolean isOver) {
        isLoadOver = isOver;
        if (isOver) {
            changeState(LOAD_OVER);
        } else {
            changeState(INIT);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        try {
            if (isFirstOnLayout) {
                isFirstOnLayout = false;
                mRefreshView = getChildAt(0);
                mPullableView = getChildAt(1);
                mLoadmoreView = getChildAt(2);
                mRefreshDist = ((ViewGroup) mRefreshView).getChildAt(0).getMeasuredHeight();
                mLoadmoreDist = ((ViewGroup) mLoadmoreView).getChildAt(0).getMeasuredHeight();
                initRefreshingView();
                initLoadingView();
                if (this.isAutoRefreshing) {
                    // mPullY = mRefreshDist;
                    // postRun(0, mRefreshDist);
                    postYIncrease(0, mRefreshDist);
                    changeState(REFRESHING);
                }
            }
            mRefreshView.layout(0, (int) (mPullY - mRefreshView.getMeasuredHeight()), mRefreshView.getMeasuredWidth(),
                    (int) mPullY);

            mPullableView.layout(0, (int) mPullY, mPullableView.getMeasuredWidth(),
                    (int) (mPullY + mPullableView.getMeasuredHeight()));

            mLoadmoreView.layout(0, (int) (mPullY + mPullableView.getMeasuredHeight()),
                    mLoadmoreView.getMeasuredWidth(),
                    (int) (mPullY + mPullableView.getMeasuredHeight() + mLoadmoreView.getMeasuredHeight()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRefreshingView() {
        mRefreshingArrow = (ImageView) mRefreshView.findViewById(R.id.iv_refreshing_arrow);
        mRefreshingBar = (ProgressBar) mRefreshView.findViewById(R.id.pb_refreshing_bar);
        mRefreshingState = (TextView) mRefreshView.findViewById(R.id.tv_refreshing_state);
    }

    private void initLoadingView() {
        mLoadingArrow = (ImageView) mLoadmoreView.findViewById(R.id.iv_loading_arrow);
        mLoadingBar = (ProgressBar) mLoadmoreView.findViewById(R.id.pb_loading_bar);
        mLoadingState = (TextView) mLoadmoreView.findViewById(R.id.tv_loading_state);
        mIsOver = (TextView) mLoadmoreView.findViewById(R.id.tv_loading_over);
    }

    /**
     * 自动刷新
     *
     * @param autoRefreshing 为true时，进入界面显示刷新头部。
     */
    public void setRefreshing(boolean autoRefreshing) {
        this.isAutoRefreshing = autoRefreshing;
    }

    public void hiddenHeader() {
        if (Math.abs(this.mPullY) == mRefreshDist) {
            this.mPullY = 0;
            requestLayout();
        }
    }

    public void hiddenFooter() {
        if (Math.abs(this.mPullY) == mLoadmoreDist) {
            this.mPullY = 0;
            requestLayout();
        }
    }

    private class YScrollDetector extends SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (distanceY != 0 && distanceX != 0) {

            }
            if (Math.abs(distanceY) >= Math.abs(distanceX)) {
                return true;
            }
            return false;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    private static boolean isVisiblity(View view) {
        boolean isVisiblity = view
                .getGlobalVisibleRect(new Rect(view.getLeft(), view.getTop(), view.getRight(), view.getBottom()));
        return isVisiblity;
    }

    /**
     * 回弹距离为0回调接口
     */
    private static interface OnDecreaseZeroListener {
        void onDecreaseZero();
    }

    /**
     * currentY 减至 targetY
     *
     * @param currentY 当前pulldown 或者 pullup值
     * @param targetY  目标值
     * @param listener pull 值减为0回调接口
     */
    private void postYDecrease(int currentY, int targetY, OnDecreaseZeroListener listener) {
        mYRunnable = new YDecreaseRunnable(currentY, targetY, listener);
        post(mYRunnable);
    }

    /**
     * currentY 减至 targetY
     *
     * @param currentY 当前pulldown 或者 pullup值
     * @param targetY  目标值
     */
    private void postYDecrease(int currentY, int targetY) {
        mYRunnable = new YDecreaseRunnable(currentY, targetY, null);
        post(mYRunnable);
    }

    /**
     * currentY 增加到 targetY
     *
     * @param currentY 当前pulldown 或者 pullup值
     * @param targetY  目标值
     */
    private void postYIncrease(int currentY, int targetY) {
        mYRunnable = new YIncreaseRunnable(currentY, targetY);
        post(mYRunnable);
    }

    private void removeRun() {
        removeCallbacks(mYRunnable);
    }

    private class YDecreaseRunnable implements Runnable {

        private final Interpolator mInterpolator;
        private int mTargetY;
        private int mCurrentY;
        private long mStartTime = -1;
        OnDecreaseZeroListener mListener;

        public YDecreaseRunnable(int currentY, int targetY, OnDecreaseZeroListener listener) {
            this.mCurrentY = currentY;
            this.mTargetY = targetY;
            this.mInterpolator = new DecelerateInterpolator();
            this.mListener = listener;
        }

        @Override
        public void run() {
            synchronized (lock) {
                if (mStartTime == -1) {
                    mStartTime = System.currentTimeMillis();
                } else {
                    long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / 400;
                    normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
                    final int delta = Math
                            .round((mCurrentY - mTargetY) * mInterpolator.getInterpolation(normalizedTime / 1000f));

                    mCurrentY -= delta;

                    if (mCurrentY > 0) {
                        if (mPullY > mCurrentY) {
                            mPullY = mCurrentY;
                        }
                    } else if (mCurrentY < 0) {
                        if (mPullY <= mCurrentY) {
                            mPullY = mCurrentY;
                        }
                    } else {
                        mPullY = 0;
                    }

                    requestLayout();
                    mCurrentY = (int) mPullY;
                }

                if (mPullY == 0) {
                    removeCallbacks(this);
                    if (null != mOnClosedListener) {
                        mOnClosedListener.onClosed();
                    }
                    if (null != this.mListener) {
                        this.mListener.onDecreaseZero();
                    }
                } else {
                    if (mCurrentY != mTargetY) {
                        postDelayed(this, 16);
                    }
                }
            }
        }
    }

    private class YIncreaseRunnable implements Runnable {

        private final Interpolator mInterpolator;
        private int targetY;
        private int currentY;
        private long mStartTime = -1;

        public YIncreaseRunnable(int currentY, int targetY) {
            this.currentY = currentY;
            this.targetY = targetY;
            mInterpolator = new DecelerateInterpolator();
        }

        @Override
        public void run() {
            synchronized (lock) {
                if (mStartTime == -1) {
                    mStartTime = System.currentTimeMillis();
                } else {
                    long normalizedTime = (1000 * (System.currentTimeMillis() - mStartTime)) / 400;
                    normalizedTime = Math.max(Math.min(normalizedTime, 1000), 0);
                    final int delta = Math
                            .round((targetY - currentY) * mInterpolator.getInterpolation(normalizedTime / 1000f));
                    currentY = currentY + delta;
                    if (currentY <= targetY) {
                        mPullY = currentY;
                        requestLayout();
                    }
                }
                if (currentY != targetY) {
                    postDelayed(this, 16);
                }
                if (mPullY >= targetY) {
                    removeCallbacks(this);
                }
            }
        }
    }
}
