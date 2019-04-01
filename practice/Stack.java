package practice;

/**
 * 实现栈的数据结构
 * Created by chriszhang on 2017/10/21.
 */
public class Stack<T> {

    private int size = 0;
    private Object[] stack;//定义数组

    public Stack() {
        stack = new Object[10];
        //当长度为10时push怎么办
        //办法1
        /**
         * 当push到长度为10时，new 一个新的长度加倍的数组，将旧数组的值赋到新数组中
         */
        //方法2
        /**
         * 不用数组，用List的add 和 remove
         */
    }
    //!!!越界问题

    /**
     * 出栈操作
     *
     * @param <T>
     * @return
     */
    public <T> T pop() {
        T t = (T) stack[size - 1];//用一个变量存储数组最后一个值
        stack[size - 1] = null;//将最后一个值赋值为null删除这个值
        size--;
        return t;
    }

    /**
     * 入栈操作
     *
     * @param obj
     */
    public T push(T obj) {
        stack[size++] = obj;
        return obj;

    }

    public boolean isEmpty() {
        if (size==0){
            return true;
        }
        return false;
    }

    /**
     * 取出栈顶元素
     *
     * @param <T>
     * @return
     */
    public <T> T peek() {
        return (T) stack[size - 1];
    }

    /**
     * 返回栈大小
     *
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * 判断字符串中的括号是否能匹配，包含()[]和{}，
     * @param chs
     * @return
     */

    /**
     * 初始化一个空栈，依次进栈，进栈过程匹配，匹配上出栈，最终栈为空
     *
     * @param chs
     * @return
     */
    public static boolean bracketMatch(char[] chs) {
        Stack stack = new Stack();
        for (int i = 0; i < chs.length; i++) {
            if (stack.isEmpty()) {
                stack.push(chs[i]);
            } else if (stack.peek().equals('[') && chs[i] == ']' || stack.peek().equals('(') && chs[i] == ')' || stack.peek().equals('{') && chs[i] == '}') {
                stack.pop();
            } else {
                stack.push(chs[i]);
            }
        }

        if (stack.isEmpty()) {
            return true;
        } else {
            return false;
        }

    }

}
