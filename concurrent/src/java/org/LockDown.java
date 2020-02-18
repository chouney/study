package org;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 锁降级demo
 * Created by manatea on 2017/3/28.
 */
public class LockDown implements Runnable{
    private ReadWriteLock lock ;
    private static int mod = 1;
    private static AtomicBoolean update= new AtomicBoolean(false);
    private Lock readLock;
    private Lock writeLock;
    public LockDown(ReadWriteLock lock) {
        this.lock = lock;
        this.readLock  = lock.readLock();
        this.writeLock = lock.writeLock();

    }

    public void run() {
        while(!Thread.interrupted()){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            readLock.lock();
            if(!update.get()){
                //进行更新，获取写锁前必须先释放读锁
                readLock.unlock();

                //锁降级开始
                writeLock.lock();
                try {
                    //双重检查，防止多次更新
                    if(update.compareAndSet(false,true)) {
                        mod++;
                    }
                    readLock.lock();
                }finally{
                    writeLock.unlock();
                }
                //锁降级完成

            }
            try{
                System.out.println(mod);
            }finally {
                readLock.unlock();
            }
        }
    }

    public static void main(String[] args){
        ExecutorService ser = Executors.newCachedThreadPool();
        ReentrantReadWriteLock writeLock = new ReentrantReadWriteLock();
        ser.execute(new LockDown(writeLock));
        ser.execute(new LockDown(writeLock));
        ser.execute(new LockDown(writeLock));
        ser.execute(new LockDown(writeLock));
        ser.execute(new LockDown(writeLock));
    }
}
