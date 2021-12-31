package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.CreateLockPresenter;
import com.cangjie.mayday.presenter.view.CreateLockView;
import com.cangjie.mayday.view.LockPatternView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateLockActivity extends PresenterActivity<CreateLockPresenter> implements CreateLockView, LockPatternView.OnPatternListener {

    @BindView(R.id.lockPatternView)
    LockPatternView mLockPatternView;
    @BindView(R.id.tv_show)
    TextView tvTips;
    private int mode;

    @Override
    protected CreateLockPresenter createPresenter() {
        mode = getIntent().getIntExtra("mode", Constants.CREATE_GESTURE);
        return new CreateLockPresenter(this, getApplicationContext(), mode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_lock);
        ButterKnife.bind(this);
        showBackButton();
        if (mode == Constants.CREATE_GESTURE){
            showTitle("创建手势");
            tvTips.setText(mContext.getString(R.string.create_activity_first));
        }else{
            showTitle("修改手势");
            tvTips.setText(mContext.getString(R.string.create_activity_warn));
        }
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

    public void fingerFirstUpError() {
        tvTips.setText(mContext.getString(R.string.finger_up_first_error));
    }

    public void fingerFirstUpSuccess() {
        tvTips.setText(mContext.getString(R.string.finger_up_first_success));
    }

    @Override
    public void clearPattern() {
        mLockPatternView.clearPattern();
    }

    public void fingerSecondUpError() {
        tvTips.setText(mContext.getString(R.string.finger_up_second_error));
    }

    @Override
    public void lockDisplayError() {
        mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
    }

    public void createLockSuccess() {
        tvTips.setText(mContext.getString(R.string.finger_up_second_success));

        if (mode == Constants.UPDATE_GESTURE){
            finish();
        }else if(mode == Constants.CREATE_GESTURE){
            startActivity(new Intent(this, PasswordStoreActivity.class));
            finish();
        }
    }

    @Override
    public void updateLockSuccess() {
        disPlay("修改成功");
        finish();
    }
}
