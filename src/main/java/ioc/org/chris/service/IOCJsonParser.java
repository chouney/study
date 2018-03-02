// Copyright (C) 2017 Meituan
// All rights reserved
package org.chris.service;

import java.io.InputStream;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/12/19 PM8:26
 **/
public class IOCJsonParser {
    String buffer;
    public void parseBean(){
        //对Resource做Encode处理
        //解析器解析
        InputStream inputStream = this.getClass().getResourceAsStream("inject.json");

    }
}