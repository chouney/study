// Copyright (C) 2017 Meituan
// All rights reserved
package generator;
import generator.DeclareGeneratorDemo;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/9/4 PM3:53
 **/
public class DeclareGeneratorSub<T> extends DeclareGeneratorDemo<T> {

    public static void main(String[] args){
        DeclareGeneratorDemo<String> declareGeneratorDemo = new DeclareGeneratorSub<String>(){};
        System.out.println(declareGeneratorDemo.getClass().getName());
        ParameterizedType type =(ParameterizedType) declareGeneratorDemo.getClass().getGenericSuperclass();
        System.out.println(Arrays.toString(type.getActualTypeArguments()));
    }
}