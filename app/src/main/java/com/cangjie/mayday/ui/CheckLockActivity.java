package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.CheckLockPresenter;
import com.cangjie.mayday.presenter.CreateLockPresenter;
import com.cangjie.mayday.presenter.view.CheckLockView;
import com.cangjie.mayday.view.LockPatternView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 检查手势正确，进入账号仓库或者打开修改手势页面
 */
public class CheckLockActivity extends PresenterActivity<CheckLockPresenter> implements CheckLockView, LockPatternView.OnPatternListener {

    @Override
    protected CheckLockPresenter createPresenter() {
        return new CheckLockPresenter(this, getApplicationContext());
    }

    @BindView(R.id.lockPatternView)
    LockPatternView mLockPatternView;
    @BindView(R.id.tv_show)
    TextView tvTips;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lock);
        ButterKnife.bind(this);
        showBackButton();
        showTitle("解锁");
        tvTips.setText(mContext.getString(R.string.check_default));
        mLockPatternView.setOnPatternListener(this);
    }

    @Override
    public void onPatternStart() {
        fingerPress();
    }

    @Override
    public void onPatternCleared() {

    }

    @Override
    public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

    }

    @Override
    public void onPatternDetected(List<LockPatternView.Cell> pattern) {
        mPresenter.check(pattern);
    }

    public void fingerPress() {
        tvTips.setText(mContext.getString(R.string.finger_press));
    }

    @Override
    public void verifySuccess() {
        startActivity(new Intent(this, PasswordStoreActivity.class));
        finish();
    }

    @Override
    public void verifyFailed() {
        tvTips.setTextColor(mContext.getResources().getColor(R.color.text_red));
        tvTips.setText(mContext.getString(R.string.check_error));
        tvTips.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.shake_x));
    }
}
