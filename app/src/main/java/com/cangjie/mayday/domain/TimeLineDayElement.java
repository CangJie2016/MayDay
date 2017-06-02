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

}
