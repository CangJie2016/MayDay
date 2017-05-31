package com.cangjie.basetool.mvp;


import com.cangjie.basetool.mvp.BaseView;

/**
 * author：CangJie on 2016/8/18 14:38
 * email：cangjie2016@gmail.com
 */
public class BasePresenter<V extends BaseView> {
    public V mvpView;
    protected int httpCount;

    public BasePresenter(V mvpView) {
        this.mvpView = mvpView;

    }
}
