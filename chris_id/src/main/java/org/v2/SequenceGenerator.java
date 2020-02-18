package org.v2;


/**
 * ID生成器工厂
 * Created by ChrisZhang on 2020/2/13.
 */
public interface SequenceGenerator {
    long generateID();
    void setMachineID(Integer machineID);
    //可逆
    Long getTimeStamp(Long genID);
    //可逆
    Integer getSeqId(Long genID);
}
