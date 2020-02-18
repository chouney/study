package org.core;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.core.config.DemoApplicationContextAware;
import org.core.service.DemoIdentifyService;
import org.core.service.DemoRepositoryService;
import org.core.service.DemoRuntimeService;
import org.core.service.DemoUserTaskService;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/7
 */
@Service
public class AppRuntimeService {

    public static void run() {
        try {
            ApplicationContext applicationContext = DemoApplicationContextAware.getContext();
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            String cmd;
            while (StringUtils.isNotEmpty(cmd = in.readLine())) {
                try {
                    String[] args = cmd.split("\\.");
                    if ("Rep".equals(args[0])) {
                        DemoRepositoryService repositoryService = applicationContext.getBean(DemoRepositoryService.class);
                        if ("create".equals(args[1])) {
                            repositoryService.proessDeployment(args[2]);
                        } else if ("list".equals(args[1])) {
                            repositoryService.getProcessInfo();
                        } else if ("switch".equals(args[1])) {
                            repositoryService.switchProcessDefinition(args[2]);
                        } else if("depList".equals(args[1])){
                            repositoryService.getDeploymentInfo();
                        }
                    } else if("User".equals(args[0])){
                        DemoIdentifyService demoIdentifyService = applicationContext.getBean(DemoIdentifyService.class);
                        if("add".equals(args[1])){
                            demoIdentifyService.addUser(args[2],args[3], ArrayUtils.subarray(args,4,args.length));
                        }else if("update".equals(args[1])){
                            demoIdentifyService.update(args[2],args[3], ArrayUtils.subarray(args,4,args.length));
                        }else if("delete".equals(args[1])){
                            demoIdentifyService.delete(args[2]);
                        } else if("info".equals(args[1])){
                            demoIdentifyService.getLoginUserInfo();
                        }else if("login".equals(args[1])){
                            demoIdentifyService.login(args[2],args[3]);
                        }else if("get".equals(args[1])){
                            demoIdentifyService.getUserInfo(args[2]);
                        }
                    } else if ("Run".equals(args[0])) {
                        DemoRuntimeService runtimeService = applicationContext.getBean(DemoRuntimeService.class);
                        if ("run".equals(args[1])) {
                            runtimeService.proessInstance(args[2]);
                        }else if("list".equals(args[1])){
                            runtimeService.getProcessInstanceList(args[2],Integer.valueOf(args[3]));
                        }else if("execList".equals(args[1])){
                            runtimeService.getExecutions(args[2],Integer.valueOf(args[3]));
                        }
                    }else if ("Task".equals(args[0])) {
                        DemoUserTaskService taskService = applicationContext.getBean(DemoUserTaskService.class);
                        if ("rbList".equals(args[1])) {
                            taskService.getRobTaskList(args[2],args[3]);
                        }else if ("subRb".equals(args[1])) {
                            taskService.submitRobTaskList(args[2],args[3],args[4]);
                        }else if("tsList".equals(args[1])){
                            taskService.getTaskList(args[2],args[3]);
                        }else if("subTs".equals(args[1])){
                            taskService.submitTaskComplete(args[2],args[3],args[4],Integer.valueOf(args[5]));
                        }else if("curTs".equals(args[1])){
                            taskService.getCurrentTask(args[2]);
                        }else if("next".equals(args[1])){
                            taskService.nextFLow(args[2]);
                        }else if("total".equals(args[1])){
                            taskService.getTotalTaskList(args[2],Integer.valueOf(args[3]));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
