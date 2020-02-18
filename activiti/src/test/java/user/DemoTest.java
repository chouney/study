package user;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.test.AbstractTestCase;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/3/26
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoTest.class)
public class DemoTest{

    private Logger logger = LoggerFactory.getLogger(DemoTest.class);


    @Before
    public void setUp(){

    }


    @Test
    public void deployProcessDefinition(){
        //create a new processEngine
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        //get repositorySerive
        RepositoryService repositoryService = processEngine.getRepositoryService();

        repositoryService.createDeployment().addClasspathResource("categorize-text.bpmn20.xml")
                .deploy();
        logger.info("Number of process definitions: " + repositoryService.createProcessDefinitionQuery().count());
    }


}
