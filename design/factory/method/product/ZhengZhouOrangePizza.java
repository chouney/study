package factory.method.product;

import factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class ZhengZhouOrangePizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create ZhengZhouOrangePizza");
    }

    @Override
    public void cut() {
        System.out.println("cut ZhengZhouOrangePizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare ZhengZhouOrangePizza");

    }

    @Override
    public void boil() {
        System.out.println("boil ZhengZhouOrangePizza");

    }
}
