package com.cangjie.mayday.domain;

/**
 * Created by 李振强 on 2017/6/8.
 */

public class BillByPieChart {
    private String billTypeName;
    private int billType;
    private double sumCost;

    public BillByPieChart(String billTypeName, int billType, double sumCost) {
        this.billTypeName = billTypeName;
        this.billType = billType;
        this.sumCost = sumCost;
    }

    public String getBillTypeName() {
        return billTypeName;
    }

    public void setBillTypeName(String billTypeName) {
        this.billTypeName = billTypeName;
    }

    public int getBillType() {
        return billType;
    }

    public void setBillType(int billType) {
        this.billType = billType;
    }

    public double getSumCost() {
        return sumCost;
    }

    public void setSumCost(double sumCost) {
        this.sumCost = sumCost;
    }
}
