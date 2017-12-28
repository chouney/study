package design.factory.method.product;

import design.factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class BeijingApplePizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create BeijingApplePizza");
    }

    @Override
    public void cut() {
        System.out.println("cut BeijingApplePizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare BeijingApplePizza");

    }

    @Override
    public void boil() {
        System.out.println("boil BeijingApplePizza");

    }
}
