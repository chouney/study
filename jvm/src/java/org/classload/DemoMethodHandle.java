// Copyright (C) 2017 Meituan
// All rights reserved
package org.classload;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;

/**
 * MethodHandle机制，通过模拟字节码指令实现动态语言特性
 * 与反射的区别见笔记
 * @author manatea
 * @version 1.0
 * @created 2017/7/18 PM4:34
 **/
public class DemoMethodHandle {
    public static class Fox {
        public void println(String s) {
            System.out.println("fox : " + s);
        }
    }

    public static void main(String[] args) throws Throwable {
//        Object obj = System.currentTimeMillis() % 2 == 0 ? System.out : new Fox();
        //无论是System.out还是Fox最终都能执行其println方法
//        demoMethodHandle(obj).invokeExact("hahahaha");
//        demoMethod(obj).invoke(obj,"hahahaha");
        /**
         * 程序员而不是JVM掌握方法分派规则的好处
         */
        DemoMethodHandle demoMethodHandle = new DemoMethodHandle();
        MH_Advantage mh_advantage = demoMethodHandle.new MH_Advantage();
        MH_Advantage.Son son = mh_advantage.new Son();
        son.think();

    }

    //MethodHandler机制
    public static MethodHandle demoMethodHandle(Object receive) throws NoSuchMethodException, IllegalAccessException {
        //方法类型定义、确定方法参数类型和返回类型
        MethodType mt = MethodType.methodType(void.class, String.class);
        //lookup()即指定receive类中查找符合方法类型的方法调用，然后这里调用的是虚方法，模拟invokevirtual，最后bindTo进行绑定，包括this隐藏参数的传递
        return MethodHandles.lookup().findVirtual(receive.getClass(), "println", mt).bindTo(receive);
    }

    //反射机制
    public static Method demoMethod(Object receive) throws NoSuchMethodException {
        //使用反射同样可以实现功能
        Method method = receive.getClass().getDeclaredMethod("println", String.class);
        method.setAccessible(true);
        return method;
    }

    public class MH_Advantage{
        public class GrandFather{
            void think() throws Throwable {
                System.out.println("GrandFather thinking");
            }
        }
        public class Father extends GrandFather{
            @Override
            void think() throws Throwable {
                System.out.println("GrandFather thinking");
            }
        }
        public class Son extends Father{
            /**
             * 如何调用GrandFather的think()方法
             */
            @Override
            void think() throws Throwable {
                /**
                 * super方法直接一级，不能越级
                 * invokeVirtual分派方案是从当前类符合的方法描述符开始向上逐级查询
                 * 因此方案已经被写死在了JVM中，因此要实现调用GrandFather的think方法就必须越过invokeVirtual指令
                 */
                MethodType mt = MethodType.methodType(void.class);
                MethodHandle mh = MethodHandles.lookup().findSpecial(GrandFather.class,"think",mt,getClass()).bindTo(this);
                mh.invoke();
            }
        }
    }
}