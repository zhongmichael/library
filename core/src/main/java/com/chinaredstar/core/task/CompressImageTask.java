package com.chinaredstar.core.task;

import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskResult;
import com.chinaredstar.core.utils.ImageCompressUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class CompressImageTask extends ITask {
    private String[] filePaths;

    private CompressImageTask(int id) {
        super(id);
    }

    public CompressImageTask(int taskId, String... filePaths) {
        super(taskId);
        this.filePaths = filePaths;
    }

    @Override
    public TaskResult doTask() {
        List<String> outPaths = new ArrayList<>();
        TaskResult result = new TaskResult();
        result.obj = outPaths;
        try {
            if (null != filePaths && filePaths.length > 0) {
                for (String path : filePaths) {
                    outPaths.add(ImageCompressUtil.compressImage(path));
                }
            }
            result.isSuccess = true;
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.isSuccess = false;
        }
        return result;
    }
}
