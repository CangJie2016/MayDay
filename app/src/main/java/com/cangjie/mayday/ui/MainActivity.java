package com.cangjie.mayday.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.basetool.view.lazy_viewpager.CustomViewPager;
import com.cangjie.basetool.view.lazy_viewpager.MyFragmentViewPagerAdapter;
import com.cangjie.data.db.DbInit;
import com.cangjie.mayday.Constants;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.MainPresenter;
import com.cangjie.mayday.presenter.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cangjie.mayday.Constants.SpKey.IS_READ;

public class MainActivity extends FragmentActivity implements MainView {

    private static final int REQUEST_CODE = 1024;

    @BindView(R.id.vp_main)
    public CustomViewPager mViewPager;

    @BindView(R.id.rb_chart)
    public RadioButton rb_chart;
    @BindView(R.id.rb_bill)
    public RadioButton rb_bill;
    @BindView(R.id.rb_more)
    public RadioButton rb_more;
    @BindView(R.id.btn_pencil)
    public Button btn_pencil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BillTypeDao mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        if (!SpUtils.getCacheBoolean(MyApplication.mContext, Constants.SpKey.INIT_BILL_TYPE)){
            DbInit.init(mBillTypeDao);
            SpUtils.setCache(MyApplication.mContext, Constants.SpKey.INIT_BILL_TYPE, true);
        }

        ButterKnife.bind(this);
        generateFragment();
        requestPermission();

        boolean isRead = SpUtils.getCacheBoolean(this, IS_READ);
        if (!isRead){
            showMustDialog();
        }
    }

    private void showMustDialog() {
        String privateContent = getString(R.string.private_content);
        new MaterialDialog.Builder(this)
                .title("隐私政策")
                .content(privateContent)
                .positiveText("同意")
                .negativeText("不同意")
                .canceledOnTouchOutside(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        SpUtils.setCache(MainActivity.this, IS_READ, true);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .show();
    }

    public void generateFragment() {
        List<Fragment> tabFragments = new ArrayList<>();
        ChartFragment chartFragment = new ChartFragment();
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        MyFragment myFragment = new MyFragment();
        tabFragments.add(chartFragment);
        tabFragments.add(timeLineFragment);
        tabFragments.add(myFragment);
        MyFragmentViewPagerAdapter mAdapter = new MyFragmentViewPagerAdapter(getSupportFragmentManager(), tabFragments);
        setViewPagerAdapter(mAdapter);
    }

    public void setViewPagerAdapter(MyFragmentViewPagerAdapter mAdapter) {
        mViewPager.setOffscreenPageLimit(0);
        //设置不可滑动
        mViewPager.setPagingEnabled(false);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setCurrentItem(1);
        rb_bill.setChecked(true);
    }

    @OnClick({R.id.rb_chart, R.id.rb_bill,R.id.rb_more})
    public void onItemClick(View view){
        switch (view.getId()){
            case R.id.rb_chart:
                hidePencilBtn();
                mViewPager.setCurrentItem(0);
                break;

            case R.id.rb_bill:
                showPencilBtn();
                mViewPager.setCurrentItem(1);
                break;

            case R.id.rb_more:
                hidePencilBtn();
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    public void showPencilBtn(){
        btn_pencil.setVisibility(View.VISIBLE);
        rb_bill.setVisibility(View.GONE);
    }
    public void hidePencilBtn(){
        btn_pencil.setVisibility(View.GONE);
        rb_bill.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.btn_pencil)
    public void clickPencil(){
        Intent intent = new Intent(this, AddBillActivity.class);
        startActivity(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void disPlay(String s) {

    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // 先判断有没有权限
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // 先判断有没有权限
                if (Environment.isExternalStorageManager()) {
                } else {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse("package:" + this.getPackageName()));
                    startActivityForResult(intent, REQUEST_CODE);
                }
            } else if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            } else {
                disPlay("获取权限失败");
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (Environment.isExternalStorageManager()) {
            } else {
                disPlay("获取权限失败");
            }
        }
    }
}
