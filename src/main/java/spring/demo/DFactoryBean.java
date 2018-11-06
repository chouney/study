package spring.demo;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/6/19
 */
@Component("dFactoryBean")
public class DFactoryBean implements FactoryBean<CModel>{

    @Override
    public CModel getObject() throws Exception {
        CModel b = new CModel();
        b.setName("factoryBean");
        return b;
    }

    @Override
    public Class<?> getObjectType() {
        return CModel.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
