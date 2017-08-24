package com.chinaredstar.demo;

import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;

import com.chinaredstar.core.base.BaseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class FrescoDemo extends BaseActivity {
    SimpleDraweeView icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.activity_fresco);
        icon = findViewById(R.id.icon);
        icon.setImageURI("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504172696&di=2ef4fb376e65802cea0c92b9532fe59d&imgtype=jpg&er=1&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F1f178a82b9014a909461e9baa1773912b31bee5e.jpg");
    }
}
