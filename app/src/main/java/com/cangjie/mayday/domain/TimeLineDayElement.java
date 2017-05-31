package com.cangjie.mayday.domain;

import java.util.List;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class TimeLineDayElement {
    private String date;
    private String sumCost;
    private List<PerCost> costList;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSumCost() {
        return sumCost;
    }

    public void setSumCost(String sumCost) {
        this.sumCost = sumCost;
    }

    public List<PerCost> getCostList() {
        return costList;
    }

    public void setCostList(List<PerCost> costList) {
        this.costList = costList;
    }

    public class PerCost{
        private int costType;// 消费类型
        private String costMoney; // 消费金额
        private String time; // 记录时间
        private String remark; //备注

        public int getCostType() {
            return costType;
        }

        public void setCostType(int costType) {
            this.costType = costType;
        }

        public String getCostMoney() {
            return costMoney;
        }

        public void setCostMoney(String costMoney) {
            this.costMoney = costMoney;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }

}
