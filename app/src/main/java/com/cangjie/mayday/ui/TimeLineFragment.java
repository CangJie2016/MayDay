package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangjie.basetool.mvp.base.PresenterFragment;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.LoadMoreAdapterWrapper;
import com.cangjie.mayday.adapter.TimeLineAdapter;
import com.cangjie.mayday.presenter.TimeLinePresenter;
import com.cangjie.mayday.presenter.view.TimeLineView;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.R.id.list;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class TimeLineFragment extends PresenterFragment<TimeLinePresenter> implements TimeLineView {
    private View rootView;
    @Bind(R.id.rv_timeline)
    RecyclerView rv_timeline;
    @Bind(R.id.tv_cost)
    TextView tv_cost;
    @Bind(R.id.tv_goal)
    TextView tv_goal;

    Context mContext;
    private TimeLineAdapter adapter;
    private LoadMoreAdapterWrapper mAdapter;
    private LoadMoreAdapterWrapper.ILoadCallback mLoadCallback;
    public static final String TIMELINE_ACTION = "com.cangjie.mayday.timeline_refresh";
    public static final String GOAL_ACTION = "com.cangjie.mayday.goal.refresh";
    private BroadcastReceiver refreshReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            initData();
        }
    };


    @Override
    protected TimeLinePresenter createPresenter() {
        return new TimeLinePresenter(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            //返回原有的布局对象  不往下走了
            return rootView;
        }
        mContext = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_time_line, container, false);
        rootView = setContentView(rootView);
        ButterKnife.bind(this, rootView);
        initData();
        showTitle("账单");
        showRightImageButton(R.drawable.btn_main_goal, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SetGoalActivity.class);
                startActivity(intent);
            }
        });
        rv_timeline.setLayoutManager(new LinearLayoutManager(mContext));
        rv_timeline.addItemDecoration(new DividerItemDecoration(mContext, R.color.white,
                DividerItemDecoration.VERTICAL_LIST));
        initRefreshBroadcast();
        return rootView;
    }

    private void initRefreshBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TIMELINE_ACTION);
        intentFilter.addAction(GOAL_ACTION);
        getActivity().registerReceiver(refreshReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(refreshReceiver);
        refreshReceiver = null;
    }

    private void initData() {
        mPresenter.loadMoneyTimeLine();
        mPresenter.loadCurrentMonthGoal();
        mPresenter.loadCurrentMonthCost();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void disPlay(String s) {
    }

    @Override
    public void currentMonthCost(double cost) {
        tv_cost.setText(getString(R.string.time_line_cost, String.valueOf(cost)));
    }

    @Override
    public void currentMonthGoal(String goalName, String goalNum) {
        if (TextUtils.isEmpty(goalName) || TextUtils.isEmpty(goalNum)){
            tv_goal.setText(getString(R.string.time_line_set_goal));
        }else{
            String goal = goalName + "-" + goalNum;
            tv_goal.setText(getString(R.string.time_line_goal, goal));
        }
    }

    @Override
    public void moneyTimeLineData(TimeLineAdapter list) {
        rv_timeline.setAdapter(list);
    }
}
