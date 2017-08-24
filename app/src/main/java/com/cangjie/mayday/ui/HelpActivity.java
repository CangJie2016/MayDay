package com.cangjie.mayday.ui;

import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anye.greendao.gen.BillDao;
import com.cangjie.basetool.mvp.base.BaseHeadActivity;
import com.cangjie.basetool.utils.SpUtils;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;
import com.cangjie.mayday.utils.MD5;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static android.R.attr.format;
import static com.cangjie.mayday.presenter.CreateLockPresenter.CREATE_LOCK_SUCCESS;

public class HelpActivity extends BaseHeadActivity {

    private EditText et_md5;
    private TextView tv_show;
    private LinearLayout container;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        showTitle("使用帮助");
        showBackButton();
        et_md5 = (EditText) findViewById(R.id.et_md5);
        tv_show = (TextView) findViewById(R.id.tv_show);
        container = (LinearLayout) findViewById(R.id.container);
        tv_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                if (count == 7){
                    container.setVisibility(View.VISIBLE);
                }
            }
        });
        tv_show.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager clipboard =
                        (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText("534256953");
                disPlay("群号复制成功！！");
                return false;
            }
        });
    }

    // 78b79dfa56b3e6f8bc7ff4b6a55b8c84
    public void confirm(View view) {
        String md5Str = et_md5.getText().toString().trim();
        if (TextUtils.isEmpty(md5Str))
            return;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd", Locale.CHINA);
        String date = format.format(new Date());
        int i = Integer.valueOf(date) * 1024;
        String s = MD5.MD5Encode(String.valueOf(i));
        if (md5Str.equals(s)){
            SpUtils.setCache(mContext, CREATE_LOCK_SUCCESS, false);
            disPlay("成功清除手势密码");
            finish();
        }else {
            disPlay("密钥错误");
        }
    }
}
