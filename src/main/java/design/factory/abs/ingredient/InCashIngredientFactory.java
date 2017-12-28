package design.factory.abs.ingredient;

import design.factory.abs.IngredientFactory;
import design.factory.abs.ingredient.fruit.Apple;
import design.factory.abs.ingredient.fruit.Fruit;
import design.factory.abs.ingredient.fruit.Orange;
import design.factory.abs.ingredient.vegetable.Carrot;
import design.factory.abs.ingredient.vegetable.Egg;
import design.factory.abs.ingredient.vegetable.Vegetable;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class InCashIngredientFactory implements IngredientFactory{
    @Override
    public Fruit createFruit() {
        return new Apple();
    }

    @Override
    public Vegetable createVegetable() {
        return new Carrot();
    }
}
