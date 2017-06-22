package jdk18;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * Created by chriszhang on 2017/6/14.
 */
//表示这个接口是一个函数式接口。加上它的接口不会被编译
//它有点像@Override，都是声明了一种使用意图，避免你把它用错
@FunctionalInterface
public interface DemoInterface {
    /**
     * jdk1.8支持接口定义静态方法。
     * 应用场景是对于某个接口可以定义一些工具。将之前工具类的部分方法迁移到接口类中
     * @param number
     * @return
     */
    static int toInteger(String number){
        return Integer.valueOf(number);
    }

    /**
     *jdk1.8支持接口定义默认方法
     * 但是不能使用默认方法来实现任何Object类的方法
     * 接口的Object重写会使得默认方法推到变得复杂
     * @param list
     * @return
     */
    default <T> void removeFirst(List<T> list){
        if(!list.isEmpty()) {
            list.remove(0);
        }
    }

    /**
     * 函数式接口
     * 如果一个接口定义个唯一一个抽象方法那么这个接口就是函数式接口
     * @param a
     * @param b
     * @return
     */
    abstract int sum(int a, int b);
}
