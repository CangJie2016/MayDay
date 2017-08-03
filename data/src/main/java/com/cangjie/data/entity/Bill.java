package com.cangjie.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;
import org.greenrobot.greendao.annotation.Generated;

import static android.os.Build.ID;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 李振强 on 2017/5/31.
 */

@Entity
public class Bill{
    @Id
    private Long id;
    private Double money;
    private Long billType;
    private java.util.Date date;
    private int isDele; // 1 dele  0 normal
    private String remarks;

    @Generated(hash = 1175771909)
    public Bill(Long id, Double money, Long billType, java.util.Date date,
            int isDele, String remarks) {
        this.id = id;
        this.money = money;
        this.billType = billType;
        this.date = date;
        this.isDele = isDele;
        this.remarks = remarks;
    }

    @Generated(hash = 1399599325)
    public Bill() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getBillType() {
        return billType;
    }

    public void setBillType(Long billType) {
        this.billType = billType;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getIsDele() {
        return isDele;
    }

    public void setIsDele(int isDele) {
        this.isDele = isDele;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}