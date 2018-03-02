package factory.abs;

import factory.abs.ingredient.fruit.Fruit;
import factory.abs.ingredient.vegetable.Vegetable;

/**
 * 生产原材料的接口，每个子工厂都负责生产某一种原料
 * Created by chriszhang on 2017/12/28.
 */
public interface IngredientFactory {
    Fruit createFruit();
    Vegetable createVegetable();
}
