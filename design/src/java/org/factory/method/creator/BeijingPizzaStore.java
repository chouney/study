package org.factory.method.creator;


import org.factory.method.Pizza;
import org.factory.method.PizzaStore;
import org.factory.method.product.BeijingApplePizza;
import org.factory.method.product.BeijingBananaPizza;
import org.factory.method.product.BeijingOrangePizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class BeijingPizzaStore implements PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        switch (type){
            case "apple":return new BeijingApplePizza();
            case "banana":return new BeijingBananaPizza();
            case "orange":return new BeijingOrangePizza();
        }
        return null;
    }
}
