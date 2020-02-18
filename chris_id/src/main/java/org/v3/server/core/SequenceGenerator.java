package org.v3.server.core;


import org.v3.client.constant.SequenceConfig;

/**
 * ID生成器工厂
 * Created by ChrisZhang on 2020/2/13.
 */
public interface SequenceGenerator {
    long generateID(SequenceConfig sequenceConfig);
    void setMachineID(Integer machineID);
    //可逆
    Long getTimeStamp(Long genID);
    //可逆
    Integer getSeqId(Long genID);
}
