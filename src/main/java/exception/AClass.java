// Copyright (C) 2017 Meituan
// All rights reserved
package exception;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/11/29 PM5:53
 **/
public class AClass {
    public static void test() throws TestError {
        try {
            BClass.test();
        } catch (TestError e) {
            System.out.println(e.getMessage());
        }
    }
    public static void main(String[] args) throws TestError {
        test();
    }
}