package org.factory.simple;

/**
 * 生产披萨的工厂
 * Created by chriszhang on 2017/12/28.
 */
public class PizzaFactory {
    public Pizza createPizza(String type){
        switch (type){
            case "apple":return new ApplePizza();
            case "banana":return new BananaPizza();
            case "orange":return new OrangePizza();
        }
        return null;
    }
}
