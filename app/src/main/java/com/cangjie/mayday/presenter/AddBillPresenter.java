package com.cangjie.mayday.presenter;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.data.entity.Bill;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.domain.PerCost;
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

    public void addBill(PerCost currentPerCost) {
        Bill bill = new Bill();
        bill.setMoney(currentPerCost.getCostMoney());
        bill.setRemarks(currentPerCost.getRemark());
        bill.setDate(new Date());
        bill.setBillType(billTypeAdapter.getCurrentTypeId());
        long insert = mBillDao.insert(bill);
        if (insert != -1)
            mvpView.addBillSuccess();
    }

    public void alterBill(PerCost currentPerCost) {
        Bill bill = new Bill();
        bill.setId(currentPerCost.getId());
        bill.setMoney(currentPerCost.getCostMoney());
        bill.setRemarks(currentPerCost.getRemark());
        bill.setDate(currentPerCost.getDate());
        bill.setBillType(billTypeAdapter.getCurrentTypeId());
        mBillDao.update(bill);
        mvpView.addBillSuccess();
    }

    public void setCurrentCostType(int costType) {
        billTypeAdapter.setCurrentCostType(costType);
        billTypeAdapter.notifyDataSetChanged();
    }

    public void deleteRecord(long id) {
        mBillDao.deleteByKey(id);
        mvpView.deleteSuccess();
    }
}
