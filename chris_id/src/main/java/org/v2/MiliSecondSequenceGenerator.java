package org.v2;

import org.v2.constant.IDTypeEnum;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public class MiliSecondSequenceGenerator extends AbstractSequenceGenerator {



    public MiliSecondSequenceGenerator(SequenceConfig sequenceConfig) {
       super(sequenceConfig, IDTypeEnum.MILISECOND);
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Long getTimeStamp(Long genID) {
        return genID >> 20 & 0x000000FFFFFFFFFFL;
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    @Override
    public Integer getSeqId(Long genID) {
        return (int) (genID >> 10 & 0x3FF);
    }

    /**
     * 生成ID
     *
     * @return
     */
    @Override
    public long generateID() {
        //根据生成方式生成不同格式的ID
        return (((((long)((((( this.getVersion() & 1) << 1 |
                this.getIdType() & 1)) << 2 |
                this.getGenerateType() & 3)) << 40 |
                this.nextTimeStamp() & 0x000000FFFFFFFFFFL)) << 10 |
                this.nextSeqId() & 0x3FF)) << 10 |
                (this.getMachineID() & 0x3FF);
    }


}
