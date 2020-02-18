package org.v3.server.core;

import org.v3.client.constant.SequenceConfig;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public abstract class AbstractSequenceGenerator implements SequenceGenerator {

    private int DEFAULT_OTHER_FIELD_BIT = 1;

    /**
     * 机器ID 0-9位
     */
    private Integer machineID;

    /**
     * 序列号 毫秒级10-19位；秒级10-29位
     */
    private AtomicInteger seqId = new AtomicInteger(0);

    /**
     * 时间戳数据，毫秒级的为20-59位；秒级30-59位
     */
    private AtomicLong timestamp = new AtomicLong(0);

    /**
     * 生成方式，指发布方式为客户端发布、客户端调用服务器发布或者服务端发布， 60-61位
     */
    private Integer generateType;

    /**
     * ID类型，表示毫秒级或秒级或其他ID类型，62位
     */
    private Integer idType;

    /**
     * 版本类型，表示ID版本，63位 ,测试环境版本为0，生产环境版本为1
     */
    private Integer version = 0;

    /**
     * SequenceConfig配置项
     */
    private SequenceConfig sequenceConfig;


    public void setSequenceConfig(SequenceConfig sequenceConfig) {
        this.machineID = sequenceConfig.getMachineID();
        this.generateType = sequenceConfig.getGenerateType().getCode();
        this.sequenceConfig = sequenceConfig;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    public abstract Long getTimeStamp(Long genID);

    /**
     * 可逆向解析
     * @param genID
     * @return
     */
    public abstract Integer getSeqId(Long genID);

    /**
     * 生成ID
     *
     * @return
     */
    public long generateID(SequenceConfig sequenceConfig){
        //检查参数
        checkParamNotNull(sequenceConfig);

        //生成ID
        Long genId =  doGenerateID(sequenceConfig);
        System.out.println("testTread——"+sequenceConfig.getMachineID() +"          id: "+genId +
                "          timestamp:"+getTimeStamp(genId) + "                seqId:"+getSeqId(genId));
        return genId;
    }

    public abstract long doGenerateID(SequenceConfig sequenceConfig);

    protected void checkParamNotNull(SequenceConfig sequenceConfig) {
        if (sequenceConfig.getMachineID() == null || this.idType == null
                || sequenceConfig.getGenerateType() == null || this.version == null) {
            throw new RuntimeException("invalid Param");
        }
    }

    protected Integer nextSeqId() {
        //同时保持形成序列闭环以及生成序列的原子性
        return this.seqId.getAndUpdate(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                //取余10位
                return (operand + 1) & 0x03FF;
            }
        });
    }

    protected Long nextTimeStamp() {
        return this.timestamp.updateAndGet(new LongUnaryOperator() {
            @Override
            public long applyAsLong(long operand) {
                long curTimeStamp;
                //保证新生成时间大于上次生成时间，防止时间回退现象
                while ((curTimeStamp = System.currentTimeMillis()) < operand) ;
                return curTimeStamp;
            }
        });
    }

    protected Integer getMachineID() {
        return machineID;
    }

    protected Integer getGenerateType() {
        return generateType;
    }

    protected Integer getIdType() {
        return idType;
    }

    protected void setIdType(Integer idType) {
        this.idType = idType;
    }

    protected Integer getVersion() {
        return version;
    }

    protected SequenceConfig getSequenceConfig() {
        return sequenceConfig;
    }

}
