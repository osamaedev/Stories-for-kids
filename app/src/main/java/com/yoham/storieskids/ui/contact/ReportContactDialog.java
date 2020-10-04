package com.yoham.storieskids.ui.contact;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseDialog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yoham.storieskids.utils.CommonUtils.isEmailValid;

public class ReportContactDialog extends BaseDialog implements IContactReportView {

    public static final String TAG = "ReportContactDialog";

    ContactReportPresenter<IContactReportView> mPresenter;

    //region bind views

    @BindView(R.id.user_name)
    AppCompatEditText mUserName;

    @BindView(R.id.user_email)
    AppCompatEditText mUserEmail;

    @BindView(R.id.contact_message)
    AppCompatEditText mMessage;

    //endregion

    public static ReportContactDialog newInstance() {
        ReportContactDialog fragment = new ReportContactDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.contact_report_dialog, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        mPresenter = new ContactReportPresenter<>(getBaseActivity().getDataManager(),
                getBaseActivity().getSchedulerProvider(), getBaseActivity().getCompositeDisposable());

        mPresenter.onAttach(this);

        return view;
    }

    //region Click events
    @OnClick(R.id.send_button)
    void onSendButtonClick() {
        if (mUserName.getText().toString().equals("")) {
            showMessage(R.string.enter_name);
            return;
        }
        else if (mUserEmail.getText().toString().equals("")) {
            showMessage(R.string.enter_email);
            return;
        }
        else if (mMessage.getText().toString().equals("")) {
            showMessage(R.string.enter_message);
            return;
        }
        if (!isEmailValid(mUserEmail.getText().toString())) {
            showMessage("بريد إلكتروني غير صحيح");
        }
        else {
            hideKeyboard();
            mPresenter.onSendReportOrContact(mUserName.getText().toString(),
                    mUserEmail.getText().toString(),
                    mMessage.getText().toString());
        }
    }

    @OnClick(R.id.cancel_button)
    void onCancelButtonClick() {
        dismissDialog(TAG);
    }
    //endregion

    @Override
    protected void setUpView() {

    }

    @Override
    public void showEmailView(Intent intent) {
        try {
            if (!isNetworkConnected()) {
                showMessage(R.string.no_internet);
                return;
            }
            startActivity(Intent.createChooser(intent, "إرسال بريد إلكتروني..."));
            dismissDialog(TAG);
        }
        catch (ActivityNotFoundException e) {
            showMessage(R.string.general_error);
        }
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
