package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.demo.AService;
import spring.demo.CModel;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/6/19
 */
public class Starter {

    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        AService aService = context.getBean("aService",AService.class);
        System.out.println(aService.getModel());
    }
}
