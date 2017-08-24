package com.cangjie.mayday.ui;

import android.os.Bundle;

import com.cangjie.basetool.mvp.base.BaseHeadActivity;
import com.cangjie.mayday.R;

public class AliPayActivity extends BaseHeadActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ali_pay);
        showBackButton();
        showTitle("多谢金主 (′・ω・`)");
    }
}
