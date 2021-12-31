package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.BillTypeAdapter;
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.presenter.AddBillPresenter;
import com.cangjie.mayday.presenter.view.AddBillView;
import com.cangjie.mayday.view.CustomSoftKeyboard;
import com.cangjie.mayday.view.DividerGridItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddBillActivity extends PresenterActivity<AddBillPresenter> implements AddBillView {

    @BindView(R.id.rv_bill_type)
    RecyclerView rv_bill_type;
    @BindView(R.id.et_remarks)
    EditText et_remarks;
    @BindView(R.id.custom_kb)
    CustomSoftKeyboard custom_kb;


    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd-HH:mm:ss", Locale.CHINA);

    private PerCost currentPerCost;
    private boolean isAlertMode;
    private RefreshTypeBroadcast refreshTypeBroadcast;


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
        mPresenter.obatinBillType();
        initView();
        initBroadcast();
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
        if (perCost != null) {
            currentPerCost = perCost;
            isAlertMode = true;
        }
        // 是否编辑修改模式
        if (isAlertMode) {
            showCacheData(currentPerCost);
            showRightImageButton(R.drawable.btn_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDeleteDialog();
                }
            });
        }
        rv_bill_type.setLayoutManager(new GridLayoutManager(mContext, 4));
        rv_bill_type.addItemDecoration(new DividerGridItemDecoration(mContext));
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

    public void add(double d) {
        String money = String.valueOf(d);
        String remarks = et_remarks.getText().toString();
        if (TextUtils.isEmpty(money) || d == 0) {
            disPlay("请填写正确的金额");
            return;
        }
        // 是否为修改模式
        if (isAlertMode) {
            Double aDouble = Double.valueOf(money);
            currentPerCost.setCostMoney(aDouble);
            currentPerCost.setRemark(remarks);
            mPresenter.alterBill(currentPerCost);
        } else {
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

    private void initBroadcast() {
        refreshTypeBroadcast = new RefreshTypeBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BillTypeDetailActivity.REFRESH_TYPE_ACTION);
        registerReceiver(refreshTypeBroadcast, intentFilter);
    }


    public class RefreshTypeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // 如果在编辑模式下修改了种类名称，则自动退出该界面
            if (isAlertMode) {
                finish();
            } else {
                mPresenter.obatinBillType();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(refreshTypeBroadcast);
    }
}
