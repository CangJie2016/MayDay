package com.cangjie.mayday.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cangjie.mayday.R;
import com.cangjie.mayday.domain.TimeLineDayElement;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private List<TimeLineDayElement> mData;
    private Context mContext;
    private OnTimeLineItemClickListener mListener;
    private final LayoutInflater mLayoutInflater;

    public TimeLineAdapter(Context context, List<TimeLineDayElement> list, OnTimeLineItemClickListener listener){
        this.mContext = context;
        this.mListener = listener;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_time_line,parent,false);
        return new TimeLineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        TimeLineDayElement element = mData.get(position);
        holder.tv_date.setText(element.getDate());
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
        @Bind(R.id.ll_container)
        LinearLayout ll_container;
        public TimeLineViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
