// Copyright (C) 2018 Meituan
// All rights reserved
package pearls.little.threadapp;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程运行任务,自定义处理时间
 * @author manatea
 * @version 1.0
 * @created 2018/2/28 PM4:50
 **/
public class Request implements Runnable {

    //每秒结果数量,用于统计
    private static volatile AtomicInteger responseCount = new AtomicInteger(0);

    //总处理时间
    private static long totalHandleTime = 0;

    //总请求数
    private static long totalRequestCount = 0;

    //处理时间(ms)
    private int handleTime ;

    public Request(int handleTime) {
        this.handleTime = handleTime;
        totalHandleTime+=handleTime;
        totalRequestCount++;
    }

    @Override
    public void run() {
        try {
            Thread.currentThread().sleep(handleTime);
        } catch (InterruptedException e) {
            System.out.println("task has interrupted");
            return;
        }
        //统计
        responseCount.incrementAndGet();
    }

    public static int getResponseCount(){
        return responseCount.intValue();
    }

    public static void resetResponseCount(){
        responseCount.set(0);
    }

    /**
     * 返回平均处理时间
     * @return
     */
    public static double getAverageHandleTime(){
        return 1.0*totalHandleTime/totalRequestCount;
    }
}