package practice;

import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by chriszhang on 2017/10/21.
 */
public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Executor executor = Executors.newFixedThreadPool(1);
       while(true) {
           Thread.currentThread().sleep(1000);
           executor.execute(() -> {
               ThreadLocal local = new ThreadLocal();
               System.out.println(local.get());
               local.set("ddd");
           });
       }

    }
}
