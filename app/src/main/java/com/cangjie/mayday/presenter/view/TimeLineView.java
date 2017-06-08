package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.mayday.adapter.TimeLineAdapter;

/**
 * Created by 李振强 on 2017/5/26.
 */

public interface TimeLineView extends BaseView{
    void currentMonthCost(double cost);

    void currentMonthGoal(String goal, String goalNum);

    void moneyTimeLineData(TimeLineAdapter list);
}
