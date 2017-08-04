package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.PasswordStoreAdapter;
import com.cangjie.mayday.presenter.PasswordStorePresenter;
import com.cangjie.mayday.presenter.view.PasswordStoreView;

public class PasswordStoreActivity extends PresenterActivity<PasswordStorePresenter> implements PasswordStoreView {

    private RecyclerView rv_password_store;
    private RefreshTypeBroadcast refreshDataBroadcast;

    @Override
    protected PasswordStorePresenter createPresenter() {
        return new PasswordStorePresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_store);
        showTitle("账号仓库");
        showBackButton();
        showRightImageButton(R.drawable.btn_add_type, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PasswordStoreActivity.this, PasswordDetailActivity.class);
                intent.putExtra("isAlert", false);
                startActivity(intent);
            }
        });
        rv_password_store = (RecyclerView) findViewById(R.id.rv_password_store);
        rv_password_store.setLayoutManager(new LinearLayoutManager(mContext));

        mPresenter.loadData();

        initBroadcast();
    }

    private void initBroadcast() {
        refreshDataBroadcast = new RefreshTypeBroadcast();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PasswordDetailActivity.REFRESH_PASSWORD_ACTION);
        registerReceiver(refreshDataBroadcast, intentFilter);
    }

    @Override
    public void showData(PasswordStoreAdapter passwordStoreAdapter) {
        rv_password_store.setAdapter(passwordStoreAdapter);
    }

    public class RefreshTypeBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.loadData();
        }
    }
}
