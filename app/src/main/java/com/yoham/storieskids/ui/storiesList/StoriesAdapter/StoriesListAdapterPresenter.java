package com.yoham.storieskids.ui.storiesList.StoriesAdapter;

import android.os.Handler;
import android.util.Log;

import com.yoham.storieskids.R;
import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.ui.storiesList.IStoriesListView;
import com.yoham.storieskids.ui.storiesList.StoriesListPresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class StoriesListAdapterPresenter<V extends IStoriesListView> extends BasePresenter<V>
        implements IStoriesListAdapterPresenter<V> {

    private static final String TAG = "StoriesLAPresenter";

    private List<Story> mListStories = new ArrayList<Story>();



    public StoriesListAdapterPresenter(IDataManager dataManager,
                                       ISchedulerProvider schedulerProvider,
                                       CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onBindRepositoryRowViewAtPosition(int position, IItemListView rowView) {

        if (mListStories.size() == 0)
            return;

        Story story = mListStories.get(position);
        rowView.setTitle(story.getTitle());
        rowView.setDescription(story.getBody().substring(0, Math.min(story.getBody().length(), 100)) + "...");
        rowView.setImage(story.getImage());
        rowView.setStoryId(story.getId_story());
    }

    @Override
    public int onGetItemCount() {
        return mListStories.size();
    }


    @Override
    public void getAnimalsStories() {
        getDataManager()
                .getAllAnimalsStories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        mListStories = stories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        getRootView().showMessage(R.string.general_error);
                        getRootView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        // Call notify data change
                        Log.d(TAG, "On Complete Called !!!");
                        getRootView().updateRecyclerView();
                        getRootView().hideLoading();
                    }
                });
    }

    @Override
    public void getAnbiaaStories() {
        getDataManager()
                .getAllAnbiaaStories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        mListStories = stories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error in loading stories");
                        Log.d(TAG, e.getMessage());
                        getRootView().showMessage(R.string.general_error);
                        getRootView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Called !!!");
                        getRootView().updateRecyclerView();
                        getRootView().hideLoading();
                    }
                });
    }

    @Override
    public void getJohaStories() {
//        getDataManager()
//                .getAllJohaStories()
//                .subscribeOn(getSchedulerProvider().io())
//                .observeOn(getSchedulerProvider().ui())
//                .subscribe(new Observer<List<Story>>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        getCompositeDisposable().add(d);
//                    }
//
//                    @Override
//                    public void onNext(List<Story> stories) {
//                        mListStories = stories;
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "Error in loading stories");
//                        Log.d(TAG, e.getMessage());
//                        getRootView().showMessage(R.string.general_error);
//                        getRootView().hideLoading();
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "On Complete Called !!!");
//                        getRootView().updateRecyclerView();
//                        getRootView().hideLoading();
//                    }
//                });
    }

    @Override
    public void getGeneralStories() {
        getDataManager()
                .getAllGeneralStories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        mListStories = stories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error in loading stories");
                        Log.d(TAG, e.getMessage());
                        getRootView().showMessage(R.string.general_error);
                        getRootView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Called !!!");
                        getRootView().updateRecyclerView();
                        getRootView().hideLoading();
                    }
                });
    }

    @Override
    public void getFavouritesStories() {
        getDataManager()
                .getAllFavouritesStories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(new Observer<List<Story>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        getCompositeDisposable().add(d);
                    }

                    @Override
                    public void onNext(List<Story> stories) {
                        mListStories = stories;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Error in loading stories");
                        Log.d(TAG, e.getMessage());
                        getRootView().showMessage(R.string.general_error);
                        getRootView().hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "On Complete Called !!!");
                        getRootView().updateRecyclerView();
                        getRootView().hideLoading();
                    }
                });
    }
}
