package com.yoham.storieskids.ui.rate;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.AppConstants;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class RateUsPresenter<V extends IRateUsDialog> extends BasePresenter<V> implements  IRateUsPresenter<V>{



    public RateUsPresenter(IDataManager dataManager,
                           ISchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onSubmitClick() {
        getDataManager().setIsRated(true);
        AppConstants.SHOULD_START_RATE_US = false;
        getRootView().openRateUsOnGooglePlay();
    }

    @Override
    public void onCancelClick() {
        // Collect other information
    }
}
