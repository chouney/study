package practice;

/**
 * 实现队列的数据结构
 * Created by chriszhang on 2017/10/21.
 */
public class CircularQueue<T> {
    private Object[] arrCirQueue = new Object[10];
    private int front = 0;
    private int tail=0;
    private int size = 0;
    //越界问题
    //如何解决offer10次后poll10后，你还能offer吗？
    /**
     * 出队列操作
     * @param <T>
     * @return
     */
    public <T> T poll(){
        T t = (T) arrCirQueue[front];
        //arrCirQueue[front]==null;
        front ++;
        front = front%10;
        size--;
        return t;
    }

    /**
     * 入队列操作
     * @param obj
     */
    public T offer(T obj){
        arrCirQueue[tail] = obj;
        tail++;
        tail = tail%10;
        size++;
        return obj;
    }

    /**
     * 取出堆首元素
     * @param <T>
     * @return
     */
    public <T> T peek(){
        front = front%10;

        return (T) arrCirQueue[front];

    }

    /**
     * 返回队列大小
     * @return
     */
    public int size(){
        size = (tail+10-front);
        return size;
    }

}
