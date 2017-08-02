package com.cangjie.mayday.presenter;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.AlertBillTypeAdapter;
import com.cangjie.mayday.presenter.view.AlertBillTypeView;
import com.cangjie.mayday.ui.AlertBillTypeActivity;

import java.util.List;

public class AlertBillTypePresenter extends BasePresenter<AlertBillTypeView> {

    private final AlertBillTypeActivity mActivity;
    private BillTypeDao mBillTypeDao;
    private AlertBillTypeAdapter billTypeAdapter;

    public AlertBillTypePresenter(AlertBillTypeView mvpView, AlertBillTypeActivity activity) {
        super(mvpView);
        this.mActivity = activity;

        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
    }

    public void obatinBillType() {
        List<BillType> billTypeList = mBillTypeDao.loadAll();
        // 手动在末尾添加“自定义”选项
        billTypeList.add(new BillType());

        billTypeAdapter = new AlertBillTypeAdapter(mActivity, billTypeList);
        mvpView.setBillTypeAdapter(billTypeAdapter);
    }

}
