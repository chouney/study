package design.factory.method.product;

import design.factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class BeijingOrangePizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create BeijingOrangePizza");
    }

    @Override
    public void cut() {
        System.out.println("cut BeijingOrangePizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare BeijingOrangePizza");

    }

    @Override
    public void boil() {
        System.out.println("boil BeijingOrangePizza");

    }
}
