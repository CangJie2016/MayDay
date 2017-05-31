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
    private int billType;
    private java.util.Date date;
    private int isDele; // 1 dele  0 normal
    @Generated(hash = 79910092)
    public Bill(Long id, Double money, int billType, java.util.Date date,
            int isDele) {
        this.id = id;
        this.money = money;
        this.billType = billType;
        this.date = date;
        this.isDele = isDele;
    }
    @Generated(hash = 1399599325)
    public Bill() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Double getMoney() {
        return this.money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public int getBillType() {
        return this.billType;
    }
    public void setBillType(int billType) {
        this.billType = billType;
    }
    public java.util.Date getDate() {
        return this.date;
    }
    public void setDate(java.util.Date date) {
        this.date = date;
    }
    public int getIsDele() {
        return this.isDele;
    }
    public void setIsDele(int isDele) {
        this.isDele = isDele;
    }
}