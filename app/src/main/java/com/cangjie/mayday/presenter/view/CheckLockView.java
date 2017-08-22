package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;

public interface CheckLockView extends BaseView {
    void verifySuccess();

    void verifyFailed();
}
