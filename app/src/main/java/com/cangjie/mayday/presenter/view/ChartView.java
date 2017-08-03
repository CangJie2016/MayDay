package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.data.entity.Bill;
import com.cangjie.mayday.domain.BillByPieChart;

import java.util.List;

/**
 * Created by 李振强 on 2017/6/7.
 */

public interface ChartView extends BaseView {
    void setPieData(List<BillByPieChart> billByPieCharts);

    void setPieDetailList(String typeName, List<Bill> bills);

    void setCurrentMonth(int currentYear, int currentMonth);

    void emptyPieDetailList();
}
