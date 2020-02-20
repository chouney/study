package org.v3.client.service;


import org.v3.client.constant.IDTypeEnum;
import org.v3.client.constant.SequenceConfig;

import java.util.List;

/**
 * Created by ChrisZhang on 2020/2/14.
 */
public interface IdGeneratorService {

    long generateId(SequenceConfig sequenceConfig, IDTypeEnum idTypeEnum);

    List<Long> batchGenerateId(SequenceConfig sequenceConfig);

    //可逆
    Long getTimeStamp(Long genID);
    //可逆
    Integer getSeqId(Long genID);
}
