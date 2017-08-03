package com.cangjie.mayday.adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.R;
import com.cangjie.mayday.ui.AddBillActivity;
import com.cangjie.mayday.ui.AlertBillTypeActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class BillTypeAdapter extends RecyclerView.Adapter<BillTypeAdapter.BillTypeViewHolder> {
    private List<BillType> mData;
    private AddBillActivity mActivity;
    private final LayoutInflater mLayoutInflater;
    private long currentType;

    public BillTypeAdapter(AddBillActivity context, List<BillType> list){
        this.mActivity = context;
        this.mData = list;
        currentType = mData.get(0).getId();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BillTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_bill_type,parent,false);
        return new BillTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillTypeViewHolder holder, int position) {
        // 当前项是否为最后一项“自定义”
        if(position == mData.size() - 1){
            holder.tv_name.setBackgroundColor(mActivity.getResources().getColor(R.color.white));
            holder.tv_name.setText("自定义");
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.startActivity(new Intent(mActivity, AlertBillTypeActivity.class));
                }
            });
        }else{
            final BillType element = mData.get(position);
            if (element.getId() == currentType)
                holder.tv_name.setBackgroundColor(mActivity.getResources().getColor(R.color.theme_color_2));
            else
                holder.tv_name.setBackgroundColor(mActivity.getResources().getColor(R.color.white));

            holder.tv_name.setText(element.getTypeName());
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentType = element.getId();
                    notifyDataSetChanged();
                }
            });
        }
    }

    public long getCurrentTypeId(){
        return currentType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setCurrentCostType(long costType) {
        currentType = costType;
    }


    public class BillTypeViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.tv_name)
        TextView tv_name;
        public BillTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
