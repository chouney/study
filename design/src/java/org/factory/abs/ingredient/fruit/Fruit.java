package org.factory.abs.ingredient.fruit;

/**
 * 抽象原料
 * Created by chriszhang on 2017/12/28.
 */
public abstract class Fruit {
    protected String name;

    public Fruit(String name) {
        this.name = name;
    }

    public String getMessage(){
        return name;
    }
}
