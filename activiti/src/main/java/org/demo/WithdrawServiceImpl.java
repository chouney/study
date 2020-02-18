package org.demo;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/7/11
 */
public class WithdrawServiceImpl implements JavaDelegate {
    private static final Logger LOG = LoggerFactory.getLogger(WithdrawServiceImpl.class);

    @Override
    public void execute(DelegateExecution execution) {
        String appId = (String) execution.getVariable("appId");
        LOG.info("发起用户支用,appId:{}",appId);
    }
}
