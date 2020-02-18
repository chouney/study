package org.v3.server.core;


import org.v3.client.constant.IDTypeEnum;
import org.v3.client.constant.SequenceConfig;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public class MiliSecondSequenceGenerator extends AbstractSequenceGenerator {



    public MiliSecondSequenceGenerator() {
        super();
        //初始化配置
        setIdType(IDTypeEnum.MILISECOND.getCode());
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Long getTimeStamp(Long genID) {
        return (genID >> 20) & 0x000000FFFFFFFFFFL;
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Integer getSeqId(Long genID) {
        return (int) ((genID >> 10) & 0x3FF);
    }

    /**
     * 生成ID
     *
     * @return
     */
    @Override
    public long doGenerateID(SequenceConfig sequenceConfig) {
        //根据生成方式生成不同格式的ID
        return (((long)(( (this.getVersion() & 1) << 1 |
                (this.getIdType() & 1)) << 2 |
                (sequenceConfig.getGenerateType().getCode() & 0x3)) << 40 |
                (this.nextTimeStamp() & 0x000000FFFFFFFFFFL)) << 10 |
                (this.nextSeqId() & 0x3FF)) << 10 |
                (sequenceConfig.getMachineID() & 0x3FF);
    }


}
