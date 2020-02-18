package org.v2;

import org.v2.constant.IDTypeEnum;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public class SecondSequenceGenerator extends AbstractSequenceGenerator {



    public SecondSequenceGenerator(SequenceConfig sequenceConfig) {
       super(sequenceConfig,IDTypeEnum.SECOND);
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
    public long generateID() {
        //根据生成方式生成不同格式的ID
        return ((((((((( this.getVersion() & 1) << 1 |
                this.getIdType() & 1)) << 2 |
                this.getGenerateType() & 3)) << 30 |
                this.nextTimeStamp() & 0x3FFFFFFF)) << 20 |
                this.nextSeqId() & 0x3FF)) << 10 |
                (this.getMachineID() & 0x3FF);
    }


}
