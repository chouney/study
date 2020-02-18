package org.factory.abs.ingredient;


import org.factory.abs.IngredientFactory;
import org.factory.abs.ingredient.fruit.Fruit;
import org.factory.abs.ingredient.fruit.Orange;
import org.factory.abs.ingredient.vegetable.Egg;
import org.factory.abs.ingredient.vegetable.Vegetable;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class SuperIngredientFactory implements IngredientFactory {
    @Override
    public Fruit createFruit() {
        return new Orange();
    }

    @Override
    public Vegetable createVegetable() {
        return new Egg();
    }
}
