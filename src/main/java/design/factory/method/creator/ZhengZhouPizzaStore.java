package factory.method.creator;

import factory.method.Pizza;
import factory.method.PizzaStore;
import factory.method.product.ZhengZhouApplePizza;
import factory.method.product.ZhengZhouBananaPizza;
import factory.method.product.ZhengZhouOrangePizza;
import factory.simple.BananaPizza;
import factory.simple.OrangePizza;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class ZhengZhouPizzaStore implements PizzaStore {
    @Override
    public Pizza createPizza(String type) {
        switch (type){
            case "apple":return new ZhengZhouApplePizza();
            case "banana":return new ZhengZhouBananaPizza();
            case "orange":return new ZhengZhouOrangePizza();
        }
        return null;
    }
}
