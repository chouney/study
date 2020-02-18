package org.v3.client.constant;


import com.alibaba.com.caucho.hessian.io.Serializer;

import java.io.Serializable;

/**
 * 序列生成配置器
 * 配置可读取
 * Created by ChrisZhang on 2020/2/14.
 */
public class SequenceConfig implements Serializable{

    private static final long serialVersionUID = 42L;

    /**
     * 生成方式，指发布方式为客户端发布、客户端调用服务器发布或者服务端发布， 60-61位
     */
    private IDGenerateTypeEnum generateType;


     /**
     * 机器ID 0-9位
     */
    private Integer machineID;

    public SequenceConfig() {
    }

    public SequenceConfig(IDGenerateTypeEnum generateType, Integer machineID) {
        this.generateType = generateType;
        this.machineID = machineID;
        checkParamNotNull();
    }


    public void setGenerateType(IDGenerateTypeEnum generateType) {
        this.generateType = generateType;
    }

    public void setMachineID(Integer machineID) {
        this.machineID = machineID;
    }

    public IDGenerateTypeEnum getGenerateType() {
        return generateType;
    }


    public Integer getMachineID() {
        return machineID;
    }

    private void checkParamNotNull() {
        if (this.machineID == null || this.generateType == null ){
            throw new RuntimeException("invalid Config");
        }
    }

    /**
     * 从配置文件中读取Id生成类型等配置，预留功能可以自行实现
     *
     */
    public void readConfigInProperties(){
    }



}
