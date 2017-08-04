package com.cangjie.mayday.presenter;

import android.app.Activity;

import com.anye.greendao.gen.BillDao;
import com.anye.greendao.gen.BillTypeDao;
import com.anye.greendao.gen.PasswordDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.entity.Bill;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.PasswordStoreAdapter;
import com.cangjie.mayday.presenter.view.PasswordStoreView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class PasswordStorePresenter extends BasePresenter<PasswordStoreView> {

    private final Activity mActivity;
    private PasswordDao mPasswordDao;
    private final SimpleDateFormat mMonthDaySimpleDateFormat;
    private final SimpleDateFormat mYearMonthSimpleDateFormat;

    public PasswordStorePresenter(PasswordStoreView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
        mPasswordDao = MyApplication.getInstances().getDaoSession().getPasswordDao();

        mMonthDaySimpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        mYearMonthSimpleDateFormat = new SimpleDateFormat("yyyyMM", Locale.CHINA);
    }

    public void loadData() {
        List<Password> passwords = mPasswordDao.queryBuilder().orderDesc(PasswordDao.Properties.Date).list();
        PasswordStoreAdapter passwordStoreAdapter = new PasswordStoreAdapter(mActivity, passwords);
        mvpView.showData(passwordStoreAdapter);
    }
}
