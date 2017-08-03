package com.cangjie.mayday.ui;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.ViewGroup;

import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.basetool.view.recycle_view.CreateRecyclerView;
import com.cangjie.mayday.R;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.cangjie.mayday.MyApplication.mContext;

public class PieTestActivity extends DemoBase implements RecyclerArrayAdapter.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.rv)
    EasyRecyclerView mRecyclerView;

    private RecyclerArrayAdapter arrayAdapter;

    private WorkLoadVerifyViewHolder.OnDragDeleteListener listener = new WorkLoadVerifyViewHolder.OnDragDeleteListener()

    {
        @Override
        public void deleteObj(String str) {
            DebugLog.w("delete " + str);
            arrayAdapter.remove(str);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_test);
        ButterKnife.bind(this);
        arrayAdapter = new RecyclerArrayAdapter(mContext) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new WorkLoadVerifyViewHolder(parent,listener);
            }
        };
        arrayAdapter.addAll(getData("index"));
        new CreateRecyclerView().CreateRecyclerView(mContext, mRecyclerView, arrayAdapter, this);
        mRecyclerView.setRefreshListener(this);
    }


    public List<String> getData(String flag) {

        int idx = 1;
        if (arrayAdapter != null) {
            idx = arrayAdapter.getItemCount();
        }
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add(flag + ":" + (idx + i));
        }
        return list;
    }

    @Override
    public void onLoadMore() {
        DebugLog.w("load more");
        arrayAdapter.addAll(getData("index"));
    }

    @Override
    public void onRefresh() {
        arrayAdapter.clear();
        arrayAdapter.addAll(getData("index"));
    }

    public void data(){
        Line line = new Line("P1-1","0700","0930","150","市政府","教育局幼儿园", 10.5, 1 , 0);
        Line line2 = new Line("P1-2","1500","1730","150","教育局幼儿园","市政府", 8, 1 , 0);
        Line line3 = new Line("P18-1","0700","0930","150","环卫局","ABC广场", 0, 2 , 33);
    }
    public class Line{
        private String lineName;
        private String startTime;
        private String endTime;
        private String runTime; //单位分钟
        private String startStationName;
        private String endStationName;
        private double price;
        private int type;// 1 可购买  2众筹
        private int applyNum;// 众筹数量

        public Line(String lineName, String startTime, String endTime, String runTime, String startStationName, String endStationName, double price, int type, int applyNum) {
            this.lineName = lineName;
            this.startTime = startTime;
            this.endTime = endTime;
            this.runTime = runTime;
            this.startStationName = startStationName;
            this.endStationName = endStationName;
            this.price = price;
            this.type = type;
            this.applyNum = applyNum;
        }

        public String getLineName() {
            return lineName;
        }

        public void setLineName(String lineName) {
            this.lineName = lineName;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getRunTime() {
            return runTime;
        }

        public void setRunTime(String runTime) {
            this.runTime = runTime;
        }

        public String getStartStationName() {
            return startStationName;
        }

        public void setStartStationName(String startStationName) {
            this.startStationName = startStationName;
        }

        public String getEndStationName() {
            return endStationName;
        }

        public void setEndStationName(String endStationName) {
            this.endStationName = endStationName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getApplyNum() {
            return applyNum;
        }

        public void setApplyNum(int applyNum) {
            this.applyNum = applyNum;
        }
    }
}
