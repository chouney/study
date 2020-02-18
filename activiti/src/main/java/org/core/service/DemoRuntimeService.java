package org.core.service;

import com.alibaba.fastjson.JSON;
import com.oracle.tools.packager.Log;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/7
 */
@Service
public class DemoRuntimeService {

    private Logger logger = LoggerFactory.getLogger(DemoRuntimeService.class);

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private ProcessEngine processEngine;


    /**
     * 启动一个流程实例
     *
     * @param deploymentId
     */
    public void proessInstance(String deploymentId) {
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employeeName", "Kermit");
        variables.put("numberOfDays", new Integer(4));
        variables.put("vacationMotivation", "I'm really tired!");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(deploymentId, variables);

        logger.info("Number of process instances: " + runtimeService.createProcessInstanceQuery().count());
    }

    /**
     * 查询流程实例列表
     *
     */
    public void getProcessInstanceList(String key,int n) {
        List<ProcessInstance> list = new ArrayList<>();
        if(n == 1) {
             list = runtimeService.createProcessInstanceQuery().processDefinitionId(key).list();
        }
        if(n == 2) {
            list = runtimeService.createProcessInstanceQuery().processDefinitionKey(key).list();
        }
        if(n ==3) {
            list = runtimeService.createProcessInstanceQuery().processDefinitionName(key).list();
        }
        if(n == 4) {
            list = runtimeService.createProcessInstanceQuery().deploymentId(key).list();
        }
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(p -> logger.info("Number of process instances: {},{},{},{},{},{},{}",
                    p.getBusinessKey(),p.getName(),p.getProcessDefinitionId(),
                    p.getProcessDefinitionVersion(),p.getDeploymentId(),p.getStartTime(),
                    JSON.toJSONString(p.getProcessVariables()),p.getSuperExecutionId()));
        }

    }

    /**
     * 查询流程实例列表
     *
     */
    public void getExecutions(String key,int n) {
        List<Execution> list = new ArrayList<>();
        if(n == 1) {
            list = runtimeService.createExecutionQuery().processDefinitionId(key).list();
        }
        if(n == 2) {
            list = runtimeService.createExecutionQuery().processDefinitionKey(key).list();
        }
        if(n ==3) {
            list = runtimeService.createExecutionQuery().processDefinitionName(key).list();
        }
        if(!CollectionUtils.isEmpty(list)){
            list.forEach(p -> logger.info("Number of execution: {},{},{},{},{},{},{}",
                    p.getId(),p.getName(),p.getActivityId(),
                    p.getParentProcessInstanceId()));
        }

    }

}
