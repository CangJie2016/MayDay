package com.cangjie.basetool.mvp.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * author：CangJie on 2016/8/18 16:44
 * email：cangjie2016@gmail.com
 */
public abstract class PresenterFragment<P> extends BaseHeadFragment {
    protected P mPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPresenter = createPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    protected abstract P createPresenter();
}
