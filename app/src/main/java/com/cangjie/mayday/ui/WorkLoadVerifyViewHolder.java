package com.cangjie.mayday.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cangjie.mayday.R;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

/**
 * Created by wuqiusen on 2017/6/16.
 */

public class WorkLoadVerifyViewHolder extends BaseViewHolder<String> {
    TextView tvNo;
    Button btnDelete;
    OnDragDeleteListener mListener;
    
    public WorkLoadVerifyViewHolder(ViewGroup parent, OnDragDeleteListener listener) {
        super(parent, R.layout.test_item);
        tvNo = $(R.id.tv_no);
        btnDelete = $(R.id.btnDelete);
        this.mListener = listener;
    }

    @Override
    public void setData(final String data) {
        super.setData(data);
        tvNo.setText(data);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteObj(data);
            }
        });
    }

    public interface OnDragDeleteListener{
        void deleteObj(String str);
    }

}
