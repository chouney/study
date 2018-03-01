// Copyright (C) 2018 Meituan
// All rights reserved
package jdk18;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author manatea
 * @version 1.0
 * @created 2018/1/18 AM11:27
 **/
public class DemoRTTI {

    private Map<String,Method> map = new HashMap<>();
    private void sayHello(){
        System.out.println("hello");
    }

    public static void main(String[] args) throws NoSuchMethodException {
        long startTime = System.currentTimeMillis();
        DemoRTTI demoRTTI = new DemoRTTI();
        Class clazz = demoRTTI.getClass();
        Method method = clazz.getDeclaredMethod("sayHello");
        System.out.println(System.currentTimeMillis()-startTime);
        demoRTTI.map.put(method.getName(),method);
        startTime = System.currentTimeMillis();
        method = demoRTTI.map.get("sayHello");
        System.out.println(System.currentTimeMillis()-startTime);
    }
}