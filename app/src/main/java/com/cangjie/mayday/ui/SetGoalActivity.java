package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.mayday.Constant;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.SetGoalPresenter;
import com.cangjie.mayday.presenter.view.SetGoalView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetGoalActivity extends PresenterActivity<SetGoalPresenter> implements SetGoalView {
    @Bind(R.id.btn_confirm)
    Button btn_confirm;
    @Bind(R.id.et_goal_name)
    EditText et_goal_name;
    @Bind(R.id.et_need_money)
    EditText et_need_money;



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
        ButterKnife.bind(this);

        String goalName = SpUtils.getCache(mContext, Constant.SpKey.GOAL_NAME);
        String goalNum = SpUtils.getCache(mContext, Constant.SpKey.GOAL_NUM);
        if (!TextUtils.isEmpty(goalName) && !TextUtils.isEmpty(goalNum)){
            et_goal_name.setText(goalName);
            et_need_money.setText(goalNum);
        }
    }


    @OnClick(R.id.btn_confirm)
        public void confirm(){
        String goalName = et_goal_name.getText().toString();
        String goalNum = et_need_money.getText().toString();
        if ((!TextUtils.isEmpty(goalName) && TextUtils.isEmpty(goalNum)) || (TextUtils.isEmpty(goalName) && !TextUtils.isEmpty(goalNum))){
            disPlay("请确认您所输入的参数");
            return;
        }
        SpUtils.setCache(mContext, Constant.SpKey.GOAL_NAME, goalName);
        SpUtils.setCache(mContext, Constant.SpKey.GOAL_NUM, goalNum);
        sendRefreshBroadcast();
        finish();
    }

    private void sendRefreshBroadcast() {
        sendBroadcast(new Intent(TimeLineFragment.GOAL_ACTION));
    }
}
