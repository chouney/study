package org.factory.abs.ingredient;


import org.factory.abs.IngredientFactory;
import org.factory.abs.ingredient.fruit.Apple;
import org.factory.abs.ingredient.fruit.Fruit;
import org.factory.abs.ingredient.vegetable.Carrot;
import org.factory.abs.ingredient.vegetable.Vegetable;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class InCashIngredientFactory implements IngredientFactory {
    @Override
    public Fruit createFruit() {
        return new Apple();
    }

    @Override
    public Vegetable createVegetable() {
        return new Carrot();
    }
}
