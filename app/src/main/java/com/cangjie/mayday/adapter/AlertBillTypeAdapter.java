package com.cangjie.mayday.adapter;

import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cangjie.data.entity.BillType;
import com.cangjie.mayday.R;
import com.cangjie.mayday.ui.AlertBillTypeActivity;
import com.cangjie.mayday.ui.BillTypeDetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class AlertBillTypeAdapter extends RecyclerView.Adapter<AlertBillTypeAdapter.BillTypeViewHolder> {
    private List<BillType> mData;
    private AlertBillTypeActivity activity;
    private final LayoutInflater mLayoutInflater;

    public AlertBillTypeAdapter(AlertBillTypeActivity context, List<BillType> list){
        this.activity = context;
        this.mData = list;
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
                holder.tv_name.setBackgroundColor(activity.getResources().getColor(R.color.white));

            holder.tv_name.setText(element.getTypeName());
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, BillTypeDetailActivity.class);
                    intent.putExtra("type", element.getTypeName());
                    intent.putExtra("id", element.getId());
                    activity.startActivity(intent);
                }
            });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class BillTypeViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_name)
        TextView tv_name;
        public BillTypeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
