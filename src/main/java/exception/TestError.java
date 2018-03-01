// Copyright (C) 2017 Meituan
// All rights reserved
package exception;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/11/29 PM5:52
 **/
public class TestError extends Exception{

    public TestError(String message) {
        super(message);
    }

    public TestError(String message, Throwable cause) {
        super(message, cause);
    }
}