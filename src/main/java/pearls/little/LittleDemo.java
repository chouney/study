// Copyright (C) 2018 Meituan
// All rights reserved
package little;

import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * little定律,系统当前容量=系统输出速率x系统的处理时间
 * (当系统的输入输出流平衡后，同样可以得出系统的输入速率=系统的输出速率)
 *
 * 所以最终我们需要知道的是：系统的输出速率 = 系统当前容量/系统的处理时间
 *
 * 具体应用到实际的线程池配置来说，QPS = 线程池的容量/Query的平均处理时长
 * 当然，简单的计算应当加上安全系数来补偿参数估计时出现的误差（根据阿姆达尔定律,误差取决于你的串行代码占比）
 * @author manatea
 * @version 1.0
 * @created 2018/2/27 PM12:03
 **/
public class LittleDemo {

    /**
     * 处理系统
     * @param <T>
     */
    private class System<T>{
        private BlockingQueue<FutureTask<T>> queue = new LinkedBlockingQueue<>();

        //终结开关
        private volatile int flag = -1;

        //输出量计数器
        private volatile long counter = 0;

        private volatile long time = 0L;

        public void pushTask(FutureTask<T> futureTask) {
            try {
                queue.put(futureTask);
            } catch (InterruptedException e) {
                java.lang.System.out.println("pushTask has interrupted");

            }
        }

        public void run() {
            new Thread(()->{
                time = java.lang.System.currentTimeMillis();
                do{
                    FutureTask task = null;
                    try {
                        task = queue.take();
                        task.run();
                        counter+=(Integer)task.get();

                        if(java.lang.System.currentTimeMillis()-time>=1000){
                            time=java.lang.System.currentTimeMillis();
                            java.lang.System.out.println("系统的输出速率为"+counter+"个每秒");
                            java.lang.System.out.println("系统的当前容量为"+queue.size()+"个");
                            counter = 0;
                        }

                    } catch (InterruptedException e) {
                        java.lang.System.out.println("SystemRunRask has interrupted,will execute the rest task");
                    } catch (ExecutionException e) {
                        java.lang.System.out.println("SystemRunRask has executionError,will execute the rest task");
                    }

                }while(!queue.isEmpty() || flag==-1);
            }).start();
        }

        public void shutdown(){
            flag=0;
        }

    }

    public static void main(String[] args) {
        LittleDemo demo = new LittleDemo();
        System<Integer> system = demo.new System<>();
        Scanner in = new Scanner(java.lang.System.in);
        system.run();
        /**
         * 输入线程
         */
        Thread pusher = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()) {
                system.pushTask(new FutureTask<>(()->{
                    //系统的处理时间设置成了50ms
                    Thread.currentThread().sleep(50L);
                    return 1;
                }));
                try {
                    //输入速率与输出速率保持一致
                    Thread.currentThread().sleep(50L);
                } catch (InterruptedException e) {
                    java.lang.System.out.println("pushThread has interrupted,exit");
                }

            }
        });
        //启动输入线程
        pusher.start();

        while(in.hasNextInt()){
            if(in.nextInt()==1){
                //输送停止
                pusher.interrupt();
                system.shutdown();
                break;
            }
        }
    }
}