package org.factory.abs.client;


import org.factory.abs.IngredientFactory;
import org.factory.abs.PizzaClient;

/**
 * Created by chriszhang on 2017/12/28.
 */
public abstract class BaseClient implements PizzaClient {
    protected IngredientFactory ingredientFactory;

    public BaseClient(IngredientFactory ingredientFactory) {
        this.ingredientFactory = ingredientFactory;
    }

    @Override
    public void createPizza() {
        System.out.println("create Pizza,useing fruit:"+this.ingredientFactory.createFruit().getMessage()+
                ", vegetable:"+this.ingredientFactory.createVegetable().getMessage());
    }
}
