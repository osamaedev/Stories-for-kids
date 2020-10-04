package com.yoham.storieskids.ui.home;

import android.os.Handler;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;
import io.reactivex.disposables.CompositeDisposable;

public class HomePresenter<V extends IHomeView> extends BasePresenter<V> implements IHomePresenter<V> {

    public HomePresenter(IDataManager dataManager, ISchedulerProvider schedulerProvider, CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onOpenCategoriesActivity() {
        getRootView().openCategoriesActivity();
    }

    @Override
    public void onOpenShareDialog() {
        getRootView().showLoading();
        getRootView().showShareDialog();
        Handler handler = new Handler();
        handler.postDelayed(() -> getRootView().hideLoading(), 3000);
    }

    @Override
    public void onOpenRateUsDialog() {
        getRootView().openRateUsDialog();
    }

    @Override
    public void onOpenFavoritesActivity() {
        getRootView().openFavoritesActivity();
    }

    @Override
    public void onOpenReportContactDialog() {
        getRootView().openContactAndReportDialog();
    }

    @Override
    public void onOpenAboutUsDialog() {
        getRootView().openAboutDialog();
    }

    @Override
    public void onShowReadButtonAnimation() {
        getRootView().showReadButtonsAnimation();
    }

    @Override
    public void onShowShareButtonAnimation() {
        getRootView().showShareButtonsAnimation();
    }

    @Override
    public void onShowRateButtonAnimation() {
        getRootView().showRateButtonsAnimation();
    }

    @Override
    public void onSetAllVisible() {
        getRootView().setAllButtonsVisible();
    }

    @Override
    public void onExit() {
        getRootView().showRatingDialog();
    }

}
