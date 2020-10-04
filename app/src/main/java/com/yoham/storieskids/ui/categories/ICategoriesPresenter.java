package com.yoham.storieskids.ui.categories;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface ICategoriesPresenter<V extends ICategoriesView> extends IBasePresenter<V> {

    void onShowAnbiaaButtonAnimation();

    void onShowJohaButtonAnimation();

    void onShowAnimalsButtonAnimation();

    void onShowGeneralButtonAnimation();

    void onShowAllButtons();

}
