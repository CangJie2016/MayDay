package com.cangjie.mayday.presenter;

import android.content.Context;
import android.view.animation.AnimationUtils;

import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.view.CheckLockView;
import com.cangjie.mayday.utils.LockPatternUtils;
import com.cangjie.mayday.view.LockPatternView;

import java.util.List;

import static com.cangjie.mayday.MyApplication.mContext;

public class CheckLockPresenter extends BasePresenter<CheckLockView> {
    public CheckLockPresenter(CheckLockView mvpView, Context context) {
        super(mvpView);
    }

    public void check(List<LockPatternView.Cell> pattern) {
        if (pattern == null) return;

        LockPatternUtils instances = LockPatternUtils.getInstances(mContext);
        if (instances.checkPattern(pattern)) {
            mvpView.verifySuccess();
        } else {
            mvpView.verifyFailed();
        }
    }
}
