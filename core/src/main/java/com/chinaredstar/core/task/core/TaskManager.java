package com.chinaredstar.core.task.core;

/**
 * Created by hairui.xiang on 2017/9/13.
 */

public class TaskManager {
    private volatile static TaskManager mManager = null;
    private TaskExecutor mTaskExecutor;

    private TaskManager() {
        mTaskExecutor = TaskExecutor.newExecutor();
    }

    public static TaskManager getInstance() {
        if (null == mManager) {
            synchronized (TaskManager.class) {
                if (null == mManager) {
                    mManager = new TaskManager();
                }
            }
        }
        return mManager;
    }

    public void excute(ITask task) {
        mTaskExecutor.submit(new TaskRunnable(task));
    }
}
