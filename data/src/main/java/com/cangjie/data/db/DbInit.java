package com.cangjie.data.db;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.data.entity.BillType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李振强 on 2017/5/31.
 */

public class DbInit {
    public static void init(BillTypeDao mBillTypeDao){
        List<BillType> list = new ArrayList<BillType>();
        BillType mBillType = new BillType();
        mBillType.setTypeId(1);
        mBillType.setTypeName("买菜");
        BillType mBillType2 = new BillType();
        mBillType2.setTypeId(2);
        mBillType2.setTypeName("零食");
        BillType mBillType3 = new BillType();
        mBillType3.setTypeId(3);
        mBillType3.setTypeName("超市");
        BillType mBillType4 = new BillType();
        mBillType4.setTypeId(4);
        mBillType4.setTypeName("话费");
        list.add(mBillType);
        list.add(mBillType2);
        list.add(mBillType3);
        list.add(mBillType4);
        mBillTypeDao.insertInTx(list);//添加一个
    }
}
