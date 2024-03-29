package org.classload;

import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载Demo,
 * 在main方法中通过定义一个新的类加载器实现类加载
 * Created by manatea on 2016/12/26.
 */
public class ClassLoaderTest {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader classLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String filename = name.substring(name.lastIndexOf(".") + 1) + ".class";
                    InputStream in = getClass().getResourceAsStream(filename);
                    if (in == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[in.available()];
                    in.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new ClassNotFoundException(e.getMessage());
                }
            }
        };
        System.out.println(ClassLoaderTest.class.getCanonicalName());
        Object o = classLoader.loadClass("jvm.classload.ClassLoaderTest").newInstance();
        System.out.println(o.getClass()+ " : "+ o.getClass().getClassLoader().toString());
        System.out.println(o instanceof ClassLoaderTest);

    }
}
