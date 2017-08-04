package com.cangjie.mayday.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cangjie.data.entity.BillType;
import com.cangjie.data.entity.Password;
import com.cangjie.mayday.R;
import com.cangjie.mayday.ui.AlertBillTypeActivity;
import com.cangjie.mayday.ui.BillTypeDetailActivity;
import com.cangjie.mayday.ui.PasswordDetailActivity;
import com.cangjie.mayday.ui.PasswordStoreActivity;

import java.io.Serializable;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by 李振强 on 2017/5/27.
 */

public class PasswordStoreAdapter extends RecyclerView.Adapter<PasswordStoreAdapter.PasswordStoreViewHolder> {
    private List<Password> mData;
    private Activity activity;
    private final LayoutInflater mLayoutInflater;

    public PasswordStoreAdapter(Activity context, List<Password> list){
        this.activity = context;
        this.mData = list;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public PasswordStoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_password_store,parent,false);
        return new PasswordStoreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PasswordStoreViewHolder holder, int position) {
        final Password password = mData.get(position);
        holder.tv_title.setText(password.getTitle());
        holder.tv_username.setText(password.getUsername());
        holder.tv_password.setText(password.getPassword());
        if (TextUtils.isEmpty(password.getRemarks())){
            holder.ll_remarks_container.setVisibility(View.GONE);
        }else{
            holder.ll_remarks_container.setVisibility(View.VISIBLE);
            holder.tv_remarks.setText(password.getRemarks());
        }
        holder.ll_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, PasswordDetailActivity.class);
                intent.putExtra("id", password.getId());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PasswordStoreViewHolder extends RecyclerView.ViewHolder{
        @Bind(R.id.ll_container)
        LinearLayout ll_container;
        @Bind(R.id.tv_title)
        TextView tv_title;
        @Bind(R.id.tv_username)
        TextView tv_username;
        @Bind(R.id.tv_password)
        TextView tv_password;
        @Bind(R.id.ll_remarks_container)
        LinearLayout ll_remarks_container;
        @Bind(R.id.tv_remarks)
        TextView tv_remarks;
        public PasswordStoreViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
