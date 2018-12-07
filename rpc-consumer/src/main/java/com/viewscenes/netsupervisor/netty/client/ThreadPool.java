package com.viewscenes.netsupervisor.netty.client;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by MACHENIKE on 2018-12-03.
 */
public class ThreadPool {

    private static final ThreadPool pool = new ThreadPool();

    public static ThreadPool newInstance(){
        return pool;
    }
    private static final ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(5);

    /**
     * 执行定时任务的线程池
     * @return
     */
    public ScheduledExecutorService scheduledPool(){
        return scheduledPool;
    }
}
