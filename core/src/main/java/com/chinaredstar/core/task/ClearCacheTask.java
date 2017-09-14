package com.chinaredstar.core.task;

import com.chinaredstar.core.task.core.ITask;
import com.chinaredstar.core.task.core.TaskResult;
import com.chinaredstar.core.utils.FileUtil;
import com.chinaredstar.core.utils.PathUtil;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class ClearCacheTask extends ITask {
    public ClearCacheTask(int id) {
        super(id);
    }

    @Override
    public TaskResult doTask() {
        TaskResult result = new TaskResult();
        result.isSuccess = FileUtil.delete(PathUtil.getAppCacheDir().toString());
        return result;
    }
}
