package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.cangjie.basetool.mvp.base.PresenterActivity;
import com.cangjie.basetool.view.lazy_viewpager.CustomViewPager;
import com.cangjie.basetool.view.lazy_viewpager.MyFragmentViewPagerAdapter;
import com.cangjie.mayday.R;
import com.cangjie.mayday.presenter.MainPresenter;
import com.cangjie.mayday.presenter.view.MainView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends PresenterActivity<MainPresenter> implements MainView {

    @Bind(R.id.vp_main)
    public CustomViewPager mViewPager;

    @Bind(R.id.rb_chart)
    public RadioButton rb_chart;
    @Bind(R.id.rb_bill)
    public RadioButton rb_bill;
    @Bind(R.id.rb_more)
    public RadioButton rb_more;
    @Bind(R.id.btn_pencil)
    public Button btn_pencil;

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        hideHeadArea();
        generateFragment();
    }

    public void generateFragment() {
        List<Fragment> tabFragments = new ArrayList<>();
        ChartFragment chartFragment = new ChartFragment();
        TimeLineFragment timeLineFragment = new TimeLineFragment();
        MyFragment myFragment = new MyFragment();
        tabFragments.add(chartFragment);
        tabFragments.add(timeLineFragment);
        tabFragments.add(myFragment);
        MyFragmentViewPagerAdapter mAdapter = new MyFragmentViewPagerAdapter(MainActivity.this.getSupportFragmentManager(), tabFragments);
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
        Intent intent = new Intent(mContext, AddBillActivity.class);
        startActivity(intent);
    }

}
