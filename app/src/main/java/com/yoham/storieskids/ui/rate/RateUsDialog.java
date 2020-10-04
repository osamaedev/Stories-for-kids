package com.yoham.storieskids.ui.rate;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseDialog;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yoham.storieskids.utils.AppUtils.openPlayStoreForRatingApp;

public class RateUsDialog extends BaseDialog implements IRateUsDialog {

    public static final String TAG = RateUsDialog.class.getSimpleName();

    private RateUsPresenter<IRateUsDialog> mPresenter;


    public static RateUsDialog newInstance() {
        RateUsDialog dialog = new RateUsDialog();
        Bundle bundle = new Bundle();
        dialog.setArguments(bundle);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rate_us_dialog, container, false);
        setUnBinder(ButterKnife.bind(this, view));

        setUpView();

        mPresenter = new RateUsPresenter<>(getBaseActivity().getDataManager(),
                getBaseActivity().getSchedulerProvider(),
                getBaseActivity().getCompositeDisposable());

        mPresenter.onAttach(this);

        return view;
    }

    @Override
    protected void setUpView() {

    }


    @OnClick(R.id.btn_submit)
    void onSubmitClick() {
        mPresenter.onSubmitClick();
        this.dismissDialog(TAG);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        mPresenter.onCancelClick();
        dismissDialog(TAG);
        getBaseActivity().finish();
    }

    @Override
    public void openRateUsOnGooglePlay() {
        openPlayStoreForRatingApp(getBaseActivity());
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(tag);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }
}
