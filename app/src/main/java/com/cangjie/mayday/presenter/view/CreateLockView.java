package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;

public interface CreateLockView extends BaseView {
    void fingerFirstUpError();

    void fingerSecondUpError();

    void lockDisplayError();

    void fingerFirstUpSuccess();

    void clearPattern();

    void createLockSuccess();

    void updateLockSuccess();
}
