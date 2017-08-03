package com.cangjie.mayday.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cangjie.basetool.mvp.base.PresenterFragment;
import com.cangjie.basetool.utils.DebugLog;
import com.cangjie.basetool.view.recycle_view.DividerItemDecoration;
import com.cangjie.data.entity.Bill;
import com.cangjie.mayday.R;
import com.cangjie.mayday.adapter.PieDetailAdapter;
import com.cangjie.mayday.domain.BillByPieChart;
import com.cangjie.mayday.presenter.ChartPresenter;
import com.cangjie.mayday.presenter.view.ChartView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.cangjie.mayday.ui.TimeLineFragment.GOAL_ACTION;
import static com.cangjie.mayday.ui.TimeLineFragment.TIMELINE_ACTION;

/**
 * Created by 李振强 on 2017/5/26.
 */

public class ChartFragment extends PresenterFragment<ChartPresenter> implements ChartView, OnChartValueSelectedListener {
    private View rootView;
    Context mContext;

    @Bind(R.id.pie_chart)
    PieChart mChart;
    @Bind(R.id.rv_pie_detail)
    RecyclerView rv_pie_detail;
    @Bind(R.id.ll_pie_detail_container)
    LinearLayout ll_pie_detail_container;
    @Bind(R.id.tv_pie_detail_tips)
    TextView tv_pie_detail_tips;
    @Bind(R.id.tv_date)
    TextView tv_type;
    @Bind(R.id.tv_sum_money)
    TextView tv_sum_money;

    @Bind(R.id.tv_current_month)
    TextView tv_current_month;


    private BroadcastReceiver refreshReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.refreshData();
        }
    };
    private PieDetailAdapter mPieDetailAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
            //返回原有的布局对象  不往下走了
            return rootView;
        }
        mContext = getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_chart, container, false);
        rootView = setContentView(rootView);
        ButterKnife.bind(this, rootView);
        showTitle("图表");
        initChartView();
        initRecycleView();

        mPresenter.initDate();
        initRefreshBroadcast();
        return rootView;
    }



    private void initRecycleView() {
        rv_pie_detail.setLayoutManager(new LinearLayoutManager(mContext));
        rv_pie_detail.addItemDecoration(new DividerItemDecoration(mContext, R.color.white,
                DividerItemDecoration.VERTICAL_LIST));
    }

    private void initRefreshBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(TIMELINE_ACTION);
        intentFilter.addAction(GOAL_ACTION);
        getActivity().registerReceiver(refreshReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().unregisterReceiver(refreshReceiver);
        refreshReceiver = null;
    }

    private void initChartView() {
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

//        mChart.setCenterTextTypeface(mTfLight);

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(38f);
        mChart.setTransparentCircleRadius(41f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
//        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }


    @Override
    protected ChartPresenter createPresenter() {
        return new ChartPresenter(this);
    }

    @Override
    public void disPlay(String s) {

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
        mPresenter.findMapElementByIndex((int)h.getX());
//        showChartData();
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
//        emptyChartData();
    }

    private void emptyChartData() {
        ll_pie_detail_container.setVisibility(View.INVISIBLE);
        mChart.setVisibility(View.INVISIBLE);
        tv_pie_detail_tips.setVisibility(View.VISIBLE);
    }

    private void showChartData() {
        ll_pie_detail_container.setVisibility(View.VISIBLE);
        mChart.setVisibility(View.VISIBLE);
        tv_pie_detail_tips.setVisibility(View.INVISIBLE);
    }
    @Override
    public void setPieData(List<BillByPieChart> billList) {

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (BillByPieChart bill : billList) {
            entries.add(new PieEntry((float) bill.getSumCost(),
                    bill.getBillTypeName(),
                    null));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
//        data.setValueTypeface(mTfLight);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    @Override
    public void setPieDetailList(String typeName, List<Bill> bills) {
        if (bills == null || bills.size() == 0)
            return;
        showChartData();
        tv_type.setText(typeName);
        double sumCost = 0;
        for (Bill bill : bills)
            sumCost += bill.getMoney();
        tv_sum_money.setText(getResources().getString(R.string.format_yuan, String.valueOf(sumCost)));
        DebugLog.w("setPieDetailList size" + bills.size());
        if (mPieDetailAdapter == null)
            mPieDetailAdapter = new PieDetailAdapter(getActivity());
        mPieDetailAdapter.setData(bills);
        rv_pie_detail.setAdapter(mPieDetailAdapter);
    }

    @Override
    public void setCurrentMonth(int currentYear, int currentMonth) {
        tv_current_month.setText(getResources().getString(R.string.format_year_month, currentYear, currentMonth));
    }

    @Override
    public void emptyPieDetailList() {
        emptyChartData();
    }

    @OnClick(R.id.btn_next_month)
    public void nextMonth(){
        mPresenter.nextMonth();
    }
    @OnClick(R.id.btn_last_month)
    public void lastMonth(){
        mPresenter.lastMonth();
    }
    @OnClick(R.id.tv_pie_detail_tips)
    public void goAddBillPage(){
        Intent intent = new Intent(mContext, AddBillActivity.class);
        startActivity(intent);
    }
}
