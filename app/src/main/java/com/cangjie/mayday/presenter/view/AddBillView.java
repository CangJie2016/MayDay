package com.cangjie.mayday.presenter.view;

import com.cangjie.basetool.mvp.BaseView;
import com.cangjie.mayday.adapter.BillTypeAdapter;

public interface AddBillView extends BaseView {
    void setBillTypeAdapter(BillTypeAdapter billTypeAdapter);

    void addBillSuccess();
}
