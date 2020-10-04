package com.yoham.storieskids.ui.contact;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface IContactReportPresenter<V extends IContactReportView> extends IBasePresenter<V> {

    void onSendReportOrContact(String name, String email, String contactText);

}
