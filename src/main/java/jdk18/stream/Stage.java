package jdk18.stream;

import java.util.function.Predicate;

/**
 * 流状态存储接口
 * @author chriszhang
 * @version 1.0
 * @date 2018/9/9
 */
public interface Stage<T> {

    <E> Stage<T> filter(Predicate<E> predicate);

}
