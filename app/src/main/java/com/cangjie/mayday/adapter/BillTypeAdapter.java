package com.cangjie.mayday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.R;
import com.cangjie.mayday.domain.TimeLineDayElement;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class BillTypeAdapter extends RecyclerView.Adapter<BillTypeAdapter.BillTypeViewHolder> {
    private List<BillType> mData;
    private Context mContext;
    private final LayoutInflater mLayoutInflater;
    private int currentType;

    public BillTypeAdapter(Context context, List<BillType> list){
        this.mContext = context;
        this.mData = list;
        currentType = mData.get(0).getTypeId();
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public BillTypeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_bill_type,parent,false);
        return new BillTypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BillTypeViewHolder holder, int position) {
        final BillType element = mData.get(position);
        if (element.getTypeId() == currentType)
            holder.tv_name.setBackgroundColor(mContext.getResources().getColor(R.color.btn_orange_light));
        else
            holder.tv_name.setBackgroundColor(mContext.getResources().getColor(R.color.white));

        holder.tv_name.setText(element.getTypeName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentType = element.getTypeId();
                notifyDataSetChanged();
            }
        });
    }

    public int getCurrentTypeId(){
        return currentType;
    }

    @Override
    public int getItemCount() {
        return mData.size();
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
