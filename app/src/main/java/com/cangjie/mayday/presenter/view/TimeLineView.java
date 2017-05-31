package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.mayday.adapter.TimeLineAdapter2;
import com.cangjie.mayday.domain.TimeLineDayElement;

import java.util.List;

/**
 * Created by 李振强 on 2017/5/26.
 */

public interface TimeLineView extends BaseView{
    void currentMonthCost(String cost);

    void currentMonthGoal(String goal);

    void moneyTimeLineData(TimeLineAdapter2 list);
}
