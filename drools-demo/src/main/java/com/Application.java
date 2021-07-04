package com;

import com.alibaba.fastjson.JSON;
import com.model.Order;
import com.utils.DroolsUtil;
import org.kie.api.runtime.KieSession;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2020/7/15
 */
public class Application {

    public static void main(String[] args){
        // 通过工具类去获取KieSession
        KieSession ksession = DroolsUtil.getKieSessionByName("point_ksession");

        List<Order> orderList = getInitData();
        try {
            for (int i = 0; i < orderList.size(); i++) {
                Order o = orderList.get(i);
                ksession.insert(o);
                ksession.fireAllRules();
                // 执行完规则后, 执行相关的逻辑
//                addScore(o);
                System.out.println(JSON.toJSONString(o));
            }
        } catch (Exception e) {

        } finally {
            ksession.destroy();
        }
    }

    private static List<Order> getInitData(){
        List<Order> result = new ArrayList<>();
        result.add(new Order(0));
        result.add(new Order(100));
        result.add(new Order(200));
        result.add(new Order(300));
        result.add(new Order(600));
        result.add(new Order(900));
        result.add(new Order(1000));
        result.add(new Order(1500));
        result.add(new Order(2500));
        return result;
    }
}
