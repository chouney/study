package org.v2.constant;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public enum IDTypeEnum{
    SECOND(0), //秒级格式
    MILISECOND(1), // 毫秒级格式
    ;

    IDTypeEnum(int code) {
        this.code = code;
    }

    private int code;
    public int getCode() {
        return code;
    }
}