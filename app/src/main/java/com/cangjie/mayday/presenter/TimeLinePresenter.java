package com.cangjie.mayday.presenter;

import android.app.Activity;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.data.entity.Bill;
import com.cangjie.mayday.Constant;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.TimeLineAdapter;
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.domain.TimeLineDayElement;
import com.cangjie.mayday.presenter.view.TimeLineView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class TimeLinePresenter extends BasePresenter<TimeLineView> {

    private final Activity mActivity;
    private BillDao mBillDao;
    private BillTypeDao mBillTypeDao;
    private final SimpleDateFormat mMonthDaySimpleDateFormat;
    private List<Bill> bills;
    private final SimpleDateFormat mYearMonthSimpleDateFormat;

    public TimeLinePresenter(TimeLineView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
        mBillDao = MyApplication.getInstances().getDaoSession().getBillDao();
        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();

        mMonthDaySimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        mYearMonthSimpleDateFormat = new SimpleDateFormat("yyyyMM", Locale.CHINA);
    }

    public void loadCurrentMonthCost() {
        double cost = 0.0;
        String currentYearMonth = mYearMonthSimpleDateFormat.format(new Date());
        for(Bill bill : bills){
            String yearMonth = mYearMonthSimpleDateFormat.format(bill.getDate());
            if (currentYearMonth.equals(yearMonth))
                cost += bill.getMoney();
        }
        mvpView.currentMonthCost(cost);
    }

    public void loadCurrentMonthGoal() {
        String goalName = SpUtils.getCache(MyApplication.mContext, Constant.SpKey.GOAL_NAME);
        String goalNum = SpUtils.getCache(MyApplication.mContext, Constant.SpKey.GOAL_NUM);
        mvpView.currentMonthGoal(goalName, goalNum);
    }

    public void loadMoneyTimeLine() {
        bills = mBillDao.queryBuilder().orderDesc(BillDao.Properties.Date).list();
        List<TimeLineDayElement> timelineList = dataTransformToModel(bills);
        TimeLineAdapter timeLineAdapter = new TimeLineAdapter(mActivity, timelineList, null);
        mvpView.moneyTimeLineData(timeLineAdapter);
    }

    private List<TimeLineDayElement> dataTransformToModel(List<Bill> bills) {
        List<TimeLineDayElement> list = new ArrayList<>();
        for (Bill bill : bills){
            Date date = bill.getDate();
            String monthDay = mMonthDaySimpleDateFormat.format(date);
            TimeLineDayElement element = isIncludeMonthDay(list, monthDay);
            // 初始化timeLine每一个子对象
            if (element == null){
                element = new TimeLineDayElement();
                element.setDate(monthDay);
                element.setCostList(new ArrayList<PerCost>());
                list.add(element);
            }
            PerCost cost = new PerCost();
            cost.setId(bill.getId());
            cost.setCostMoney(bill.getMoney());
            cost.setCostType(bill.getBillType());
            cost.setDate(bill.getDate());
            cost.setRemark(bill.getRemarks());
            List<PerCost> originList = element.getCostList();
            originList.add(cost);
            element.setCostList(originList);
        }
        return list;
    }

    // 判断当前LIST是否有MMdd的集合
    private TimeLineDayElement isIncludeMonthDay(List<TimeLineDayElement> list, String monthDay) {
        for (TimeLineDayElement element : list){
            if (element.getDate().equals(monthDay)){
                return element;
            }
        }
        return null;
    }
}
