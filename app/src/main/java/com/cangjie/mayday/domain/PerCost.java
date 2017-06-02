package com.cangjie.mayday.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李振强 on 2017/6/2.
 */

public class PerCost implements Serializable {
    private long id;
    private int costType;// 消费类型
    private double costMoney; // 消费金额
    private Date date; // 记录时间
    private String remark; //备注

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getCostType() {
        return costType;
    }

    public void setCostType(int costType) {
        this.costType = costType;
    }

    public double getCostMoney() {
        return costMoney;
    }

    public void setCostMoney(double costMoney) {
        this.costMoney = costMoney;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}

