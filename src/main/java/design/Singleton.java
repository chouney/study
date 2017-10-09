// Copyright (C) 2017 Meituan
// All rights reserved
package design;

import algorithm.Main;

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
        private static Main main = new Main();
    }

    public static Main getMainInstance(){
        return MainHolder.main;
    }
    public static void main(String[] args){
        Main m = getMainInstance();
        Main m2 = getMainInstance2();
    }

    /**
     * 单例模式2
     */
    private static volatile Main main2;
    public static Main getMainInstance2(){
        if(main2 == null){
            synchronized (Singleton.class){
                if(main2 == null) {
                    main2 = new Main();
                }
            }
        }
        return main2;
    }
}