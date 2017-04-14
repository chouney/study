package sq;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by manatea on 2017/3/14.
 */
public class Consumer implements Runnable {
    private static int count = 0;
    private final int id = count++;
    private ProductBuffer productBuffer;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;


    public Consumer(ProductBuffer productBuffer, ReentrantLock lock, Condition notFull, Condition notEmpty) {
        this.productBuffer = productBuffer;
        this.lock = lock;
        this.notFull = notFull;
        this.notEmpty = notEmpty;
    }


    public void run() {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                while (productBuffer.isEmpty()) {
                    notEmpty.await();
//                        productBuffer.wait();
                }
                Product product = productBuffer.poll();
                System.out.println("Thread id : " + Thread.currentThread().getId() + " Consumer id :" + product.getId() + " is consume product");
                product.consumeProduct();
                notFull.signalAll();
//                    productBuffer.notifyAll();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
