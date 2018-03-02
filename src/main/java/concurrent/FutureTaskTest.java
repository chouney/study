import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * FutureTask demo
 * ConcurrentHashMap作为task管理容器，管理执行的FutureTask以及结果。
 *
 * Created by manatea on 2017/4/21.
 */
public class FutureTaskTest {
    //存储Task集合
    private ConcurrentHashMap<String,FutureTask<String>> map = new ConcurrentHashMap<String, FutureTask<String>>();

    private String executeTask(final String taskName){
        while(true){
            FutureTask<String> futureTask = map.get(taskName);
            //如果没有Task则创建新的Task
            if(futureTask == null){
                Callable<String> callable = new Callable<String>() {
                    public String call() throws Exception {
                        Thread.sleep(1000);
                        return taskName;
                    }
                };
                //利用Concurrent解决并发冲突，同时查询该Task是否已经执行
                futureTask = new FutureTask<String>(callable);
                FutureTask<String> preTask = map.putIfAbsent(taskName,futureTask);
                if(preTask == null){
                    System.out.println("第一次添加该TaskName,将要执行Task");
                    futureTask.run();
                }else{
                    System.out.println("第二次添加该TaskName,直接获取task结果");
                    futureTask = map.get(taskName);
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
    public static void main(String[] args) throws InterruptedException {
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

        Scanner in = new Scanner(System.in);
        while(in.hasNextInt());

    }

}
