package com.cangjie.mayday.presenter;

import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.mayday.presenter.view.WelcomeView;

public class WelcomePresenter extends BasePresenter<WelcomeView> {
    public WelcomePresenter(WelcomeView mvpView) {
        super(mvpView);
    }
}
