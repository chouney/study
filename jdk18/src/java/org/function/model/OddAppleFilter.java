package org.function.model;

import java.util.function.Predicate;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/3/18
 */
public class OddAppleFilter implements Predicate<Apple> {
    @Override
    public boolean test(Apple apple) {
        return apple != null && (apple.getWeight() & 1) == 1;
    }

}
