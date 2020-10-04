package com.yoham.storieskids.ui.base;

public interface IBasePresenter<V extends IBaseView> {

    void onAttach(V rootView);

    void onDetach();

    void handleError(Error error);

}
