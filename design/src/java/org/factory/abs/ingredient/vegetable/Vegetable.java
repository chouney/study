package org.factory.abs.ingredient.vegetable;

/**
 * Created by chriszhang on 2017/12/28.
 */
public abstract class Vegetable {
    protected String name;

    public Vegetable(String name) {
        this.name = name;
    }

    public String getMessage(){
        return name;
    }
}
