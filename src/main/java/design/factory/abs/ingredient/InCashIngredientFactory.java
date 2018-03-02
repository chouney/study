package factory.abs.ingredient;

import factory.abs.IngredientFactory;
import factory.abs.ingredient.fruit.Apple;
import factory.abs.ingredient.fruit.Fruit;
import factory.abs.ingredient.fruit.Orange;
import factory.abs.ingredient.vegetable.Carrot;
import factory.abs.ingredient.vegetable.Egg;
import factory.abs.ingredient.vegetable.Vegetable;

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
