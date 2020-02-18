package org.v1;


import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.IntUnaryOperator;
import java.util.function.LongUnaryOperator;

/**
 * 该64位 Sequence只支持毫秒级访问，只支持单机生成ID
 * Created by ChrisZhang on 2020/2/13.
 */
public class SimpleSequenceGenerator implements SequenceGenerator {

    /**
     * 机器ID 0-9位
     */
    private Integer machineID;

    /**
     * 序列号 10-19位
     */
    private AtomicInteger seqId = new AtomicInteger(0);

    /**
     * 时间戳数据，毫秒级的为20-59位
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
     * 版本类型，表示ID版本，63位
     */
    private Integer version;


    public SimpleSequenceGenerator(Integer generateType, Integer idType, Integer version) {
        this(null, generateType, idType, version);
    }


    public SimpleSequenceGenerator(Integer machineID, Integer generateType, Integer idType, Integer version) {
        this.machineID = machineID;
        this.generateType = generateType;
        this.idType = idType;
        this.version = version;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
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
        return (int) (genID >> 10 & 0x03FF);
    }

    /**
     * 生成ID
     *
     * @return
     */
    public long generateID() {
        //校验必填字段
        checkParamNotNull();

        return (((((long)((((( this.version & 1) << 1 |
                this.idType & 1)) << 2 |
                this.generateType & 0x3)) << 40 |
                this.nextTimeStamp() & 0x3FFFFFFF)) << 10 |
                this.nextSeqId() & 0x3FF)) << 10 |
                (this.machineID & 0x3FF);
    }

    private void checkParamNotNull() {
        if (this.machineID == null || this.idType == null
                || this.generateType == null
                || this.version == null) {
            throw new RuntimeException("invalid Param");
        }
    }

    private Integer nextSeqId() {
        //同时保持形成序列闭环以及生成序列的原子性
        return this.seqId.getAndUpdate(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                //取余10位
                return (operand + 1) & 0x03FF;
            }
        });
    }

    private Long nextTimeStamp() {
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
}
