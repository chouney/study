package org.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/4/22
 */
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class Application {

    /**
     * 本demo着重实现以下功能点：
     * 1.实现流程定义的热加载
     * 2.实现流程定义的热部署
     * 3.实现用户和组关系的热配置
     * 4.完成一个分单流程
     *

     生成流程定义
     动态流程实例化
     动态部署流程
     任务的分配
     分配人审批
     实现BPMN.IO实现
     流程实例流水追踪



     20190709
     第一步：
     构建审批流程
     定义静态资源
     定义静态用户组
     提供流程实例的任务流水查询
     提供资源定义查询
     提供流程实例查询
     第二步
     实现资源的动态加载
     实现流程的热部署（版本）
     实现用户组的热配置

     * @param args
     */
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
        new Thread(AppRuntimeService::run).start();
    }

}
