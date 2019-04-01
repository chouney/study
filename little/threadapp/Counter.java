// Copyright (C) 2018 Meituan
// All rights reserved
package pearls.little.threadapp;


/**
 * 计数器,用于统计，验证little定律
 * @author manatea
 * @version 1.0
 * @created 2018/2/28 PM5:57
 **/
public class Counter extends Thread{

    //时间戳,用于统计
    private long timeStamp = System.currentTimeMillis();

    private WebServer webServer;

    private UserSimulator userSimulator;

    public Counter(WebServer webServer,UserSimulator userSimulator){
        super("manatea-counter-thread");
        this.webServer = webServer;
        this.userSimulator = userSimulator;
    }

    @Override
    public void run() {
        while(true) {
            if (System.currentTimeMillis() - timeStamp > 1000) {
                timeStamp = System.currentTimeMillis();
                System.out.println("系统输入速率为" + UserSimulator.getRequestCount() + "个每秒");
                System.out.println("系统输出速率为" + Request.getResponseCount() + "个每秒");
                System.out.println("系统平均处理时间为" + Request.getAverageHandleTime() + "毫秒");
                System.out.println("正在处理的线程数有" + webServer.getThreadCount() + "个");
                Request.resetResponseCount();
                UserSimulator.resetRequestCount();
            }
        }
    }

}