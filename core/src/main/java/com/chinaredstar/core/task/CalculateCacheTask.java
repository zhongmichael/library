package com.chinaredstar.core.task;

import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskResult;
import com.chinaredstar.core.utils.FileSizeCalculateUtil;
import com.chinaredstar.core.utils.PathUtil;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class CalculateCacheTask extends ITask {
    private String[] paths;

    public CalculateCacheTask(int id) {
        super(id);
    }

    public CalculateCacheTask(int id, String... paths) {
        super(id);
        this.paths = paths;
    }

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        try {
            double size = 0;
            if (null != paths && paths.length != 0) {
                for (String path : paths) {
                    size += FileSizeCalculateUtil.calculateFileSize(path, FileSizeCalculateUtil.UNIT_MB);
                }
            } else {
                size = FileSizeCalculateUtil.calculateFileSize(PathUtil.getAppCacheDir().toString(), FileSizeCalculateUtil.UNIT_MB);
            }
            result.obj = size + "MB";
            result.isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
            result.obj = 0 + "MB";
            result.isSuccess = false;
        }
        return result;
    }
}
