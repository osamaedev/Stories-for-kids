package com.yoham.storieskids.ui.home;

import com.yoham.storieskids.ui.base.IBaseView;

public interface IHomeView extends IBaseView {


    void openCategoriesActivity();

    void showShareDialog();

    void openRateUsDialog();


    void openFavoritesActivity();

    void openContactAndReportDialog();

    void openAboutDialog();

    void showReadButtonsAnimation();

    void showShareButtonsAnimation();

    void showRateButtonsAnimation();

    void updateVersion();

    void setAllButtonsVisible();

    void showRatingDialog();
}
