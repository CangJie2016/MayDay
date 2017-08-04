package com.cangjie.mayday.presenter;

import com.anye.greendao.gen.PasswordDao;
import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.presenter.view.PasswordDetailView;

import java.util.Date;

public class PasswordDetailPresenter extends BasePresenter<PasswordDetailView> {
    private final PasswordDao mPasswordDao;

    public PasswordDetailPresenter(PasswordDetailView mvpView) {
        super(mvpView);
        mPasswordDao = MyApplication.getInstances().getDaoSession().getPasswordDao();
    }

    public void save(String title, String username, String password, String remarks) {
        Password passwordObj = new Password(null, title, username, password, remarks, new Date(), 0);
        long insert = mPasswordDao.insert(passwordObj);
        DebugLog.w("insert : " + insert);
        if (insert != -1)
            mvpView.success();
    }

    public void loadDataById(long id) {
        Password password = mPasswordDao.load(id);
        mvpView.showCacheData(password);
    }

    public void update(long mCacheDataId, String title, String username, String password, String remarks) {
        Password passwordObj = new Password(mCacheDataId, title, username, password, remarks, new Date(), 0);
        mPasswordDao.update(passwordObj);
        mvpView.success();
    }

    public void delete(long mCacheDataId) {
        mPasswordDao.deleteByKey(mCacheDataId);
        mvpView.success();
    }
}
