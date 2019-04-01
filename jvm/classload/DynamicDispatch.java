package classload;
// Copyright (C) 2017 Meituan
// All rights reserved

/**
 * 动态分派demo，原理需要查看编译源码，重写的原理
 * @author manatea
 * @version 1.0
 * @created 2017/7/11 PM4:55
 **/
public class DynamicDispatch {
    static abstract class Human{
        abstract void sayhello();
    }
    static class Man extends Human{
        void sayhello(){
            System.out.println("man sayHello");
        }

    }
    static class Woman extends Human{
        void sayhello(){
            System.out.println("woman sayHello");
        }

    }
    public static void main(String[] args){
        Human man = new Man();
        Human woman = new Woman();
        man.sayhello();
        woman.sayhello();
        man = new Woman();
        man.sayhello();
    }
}