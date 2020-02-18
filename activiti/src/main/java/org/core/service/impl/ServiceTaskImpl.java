package org.core.service.impl;

import org.activiti.api.process.runtime.connector.Connector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * seviceTask节点指定执行任务
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/12
 */
@Configuration
public class ServiceTaskImpl {

    @Bean
    public Connector processTextConnector() {
        return integrationContext -> {
            Map<String, Object> inBoundVariables = integrationContext.getInBoundVariables();
            String contentToProcess = (String) inBoundVariables.get("employeeName");
            // Logic Here to decide if content is approved or not
            if (contentToProcess.contains("activiti")) {
                integrationContext.addOutBoundVariable("approved",
                        true);
            } else {
                integrationContext.addOutBoundVariable("approved",
                        false);
            }
            return integrationContext;
        };
    }

    @Bean
    public Connector tagTextConnector() {
        return integrationContext -> {
            String contentToTag = (String) integrationContext.getInBoundVariables().get("employeeName");
            contentToTag += " :) ";
            integrationContext.addOutBoundVariable("fileContent",
                    contentToTag);
            System.out.println("Final Content: " + contentToTag);
            return integrationContext;
        };
    }

    @Bean
    public Connector discardTextConnector() {
        return integrationContext -> {
            String contentToDiscard = (String) integrationContext.getInBoundVariables().get("employeeName");
            contentToDiscard += " :( ";
            integrationContext.addOutBoundVariable("fileContent",
                    contentToDiscard);
            System.out.println("Final Content: " + contentToDiscard);
            return integrationContext;
        };
    }


    @Bean
    public Connector diagramServiceTask() {
        return integrationContext -> {
            System.out.println("执行自动任务");
            integrationContext.getOutBoundVariables().put("next",false);
            return integrationContext;
        };
    }

    @Bean
    public Connector autoCrossTask() {
        return integrationContext -> {
            System.out.println("执行交叉检查自动任务");
            integrationContext.getOutBoundVariables().put("hasPass",true);
            return integrationContext;
        };
    }
}
