package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;

import butterknife.Bind;
import butterknife.ButterKnife;


public class BillTypeDetailActivity extends AppCompatActivity {

    private BillTypeDao mBillTypeDao;
    private long id;
    @Bind(R.id.et_type_name)
    EditText et_type_name;
    @Bind(R.id.btn_add)
    Button btn_add;
    @Bind(R.id.btn_save)
    Button btn_save;
    @Bind(R.id.btn_delete)
    Button btn_delete;

    public static final String REFRESH_TYPE_ACTION = "com.cangjie.refresh_type_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_bill_type_detail);
        ButterKnife.bind(this);
        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();

        boolean isAlterMode = getIntent().getBooleanExtra("isAlter", true);
        if (isAlterMode){
            String billType = getIntent().getStringExtra("type");
            id = getIntent().getLongExtra("id", -1);
            et_type_name.setText(billType);
            btn_save.setVisibility(View.VISIBLE);
            btn_delete.setVisibility(View.VISIBLE);
        }else{
            btn_add.setVisibility(View.VISIBLE);
        }
    }

    public void save(View view){
        BillType billType = new BillType();
        billType.setId(id);
        String newTypeName = et_type_name.getText().toString().trim();
        billType.setTypeName(newTypeName);
        mBillTypeDao.update(billType);
        refreshTypeBroadcast();
        finish();
    }

    public void delete(View view){
        mBillTypeDao.deleteByKey(id);
        refreshTypeBroadcast();
        finish();
    }

    public void add(View view){
        String newTypeName = et_type_name.getText().toString().trim();
        BillType billType = new BillType(null, newTypeName);
        long insert = mBillTypeDao.insert(billType);
        DebugLog.w("insert " + insert);
        refreshTypeBroadcast();
        finish();
    }

    public void refreshTypeBroadcast(){
        sendBroadcast(new Intent(REFRESH_TYPE_ACTION));
    }
}
