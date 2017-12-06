package com.cangjie.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by 李振强 on 2017/5/31.
 */

@Entity
public class Password {
    @Id
    private Long id;
    private String title;
    private String username;
    private String password;
    private String remarks;
    private Date date;
    private int isDele; // 1 dele  0 normal

    @Generated(hash = 463328313)
    public Password(Long id, String title, String username, String password,
            String remarks, Date date, int isDele) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.password = password;
        this.remarks = remarks;
        this.date = date;
        this.isDele = isDele;
    }

    @Generated(hash = 565943725)
    public Password() {
    }

    @Override
    public String toString() {
        return id + "," + title + "," + username + "," + password + "," + remarks + "," + date.getTime() + "," + isDele ;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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


}