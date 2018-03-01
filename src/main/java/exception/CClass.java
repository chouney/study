// Copyright (C) 2017 Meituan
// All rights reserved
package exception;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/11/29 PM5:53
 **/
public class CClass {
    public static void test() throws TestError {
        throw new TestError("CClass Error");
    }
}