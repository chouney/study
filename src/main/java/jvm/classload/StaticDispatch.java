package classload;
// Copyright (C) 2017 Meituan
// All rights reserved

/**
 * 静态分派demo,根据变量的静态类型进行分配，重载的实现原理
 * @author manatea
 * @version 1.0
 * @created 2017/7/11 PM4:55
 **/
public class StaticDispatch {
    static abstract class Human{
        protected String m = "human";
    }
    static class Man extends Human{
        protected String m = "man";
    }
    static class Woman extends Human{
        protected String m = "woman";
    }
    public void sayHello(Human m){
        System.out.println(m.m);
    }
    public void sayHello(Man m){
        System.out.println(m.m);
    }
    public void sayHello(Woman m){
        System.out.println(m.m);
    }
    public static void main(String[] args){
        Human man = new Man();
        Human woman = new Woman();
        StaticDispatch m = new StaticDispatch();
        m.sayHello(man);
        m.sayHello(woman);
    }
}