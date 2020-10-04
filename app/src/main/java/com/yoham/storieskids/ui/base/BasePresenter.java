package com.yoham.storieskids.ui.base;

import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {

    private static final String TAG = BasePresenter.class.getSimpleName();

    private IDataManager mDataManager;
    private ISchedulerProvider mSchedulerProvider;
    private CompositeDisposable mCompositeDisposable;

    private V mRootView;


    public BasePresenter(IDataManager dataManager,
                         ISchedulerProvider schedulerProvider,
                         CompositeDisposable compositeDisposable) {
        this.mCompositeDisposable = compositeDisposable;
        this.mSchedulerProvider = schedulerProvider;
        this.mDataManager = dataManager;
    }

    public V getRootView() {
        return mRootView;
    }

    protected IDataManager getDataManager() {
        return mDataManager;
    }

    public ISchedulerProvider getSchedulerProvider() {
        return mSchedulerProvider;
    }

    public CompositeDisposable getCompositeDisposable() {
        return mCompositeDisposable;
    }

    public boolean isViewAttached() {
        return mRootView != null;
    }

    @Override
    public void onAttach(V rootView) {
        this.mRootView = rootView;
    }

    @Override
    public void onDetach() {
        mCompositeDisposable.dispose();
        if (mRootView != null) {
            mRootView = null;
        }
    }

    @Override
    public void handleError(Error error) {
        // error handling
    }
}
