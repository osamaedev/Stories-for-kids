package com.yoham.storieskids.ui.splash;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface ISplashPresenter<V extends ISplashView> extends IBasePresenter<V> {

    void onStartSplashAnimation();

    void onStartHomeActivity();

    void onShowRateUsDecision();
}
