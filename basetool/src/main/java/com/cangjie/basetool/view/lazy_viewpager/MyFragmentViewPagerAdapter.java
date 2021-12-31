package com.cangjie.basetool.view.lazy_viewpager;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2015/7/9.
 */
public class MyFragmentViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList;
    public MyFragmentViewPagerAdapter(FragmentManager fm, List<Fragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public Fragment getItem(int i) {
        return mFragmentList.get(i);
    }


}