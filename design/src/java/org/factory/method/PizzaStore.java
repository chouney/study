package org.factory.method;

/**
 * 工厂维度
 * 将多个公共特性的工厂聚合起来就是工厂方法模式
 * 工厂方法维护的是工厂-产品的生产关系
 * Created by chriszhang on 2017/12/28.
 */
public interface PizzaStore {
    Pizza createPizza(String type);
}
