import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者模型
 * 管理器
 * Created by manatea on 2017/3/14.
 */
public class SQManager {
    Executor executor = Executors.newFixedThreadPool(20, new ThreadFactory() {
        private int i = 1;
        public Thread newThread(Runnable r) {
            return new Thread(r,"Thread-"+i++);
        }
    });
    ReentrantLock condition = new ReentrantLock();

    public void runTask(){
        Condition notFull = condition.newCondition();
        Condition notEmpty = condition.newCondition();
        ProductBuffer productBuffer = new ProductBuffer(10);
        executor.execute(new Producter(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Producter(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Consumer(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Producter(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Consumer(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Producter(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Producter(productBuffer,condition,notFull,notEmpty));
        executor.execute(new Consumer(productBuffer,condition,notFull,notEmpty));
    }

    public static void main(String[] args){
        SQManager s = new SQManager();
        s.runTask();
    }
}
