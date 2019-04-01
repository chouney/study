// Copyright (C) 2018 Meituan
// All rights reserved
package pearls.little.threadapp;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 简陋的模拟服务器配置
 * @author manatea
 * @version 1.0
 * @created 2018/2/28 PM4:46
 **/
public class WebServer {

    private ThreadPoolExecutor executor = null;


    public WebServer(int nThreads) {
        executor = new ThreadPoolExecutor(nThreads, nThreads,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),
                new ThreadFactory() {
                    private int count = 0;
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r,"manatea-"+count++);
                    }
                });
    }


    private void execute(Runnable command) {
        executor.execute(command);
    }

    /**
     * 处理请求
     * @param request
     */
    public void handleRequest(Request request){
        execute(request);
    }

    public int getThreadCount(){
        return executor.getActiveCount();
    }
}