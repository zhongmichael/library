package com.chinaredstar.core.task.core;

import com.chinaredstar.core.utils.LogUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by hairui.xiang on 2017/9/13.
 */

public class TaskManager {
    private volatile static TaskManager mManager = null;
    private ExecutorService mTaskExecutor;

    private TaskManager() {
        int threadCount = TheadCountCalculator.calculateBestThreadCount();
        LogUtil.d("calculateBestThreadCount: " + threadCount);
        mTaskExecutor = Executors.newFixedThreadPool(threadCount);
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
