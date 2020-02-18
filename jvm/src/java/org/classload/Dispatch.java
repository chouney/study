// Copyright (C) 2017 Meituan
// All rights reserved
package org.classload;

/**
 * 单分派和多分派的demo，分别解释了动态分派和静态分派所属
 * @author manatea
 * @version 1.0
 * @created 2017/7/18 AM11:57
 **/
public class Dispatch {
    public static class QQ{}
    public static class _360{}
    public static class Father{
        public void hardChoice(QQ qq){
            System.out.println("father choose QQ");
        }
        public void hardChoice(_360 _360){
            System.out.println("father choose 360");
        }
    }
    public static class Son extends Father{
        public void hardChoice(QQ qq){
            System.out.println("son choose QQ");
        }
        public void hardChoice(_360 _360){
            System.out.println("son choose 360");
        }
    }

    public static void main(String[] args){
        //静态分派是多分派，因为需要方法接收者的静态类型以及方法参数类型两个信息才能确定唯一一个方法调用版本
        //动态分派是单分派，因为仅根据方法参数类型的实际类型就能找出唯一一个实际的方法调用版本
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new QQ());
        son.hardChoice(new _360());



    }
}