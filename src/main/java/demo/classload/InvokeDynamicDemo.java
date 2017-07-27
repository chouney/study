// Copyright (C) 2017 Meituan
// All rights reserved
package demo.classload;

import java.lang.invoke.*;

import static java.lang.invoke.MethodHandles.lookup;

/**
 * invokeDynamic命令Demo
 * 因为该命令并非面向Java而是其他运行在JVM上的动态语言，因此单纯使用Java编译器无法生成其字节码。
 * 使用简单工具INDY来转换为invokeDynamic命令字节码
 * (如果没有下载INDY工具进行字节码转化,那么则无法编译成JVM的invokeDynamic指令)
 * 整理的实现思路其实就是通过lookup的findStatic方法找到MethodHandle句柄（包含类，方法名，方法类型），再通过CallSite调用invokeDynamic命令
 * @author manatea
 * @version 1.0
 * @created 2017/7/21 PM7:05
 **/
public class InvokeDynamicDemo {
    public static void main(String[] args) throws Throwable {
        INDY_BootStrapMethod().invokeExact("qweqwe");
        //使用如下方法同样能实现功能
//        BootStrapMethod(lookup(),"testMethod",MethodType.methodType(void.class,String.class)).dynamicInvoker().invokeExact("123466");
    }

    private static void testMethod(String s) {
        System.out.println("this is testMethod , param :{}" + s);
    }

    private static CallSite BootStrapMethod(MethodHandles.Lookup lookup, String name, MethodType mt) throws NoSuchMethodException, IllegalAccessException {
        return new ConstantCallSite(lookup.findStatic(InvokeDynamicDemo.class, name, mt));
    }

    //MethodHandle的MethodType方法类型
    private static MethodType MT_BootStrapMethod(){
        return MethodType.methodType(CallSite.class,MethodHandles.Lookup.class,String.class,MethodType.class);
        //使用如下方法同样能实现功能
//        return MethodType.fromMethodDescriptorString("(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/CallSite;",null);
    }

    //返回的是MethodHandle，其中第二个参数为上面的BootStrapMethod方法,找到BootStrapMethod方法
    private static MethodHandle MH_BootStrapMethod() throws NoSuchMethodException, IllegalAccessException {
        return lookup().findStatic(InvokeDynamicDemo.class,"BootStrapMethod",MT_BootStrapMethod());
    }

    //上层方法调用
    private static MethodHandle INDY_BootStrapMethod() throws Throwable {
        CallSite cs = (CallSite) MH_BootStrapMethod().invokeWithArguments(lookup(),"testMethod",MethodType.fromMethodDescriptorString("(Ljava/lang/String;)V",null));
        return cs.dynamicInvoker();
    }

}