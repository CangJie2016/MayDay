package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.data.entity.Password;

public interface PasswordDetailView extends BaseView {
    void success();

    void showCacheData(Password password);
}
