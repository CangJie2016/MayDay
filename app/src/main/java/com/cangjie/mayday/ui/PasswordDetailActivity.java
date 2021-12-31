package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.PasswordDetailPresenter;
import com.cangjie.mayday.presenter.view.PasswordDetailView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.R.attr.password;

public class PasswordDetailActivity extends PresenterActivity<PasswordDetailPresenter> implements PasswordDetailView {
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_userName)
    EditText et_userName;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_remarks)
    EditText et_remarks;
    @BindView(R.id.btn_add)
    Button btn_add;
    @BindView(R.id.btn_update)
    Button btn_update;
    @BindView(R.id.btn_delete)
    Button btn_delete;

    public static final String REFRESH_PASSWORD_ACTION = "com.cangjie.refresh.password";
    private long mCacheDataId;

    @Override
    protected PasswordDetailPresenter createPresenter() {
        return new PasswordDetailPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_detail);
        ButterKnife.bind(this);
        showBackButton();
        boolean isAlert = getIntent().getBooleanExtra("isAlert", true);
        if (isAlert){
            showTitle("修改账户");
            // 修改模式
            btn_update.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
            mCacheDataId = getIntent().getLongExtra("id", -1);
            if (mCacheDataId == -1)
                return;
            mPresenter.loadDataById(mCacheDataId);
        }else{
            showTitle("新增账户");
            // 新增模式
            btn_add.setVisibility(View.VISIBLE);
            et_title.requestFocus();
        }
    }

    @OnClick(R.id.btn_add)
    public void add(){
        String title = et_title.getText().toString().trim();
        String username = et_userName.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String remarks = et_remarks.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            disPlay("请输入标题、用户名、密码");
            return;
        }
        mPresenter.save(title, username, password, remarks);
    }
    @OnClick(R.id.btn_update)
    public void update(){
        String title = et_title.getText().toString().trim();
        String username = et_userName.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String remarks = et_remarks.getText().toString().trim();
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            disPlay("请输入标题、用户名、密码");
            return;
        }
        mPresenter.update(mCacheDataId, title, username, password, remarks);
    }
    @OnClick(R.id.btn_delete)
    public void delete(){
        mPresenter.delete(mCacheDataId);
    }

    @Override
    public void success() {
        sendBroadcast(new Intent(REFRESH_PASSWORD_ACTION));
        finish();
    }

    @Override
    public void showCacheData(Password password) {
        et_title.setText(password.getTitle());
        et_userName.setText(password.getUsername());
        et_password.setText(password.getPassword());
        et_remarks.setText(password.getRemarks());
    }
}
