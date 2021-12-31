package com.cangjie.basetool.mvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

import com.cangjie.basetool.utils.ToastHelper;


public abstract class BaseActivity extends Activity {
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mContext = this;
        super.onCreate(savedInstanceState);
    }

    protected abstract void showLoading();
    protected abstract void hideLoading();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void disPlay(String toast){
        ToastHelper.showToast(toast,this);
    }
}
