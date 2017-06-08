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
        list.add(new BillType(1, "用餐"));
        list.add(new BillType(2, "交通"));
        list.add(new BillType(3, "服饰"));
        list.add(new BillType(4, "丽人"));
        list.add(new BillType(5, "日用品"));
        list.add(new BillType(6, "娱乐"));
        list.add(new BillType(7, "食材"));
        list.add(new BillType(8, "零食"));
        list.add(new BillType(9, "酒水"));
        list.add(new BillType(10, "住房"));
        list.add(new BillType(11, "通讯"));
        list.add(new BillType(12, "家居"));
        list.add(new BillType(13, "人情"));
        list.add(new BillType(14, "学习"));
        list.add(new BillType(15, "医疗"));
        list.add(new BillType(16, "旅游"));
        list.add(new BillType(17, "数码"));
        mBillTypeDao.insertInTx(list);//添加一个
    }
}
