package com.chinaredstar.core.view.pulltorefresh.t2;

import com.istone.activity.R;

import android.content.Context;
import android.content.res.TypedArray;
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
		// 获取当前View实例的模式(上拉 ,下拉, 上拉下拉皆可)
		int Mode = 0;
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.Pullable);
		Mode = a.getInt(R.styleable.Pullable_PullMode,
				0);
		a.recycle();
		if (Mode == 0) {
			canPullUp = true;
			canPullDown = true;
		} else if (Mode == -1) {
			canPullUp = true;
			canPullDown = false;
		} else {
			canPullUp = false;
			canPullDown = true;
		}
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
	 * TCL 自行设定是否可以下拉刷新 和 上拉加载
	 * 
	 * @param canPullDown
	 * @param canPullUp
	 */
	public void setMode(boolean canPullDown, boolean canPullUp) {
		this.canPullDown = canPullDown;
		this.canPullUp = canPullUp;
	}

}
