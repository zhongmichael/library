package com.chinaredstar.core.view.pulltorefresh.t2;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.chinaredstar.core.view.ObservableScrollView;


public class IPullableFrameLayout extends FrameLayout implements IPullable {
	private boolean isCanPullDown = true;
	private boolean isCanPullUp = true;

	public IPullableFrameLayout(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public IPullableFrameLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public IPullableFrameLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canPullUp() {
		// TODO Auto-generated method stub
		if (!isCanPullUp)
			return isCanPullUp;
		if (null != getChildAt(0) && getChildAt(0) instanceof ObservableScrollView) {
			ObservableScrollView mObservableScrollView = (ObservableScrollView) getChildAt(0);
			return mObservableScrollView.is2Bottom();
		}
		return isCanPullUp;
	}

	@Override
	public boolean canPullDown() {
		// TODO Auto-generated method stub
		return isCanPullDown;
	}

	public void setCanPullDown(boolean isCanPullDown) {
		this.isCanPullDown = isCanPullDown;
	}

	public void setCanPullUp(boolean isCanPullUp) {
		this.isCanPullUp = isCanPullUp;
	}
}
