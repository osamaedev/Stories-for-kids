package com.yoham.storieskids.ui.reading;

import android.os.Handler;
import android.util.Log;

import com.yoham.storieskids.R;
import com.yoham.storieskids.data.IDataManager;
import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.ui.base.BasePresenter;
import com.yoham.storieskids.utils.rx.ISchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

public class ReadingPresenter<V extends IReadingView> extends BasePresenter<V> implements IReadingPresenter<V> {

    private static final String TAG = "ReadingPresenter";

    private Story mStory = new Story();

    public ReadingPresenter(IDataManager dataManager,
                            ISchedulerProvider schedulerProvider,
                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onShowInterstitialAd() {
        getRootView().showInterstitialAd();
    }

    @Override
    public void onLoadStoryById(int storyId) {
        getRootView().showLoading();
        getCompositeDisposable().add(getDataManager()
                .getStoryById(storyId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(story -> {
                    mStory = story;
                    getRootView().setStoryTitle(story.getTitle());
                    getRootView().setStoryToolBarTitle(story.getTitle());
                    getRootView().setStoryBody(story.getBody());
                    getRootView().setStoryImage(story.getImage());
                    getRootView().hideLoading();
                    if (mStory.getIs_favorites() == 1) {
                        getRootView().setEnableFavouriteIcon(true);
                    }
                }, throwable -> {
                    getRootView().hideLoading();
                    getRootView().showMessage(R.string.general_error);
                }));
    }

    @Override
    public void onLoadIconFavorite() {
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            if (mStory.getIs_favorites() == 1) {
                getRootView().setEnableFavouriteIcon(true);
            }
        }, 500);
    }

    @Override
    public void onAddToFavourite() {
        if (mStory.getIs_favorites() == 0) {
            mStory.setIs_favorites(1);
            getRootView().setEnableFavouriteIcon(true);

            getCompositeDisposable().add(getDataManager()
            .setStoryAsFavourite(mStory.getId_story(), 1)
            .subscribe(aBoolean -> {
                if (aBoolean) {
                    getRootView().showMessage(R.string.added_to_favourites);
                }
            }, throwable -> {
                getRootView().showMessage(R.string.general_error);
                getRootView().setEnableFavouriteIcon(false);
            }));
        }
        else if (mStory.getIs_favorites() == 1) {
            mStory.setIs_favorites(0);
            getRootView().setEnableFavouriteIcon(false);
            getCompositeDisposable().add(getDataManager()
                    .setStoryAsFavourite(mStory.getId_story(), 0)
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            getRootView().showMessage(R.string.removed_from_favourites);
                        }
                    }, throwable -> {
                        getRootView().showMessage(R.string.general_error);
                        getRootView().setEnableFavouriteIcon(true);
                    }));
        }
    }
}
