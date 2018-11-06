package spring.demo;

import org.springframework.stereotype.Component;

/**
 * @author chriszhang
 * @version 1.0
 * @date 2018/6/19
 */
@Component
public class BDao {

    public CModel getCModel(){
        CModel cModel = new CModel();
        cModel.setAge(10);
        cModel.setName("Manatea");
        return cModel;
    }
}
