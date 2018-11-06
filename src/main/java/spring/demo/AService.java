package spring.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.annotation.AopTest;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/6/19
 */
@Service("aService")
public class AService {

    @Autowired
    private BDao bDao;

    @AopTest
    public CModel getModel(){
        return bDao.getCModel();
    }

}
