package com.cangjie.mayday.presenter;

import android.content.Context;

import com.cangjie.basetool.mvp.BasePresenter;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.presenter.view.CreateLockView;
import com.cangjie.mayday.utils.LockPatternUtils;
import com.cangjie.mayday.utils.ShortLockPatternUtils;
import com.cangjie.mayday.view.LockPatternView;

import java.util.List;

import static com.cangjie.mayday.MyApplication.instances;

public class CreateLockPresenter extends BasePresenter<CreateLockView> {

    public static final String CREATE_LOCK_SUCCESS = "CREATE_LOCK_SUCCESS";
    private final Context mContext;
    private boolean isFinishOnce = false;
    private int createMode;
    private final LockPatternUtils instances;
    private final ShortLockPatternUtils shortLockPatternUtils;

    public CreateLockPresenter(CreateLockView mvpView, Context context, int mode) {
        super(mvpView);
        this.mContext = context;
        this.createMode = mode;

        instances = LockPatternUtils.getInstances(mContext);
        shortLockPatternUtils = ShortLockPatternUtils.getInstances(mContext);
    }


    public void check(List<LockPatternView.Cell> pattern) {
        if (pattern == null) return;

        if (pattern.size() < LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {

            if (!isFinishOnce) {
                mvpView.fingerFirstUpError();
            } else {
                mvpView.fingerSecondUpError();
            }
            mvpView.lockDisplayError();
            return;
        }
        // 第一次画
        if (!isFinishOnce) {
            mvpView.fingerFirstUpSuccess();
            if (createMode == Constants.CREATE_GESTURE) {
                instances.saveLockPattern(pattern);
            } else if (createMode == Constants.UPDATE_GESTURE) {
                shortLockPatternUtils.saveLockPattern(pattern);
            }
            mvpView.clearPattern();
            isFinishOnce = true;
        } else {
            // 第二次画
            if (createMode == Constants.CREATE_GESTURE) {
                if (instances.checkPattern(pattern)) {
                    mvpView.createLockSuccess();
                    SpUtils.setCache(mContext, CREATE_LOCK_SUCCESS, true);
                } else {
                    mvpView.fingerSecondUpError();
                    mvpView.lockDisplayError();
                }
                isFinishOnce = false;
            } else if (createMode == Constants.UPDATE_GESTURE) {
                if (shortLockPatternUtils.checkPattern(pattern)) {
                    instances.saveLockPattern(pattern);
                    SpUtils.setCache(mContext, CREATE_LOCK_SUCCESS, true);
                    mvpView.updateLockSuccess();
                } else {
                    mvpView.fingerSecondUpError();
                    mvpView.lockDisplayError();
                }
                isFinishOnce = false;
            }
        }
    }
}
