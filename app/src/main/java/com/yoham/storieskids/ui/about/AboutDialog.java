package com.yoham.storieskids.ui.about;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yoham.storieskids.BuildConfig;
import com.yoham.storieskids.R;
import com.yoham.storieskids.ui.base.BaseDialog;
import com.yoham.storieskids.ui.contact.ContactReportPresenter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AboutDialog extends BaseDialog {

    public static final String TAG = AboutDialog.class.getSimpleName();

    //region Bind views
    @BindView(R.id.about_email)
    AppCompatTextView mEmail;

    @BindView(R.id.about_app_version)
    AppCompatTextView mAppVersion;
    //endregion

    public static AboutDialog newInstance() {
        AboutDialog fragment = new AboutDialog();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_dialog, container, false);

        setUnBinder(ButterKnife.bind(this, view));

        setUpView();

        return view;
    }


    @Override
    protected void setUpView() {
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        mAppVersion.setText(version);
        mEmail.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void dismissDialog(String tag) {
        super.dismissDialog(tag);
    }
}
