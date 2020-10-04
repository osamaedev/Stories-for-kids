package com.yoham.storieskids.ui.reading;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface IReadingPresenter<V extends IReadingView> extends IBasePresenter<V> {

    void onShowInterstitialAd();

    void onLoadStoryById(int storyId);

    void onAddToFavourite();

    void onLoadIconFavorite();

}
