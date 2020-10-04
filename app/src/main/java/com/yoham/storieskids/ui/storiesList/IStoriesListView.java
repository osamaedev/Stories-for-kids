package com.yoham.storieskids.ui.storiesList;

import com.yoham.storieskids.ui.base.IBaseView;

public interface IStoriesListView extends IBaseView {

    void showInterstitialAd();

    void setUpAndShowRecyclerView();

    void updateRecyclerView();

}
