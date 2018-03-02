package factory.abs.client;

import factory.abs.IngredientFactory;
import factory.abs.PizzaClient;

/**
 * Created by chriszhang on 2017/12/28.
 */
public abstract class BaseClient implements PizzaClient{
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
