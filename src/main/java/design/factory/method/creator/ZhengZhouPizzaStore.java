package design.factory.method.creator;

import design.factory.method.Pizza;
import design.factory.method.PizzaStore;
import design.factory.method.product.ZhengZhouApplePizza;
import design.factory.method.product.ZhengZhouBananaPizza;
import design.factory.method.product.ZhengZhouOrangePizza;
import design.factory.simple.BananaPizza;
import design.factory.simple.OrangePizza;

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
