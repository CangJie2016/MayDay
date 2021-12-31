package com.cangjie.mayday.adapter;

/**
 * Created by 李振强 on 2017/5/20.
 */

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 与数据源相关的字段和方法封装在父类中
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected List<T> dataSet = new ArrayList<>();


    public void updateData(List dataSet) {
        this.dataSet.clear();
        appendData(dataSet);
    }

    public void clearData(){
        this.dataSet.clear();
    }

    public void appendData(List dataSet) {
        if (dataSet != null && !dataSet.isEmpty()) {
            this.dataSet.addAll(dataSet);
            notifyDataSetChanged();
        }
    }

    public List<T> getDataSet() {
        return dataSet;
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
}