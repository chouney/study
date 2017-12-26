// Copyright (C) 2017 Meituan
// All rights reserved
package ioc.service;

import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Properties;

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