package com.cangjie.basetool.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class BaseFragment extends Fragment {
    public Activity mActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.mActivity = this.getActivity();
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    public void onDestroy() {
        super.onDestroy();
    }

}
