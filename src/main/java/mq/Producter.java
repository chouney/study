import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者
 * 生产者实例
 * Created by manatea on 2017/3/14.
 */
public class Producter implements Runnable {
    private static int count = 0;
    private final int id = count++;
    private ProductBuffer productBuffer;
    private ReentrantLock lock;
    private Condition notFull;
    private Condition notEmpty;

    public Producter(ProductBuffer productBuffer, ReentrantLock lock, Condition notFull, Condition notEmpty) {
        this.productBuffer = productBuffer;
        this.lock = lock;
        this.notFull = notFull;
        this.notEmpty = notEmpty;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                lock.lock();
                while (productBuffer.isFull()) {
                    notFull.await();
//                        productBuffer.wait();
                }
                Product product = new Product();
                Thread.sleep(800);
                System.out.println("Thread id : " + Thread.currentThread().getId() + " Producter id :" + product.getId() + " is building product");
                productBuffer.offer(product);
//                    productBuffer.notifyAll();
                notEmpty.signalAll();
                lock.unlock();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
