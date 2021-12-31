package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.AlertBillTypeAdapter;
import com.cangjie.mayday.presenter.AlertBillTypePresenter;
import com.cangjie.mayday.presenter.view.AlertBillTypeView;
import com.cangjie.mayday.view.DividerGridItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AlertBillTypeActivity extends PresenterActivity<AlertBillTypePresenter> implements AlertBillTypeView {

    @BindView(R.id.rv_bill_type)
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
        showRightImageButton(R.drawable.btn_add_type, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AlertBillTypeActivity.this, BillTypeDetailActivity.class);
                intent.putExtra("isAlter", false);
                startActivity(intent);
            }
        });
        mPresenter.obatinBillType();
        rv_bill_type.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_bill_type.addItemDecoration(new DividerGridItemDecoration(mContext));

        initBroadcast();
    }

    private void initBroadcast() {
        refreshTypeBroadcast = new RefreshTypeBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BillTypeDetailActivity.REFRESH_TYPE_ACTION);
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
