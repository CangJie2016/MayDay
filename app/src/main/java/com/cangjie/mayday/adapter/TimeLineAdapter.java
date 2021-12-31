package com.cangjie.mayday.adapter;

import android.app.Activity;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anye.greendao.gen.BillTypeDao;
import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.MyApplication;
import com.cangjie.mayday.R;
import com.cangjie.mayday.domain.PerCost;
import com.cangjie.mayday.domain.TimeLineDayElement;
import com.cangjie.mayday.ui.AddBillActivity;
import com.cangjie.mayday.utils.RoundNumberUtils;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private List<TimeLineDayElement> mData;
    private Activity mActivity;
    private OnTimeLineItemClickListener mListener;
    private final LayoutInflater mLayoutInflater;
    private final List<BillType> mBillTypeList;

    public TimeLineAdapter(Activity activity, List<TimeLineDayElement> list, OnTimeLineItemClickListener listener){
        this.mActivity = activity;
        this.mListener = listener;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(activity);

        BillTypeDao mBillTypeDao = MyApplication.getInstances().getDaoSession().getBillTypeDao();
        mBillTypeList = mBillTypeDao.loadAll();
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_time_line,parent,false);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        final TimeLineDayElement element = mData.get(position);
        String monthDay = element.getDate().substring(4);
        holder.tv_date.setText(Integer.valueOf(monthDay.substring(0,2))+"月" + Integer.valueOf(monthDay.substring(2,4))+"日");
        BigDecimal sumMoney = new BigDecimal("0");
        for (final PerCost perCost : element.getCostList()){
            View view = mLayoutInflater.inflate(R.layout.item_time_line2,holder.ll_container,false);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, AddBillActivity.class);
                    intent.putExtra("perCost", perCost);
                    mActivity.startActivity(intent);
                }
            });
            TextView tv_type = (TextView) view.findViewById(R.id.tv_type);
            TextView tv_money = (TextView) view.findViewById(R.id.tv_money);
            String typeName = typeName(perCost.getCostType());
            tv_type.setText(typeName);
            String money = RoundNumberUtils.transformMoneyString(perCost.getCostMoney());
            tv_money.setText(mActivity.getResources().getString(R.string.format_yuan, money));
            sumMoney = sumMoney.add(new BigDecimal(String.valueOf(perCost.getCostMoney())));
            holder.ll_container.addView(view);

        }
        String sumMoneyStr = RoundNumberUtils.transformMoneyString(sumMoney.doubleValue());
        holder.tv_sum_money.setText(mActivity.getResources().getString(R.string.format_yuan, sumMoneyStr));
    }

    private String typeName(long billType) {
        for(BillType type : mBillTypeList){
            if (type.getId() == billType)
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
        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_sum_money)
        TextView tv_sum_money;
        @BindView(R.id.ll_container)
        LinearLayout ll_container;
        public TimeLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
