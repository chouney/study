package org.ver9;

public interface InterfaceCase {

    //jdk8提供default 接口默认方法
    default void printf(boolean isString){
        if(isString)
            printStr();
        else
            printInt();
    }


    //jdk9在default实现的基础上允许了接口实现私有方法和静态私有方法
    private static void printInt(){
        System.out.println(123456);
    }

    private void printStr(){
        System.out.println("123456Str");
    }
}
