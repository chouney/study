package design.factory.abs;

/**
 * 抽象工厂维护工厂-客户的关系，抽象工厂下的各个实例工厂生产各个原材料，客户通过工厂进行产品组装生产
 * Created by chriszhang on 2017/12/28.
 */
public interface PizzaClient {
    void createPizza();
}
