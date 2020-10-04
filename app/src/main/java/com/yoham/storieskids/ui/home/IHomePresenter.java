package com.yoham.storieskids.ui.home;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface IHomePresenter<V extends IHomeView> extends IBasePresenter<V> {

    void onOpenCategoriesActivity();

    void onOpenShareDialog();

    void onOpenRateUsDialog();

    void onOpenFavoritesActivity();

    void onOpenReportContactDialog();

    void onOpenAboutUsDialog();

    void onShowReadButtonAnimation();

    void onShowShareButtonAnimation();

    void onShowRateButtonAnimation();

    void onSetAllVisible();

    void onExit();

}
