package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.AlertBillTypeAdapter;
import com.cangjie.mayday.presenter.AlertBillTypePresenter;
import com.cangjie.mayday.presenter.view.AlertBillTypeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AlertBillTypeActivity extends PresenterActivity<AlertBillTypePresenter> implements AlertBillTypeView {

    @Bind(R.id.rv_bill_type)
    RecyclerView rv_bill_type;
    private RefreshTypeBroadcast refreshTypeBroadcast;

    @Override
    protected AlertBillTypePresenter createPresenter() {
        return new AlertBillTypePresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_bill_type);
        ButterKnife.bind(this);
        showTitle("编辑类型");
        showBackButton();
        mPresenter.obatinBillType();
        rv_bill_type.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_bill_type.addItemDecoration(new DividerItemDecoration(mContext, R.color.white,
                DividerItemDecoration.VERTICAL_LIST));

        refreshTypeBroadcast = new RefreshTypeBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(AlertBillTypeDetailActivity.REFRESH_TYPE_ACTION);
        registerReceiver(refreshTypeBroadcast, intentFilter);
    }

    @Override
    public void setBillTypeAdapter(AlertBillTypeAdapter billTypeAdapter) {
        rv_bill_type.setAdapter(billTypeAdapter);
    }

    public class RefreshTypeBroadcast extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.obatinBillType();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshTypeBroadcast);
    }
}
