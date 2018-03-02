package factory.abs.client;

import factory.abs.ingredient.SuperIngredientFactory;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class ZhengZhouClient extends BaseClient{

    public ZhengZhouClient() {
        super(new SuperIngredientFactory());
    }
}
