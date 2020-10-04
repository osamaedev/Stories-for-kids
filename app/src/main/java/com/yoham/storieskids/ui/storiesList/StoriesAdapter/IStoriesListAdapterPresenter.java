package com.yoham.storieskids.ui.storiesList.StoriesAdapter;

import com.yoham.storieskids.ui.base.IBasePresenter;
import com.yoham.storieskids.ui.base.IBaseView;
import com.yoham.storieskids.ui.storiesList.IStoriesListView;

public interface IStoriesListAdapterPresenter<V extends IStoriesListView> extends IBasePresenter<V> {

    void getAnimalsStories();

    void getAnbiaaStories();

    void getJohaStories();

    void getGeneralStories();

    void getFavouritesStories();

    void onBindRepositoryRowViewAtPosition(int position, IItemListView rowView);

    int onGetItemCount();

}
