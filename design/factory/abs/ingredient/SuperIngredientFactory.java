package factory.abs.ingredient;

import factory.abs.IngredientFactory;
import factory.abs.ingredient.fruit.Fruit;
import factory.abs.ingredient.fruit.Orange;
import factory.abs.ingredient.vegetable.Egg;
import factory.abs.ingredient.vegetable.Vegetable;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class SuperIngredientFactory implements IngredientFactory{
    @Override
    public Fruit createFruit() {
        return new Orange();
    }

    @Override
    public Vegetable createVegetable() {
        return new Egg();
    }
}
