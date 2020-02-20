package org.v3.client.constant;

import java.io.Serializable;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public enum IDGenerateTypeEnum implements Serializable{


    LOCAL(1), //本地生成方式
    CLIENT_SDK(2), //本地调用远程服务生成方式
    OTHER(3), //其他方式
    ;

    private int code;

    private static final long serialVersionUID = 44L;

    IDGenerateTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
