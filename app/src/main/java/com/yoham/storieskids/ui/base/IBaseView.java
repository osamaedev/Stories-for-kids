package com.yoham.storieskids.ui.base;

import androidx.annotation.StringRes;

public interface IBaseView {

    void showLoading();

    void hideLoading();

    void showMessage(@StringRes int resId);

    void showMessage(String message);

    boolean isNetworkConnected();

    void hideKeyboard();
}
