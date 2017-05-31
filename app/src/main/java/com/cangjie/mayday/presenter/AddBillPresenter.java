package com.cangjie.mayday.presenter;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.anye.greendao.gen.UserDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.data.db.DbInit;
import com.cangjie.data.entity.Bill;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.Constant;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.presenter.view.AddBillView;
import com.cangjie.mayday.ui.AddBillActivity;

import java.util.Date;
import java.util.List;

public class AddBillPresenter extends BasePresenter<AddBillView> {

    private final AddBillActivity mActivity;
    private BillDao mBillDao;
    private BillTypeDao mBillTypeDao;
    private BillTypeAdapter billTypeAdapter;

    public AddBillPresenter(AddBillView mvpView, AddBillActivity activity) {
        super(mvpView);
        this.mActivity = activity;

        mBillDao = MyApplication.getInstances().getDaoSession().getBillDao();
        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
    }

    public void obatinBillType() {
        List<BillType> billTypeList = mBillTypeDao.loadAll();
        billTypeAdapter = new BillTypeAdapter(mActivity, billTypeList);
        mvpView.setBillTypeAdapter(billTypeAdapter);
    }

    public void addBill(Double aDouble) {
        Bill bill = new Bill();
        bill.setMoney(aDouble);
        bill.setDate(new Date());
        bill.setBillType(billTypeAdapter.getCurrentTypeId());
        long insert = mBillDao.insert(bill);
        if (insert != -1)
            mvpView.addBillSuccess();
    }
}
