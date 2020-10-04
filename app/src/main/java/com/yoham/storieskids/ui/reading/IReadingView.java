package com.yoham.storieskids.ui.reading;

import android.graphics.Bitmap;

import com.yoham.storieskids.ui.base.IBaseView;

public interface IReadingView extends IBaseView {

    void showInterstitialAd();

    void setStoryTitle(String storyTitle);

    void setStoryBody(String storyBody);

    void setStoryImage(Bitmap image);

    void setStoryToolBarTitle(String toolBarTitle);

    void setEnableFavouriteIcon(boolean state);

}
