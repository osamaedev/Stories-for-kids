package com.yoham.storieskids.ui.storiesList.StoriesAdapter;

import android.graphics.Bitmap;

import com.yoham.storieskids.ui.base.IBaseView;

public interface IItemListView extends IBaseView {

    void setTitle(String title);

    void setDescription(String description);

    void setImage(Bitmap image);

    void setStoryId(int storyId);

}
