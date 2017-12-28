package design.factory.abs.client;

import design.factory.abs.ingredient.InCashIngredientFactory;

/**
 * Created by chriszhang on 2017/12/28.
 */
public class BeijingClient extends BaseClient{
    public BeijingClient() {
        super(new InCashIngredientFactory());
    }

}
