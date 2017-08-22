package com.cangjie.mayday.presenter;

import android.app.Activity;
import android.text.TextUtils;

import com.anye.greendao.gen.PasswordDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.adapter.PasswordStoreAdapter;
import com.cangjie.mayday.presenter.view.PasswordStoreView;

import java.util.List;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class PasswordStorePresenter extends BasePresenter<PasswordStoreView> {

    private final Activity mActivity;
    private PasswordDao mPasswordDao;
    private List<Password> passwords;
    private PasswordStoreAdapter passwordStoreAdapter;

    public PasswordStorePresenter(PasswordStoreView mvpView, Activity activity) {
        super(mvpView);
        this.mActivity = activity;
        mPasswordDao = MyApplication.getInstances().getDaoSession().getPasswordDao();
    }

    public void loadAllData() {
        passwords = mPasswordDao.queryBuilder().orderDesc(PasswordDao.Properties.Date).list();
        showData();
    }

    public void query(String key) {
        if (TextUtils.isEmpty(key)) {
            loadAllData();
            return;
        }
        DebugLog.w("key" + key);
        passwords = mPasswordDao.queryBuilder().whereOr(PasswordDao.Properties.Title.like("%" + key + "%"), PasswordDao.Properties.Username.like("%" + key + "%"),
                PasswordDao.Properties.Password.like("%" + key + "%"),PasswordDao.Properties.Remarks.like("%" + key + "%")).orderDesc(PasswordDao.Properties.Date).list();
        DebugLog.w("size" + passwords.size());
        showData();
    }

    private void showData() {
        passwordStoreAdapter = new PasswordStoreAdapter(mActivity, passwords);
        mvpView.showData(passwordStoreAdapter);
    }
}
