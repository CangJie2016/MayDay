package com.cangjie.mayday.presenter;

import android.app.Activity;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.data.entity.Bill;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.TimeLineAdapter;
import com.cangjie.mayday.adapter.TimeLineAdapter2;
import com.cangjie.mayday.domain.TimeLineDayElement;
import com.cangjie.mayday.presenter.view.TimeLineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class TimeLinePresenter extends BasePresenter<TimeLineView> {

    private final Activity mActivity;
    private BillDao mBillDao;
    private BillTypeDao mBillTypeDao;
    public TimeLinePresenter(TimeLineView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
        mBillDao = MyApplication.getInstances().getDaoSession().getBillDao();
        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
    }

    public void loadCurrentMonthCost() {
        String cost = "1200.00";
        mvpView.currentMonthCost(cost);
    }

    public void loadCurrentMonthGoal() {
        String goal = "10.00";
        mvpView.currentMonthGoal(goal);
    }

    public void loadMoneyTimeLine() {
        List<Bill> bills = mBillDao.queryBuilder().orderDesc(BillDao.Properties.Id).list();
        TimeLineAdapter2 timeLineAdapter2 = new TimeLineAdapter2(mActivity, bills, new TimeLineAdapter2.OnTimeLineItemClickListener() {
        });
        mvpView.moneyTimeLineData(timeLineAdapter2);
    }
}
