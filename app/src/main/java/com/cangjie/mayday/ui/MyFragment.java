package com.cangjie.mayday.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangjie.basetool.mvp.base.BaseHeadFragment;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.basetool.utils.ToastHelper;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cangjie.mayday.presenter.CreateLockPresenter.CREATE_LOCK_SUCCESS;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class MyFragment extends BaseHeadFragment {
    private View rootView;
    Context mContext;

    @Bind(R.id.tv_version)
    TextView tv_version;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            //返回原有的布局对象  不往下走了
            return rootView;
        }
        mContext = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_my, container, false);
        rootView = setContentView(rootView);
        ButterKnife.bind(this, rootView);
        showTitle("更多");

        try {
            initVersion();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return rootView;
    }

    private void initVersion() throws PackageManager.NameNotFoundException {
        PackageInfo packageInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
        String versionName = packageInfo.versionName;
        tv_version.setText(getString(R.string.version, versionName));

    }

    @OnClick({R.id.ll_user_info, R.id.ll_credits_exchange,R.id.ll_activity,R.id.ll_auto,R.id.ll_version,R.id.ll_suggest})
    public void onItemClick(View view){
        ToastHelper.showToast("正在开发中", mContext);
    }

    @OnClick(R.id.ll_password_store)
    public void passwordStore(){
        boolean isLock = SpUtils.getCacheBoolean(mContext, CREATE_LOCK_SUCCESS);
        Intent intent = new Intent();
        if (isLock){
            intent.setClass(getActivity(), CheckLockActivity.class);
        }else{
            intent.setClass(getActivity(), CreateLockActivity.class);
        }
        startActivity(intent);
    }
    @OnClick(R.id.ll_help)
    public void help(){
        Intent intent = new Intent();
        intent.setClass(getActivity(), HelpActivity.class);
        startActivity(intent);
    }
}
