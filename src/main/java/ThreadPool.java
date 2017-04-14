/**
 * Created by manatea on 2017/3/8.
 */
public interface ThreadPool {
    //执行一个任务，线程任务
    void execute(Runnable runnable);
    void shutdown();
    void addWorkers(int num);
    void removeWorkers(int num);
    int getJobSize();
}
