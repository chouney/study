package org.factory.method.creator;


import org.factory.method.Pizza;
import org.factory.method.PizzaStore;
import org.factory.method.product.ZhengZhouApplePizza;
import org.factory.method.product.ZhengZhouBananaPizza;
import org.factory.method.product.ZhengZhouOrangePizza;

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
