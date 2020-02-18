package org;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.v3.client.constant.IDGenerateTypeEnum;
import org.v3.client.constant.IDTypeEnum;
import org.v3.client.constant.SequenceConfig;
import org.v3.client.service.IdGeneratorService;

import java.io.IOException;

/**
 * Created by ChrisZhang on 2020/2/17.
 */
public class Application {

    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/chris-consumer.xml");
        context.start();
        SequenceConfig sequenceConfig = new SequenceConfig(IDGenerateTypeEnum.CLIENT_SDK,1);
        while(true){
            System.in.read();
            IdGeneratorService idGeneratorService = context.getBean("idGeneratorService", IdGeneratorService.class);
            Long genId = idGeneratorService.generateId(sequenceConfig, IDTypeEnum.MILISECOND);
            System.out.println("生成的ID为： "+ genId + "           时间戳:  "+ idGeneratorService.getTimeStamp(genId) + "      序列为："+ idGeneratorService.getSeqId(genId));
        }
    }
}
