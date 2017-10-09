// Copyright (C) 2017 Meituan
// All rights reserved
package jdk18;

import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/8/1 PM7:03
 **/
public class DemoPredicate {

    class Apple{
        int weight,color;
        Apple(int weight,int color){
            this.weight = weight;
            this.color = color;
        }

    }

    public <T> boolean filter(T t,Predicate<T> predicate){
        return predicate.test(t);
    }
}