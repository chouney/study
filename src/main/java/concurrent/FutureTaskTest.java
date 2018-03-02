package concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask demo
 * Created by manatea on 2017/4/21.
 */
public class FutureTaskTest {
    //存储Task
    private ConcurrentHashMap<String,FutureTask<String>> map = new ConcurrentHashMap<String, FutureTask<String>>();

    private String executeTask(final String taskName){
        while(true){
            FutureTask<String> futureTask = map.get(taskName);
            if(futureTask == null){
                Callable<String> callable = new Callable<String>() {
                    public String call() throws Exception {
                        Thread.sleep(5000);
                        return taskName;
                    }
                };
                FutureTask<String> futureTask1 = new FutureTask<String>(callable);
                futureTask = map.putIfAbsent(taskName,futureTask1);
                if(futureTask == null){
                    System.out.println("1111");
                    futureTask = futureTask1;
                    futureTask.run();
                }
            }
            //等待Task结果返回
            try {
                return futureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args){
        final FutureTaskTest f = new FutureTaskTest();
        Runnable r = new Runnable() {
            public void run() {
                System.out.println(f.executeTask("task1"));
            }
        };
        Runnable r1 = new Runnable() {
            public void run() {
                System.out.println(f.executeTask("task1"));
            }
        };
        new Thread(r).start();
        new Thread(r1).start();
    }

}
