package org.factory.method.product;


import org.factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class ZhengZhouBananaPizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create ZhengZhouBananaPizza");
    }

    @Override
    public void cut() {
        System.out.println("cut ZhengZhouBananaPizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare ZhengZhouBananaPizza");

    }

    @Override
    public void boil() {
        System.out.println("boil ZhengZhouBananaPizza");

    }
}
