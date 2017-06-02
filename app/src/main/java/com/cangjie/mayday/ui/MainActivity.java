package com.cangjie.mayday.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
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

    @Bind(R.id.rb_buy_ticket)
    public RadioButton rb_buy_ticket;
    @Bind(R.id.rb_riding)
    public RadioButton rb_riding;
    @Bind(R.id.rb_my)
    public RadioButton rb_my;

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
        rb_riding.setChecked(true);
    }

    @OnClick({R.id.rb_buy_ticket, R.id.rb_riding,R.id.rb_my})
    public void onItemClick(View view){
        switch (view.getId()){
            case R.id.rb_buy_ticket:
                mViewPager.setCurrentItem(0);
                break;

            case R.id.rb_riding:
                mViewPager.setCurrentItem(1);
                break;

            case R.id.rb_my:
                mViewPager.setCurrentItem(2);
                break;
        }
    }
}
