package org.v3.server.web;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public class Application {

    public static void main(String [] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/chris-provider.xml");
        context.start();
        System.in.read();
    }
}
