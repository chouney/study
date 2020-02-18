package org.v3.server.service.impl;

import org.v3.server.core.SequenceGenerator;
import org.v3.client.constant.IDTypeEnum;
import org.v3.client.constant.SequenceConfig;
import org.v3.client.service.IdGeneratorService;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public class IdGeneratorServiceImpl implements IdGeneratorService {

    @Resource(name = "miliSecIdGenerator")
    private SequenceGenerator miliSecIdGenerator;

    @Resource(name = "secondIdGenerator")
    private SequenceGenerator secondIdGenerator;

    @Override
    public long generateId(SequenceConfig sequenceConfig, IDTypeEnum idTypeEnum) {
        return IDTypeEnum.SECOND.equals(idTypeEnum) ? secondIdGenerator.generateID(sequenceConfig) : miliSecIdGenerator.generateID(sequenceConfig);
    }

    @Override
    public List<Long> batchGenerateId(SequenceConfig sequenceConfig) {
        return null;
    }


    @Override
    public Long getTimeStamp(Long genID) {
        return (1 & genID >> 62) == IDTypeEnum.SECOND.getCode()  ? secondIdGenerator.getTimeStamp(genID) : miliSecIdGenerator.getTimeStamp(genID);
    }

    @Override
    public Integer getSeqId(Long genID) {
        return (1 & genID >> 62) == IDTypeEnum.SECOND.getCode()  ? secondIdGenerator.getSeqId(genID) : miliSecIdGenerator.getSeqId(genID);
    }



}
