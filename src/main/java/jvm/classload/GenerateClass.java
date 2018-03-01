// Copyright (C) 2017 Meituan
// All rights reserved
package jvm.classload;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 字节码生成技术，动态代理实现。
 * 该例子没有具体讲generateClass的实现原理
 * 实际应用中也很少使用byte为单位产生的字节码，这样的也是高度模板化的
 * 尽量使用封装好的类库生成字节码
 * @author manatea
 * @version 1.0
 * @created 2017/7/27 AM10:33
 **/
public class GenerateClass {

    public interface IHello {
        void sayHello();
    }

    public class Hello implements IHello {
        @Override
        public void sayHello() {
            System.out.println("hello");
        }
    }


    public class Proxy implements InvocationHandler {

        private Object obj;

        public Object bind(Object obj) {
            this.obj = obj;
            return java.lang.reflect.Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("welcome");
            return method.invoke(obj, args);
        }
    }

    public static void main(String[] args) {
        //动态代理的字节码输出文件保留，可以查看，里面的super.h就是代理的InvocationHandler类，
//        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
        GenerateClass generateClass = new GenerateClass();
        IHello hello = (IHello) generateClass.new Proxy().bind(generateClass.new Hello());
        hello.sayHello();
    }

}