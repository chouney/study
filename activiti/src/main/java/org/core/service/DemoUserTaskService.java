package org.core.service;

import com.alibaba.fastjson.JSON;
import org.activiti.api.task.model.payloads.ClaimTaskPayload;
import org.activiti.api.task.model.payloads.CompleteTaskPayload;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.ProcessInstanceQueryImpl;
import org.activiti.engine.task.Task;
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
public class DemoUserTaskService {

    private Logger logger = LoggerFactory.getLogger(DemoUserTaskService.class);

    @Resource
    private TaskRuntime taskRuntime;

    @Resource
    private TaskService taskService;

    @Resource
    private DemoIdentifyService demoIdentifyService;


    /**
     * 获取抢单表单
     * @param name
     * @param password
     */
    public void getRobTaskList(String name,String password){
        demoIdentifyService.login(name,password);
        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(name).listPage(0,10);
        if(!CollectionUtils.isEmpty(tasks)){
            tasks.forEach( t -> logger.info("获取流程所有任务表单，taskList:{},{},{},{},{},{},{},{},{},{},{},{}",
                    t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                    t.getTaskDefinitionKey(),t.getDelegationState(),t.isSuspended(),t.getExecutionId(),
                    t.getClaimTime(),t.getClaimTime(),
                    JSON.toJSONString(t.getProcessVariables()),JSON.toJSONString(t.getTaskLocalVariables())));

        }
    }

    /**
     * 提交抢单申请
     * @param name
     * @param password
     */
    public void submitRobTaskList(String name,String password,String taskId){
        demoIdentifyService.login(name,password);
        taskRuntime.claim(new ClaimTaskPayload(taskId,""));
        logger.info("提交表单抢单申请，name:{},password:{}，taskId:{}",name,password,taskId);
    }

    /**
     * 获取任务表单
     * @param name
     * @param password
     */
    public void getTaskList(String name,String password){
        demoIdentifyService.login(name,password);
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(name).listPage(0,10);
        if(!CollectionUtils.isEmpty(tasks)){
            tasks.forEach( t -> logger.info("获取流程所有任务表单，taskList:{},{},{},{},{},{},{},{},{},{},{},{}",
                    t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                    t.getTaskDefinitionKey(),t.getDelegationState(),t.isSuspended(),t.getExecutionId(),
                    t.getClaimTime(),t.getCreateTime(),
                    JSON.toJSONString(t.getProcessVariables()),JSON.toJSONString(t.getTaskLocalVariables())));

        }
    }

    /**
     * 完成任务
     * @param name
     * @param password
     */
    public void submitTaskComplete(String name,String password,String taskId,int hasPass){
        demoIdentifyService.login(name,password);
        Map<String,Object> map = new HashMap<>();
        map.put("hasPass",hasPass == 1);
        org.activiti.api.task.model.Task task =  taskRuntime.complete(new CompleteTaskPayload(taskId,map));
        logger.info("完成任务，name:{},password:{}，taskId:{},map:{}",name,password,task,JSON.toJSONString(map));
    }

    /**
     * 获取当前执行任务
     */
    public void getCurrentTask(String processInstanceId){
        List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstanceId).list();
        Task t = null;
        if(!CollectionUtils.isEmpty(tasks)){
            logger.info("获取当前任务表单，taskList:{},{},{},{},{},{},{},{},{},{},{},{}",
                    t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                    t.getTaskDefinitionKey(),t.getDelegationState(),t.isSuspended(),t.getExecutionId(),
                    t.getClaimTime(),t.getClaimTime(),
                    JSON.toJSONString(t.getProcessVariables()),JSON.toJSONString(t.getTaskLocalVariables()));
            t  = tasks.get(0);
        }
        logger.info("获取当前任务表单，taskList:{},{},{},{},{},{},{},{},{},{},{},{}",
                t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                t.getTaskDefinitionKey(),t.getDelegationState(),t.isSuspended(),t.getExecutionId(),
                t.getClaimTime(),t.getClaimTime(),
                JSON.toJSONString(t.getProcessVariables()),JSON.toJSONString(t.getTaskLocalVariables()));
    }

    /**
     * 1.todo 经过测试发现人工结点会自动直接跳过，
     * 2.todo 另外需要修改配置修改流程的历史记录
     */

    /**
     * 下一个环节
     */
    public void nextFLow(String taskId){
        Map<String,Object> map = new HashMap<>();
        map.put("hasPass",0);
        org.activiti.api.task.model.Task t =  taskRuntime.complete(new CompleteTaskPayload(taskId,map));
        logger.info("获取当前任务表单，taskList:{},{},{},{},{},{},{},{},{},{}",
                t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                t.getStatus(),t.getClaimedDate(),t.getCompletedDate(),t.getCreatedDate(),
                t.getDescription(),t.getDueDate());

    }


    /**
     * 获取任务表单
     */
    public void getTotalTaskList(String key,int n){
        List<Task> list = new ArrayList<>();
        if(n == 1) {
            list = taskService.createTaskQuery().processDefinitionId(key).list();
        }
        if(n == 2) {
            list = taskService.createTaskQuery().processDefinitionKey(key).list();
        }
        if(n ==3) {
            list = taskService.createTaskQuery().processDefinitionName(key).list();
        }
        if(n == 4) {
            list = taskService.createTaskQuery().deploymentId(key).list();
        }
        if(!CollectionUtils.isEmpty(list)){
            list.forEach( t -> logger.info("获取流程所有任务表单，taskList:{},{},{},{},{},{},{},{},{},{},{},{}",
                    t.getId(),t.getName(),t.getOwner(),t.getAssignee(),
                    t.getTaskDefinitionKey(),t.getDelegationState(),t.isSuspended(),t.getExecutionId(),
                    t.getClaimTime(),t.getClaimTime(),
                    JSON.toJSONString(t.getProcessVariables()),JSON.toJSONString(t.getTaskLocalVariables())));

        }
    }

}
