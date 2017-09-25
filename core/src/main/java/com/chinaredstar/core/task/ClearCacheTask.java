package com.chinaredstar.core.task;

import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskResult;
import com.chinaredstar.core.utils.FileUtil;
import com.chinaredstar.core.utils.PathUtil;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class ClearCacheTask extends ITask {
    private String[] paths;

    public ClearCacheTask(int id) {
        super(id);
    }

    public ClearCacheTask(int id, String[] paths) {
        super(id);
        this.paths = paths;
    }

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        result.isSuccess = true;
        if (null != paths && paths.length != 0) {
            for (String path : paths) {
                result.isSuccess = result.isSuccess && FileUtil.delete(path);
            }
        } else {
            result.isSuccess = FileUtil.delete(PathUtil.getAppCacheDir().getAbsolutePath());
        }
        return result;
    }
}
