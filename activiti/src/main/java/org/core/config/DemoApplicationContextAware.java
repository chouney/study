package org.core.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2019/5/7
 */
@Component
public class DemoApplicationContextAware implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        DemoApplicationContextAware.context = applicationContext;
    }

    public static ApplicationContext getContext(){
        return context;
    }
}
