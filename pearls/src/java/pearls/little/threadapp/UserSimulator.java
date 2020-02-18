// Copyright (C) 2018 Meituan
// All rights reserved
package pearls.little.threadapp;

import java.util.Scanner;

/**
 * 用户模拟器(压测模拟器)
 * 模拟用户请求服务器的速率
 * @author manatea
 * @version 1.0
 * @created 2018/2/28 PM5:03
 **/
public class UserSimulator extends Thread{

    //请求频率ms(调整请求频率来进行压测)
    private int requestRate;

    private boolean shutdown = false;

    //每秒请求数量,用于统计
    private static volatile int requestCount = 0;

    private WebServer webServer;

    private int handleTime;


    public UserSimulator(WebServer webServer, int handleTime, int requestRate) {
        super("manatea-user-thread");
        this.requestRate = requestRate;
        this.webServer = webServer;
        this.handleTime = handleTime;
    }

    /**
     * 模拟用户发送请求
     * @param webServer
     * @param handleTime 输出速率,这里使用系统的处理时间代替
     */
    public void simulateRequest(WebServer webServer,int handleTime){
        while(!shutdown){
            try {
                Thread.currentThread().sleep(requestRate);
            } catch (InterruptedException e) {
                java.lang.System.out.println("pushRequest has interrupted,exit");
                return;
            }
            requestCount++;
            webServer.handleRequest(new Request(handleTime));
        }
    }

    @Override
    public void run(){
        simulateRequest(this.webServer,handleTime);
    }


    public void shutdown(){
        shutdown = true;
    }

    public static int getRequestCount(){
        return requestCount;
    }

    public static void resetRequestCount(){
        requestCount = 0;
    }

    public static void main(String[] args){
        WebServer webServer = new WebServer(50);
        UserSimulator userSimulator = new UserSimulator(webServer,724,500);
        Counter counter = new Counter(webServer,userSimulator);
        counter.start();
        userSimulator.start();
        System.out.println("demo启动!!!");
        Scanner in = new Scanner(System.in);
        while(in.hasNextInt()){
            if(in.nextInt()==1){
                userSimulator.shutdown();
                break;
            }
        }
    }

}