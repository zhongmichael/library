package com.chinaredstar.core.task.core;

import android.os.Process;
import android.support.annotation.NonNull;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by hairui.xiang on 2017/9/14.
 */

public class TaskExecutor extends ThreadPoolExecutor {

    public static TaskExecutor newExecutor() {
        int threadCount = TheadCountCalculator.calculateBestThreadCount();
        return new TaskExecutor(threadCount, threadCount, 0, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), new TaskThreadFactory());
    }

    private TaskExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
    }

    static class TaskThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(@NonNull Runnable runnable) {
            return new TaskThead(runnable);
        }
    }

    static class TaskThead extends Thread {
        public TaskThead(Runnable target) {
            super(target);
        }

        @Override
        public void run() {
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            super.run();
        }
    }
}
