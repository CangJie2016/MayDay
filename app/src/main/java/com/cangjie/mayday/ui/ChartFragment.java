package com.cangjie.mayday.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cangjie.basetool.mvp.base.BaseHeadFragment;
import com.cangjie.basetool.utils.ToastHelper;
import com.cangjie.mayday.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class ChartFragment extends BaseHeadFragment {
    private View rootView;
    Context mContext;


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
        rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        rootView = setContentView(rootView);
        ButterKnife.bind(this, rootView);
        showTitle("统计");
        return rootView;
    }
}
