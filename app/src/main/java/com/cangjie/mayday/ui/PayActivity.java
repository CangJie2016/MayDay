package com.cangjie.mayday.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cangjie.basetool.mvp.base.BaseHeadActivity;
import com.cangjie.mayday.R;

public class PayActivity extends BaseHeadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        showTitle("请选择打赏方式");
        showBackButton();
    }

    public void wx(View view){
        startActivity(new Intent(this, WxPayActivity.class));
    }

    public void ali(View view){
        startActivity(new Intent(this, AliPayActivity.class));
    }
}
