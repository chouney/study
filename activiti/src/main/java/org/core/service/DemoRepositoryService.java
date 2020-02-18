package org.core.service;

import com.alibaba.fastjson.JSON;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/7
 */
@Service
public class DemoRepositoryService {

    private Logger logger = LoggerFactory.getLogger(DemoRepositoryService.class);

    @Resource
    private RepositoryService repositoryService;


    /**
     * 停用/激活流程定义
     */
    public void switchProcessDefinition(String deploymentName) {
        if (repositoryService.isProcessDefinitionSuspended(deploymentName)) {
            logger.info("流程定义被激活:{}", deploymentName);
            repositoryService.activateProcessDefinitionByKey(deploymentName);
        } else {
            logger.info("流程定义被暂停:{}", deploymentName);
            repositoryService.suspendProcessDefinitionByKey(deploymentName);
        }

    }


    /**
     * 当Bpmn资源被加载后，会生成ProcessDefintion流程定义以及Deployment部署 两个数据结构
     */

    /**
     *
     * 默认的ProcessDefinitionId为,${key}:${version}:${ID}
     */

    /**
     * 获取流程定义信息
     *
     * @return
     */
    public void getProcessInfo() {
        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        logger.info("Number of process definitions: " + list.size());
        list.forEach(deployment -> logger.info("Number of one of process definitions:{},{},{},{},{},{},{}", deployment.getId(), deployment.getKey(),
                deployment.getName(), deployment.getVersion(), deployment.getCategory(), deployment.getDescription(),deployment.getDeploymentId()));
    }

    /**
     * 获取流程定义信息
     *
     * @return
     */
    public void getDeploymentInfo() {
        List<Deployment> list = repositoryService.createDeploymentQuery().list();
        logger.info("Number of process deployment: " + list.size());
        list.forEach(deployment -> logger.info("Number of one of deployment:{},{},{},{},{}", deployment.getId(), deployment.getKey(),
                deployment.getName(),  deployment.getCategory(),  deployment.getDeploymentTime()));

    }



    /**
     * 实现流程定义的加载
     * addClasspathResource只能加载bpmn.20.xml后缀的配置文件
     *
     * @param resName
     */
    public void proessDeployment(String resName) {
        repositoryService.createDeployment().addClasspathResource(resName + ".bpmn20.xml").key(UUID.randomUUID().toString())
                .deploy();
        logger.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
        ProcessDefinition deployment = repositoryService.createProcessDefinitionQuery().list().get(0);
        logger.info("Number of one of process definitions:{},{},{},{},{},{},{}", deployment.getId(), deployment.getKey(),
                deployment.getName(), deployment.getVersion(), deployment.getCategory(), deployment.getDescription(), deployment.getDiagramResourceName());

    }
}
