package com.cangjie.mayday.presenter;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.data.db.DbInit;
import com.cangjie.mayday.Constant;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.presenter.view.MainView;

public class MainPresenter extends BasePresenter<MainView> {

    private BillDao mBillDao;
    private BillTypeDao mBillTypeDao;

    public MainPresenter(MainView mvpView) {
        super(mvpView);
        mBillDao = MyApplication.getInstances().getDaoSession().getBillDao();
        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        if (!SpUtils.getCacheBoolean(MyApplication.mContext, Constant.SpKey.INIT_BILL_TYPE)){
            DbInit.init(mBillTypeDao);
            SpUtils.setCache(MyApplication.mContext, Constant.SpKey.INIT_BILL_TYPE, true);
        }
    }
}
