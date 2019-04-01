// Copyright (C) 2017 Meituan
// All rights reserved
package singleton;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/8/1 PM9:53
 **/
public class Singleton {
    /**
     * 单例模式1
     */
    private static class MainHolder{
        private static Singleton singleton = new Singleton();
    }

    public static Singleton getMainInstance(){
        return MainHolder.singleton;
    }
    public static void main(String[] args){
        Singleton singleton = getMainInstance();
        Singleton singleton2 = getMainInstance2();
    }

    /**
     * 单例模式2
     */
    private static volatile Singleton singleton2;
    public static Singleton getMainInstance2(){
        if(singleton2 == null){
            synchronized (Singleton.class){
                if(singleton2 == null) {
                    singleton2 = new Singleton();
                }
            }
        }
        return singleton2;
    }
}