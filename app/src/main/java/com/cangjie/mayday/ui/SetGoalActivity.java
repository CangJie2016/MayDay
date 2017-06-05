package com.cangjie.mayday.ui;

import android.os.Bundle;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.SetGoalPresenter;
import com.cangjie.mayday.presenter.view.SetGoalView;

public class SetGoalActivity extends PresenterActivity<SetGoalPresenter> implements SetGoalView {
    @Override
    protected SetGoalPresenter createPresenter() {
        return new SetGoalPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);

        showTitle("制定目标");
        showBackButton();
    }
}
