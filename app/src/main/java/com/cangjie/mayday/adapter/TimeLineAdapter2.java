package com.cangjie.mayday.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.data.entity.Bill;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;
import com.cangjie.mayday.ui.AddBillActivity;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class TimeLineAdapter2 extends RecyclerView.Adapter<TimeLineAdapter2.TimeLineViewHolder> {
    private List<Bill> mData;
    private Context mActivity;
    private OnTimeLineItemClickListener mListener;
    private final LayoutInflater mLayoutInflater;
    private final SimpleDateFormat mSimpleDateFormat;
    private final List<BillType> mBillTypeList;

    public TimeLineAdapter2(Activity activity, List<Bill> list, OnTimeLineItemClickListener listener){
        this.mActivity = activity;
        this.mListener = listener;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(mActivity);
        mSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

        BillTypeDao mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        mBillTypeList = mBillTypeDao.loadAll();
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_time_line2,parent,false);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        final Bill element = mData.get(position);

        holder.tv_date.setText(mSimpleDateFormat.format(element.getDate()));
        String typeName = typeName(element.getBillType());
        holder.tv_type.setText(typeName);
        holder.tv_money.setText(String.valueOf(element.getMoney()));
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, AddBillActivity.class);
                intent.putExtra("bill", element.getMoney());
                mActivity.startActivity(intent);
            }
        });
    }

    private String typeName(int billType) {
        for(BillType type : mBillTypeList){
            if (type.getTypeId() == billType)
                return type.getTypeName();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public interface OnTimeLineItemClickListener{

    }

    public class TimeLineViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_date)
        TextView tv_date;
        @Bind(R.id.tv_type)
        TextView tv_type;
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.ll_container)
        LinearLayout container;
        public TimeLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
