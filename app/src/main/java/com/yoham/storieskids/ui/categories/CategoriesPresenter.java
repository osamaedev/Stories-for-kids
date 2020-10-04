package com.yoham.storieskids.ui.categories;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class CategoriesPresenter<V extends ICategoriesView> extends BasePresenter<V>
        implements ICategoriesPresenter<V> {


    public CategoriesPresenter(IDataManager dataManager,
                               ISchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }


    @Override
    public void onShowAnbiaaButtonAnimation() {
        getRootView().showAnbiaaButtonAnimation();
    }

    @Override
    public void onShowJohaButtonAnimation() {
        getRootView().showJohaButtonAnimation();
    }

    @Override
    public void onShowAnimalsButtonAnimation() {
        getRootView().showAnimalsButtonAnimation();
    }

    @Override
    public void onShowGeneralButtonAnimation() {
        getRootView().showGeneralButtonAnimation();
    }

    @Override
    public void onShowAllButtons() {
        getRootView().setAllButtonsVisible();
    }
}
