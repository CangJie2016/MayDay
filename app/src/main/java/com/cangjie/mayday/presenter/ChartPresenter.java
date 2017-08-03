package com.cangjie.mayday.presenter;

import android.util.SparseArray;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.entity.Bill;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.domain.BillByPieChart;
import com.cangjie.mayday.presenter.view.ChartView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 李振强 on 2017/6/7.
 */

public class ChartPresenter extends BasePresenter<ChartView> {

    private final BillDao mBillDao;
    private final SimpleDateFormat format;
    private List<Bill> bills;
    private List<BillType> mBillTypeList;
    private SparseArray<List<Bill>> billMaps;

    private int currentYear;
    private int currentMonth;

    public ChartPresenter(ChartView mvpView) {
        super(mvpView);
        mBillDao = MyApplication.getInstances().getDaoSession().getBillDao();
        format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
    }

    // 初始化账单类型及账单内容，可用于刷新广播和初始化
    public void refreshData(){
        String mBeginDate = String.valueOf(currentYear) + monthZeroFill(currentMonth) + "01";
        int endYear = calculateNextMonthWhichYear(currentYear, currentMonth);
        int endMonth = calculateNextMonthWhichMonth(currentYear, currentMonth);
        String mEndDate = String.valueOf(endYear) + monthZeroFill(endMonth) + "01";

        DebugLog.w(mBeginDate + "," + mEndDate);
        BillTypeDao mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        mBillTypeList = mBillTypeDao.loadAll();
        queryMonthBill(mBeginDate, mEndDate);
    }

    // 查询月份账单，可用于切换月份时使用
    public void queryMonthBill(String beginDate, String endDate) {
        // 重置数据
        if (bills != null)
            bills.clear();
        try {
            Date parse1 = format.parse(beginDate);
            Date parse2 = format.parse(endDate);

            bills = mBillDao.queryBuilder().where(BillDao.Properties.Date.between(parse1,parse2)).orderDesc(BillDao.Properties.Date).list();
            DebugLog.w("size" + bills.size());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        billMaps = putDataToMap();

        List<BillByPieChart> billByPieCharts = makePieDataByMap(billMaps);

        mvpView.setPieData(billByPieCharts);
        findMapElementByIndex(0);
    }

    private List<BillByPieChart> makePieDataByMap(SparseArray<List<Bill>> billMaps) {
        List<BillByPieChart> billByPieCharts = new ArrayList<>();
        for (int i = 0; i< billMaps.size(); i++){
            List<Bill> bills = billMaps.valueAt(i);
            double sumCost = 0;
            long billTypeId = bills.get(0).getBillType();
            String billTypeName = typeName(billTypeId);
            for (Bill bill : bills)
                sumCost += bill.getMoney();
            billByPieCharts.add(new BillByPieChart(billTypeName, billTypeId, sumCost));
        }
        return billByPieCharts;
    }

    private SparseArray<List<Bill>> putDataToMap() {
        SparseArray<List<Bill>> billMaps = new SparseArray<>();
        for (Bill bill : bills){
            int billType = Integer.valueOf(String.valueOf(bill.getBillType()));
            List<Bill> tempBills = billMaps.get(billType);
            // 若value为空，则创建一个
            if (tempBills == null){
                tempBills = new ArrayList<>();
                billMaps.put(billType, tempBills);
            }
            // 直接往里面放
            tempBills.add(bill);
        }
        DebugLog.w("map size" + billMaps.size());
        return billMaps;
    }

    private String typeName(long billType) {
        for(BillType type : mBillTypeList){
            if (type.getId() == billType)
                return type.getTypeName();
        }
        return null;
    }


    public void findMapElementByIndex(int x) {
        if(billMaps == null || billMaps.size() == 0){
            mvpView.emptyPieDetailList();
            return;
        }
        List<Bill> bills = billMaps.valueAt(x);
        mvpView.setPieDetailList(typeName(bills.get(0).getBillType()), bills);
    }

    public void initDate() {
        Date date = new Date();
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.CHINA);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.CHINA);
        currentYear = Integer.valueOf(yearFormat.format(date));
        currentMonth = Integer.valueOf(monthFormat.format(date));
        DebugLog.w("CurrentYear" + currentYear);
        DebugLog.w("currentMonth" + currentMonth);
        mvpView.setCurrentMonth(currentYear, currentMonth);
        refreshData();
    }

    // 计算上月月份的年份
    private int calculateLastMonthWhichYear(int year, int month){
        if (month == 1){
            year -= 1;
        }
        return year;
    }
    // 计算上月月份的月份
    private int calculateLastMonthWhichMonth(int year, int month){
        if (month == 1){
            month = 12;
        }else{
            month -= 1;
        }
        return month;
    }
    // 计算下月月份的年份
    private int calculateNextMonthWhichYear(int year, int month){
        if (month == 12){
            year += 1;
        }
        return year;
    }

    // 计算下月月份的月份
    private int calculateNextMonthWhichMonth(int year, int month){
        if (month == 12){
            month = 1;
        }else{
            month += 1;
        }
        return month;
    }

    private String monthZeroFill(int month) {
        if (month >= 10){
            return String.valueOf(month);
        }else{
            return "0" + String.valueOf(month);
        }
    }

    public void nextMonth() {
        currentYear = calculateNextMonthWhichYear(currentYear, currentMonth);
        currentMonth = calculateNextMonthWhichMonth(currentYear, currentMonth);
        mvpView.setCurrentMonth(currentYear, currentMonth);
        refreshData();
    }

    public void lastMonth() {
        currentYear = calculateLastMonthWhichYear(currentYear, currentMonth);
        currentMonth = calculateLastMonthWhichMonth(currentYear, currentMonth);
        mvpView.setCurrentMonth(currentYear, currentMonth);
        refreshData();
    }
}
