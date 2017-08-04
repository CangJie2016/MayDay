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
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.domain.TimeLineDayElement;
import com.cangjie.mayday.ui.AddBillActivity;
import com.cangjie.mayday.utils.RoundNumberUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.cangjie.mayday.R.id.tv_money;
import static com.cangjie.mayday.R.id.tv_sum_money;
import static com.cangjie.mayday.R.id.tv_type;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class PieDetailAdapter extends RecyclerView.Adapter<PieDetailAdapter.PieDetailViewHolder> {
    private final Context mContext;
    private List<Bill> mData;
    private final LayoutInflater mLayoutInflater;
    private final List<BillType> mBillTypeList;
    private final SimpleDateFormat mFormat;

    public PieDetailAdapter(Activity activity){
        this.mContext = activity;
        mLayoutInflater = LayoutInflater.from(activity);

        mFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);

        BillTypeDao mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        mBillTypeList = mBillTypeDao.loadAll();
    }

    @Override
    public PieDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_pie_detail,parent,false);
        return new PieDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PieDetailViewHolder holder, int position) {
        Bill bill = mData.get(position);
        Date date = bill.getDate();

        holder.tv_type.setText(mFormat.format(date));
        String money = RoundNumberUtils.transformMoneyString(bill.getMoney());
        holder.tv_money.setText(mContext.getResources().getString(R.string.format_yuan, money));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setData(List<Bill> bills) {
        mData = bills;
        notifyDataSetChanged();
    }

    public class PieDetailViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_money)
        TextView tv_money;
        @Bind(R.id.tv_type)
        TextView tv_type;
        @Bind(R.id.ll_container)
        LinearLayout ll_container;
        public PieDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
