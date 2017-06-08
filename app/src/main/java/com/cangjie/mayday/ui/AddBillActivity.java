package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.basetool.utils.ToastHelper;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.presenter.AddBillPresenter;
import com.cangjie.mayday.presenter.view.AddBillView;
import com.cangjie.mayday.view.CustomSoftKeyboard;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AddBillActivity extends PresenterActivity<AddBillPresenter> implements AddBillView {

    @Bind(R.id.rv_bill_type)
    RecyclerView rv_bill_type;
    @Bind(R.id.et_remarks)
    EditText et_remarks;
    @Bind(R.id.custom_kb)
    CustomSoftKeyboard custom_kb;


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
        showRightImageButton(R.drawable.btn_delete, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });
        mPresenter.obatinBillType();
        initView();
    }

    private void showDeleteDialog() {
        new MaterialDialog.Builder(this)
                .title("温馨提示")
                .content("是否确认删除记录？")
                .positiveText("删除")
                .negativeText("取消")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        mPresenter.deleteRecord(currentPerCost.getId());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                })
                .show();
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
        custom_kb.setOnSoftKeyBoardListener(new CustomSoftKeyboard.OnSoftKeyBoardListener() {
            @Override
            public void onClickSubmitListener(double d) {
                add(d);
            }
        });
    }

    private void showCacheData(PerCost currentPerCost) {
        et_remarks.setText(currentPerCost.getRemark());
        custom_kb.setMoney(currentPerCost.getCostMoney());
        mPresenter.setCurrentCostType(currentPerCost.getCostType());
    }

    public void add(double d){
        String money = String.valueOf(d);
        String remarks = et_remarks.getText().toString();
        if(TextUtils.isEmpty(money)){
            disPlay("请填写正确的金额");
            return;
        }
        // 是否为修改模式
        if (isAlertMode){
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

    @Override
    public void deleteSuccess() {
        sendRefreshBroadcast();
        finish();
        disPlay("删除成功");
    }

    private void sendRefreshBroadcast() {
        sendBroadcast(new Intent(TimeLineFragment.TIMELINE_ACTION));
    }

}
