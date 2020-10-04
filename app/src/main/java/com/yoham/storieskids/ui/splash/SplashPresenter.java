package com.yoham.storieskids.ui.splash;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.AppConstants;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

import static com.yoham.storieskids.utils.AppConstants.DAY_UNTIL_SHOW_RATE;
import static com.yoham.storieskids.utils.AppConstants.LAUNCHES_UNTIL_SHOW_RATE;

public class SplashPresenter<V extends ISplashView> extends BasePresenter<V>
        implements ISplashPresenter<V> {



    public SplashPresenter(IDataManager dataManager,
                           ISchedulerProvider schedulerProvider,
                           CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onStartSplashAnimation() {
        getRootView().showSplashAnimation();
    }

    @Override
    public void onStartHomeActivity() {
        getRootView().openHomeActivity();
    }

    @Override
    public void onShowRateUsDecision() {
        long date_first_launch = getDataManager().getTimeUsingApp();
        int launches_count = getDataManager().getNumberOfLaunches();
        if (!getDataManager().getIsRated()) {
            getDataManager().setNumberOfLaunches(launches_count + 1);
            if (date_first_launch == AppConstants.NULL_INDEX) {
                getDataManager().setTimeUsingApp(System.currentTimeMillis());
                return;
            }
            if (System.currentTimeMillis() >=
                    (date_first_launch + (DAY_UNTIL_SHOW_RATE * 24 * 60 * 60 * 1000))
                    || launches_count >= LAUNCHES_UNTIL_SHOW_RATE) {
                AppConstants.SHOULD_START_RATE_US = true;
            }
        }
    }
}
