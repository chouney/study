// Copyright (C) 2017 Meituan
// All rights reserved
package jvm.unpackage;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * 通过该demo得出两个结论：
 * 1.封装类在不遇到算数符号的情况下不会执行自动拆箱
 * 2.封装类的equal方法不处理数字数据的转型关系
 * @author manatea
 * @version 1.0
 * @created 2017/7/27 PM4:12
 **/
public class DemoPackageAndUnpackage {
    public static void main(String[] main){

        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        System.out.println(c==d);
        System.out.println(e==f);
        System.out.println(c==(a+b));
        System.out.println(c.equals(a+b));
        System.out.println(g==(a+b));
        System.out.println(g.equals(a+b));
        int[][] dir = {{1,0},{-1,0},{0,1},{0,-1}};
        char[] chs = "asdasd".toCharArray();
    }
}