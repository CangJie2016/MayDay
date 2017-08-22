package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.PasswordStoreAdapter;
import com.cangjie.mayday.presenter.PasswordStorePresenter;
import com.cangjie.mayday.presenter.view.PasswordStoreView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PasswordStoreActivity extends PresenterActivity<PasswordStorePresenter> implements PasswordStoreView {

    private RecyclerView rv_password_store;
    private RefreshTypeBroadcast refreshDataBroadcast;
    @Bind(R.id.et_key)
    EditText et_key;

    @Override
    protected PasswordStorePresenter createPresenter() {
        return new PasswordStorePresenter(this, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_store);
        ButterKnife.bind(this);
        showTitle("账号仓库");
        showBackButton();
        showRightImageButton(R.drawable.btn_lock, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(PasswordStoreActivity.this, CreateLockActivity.class);
                intent.putExtra("mode", Constants.UPDATE_GESTURE);
                startActivity(intent);
            }
        });
        rv_password_store = (RecyclerView) findViewById(R.id.rv_password_store);
        rv_password_store.setLayoutManager(new LinearLayoutManager(mContext));

        mPresenter.loadAllData();
        et_key.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                rx.Observable.timer(600, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<Long>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                DebugLog.w(e.getMessage());
                            }

                            @Override
                            public void onNext(Long aLong) {

                                mPresenter.query(s.toString());
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
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
            mPresenter.loadAllData();
        }
    }

    @OnClick(R.id.btn_add)
    public void add() {
        Intent intent = new Intent(PasswordStoreActivity.this, PasswordDetailActivity.class);
        intent.putExtra("isAlert", false);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (refreshDataBroadcast != null)
            unregisterReceiver(refreshDataBroadcast);
    }
}
