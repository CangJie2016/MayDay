package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.mayday.adapter.PasswordStoreAdapter;
import com.cangjie.mayday.adapter.TimeLineAdapter;

/**
 * Created by 李振强 on 2017/5/26.
 */

public interface PasswordStoreView extends BaseView{
    void showData(PasswordStoreAdapter passwordStoreAdapter);

}
