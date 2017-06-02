package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.presenter.AddBillPresenter;
import com.cangjie.mayday.presenter.view.AddBillView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddBillActivity extends PresenterActivity<AddBillPresenter> implements AddBillView {

    @Bind(R.id.rv_bill_type)
    RecyclerView rv_bill_type;
    @Bind(R.id.et_money)
    EditText et_money;
    @Bind(R.id.et_remarks)
    EditText et_remarks;
    @Bind(R.id.et_time)
    EditText et_time;
    @Bind(R.id.btn_add)
    Button btn_add;

    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HH:mm:ss", Locale.CHINA);

    private PerCost currentPerCost;
    private boolean isAlertMode;


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
        PerCost perCost = (PerCost) getIntent().getSerializableExtra("perCost");
        if (perCost != null){
            currentPerCost = perCost;
            isAlertMode = true;
        }
        // 是否编辑修改模式
        if (isAlertMode)
            showCacheData(currentPerCost);
        rv_bill_type.setLayoutManager(new GridLayoutManager(mContext,4));
        rv_bill_type.addItemDecoration(new DividerItemDecoration(mContext, R.color.white,
                DividerItemDecoration.VERTICAL_LIST));
    }

    private void showCacheData(PerCost currentPerCost) {
        et_money.setText(String.valueOf(currentPerCost.getCostMoney()));
        et_remarks.setText(currentPerCost.getRemark());
        et_time.setText(format.format(currentPerCost.getDate()));
        btn_add.setText("修改");
    }

    @OnClick(R.id.btn_add)
    public void add(){
        String money = et_money.getText().toString();
        String remarks = et_remarks.getText().toString();
        String time = et_time.getText().toString();
        if(TextUtils.isEmpty(money)){
            disPlay("请填写正确的金额");
            return;
        }
        // 是否编辑修改模式
        if (isAlertMode){
            try {
                currentPerCost.setDate(format.parse(time));
            } catch (ParseException e) {
                DebugLog.w(e.getMessage());
            }
            Double aDouble = Double.valueOf(money);
            currentPerCost.setCostMoney(aDouble);
            currentPerCost.setRemark(remarks);
            mPresenter.alterBill(currentPerCost);
        }else{
            PerCost perCost = new PerCost();
            Double aDouble = Double.valueOf(money);
            perCost.setCostMoney(aDouble);
            perCost.setRemark(remarks);
            mPresenter.addBill(perCost);
        }
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
