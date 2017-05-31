package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.EditText;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.data.entity.Bill;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.presenter.AddBillPresenter;
import com.cangjie.mayday.presenter.view.AddBillView;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBillActivity extends PresenterActivity<AddBillPresenter> implements AddBillView {

    @Bind(R.id.rv_bill_type)
    RecyclerView rv_bill_type;
    @Bind(R.id.et_money)
    EditText et_money;


    @Override
    protected AddBillPresenter createPresenter() {
        return new AddBillPresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);
        ButterKnife.bind(this);
        showTitle("记账");
        showBackButton();
        initView();
        mPresenter.obatinBillType();
    }

    private void initView() {
        double money = getIntent().getDoubleExtra("bill", 0);
        if (money != 0)
            et_money.setText(String.valueOf(money));
        rv_bill_type.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_bill_type.addItemDecoration(new DividerItemDecoration(mContext, R.color.white,
                DividerItemDecoration.VERTICAL_LIST));
    }

    @OnClick(R.id.btn_add)
    public void add(){
        String money = et_money.getText().toString();
        if(TextUtils.isEmpty(money)){
            disPlay("请填写正确的金额");
            return;
        }
    Double aDouble = Double.valueOf(money);
        mPresenter.addBill(aDouble);
}

    @Override
    public void setBillTypeAdapter(BillTypeAdapter billTypeAdapter) {
        rv_bill_type.setAdapter(billTypeAdapter);
    }

    @Override
    public void addBillSuccess() {
        sendRefreshBroadcast();
        finish();
    }

    private void sendRefreshBroadcast() {
        sendBroadcast(new Intent(TimeLineFragment.ACTION));
    }
}
