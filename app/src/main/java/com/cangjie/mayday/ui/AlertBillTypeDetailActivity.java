package com.cangjie.mayday.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;


public class AlertBillTypeDetailActivity extends AppCompatActivity {

    private BillTypeDao mBillTypeDao;
    private long typeId;
    private EditText et_type_name;

    public static final String REFRESH_TYPE_ACTION = "com.cangjie.refresh_type_action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_bill_type_detail);

        mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        et_type_name = (EditText) findViewById(R.id.et_type_name);
        String billType = getIntent().getStringExtra("type");
        typeId = getIntent().getLongExtra("typeId", -1);
        et_type_name.setText(billType);
    }

    public void save(View view){
        BillType billType = new BillType();
        billType.setId(typeId);
        String newTypeName = et_type_name.getText().toString().trim();
        billType.setTypeName(newTypeName);
        mBillTypeDao.update(billType);
        refreshTypeBroadcast();
    }

    public void delete(View view){
        mBillTypeDao.deleteByKey(typeId);
        refreshTypeBroadcast();
    }

    public void refreshTypeBroadcast(){
        sendBroadcast(new Intent(REFRESH_TYPE_ACTION));
    }
}
