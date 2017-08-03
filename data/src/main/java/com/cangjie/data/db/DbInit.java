package com.cangjie.data.db;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.data.entity.BillType;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李振强 on 2017/5/31.
 */

public class DbInit {
    public static void init(BillTypeDao mBillTypeDao) {
        List<BillType> list = new ArrayList<BillType>();
        list.add(new BillType("用餐"));
        list.add(new BillType("交通"));
        list.add(new BillType("服饰"));
        list.add(new BillType("丽人"));
        list.add(new BillType("日用品"));
        list.add(new BillType("娱乐"));
        list.add(new BillType("食材"));
        list.add(new BillType("零食"));
        list.add(new BillType("酒水"));
        list.add(new BillType("住房"));
        list.add(new BillType("通讯"));
        list.add(new BillType("家居"));
        list.add(new BillType("人情"));
        list.add(new BillType("学习"));
        list.add(new BillType("医疗"));
        list.add(new BillType("旅游"));
        list.add(new BillType("数码"));
        mBillTypeDao.insertInTx(list);//添加一个
    }
}
