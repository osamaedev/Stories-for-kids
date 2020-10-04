package com.yoham.storieskids.ui.contact;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

import static com.yoham.storieskids.utils.CommonUtils.sendEmailContactReport;

public class ContactReportPresenter<V extends IContactReportView> extends BasePresenter<V> implements IContactReportPresenter<V>{




    public ContactReportPresenter(IDataManager dataManager,
                                  ISchedulerProvider schedulerProvider,
                                  CompositeDisposable compositeDisposable) {

        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSendReportOrContact(String name, String email, String contactText) {
        getRootView().showEmailView(sendEmailContactReport(name, email, contactText));
    }
}
