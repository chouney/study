// Copyright (C) 2017 Meituan
// All rights reserved
package ioc.core;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/12/19 PM8:26
 **/
public interface Element<K,V> {

    /**
     * 获取元素value值
     * @return
     */
    V getValue();

    /**
     * 获取元素key值
     * @return
     */
    K getKey();

}