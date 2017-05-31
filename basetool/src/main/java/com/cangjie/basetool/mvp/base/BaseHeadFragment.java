package com.cangjie.basetool.mvp.base;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cangjie.basetool.R;
import com.cangjie.basetool.utils.AnimationHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseHeadFragment extends BaseFragment {

	public View baseView;

    private RelativeLayout rel_contentArea;
    private Button btn_backButton;
    private RelativeLayout rel_mLoading;
    private RelativeLayout rel_headArea;
    private Button btn_headRightButton;
    private ImageButton ibtn_headRightImageButton;
    private TextView btn_headTitle;


    public Button getHeadRightButton() {
        return btn_headRightButton;
    }


    public BaseHeadFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		super.onCreateView(inflater,container,savedInstanceState);
        baseView = inflater.inflate(R.layout.base_head, container, false);
        assignHeadViews(baseView);
        return baseView;
    }

    public View setContentView(View view) {
        rel_contentArea.addView(view);
        return baseView;
    }
    public void hideHeadArea(){
        rel_headArea.setVisibility(View.GONE);
    }
    public void showLoading() {
        rel_mLoading.setVisibility(View.VISIBLE);
    }

    public void hideLoading() {
        AnimationHelper.crossfade(rel_mLoading, rel_contentArea, getResources().getInteger(android.R.integer.config_shortAnimTime));
    }
    public void showBackButton(OnClickListener listener) {

        btn_backButton.setOnClickListener(listener);
        btn_backButton.setVisibility(View.VISIBLE);
    }

    public void showRightImageButton(int imageResource, OnClickListener listener) {
        ibtn_headRightImageButton.setImageResource(imageResource);
        ibtn_headRightImageButton.setOnClickListener(listener);
        ibtn_headRightImageButton.setVisibility(View.VISIBLE);
    }

    public void showTitle(String title) {
        btn_headTitle.setText(title);
        btn_headTitle.setVisibility(View.VISIBLE);
    }

    public void showHeadRightButton(String text, OnClickListener listener) {
        btn_headRightButton.setText(text);
        btn_headRightButton.setOnClickListener(listener);
        btn_headRightButton.setVisibility(View.VISIBLE);
    }


    private void assignHeadViews(View view) {
        rel_contentArea = (RelativeLayout) view.findViewById(R.id.rel_base_contentArea);
        btn_backButton = (Button) view.findViewById(R.id.btn_base_head_back);
        btn_headTitle = (TextView) view.findViewById(R.id.tv_base_head_title);
        btn_headRightButton = (Button) view.findViewById(R.id.btn_base_head_right_button);
        ibtn_headRightImageButton = (ImageButton) view.findViewById(R.id.btn_base_head_right_imgbutton);
        rel_mLoading = (RelativeLayout) view.findViewById(R.id.rel_base_loading);
        rel_headArea = (RelativeLayout) view.findViewById(R.id.rel_base_headArea);
        btn_headTitle.setVisibility(View.GONE);
        btn_headRightButton.setVisibility(View.GONE);

    }
}
