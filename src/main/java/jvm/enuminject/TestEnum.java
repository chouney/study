// Copyright (C) 2017 Meituan
// All rights reserved
package jvm.enuminject;

/**
 * @author manatea
 * @version 1.0
 * @created 2017/9/28 AM11:35
 **/
public enum TestEnum {

    ;

    private String module;
    private int id;
    private String desc;
    private int rank;

    TestEnum(String module, int id, String desc, int rank) {
        this.module = module;
        this.id = id;
        this.desc = desc;
        this.rank = rank;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
