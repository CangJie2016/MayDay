package com.cangjie.mayday.ui;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anye.greendao.gen.PasswordDao;
import com.cangjie.basetool.mvp.base.BaseHeadFragment;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.basetool.utils.ToastHelper;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.anye.greendao.gen.PasswordDao.Properties.Password;
import static com.cangjie.mayday.presenter.CreateLockPresenter.CREATE_LOCK_SUCCESS;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class MyFragment extends BaseHeadFragment {
    private View rootView;
    Context mContext;

    @BindView(R.id.tv_version)
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

    @OnClick({R.id.ll_user_info, R.id.ll_credits_exchange, R.id.ll_activity, R.id.ll_auto, R.id.ll_version, R.id.ll_suggest})
    public void onItemClick(View view) {
        ToastHelper.showToast("正在开发中", mContext);
    }

    @OnClick(R.id.ll_password_store)
    public void passwordStore() {
        boolean isLock = SpUtils.getCacheBoolean(mContext, CREATE_LOCK_SUCCESS);
        Intent intent = new Intent();
        if (isLock) {
            intent.setClass(getActivity(), CheckLockActivity.class);
        } else {
            intent.setClass(getActivity(), CreateLockActivity.class);
        }
        startActivity(intent);
    }

    @OnClick(R.id.ll_help)
    public void help() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), HelpActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_tips)
    public void tips() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), PayActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_app)
    public void explain() {
        Intent intent = new Intent();
        intent.setClass(getActivity(), ExplainActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.ll_import)
    public void importData() {
        new MaterialDialog.Builder(getContext())
                .title("账号仓库")
                .content("请确认是否要导入目录下的export.txt文件？导入后请注意删除该文件")
                .positiveText("确认导入")
                .negativeText("取消")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        String path = Environment.getExternalStorageDirectory() + "/export.txt";
                        readFileByLines(path);
                        ToastHelper.showToast("账号仓库已成功导入，请查看", getContext());
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                        dialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readFileByLines(String fileName) {
        BufferedReader reader = null;
        try {
            PasswordDao mPasswordDao = MyApplication.getInstances().getDaoSession().getPasswordDao();
            File file = new File(fileName);
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                String[] split = tempString.split(",");
                Long time = Long.valueOf(split[5]);
                com.cangjie.data.entity.Password password = new Password(null, split[1], split[2], split[3], split[4], new Date(time), Integer.valueOf(split[6]));
                mPasswordDao.insert(password);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            ToastHelper.showToast("出错了：" + e.getMessage(), MyApplication.mContext);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }


}
