package com.cangjie.mayday.view;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.basetool.utils.ToastHelper;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;
import com.facebook.stetho.common.LogUtil;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 李振强 on 2017/6/6.
 */

public class CustomSoftKeyboard extends LinearLayout {

    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.btn_soft_kb_calculate)
    Button btn_soft_kb_operation;

    private boolean isOperation = false;

    private static final int OPERATION_ADD = 1, OPERATION_MINUS = 2;
    private int currentOperation;
    private String num1, num2;
    private OnSoftKeyBoardListener mListener;

    public CustomSoftKeyboard(Context context) {
        this(context, null);
    }

    public CustomSoftKeyboard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSoftKeyboard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.layout_soft_keyboard, this);
        ButterKnife.bind(this);
    }

    private void softKeyboardInputNum(int lastInputKey) {
        et_money.getText().append(String.valueOf(lastInputKey));
//        if (isOperation){
//            String e = et_money.getText().toString();
//            String split[] = new String[0];
//            if (e.contains("+")){
//                split = e.split("\\+");
//            }else if(e.contains("-")){
//                split = e.split("-");
//            }
//            num1 = split[0];
//            num2 = split[1];
//        }
//        et_money.setText();
    }

    public void setOnSoftKeyBoardListener(OnSoftKeyBoardListener listener) {
        this.mListener = listener;
    }

    @OnClick(R.id.btn_soft_kb_point)
    public void kb_point() {
        et_money.getText().append(".");
    }

    @OnClick(R.id.btn_soft_kb_submit)
    public void submit() {
        String result = et_money.getText().toString();
        if (TextUtils.isEmpty(result))
            return;
        Double aDouble = 0.0;
        try {
            aDouble = Double.valueOf(result);
        } catch (Exception e) {

        }
        if (mListener != null) {
            mListener.onClickSubmitListener(aDouble);
        } else {
//            throw new RuntimeException("listener can not be null");
        }
    }

    @OnClick(R.id.btn_soft_kb_back)
    public void back() {
        if (et_money.getText().toString().length() == 0)
            return;
        et_money.getText().delete(et_money.getText().length() - 1, et_money.getText().length());
    }

    @OnClick({R.id.btn_soft_kb_add, R.id.btn_soft_kb_minus})
    public void softKeyboardInputOperation(View view) {
        switch (view.getId()) {
            case R.id.btn_soft_kb_add:
                operator(OPERATION_ADD);
                break;
            case R.id.btn_soft_kb_minus:
                operator(OPERATION_MINUS);
                break;
        }
    }

    private void operator(int operation) {
        // 判断第二个数是否有内容， 如果没有，则不管，如果有，则num1 num2进行加减后归到num1中
        if (!TextUtils.isEmpty(num2)) {
            calculate();
        } else {
            // 还没处于计算中
            if (!isOperation) {
                // 设置当前符号
                currentOperation = operation;
                isOperation = true;
                if (currentOperation == OPERATION_ADD)
                    et_money.getText().append("+");
                else if (currentOperation == OPERATION_MINUS)
                    et_money.getText().append("-");
            } else {
                //正在计算中
            }
        }
    }

    @OnClick(R.id.btn_soft_kb_calculate)
    public void calculate() {
        double result = 0;
        switch (currentOperation) {
            case OPERATION_ADD:
                result = Double.valueOf(num1) + Double.valueOf(num2);
                break;
            case OPERATION_MINUS:
                result = Double.valueOf(num1) - Double.valueOf(num2);
                break;
        }
        num1 = String.valueOf(result);
        et_money.setText(num1);
    }

    @OnClick({R.id.btn_soft_kb_0, R.id.btn_soft_kb_1, R.id.btn_soft_kb_2, R.id.btn_soft_kb_3, R.id.btn_soft_kb_4, R.id.btn_soft_kb_5,
            R.id.btn_soft_kb_6, R.id.btn_soft_kb_7, R.id.btn_soft_kb_8, R.id.btn_soft_kb_9})
    public void softKeyboard(View view) {
        int lastInputKey = 0;
        switch (view.getId()) {
            case R.id.btn_soft_kb_0:
                lastInputKey = 0;
                break;
            case R.id.btn_soft_kb_1:
                lastInputKey = 1;
                break;
            case R.id.btn_soft_kb_2:
                lastInputKey = 2;
                break;
            case R.id.btn_soft_kb_3:
                lastInputKey = 3;
                break;
            case R.id.btn_soft_kb_4:
                lastInputKey = 4;
                break;
            case R.id.btn_soft_kb_5:
                lastInputKey = 5;
                break;
            case R.id.btn_soft_kb_6:
                lastInputKey = 6;
                break;
            case R.id.btn_soft_kb_7:
                lastInputKey = 7;
                break;
            case R.id.btn_soft_kb_8:
                lastInputKey = 8;
                break;
            case R.id.btn_soft_kb_9:
                lastInputKey = 9;
                break;
        }
        softKeyboardInputNum(lastInputKey);
    }

    public void setMoney(double costMoney) {
        et_money.setText(String.valueOf(costMoney));
    }

    public interface OnSoftKeyBoardListener {
        void onClickSubmitListener(double d);
    }
}
