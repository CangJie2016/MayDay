package com.cangjie.data.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by 李振强 on 2017/5/31.
 */

@Entity
public class BillType{
    @Id
    private Long id;
    private String typeName;
    private int typeId;
    @Generated(hash = 1660893950)
    public BillType(Long id, String typeName, int typeId) {
        this.id = id;
        this.typeName = typeName;
        this.typeId = typeId;
    }
    public BillType(int typeId, String typeName) {
        this.typeName = typeName;
        this.typeId = typeId;
    }
    @Generated(hash = 778408781)
    public BillType() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTypeName() {
        return this.typeName;
    }
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    public int getTypeId() {
        return this.typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    
}
