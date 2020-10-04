package com.yoham.storieskids.ui.storiesList;

import com.yoham.storieskids.data.db.model.Story;
import com.yoham.storieskids.ui.base.IBasePresenter;

import java.util.List;

public interface IStoriesListPresenter<V extends IStoriesListView> extends IBasePresenter<V> {

    void onInterstitialAdCalled();

    void onSetUpRecyclerView();
}
