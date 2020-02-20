package org.v3.client.constant;

import java.io.Serializable;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public enum IDTypeEnum implements Serializable{
    SECOND(0), //秒级格式
    MILISECOND(1), // 毫秒级格式

    ;

    IDTypeEnum(int code) {
        this.code = code;
    }

    private int code;

    private static final long serialVersionUID = 43L;

    public int getCode() {
        return code;
    }
}