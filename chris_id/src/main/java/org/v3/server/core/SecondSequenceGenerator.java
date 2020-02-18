package org.v3.server.core;


import org.v3.client.constant.IDTypeEnum;
import org.v3.client.constant.SequenceConfig;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public class SecondSequenceGenerator extends AbstractSequenceGenerator {



    public SecondSequenceGenerator() {
       super();
        //初始化配置
        setIdType(IDTypeEnum.SECOND.getCode());
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Long getTimeStamp(Long genID) {
        return genID >> 30 & 0x3FFFFFFF;
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Integer getSeqId(Long genID) {
        return (int) (genID >> 10 & 0xFFFFF);
    }

    /**
     * 生成ID
     *
     * @return
     */
    @Override
    public long doGenerateID(SequenceConfig sequenceConfig) {
        //根据生成方式生成不同格式的ID
        return (((( (this.getVersion() & 1) << 1 |
                (this.getIdType() & 1)) << 2 |
                (sequenceConfig.getGenerateType().getCode() & 0x3)) << 30 |
                (this.nextTimeStamp() & 0x3FFFFFFF)) << 20 |
                (this.nextSeqId() & 0xFFFFF)) << 10 |
                (sequenceConfig.getMachineID() & 0x3FF);
    }


}
