// Copyright (C) 2016 Meituan
// All rights reserved
package jvm.enuminject.common;

import java.util.List;

/**
 * 多层标签结构
 * @author yuqifeng
 * @version 1.0
 * @created 16/1/27 上午10:55
 **/
public class MultiLevelLabel {

    /**
     * 标签id...
     */
    private int id;

    /**
     * 父级标签id
     */
    private int parentId = 0;

    /**
     * 标签描述...
     */
    private String desc;

    /**
     * 是否勾选...
     */
    private boolean isChecked = false;

    /**
     * 子标签...
     */
    List<MultiLevelLabel> subLabels;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<MultiLevelLabel> getSubLables() {
        return subLabels;
    }

    public void setSubLables(List<MultiLevelLabel> subLabels) {
        this.subLabels = subLabels;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }
}