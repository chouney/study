package practice;

/**
 * 实现队列的数据结构
 * Created by chriszhang on 2017/10/21.
 */
public class Queue<T> {
    /**
     * Node代表队列链的结点
     */
    private Object[] arrQueue = new Object[4];
    private int size=0;
    private int front = 0;
    private int tail=-1;//初始化成-1  插入需要每次找到队列的最后一位  0，加一

    //越界问题
    //如何解决offer10次后poll10后，你还能offer吗？
    /**
     * 出队列操作
     * @param <T>
     * @return
     */
    public <T> T poll(){
        T t = (T) arrQueue[front++];
        //arrQueue[front++]=null;
        size--;
        return t;
    }

    /**
     * 入队列操作
     * @param obj
     */
    public T offer(T obj){
        arrQueue[++tail] = obj;
        size++;
        return obj;
    }

    /**
     * 取出堆首元素
     * @param <T>
     * @return
     */
    public <T> T peek(){

        return (T) arrQueue[front];
    }

    /**
     * 返回队列大小
     * @return
     */
    public int size(){
        //front = new Node();

        return size;
    }

}
