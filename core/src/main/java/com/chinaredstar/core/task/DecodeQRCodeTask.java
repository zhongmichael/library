package com.chinaredstar.core.task;

import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskResult;

import cn.bingoogolapple.qrcode.zxing.QRCodeDecoder;

/**
 * Created by hairui.xiang on 2017/9/14.
 * 解析二维码图片
 */

public class DecodeQRCodeTask extends ITask {
    private String qrPath;

    public DecodeQRCodeTask(int id, String qrPath) {
        super(id);
        this.qrPath = qrPath;
    }

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        result.isSuccess = true;
        result.obj = QRCodeDecoder.syncDecodeQRCode(qrPath);
        return result;
    }
}
