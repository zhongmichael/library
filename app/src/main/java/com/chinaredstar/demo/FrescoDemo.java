package com.chinaredstar.demo;

import android.os.Bundle;

import com.chinaredstar.core.base.BaseActivity;
import com.chinaredstar.core.fresco.ImageUtil;
import com.facebook.drawee.view.SimpleDraweeView;

/**
 * Created by hairui.xiang on 2017/8/24.
 */

public class FrescoDemo extends BaseActivity {
    SimpleDraweeView icon;
    String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1504172696&di=2ef4fb376e65802cea0c92b9532fe59d&imgtype=jpg&er=1&src=http%3A%2F%2Fc.hiphotos.baidu.com%2Fzhidao%2Fpic%2Fitem%2F1f178a82b9014a909461e9baa1773912b31bee5e.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresco);
        icon = findViewById(R.id.icon);
        icon.setImageURI(url);
        ImageUtil.savePicToAlbum(url, "flower", "this is pic");
    }
}
