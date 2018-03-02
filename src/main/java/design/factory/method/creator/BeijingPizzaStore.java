package factory.method.creator;

import factory.method.Pizza;
import factory.method.PizzaStore;
import factory.method.product.BeijingApplePizza;
import factory.method.product.BeijingBananaPizza;
import factory.method.product.BeijingOrangePizza;

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
