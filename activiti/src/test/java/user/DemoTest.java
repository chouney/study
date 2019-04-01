package user;

import org.activiti.engine.impl.test.AbstractTestCase;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/3/26
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTest{

    @Rule
    public ActivitiRule activitiRule = new ActivitiRule("activiti.cfg.xml");

    @Before
    public void setUp(){

    }


}
