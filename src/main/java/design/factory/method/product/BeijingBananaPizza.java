package factory.method.product;

import factory.method.Pizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class BeijingBananaPizza implements Pizza {
    @Override
    public void create() {
        System.out.println("create BeijingBananaPizza");
    }

    @Override
    public void cut() {
        System.out.println("cut BeijingBananaPizza");

    }

    @Override
    public void prepare() {
        System.out.println("prepare BeijingBananaPizza");

    }

    @Override
    public void boil() {
        System.out.println("boil BeijingBananaPizza");

    }
}
