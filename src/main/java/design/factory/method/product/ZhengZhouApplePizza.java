package design.factory.method.product;

import design.factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class ZhengZhouApplePizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create ZhengZhouApplePizza");
    }

    @Override
    public void cut() {
        System.out.println("cut ZhengZhouApplePizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare ZhengZhouApplePizza");

    }

    @Override
    public void boil() {
        System.out.println("boil ZhengZhouApplePizza");

    }
}
