package com.yoham.storieskids.ui.rate;

import com.yoham.storieskids.ui.base.IBasePresenter;

public interface IRateUsPresenter<V extends IRateUsDialog> extends IBasePresenter<V> {

    void onSubmitClick();

    void onCancelClick();

}
