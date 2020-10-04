package com.yoham.storieskids.ui.storiesList;

import android.os.Handler;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;

public class StoriesListPresenter<V extends IStoriesListView>
        extends BasePresenter<V> implements IStoriesListPresenter<V> {

    public StoriesListPresenter(IDataManager dataManager,
                                ISchedulerProvider schedulerProvider,
                                CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onInterstitialAdCalled() {
        getRootView().showInterstitialAd();
    }

    @Override
    public void onSetUpRecyclerView() {
        getRootView().showLoading();
        getRootView().setUpAndShowRecyclerView();
    }
}
