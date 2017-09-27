package com.chinaredstar.core.view.pulltorefresh.t2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class IPullableGridView extends GridView implements IPullable {
	private boolean isCanPulldown = false;
	private boolean isCanPullup = false;

	public IPullableGridView(Context context) {
		super(context);
	}

	public IPullableGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public IPullableGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown() {
		try {
			if (!isCanPulldown)
				return false;
			if (getCount() == 0) {
				// 没有item的时候也可以下拉刷新
				return true;
			} else if (getFirstVisiblePosition() == 0
					&& getChildAt(0).getTop() >= 0) {
				// 滑到顶部了
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean canPullUp() {
		try {
			if (!isCanPullup)
				return false;
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
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public void setCanPulldown(boolean isCanPulldown) {
		this.isCanPulldown = isCanPulldown;
	}

	public void setCanPullup(boolean isCanPullup) {
		this.isCanPullup = isCanPullup;
	}

}
