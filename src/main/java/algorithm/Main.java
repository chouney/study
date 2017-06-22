package algorithm;

import java.io.*;
import java.util.*;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("这是线程1的任务1");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("这是线程1的任务1,1秒后的");

            }
        },"这是一个线程1").start();
        new Thread(new Runnable() {
            public void run() {
                System.out.println("这是线程2的任务2");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("这是线程1的任务1，5秒后的");
            }
        },"这是一个线程2").start();
        System.out.println("这是任务3");
        System.out.println("这是任务4");
    }

}