package design.factory.method.creator;

import design.factory.method.Pizza;
import design.factory.method.PizzaStore;
import design.factory.method.product.BeijingApplePizza;
import design.factory.method.product.BeijingBananaPizza;
import design.factory.method.product.BeijingOrangePizza;

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
